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
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

public class Servant extends Thread {
		
		private ObjectInputStream ois; 
		private ObjectOutputStream oos; 
		private Socket fromClient;
		private BigInteger chiaveBob, PubBob;
		private DateFormat data;
		private DateFormat time;
		String str, response;
		Protocol protocol;
		SecretKeySpec chiaveAes;
		byte[] encryptedMessage;
		String decrypted;
		byte[] encrypted;
		int tipo;
				

		// Constructor 
		public Servant(Socket fromClient, ObjectInputStream ois, ObjectOutputStream oos, 
				BigInteger chiaveBob, BigInteger PubBob, BigInteger p, BigInteger g, Protocol protocol) 
		{
			this.fromClient = fromClient;
			this.ois = ois;
			this.oos = oos;
			this.chiaveBob = chiaveBob;
			this.PubBob = PubBob;
			this.protocol = protocol;
			
			data = new SimpleDateFormat("yyyy/MM/dd"); 
			time = new SimpleDateFormat("hh:mm:ss"); 
		}

		/*
		* (Client)  State 0
		* Alice -----------------p, g, PubAlice (Alice's public key) -------------->
		*  																	State 1
		* 		<---------- Encrypted Aes message, PubBob (Bob's public key) -------- Bob																
		*           State 2
		*       --------------------- Encrypted Aes message ------------------------>
		*/

		@SuppressWarnings("deprecation")
		@Override
		public void run() 
		{ 
			// session key used to encrypt
			System.out.println("Chiave Bob " + chiaveBob.toString(16));
			protocol.setPubKey(PubBob);
			protocol.setType(1); // 1 state
			// Ask user what he wants
			str = "Type | Date | Time | (Exit) for quitting";
			
			try {
				// calculates AES Key
				chiaveAes = Aes.getAesKey(chiaveBob.toString());
			
			    // encrypt the message to the client
				byte[] encrypted = Aes.encryptAes(str, chiaveAes);
				protocol.setEncryptedMessage(encrypted);
				// serializes the object
				oos.writeObject(protocol);
				oos.flush();
				
				
				while (true) 
				{ 
				
					protocol = (Protocol) ois.readObject(); 
					tipo = protocol.getType();
					// if tipo == 2 means response of client with encrypted message (state 2)
					if(tipo == 2)
					{
			    	
						encryptedMessage = protocol.getEncryptedMessage();
						decrypted = Aes.decryptAes(encryptedMessage, chiaveAes);
						System.out.println("Server: " + decrypted);
				        
						if(decrypted.equals("Exit")) 
						{ 
							System.out.println("Connection closed from " 
									+ fromClient.getInetAddress().toString() + " port " + fromClient.getPort()); 
							ois.close();
							oos.close();
							fromClient.close();
							return;
												
						} 
					
						// creating Date object 
						Date date = new Date(); 
						
						// resetting Protocol object to state 2
						protocol.setType(2);
										
						switch (decrypted) 
						{ 
					
							case "Date" : 
								response = data.format(date); 
							
								encrypted = Aes.encryptAes(response, chiaveAes);
								protocol.setEncryptedMessage(encrypted);
								oos.writeObject(protocol);
								oos.flush();
								break; 
							
							case "Time" : 
								response = time.format(date); 
							
								encrypted = Aes.encryptAes(response, chiaveAes);
								protocol.setEncryptedMessage(encrypted);
								oos.writeObject(protocol);
								oos.flush();
								break; 
							
							default: 
								response = str; 
								encrypted = Aes.encryptAes(response, chiaveAes);
								protocol.setEncryptedMessage(encrypted);
								oos.writeObject(protocol);
								oos.flush();
								break; 
						} // switch
			
					}  //if
				} // while
		} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		} // catch
	} // run
}
			
			