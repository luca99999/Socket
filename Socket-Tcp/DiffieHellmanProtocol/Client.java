/*
 * Copyright (c) 2019 - Luca Bernardini
 * 
 *  This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.Socket;
import javax.crypto.spec.SecretKeySpec;

/*
 * (Client)  State 0
 * Alice -----------------p, g, PubAlice (Alice's public key) -------------->
 *  * 																	State 1
 * 		<---------- Encrypted Aes message, PubBob (Bob's public key) -------- Bob																
 *           State 2
 *       --------------------- Encrypted Aes message ------------------------>
 */



public class Client {
	
 
  public static void main(String args[]) throws Exception {
    Socket toServerCoonnection;
    int porta = 5050;
    byte[] encryptedMessage;
    BigInteger p, g, privateKey;
    BigInteger PubAlice, PubBob = null;
    Protocol protocollo;
    BigInteger chiaveAlice = null;
    SecretKeySpec chiaveAes = null;
    String decrypted;
    BufferedReader keyboard = null;
    String response;
    byte[] encrypted;
    
      
    DiffieHellman.DHkeys();
    // g public prime generator
	// p public prime modulus
    p = DiffieHellman.getP();
    g = DiffieHellman.getG();
    // private key of Alice
	privateKey = DiffieHellman.getPrivateKey();
	// PubAlice public key of Alice
    PubAlice = g.modPow(privateKey, p);
    
   
    toServerCoonnection = new Socket(InetAddress.getLocalHost(), porta);
    ObjectOutputStream oos = new ObjectOutputStream(toServerCoonnection.getOutputStream());
    ObjectInputStream ois = new ObjectInputStream(toServerCoonnection.getInputStream());
   
    
    protocollo = new Protocol(p, g);
    protocollo.setPubKey(PubAlice);
    // 0 = it starts key agreement (state 0), it sends p,g and public key
    // that have to be public.
    protocollo.setType(0);

    // serialize the object in the stream
    oos.writeObject(protocollo);
    oos.flush();
   
   
    // it waits for pub key of Bob and encrypted message
    protocollo = (Protocol) ois.readObject(); 
    int tipo = protocollo.getType();
    // tipo == 1 means response of server with its public key and encrypted message (state 1)
    if(tipo == 1)
    {
    	PubBob = protocollo.getPubKey();
    	// Alice calculate the agreement key used to obtain a common Aes key
       	chiaveAlice = PubBob.modPow(privateKey,  p);
       	
       	System.out.println("Chiave Alice " + chiaveAlice.toString(16));
       	// calculate Aes key
    	chiaveAes = Aes.getAesKey(chiaveAlice.toString());
      
    	// decrypts the message and prints it
    	// sends encrpypted message to Server
    	encryptedMessage = protocollo.getEncryptedMessage();
    	decrypted = Aes.decryptAes(encryptedMessage, chiaveAes);
    	System.out.println("Client: " + decrypted);
        System.out.println();
    	keyboard = new BufferedReader(new InputStreamReader(System.in)); 
    	response = keyboard.readLine();
    	encrypted = Aes.encryptAes(response, chiaveAes);
        // set state 2
    	protocollo.setType(2);
    	protocollo.setEncryptedMessage(encrypted);
    	oos.writeObject(protocollo);
    	oos.flush();
    
    }
    else
    	System.exit(1);
    
    // secure encrypted channel tipo == 2 (state 2)
    
    while(true)
    {
    	protocollo = (Protocol) ois.readObject(); 
        tipo = protocollo.getType();
        // tipo == 2 means response of server with encrypted message (state 1)
        if(tipo == 2)
        {
        	encryptedMessage = protocollo.getEncryptedMessage();
            decrypted = Aes.decryptAes(encryptedMessage, chiaveAes);            
            System.out.println("Client: " + decrypted);
            System.out.println("Type [Date | Time | (Exit) for quitting]");
            response = keyboard.readLine();
                      
            encrypted = Aes.encryptAes(response, chiaveAes);
            
            protocollo.setType(2);
            protocollo.setEncryptedMessage(encrypted);
            oos.writeObject(protocollo);
            oos.flush();
            if(response.equals("Exit"))
            	break;
          	
        }
        else
        	System.exit(2);
    	
    }
   
    ois.close();
    oos.close();
    toServerCoonnection.close();
  }

}