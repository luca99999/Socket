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
 
public class UdpServerMultithreaded
{
    public static void main(String args[])
    {
    	    	
        DatagramSocket sock = null;
        DatagramSocket sock2 = null;
        int x = 0;
        
        try
        {
            
            sock = new DatagramSocket(11234);
             
            byte[] buffer = new byte[1500];
            
            DatagramPacket ingresso = new DatagramPacket(buffer, buffer.length);
                       
            echo("Server: Waiting for...");
                        
            while(true)
            {
                sock.receive(ingresso);
                byte[] data = ingresso.getData();
                String s = new String(data, 0, ingresso.getLength());
                 
                // Prints details of incoming data
                // 1) getAddress() client ip address
                // 2) getPort() client number port (given by client operating system)
                // 3) data sent by the client and processed in String
                echo(ingresso.getAddress().getHostAddress() + " : " + ingresso.getPort() + " - " + s);
                 
                
                // Creates a new socket with a different port number. Acts as a TCP server.
                
                sock2 = new DatagramSocket();
                x++;
                
                Runnable se = new Servant(sock2, ingresso, x);
                Thread t = new Thread(se);
               
                t.run();
                
            }
        }
         
        catch(IOException e)
        {
            System.err.println("IOException " + e);
        }
    }
     
    public static void echo(String msg)
    {
        System.out.println(msg);
    }
}