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



import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/*
* (Client)  State 0
* Alice -----------------p, g, PubAlice (Alice's public key) -------------->
*  																	State 1
* 		<---------- Encrypted Aes message, PubBob (Bob's public key) -------- Bob																
*           State 2
*       --------------------- Encrypted Aes message ------------------------>
*/



public class Server {

  @SuppressWarnings("resource")
public static void main(String args[]) throws Exception {
    ServerSocket server;
    Socket fromClient;
    int porta = 5050;
    int tipo;
    Protocol protocollo = null;
    BigInteger p = null, g = null, PubAlice, PubBob = null, privatekey, chiaveBob = null;
    Random r;
    int bitlength = 1024;
    

    server = new ServerSocket(porta);
    System.out.println("Waiting for a connection on " + server.getLocalPort());

    while(true)
    {
    
    	fromClient = server.accept();
    	ObjectOutputStream oos = new ObjectOutputStream(fromClient.getOutputStream());
    	ObjectInputStream ois = new ObjectInputStream(fromClient.getInputStream());
    	    
    	protocollo = (Protocol) ois.readObject(); 
 	
    	tipo = protocollo.getType();
       
    	// it receives a request from the client containing p, g and the Alice's public key
    	if(tipo == 0)
    	{
    		p = protocollo.getP();
    		g = protocollo.getG();
    		PubAlice = protocollo.getPubKey(); 
    		
    		r = new Random();
    		// private key of Bob
    		privatekey  = java.math.BigInteger.probablePrime(bitlength / 2, r);
    		// PubBob public key of Bob
    		PubBob = g.modPow(privatekey,  p);
    		// Alice and Bob calculate the agreement key used to obtain a common Aes key
    		chiaveBob = PubAlice.modPow(privatekey,  p);
    		
    		// create a new thread object passing socket, input and output stream, Bob's public key
    		// p, g and the object protocol
        	Thread t = new Servant(fromClient, ois, oos, chiaveBob, PubBob, p, g, protocollo); 

        	// Invoking the start() method 
        	t.start(); 
    	}
    	
    }
         
  }
}