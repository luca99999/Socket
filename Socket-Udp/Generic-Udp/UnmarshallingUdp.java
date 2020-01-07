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

import javax.xml.bind.JAXB;


public class UnmashallingUdp {

    public static void main(String args[])
    {
	    	    	
	     DatagramSocket sock = null;
	         
	     try
	     {	            
	            sock = new DatagramSocket(11234);
	             
	            byte[] buffer = new byte[1500];
	            
	            DatagramPacket ingresso = new DatagramPacket(buffer, buffer.length);
	                       
	            sock.receive(ingresso);
	            byte[] data = ingresso.getData();
	            String s = new String(data, 0, ingresso.getLength());
	           
	            // converts xml to object of type Message     
	            Message msg = JAXB.unmarshal(new StringReader(s), Message.class);
	            
	            System.out.println("Id: " + msg.getId());
	            System.out.println("Message: " + msg.getMessage());
	            System.out.println("Lenght: " + msg.getLenght());
	       }
	       catch(IOException e)
	       {
	            System.err.println("IOException " + e);
	       }
	}
	 	   
}