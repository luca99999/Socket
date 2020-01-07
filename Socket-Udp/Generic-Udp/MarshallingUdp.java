/*
 *  Copyright (c) 2019 - Luca Bernardini
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




import java.io.*;
import java.net.*;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXB;


public class MarshallingUdp {

	
	    public static void main(String args[])
	    {
	        DatagramSocket sock = null;
	        int port = 11234;
	        String s;
	        String message = "a message to send";
	        
	        
	        Message msg = new Message();
	        msg.setId(1);
	        msg.setMessage(message);
	        msg.setLenght(message.length());
	        
	        StringWriter sw = new StringWriter();
	        // Marshals object msg into an xml String
	        JAXB.marshal(msg, sw);
	        // convert StringBuffer to String
	        s = sw.toString();
	       
	        try
	        {
	        	
	            sock = new DatagramSocket();
	       	          
	            InetAddress host = InetAddress.getByName("127.0.0.1");
	             
	            byte[] b = s.getBytes();
	                
	            DatagramPacket dp = new DatagramPacket(b , b.length , host , port);
	               
	            // sends the Udp datagram packet with xml payload to server waiting
	            // on localhost with port number 11234 
	            sock.send(dp);
	               
	        }
	        catch(IOException e)
	        {
	            System.err.println("IOException " + e);
	        }
	    }
	     
	   
	}
