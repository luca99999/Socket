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
 
 import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.net.ssl.SSLSocketFactory;

public class SSLClient {
	static int porta = 11234;

	public static void main(String[] args) throws UnknownHostException, IOException {
		
		// set the store where finds the key
		System.setProperty("javax.net.ssl.trustStore" , "se.store");
		
		// it create a secure Client socket
		SSLSocketFactory factory = (SSLSocketFactory)SSLSocketFactory.getDefault();
		Socket socket = factory.createSocket("localhost", porta);
		
		// create the buffered output socket channel, the parameter true means autoflush
		PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
		// create the buffered input socket channel
		BufferedReader socketbReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		// create the buffered input channel from keyboard
		BufferedReader tastiera = new BufferedReader(new InputStreamReader(System.in));
		
		String message = "";
		while(true) 
		{
			System.out.println("Inserisci [Date] , [Time] o [quit for exit] ");
			message = tastiera.readLine();
			
			// send quit to the server breaks the while loop and end the thread
			if(message.equals("quit"))
			{
				printWriter.println("quit");
				socket.close();
				break;
				
			}
			
			//send the request to the server
			printWriter.println(message);
			
			System.out.println("Risposta dal Server");
			// reads the response from the server
			System.out.println(socketbReader.readLine());
			
		}

	}

}
