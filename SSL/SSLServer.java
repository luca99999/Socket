/*
 * Copyright (c) 2020 - Luca Bernardini
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
 
import java.io.IOException;
import java.net.ServerSocket;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

public class SSLServer {
    static SSLServerSocket serverSocket;	
	static int porta = 11234;
    
	public static void main(String[] args) throws IOException {
		    
			// set the store where  finds the key
			System.setProperty("javax.net.ssl.keyStore" , "se.store");
			// set the password to access the store
			System.setProperty("javax.net.ssl.keyStorePassword" , "prova12");
			
			// it creates a secure Server socket
			SSLServerSocketFactory factory = (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
			serverSocket = (SSLServerSocket) factory.createServerSocket(porta);
			
			System.out.println("Server waiting for....");
			
			// He is listening to new requests by creating a thread for each of them
			while(true)
			{
				new Servant(serverSocket.accept()).start();
			}
	}

}

