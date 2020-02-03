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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Servant extends Thread {
	Socket socket;
	private DateFormat data;
	private DateFormat time;
	BufferedReader buffer;
	String response;
	
	public Servant(Socket socket) {
		super();
		this.socket = socket;
		data = new SimpleDateFormat("yyyy/MM/dd"); 
		time = new SimpleDateFormat("hh:mm:ss"); 
	}
	
	public void run() 
	{
		
		try {
			
			// create the buffered output channel, the parameter true means autoflush
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
		    // create the buffered input channel
			buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			while(true)
			{
				String stringa  = buffer.readLine();
				if(stringa.contentEquals("quit"))
				{
					socket.close();
					// close while loop and exits from thread
					break;
				}
						
				Date date = new Date(); 
				
				switch (stringa) 
				{ 
					case "Date" : 
						response = data.format(date); 
						break; 
					
					case "Time" : 
						response = time.format(date); 
					    break; 
				    
					default: 
						response = "errore"; 
						break; 
				} // switch
	
				printWriter.println(response); 
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
