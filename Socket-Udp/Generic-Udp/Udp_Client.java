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
 
public class Udp_Client
{
    public static void main(String args[])
    {
        DatagramSocket sock = null;
        int port = 11234;
        String s;
        
        
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
         
        try
        {
        	
            sock = new DatagramSocket();
             
          
            InetAddress host = InetAddress.getByName("127.0.0.1");
             
            while(true)
            {  
                echo("Inserisci il messaggio da inviare : ");
                
                                
                s = (String)cin.readLine();
               
                byte[] b = s.getBytes();
                
                DatagramPacket dp = new DatagramPacket(b , b.length , host , port);
                // invia sul socket i dati
                sock.send(dp);
                 
                byte[] buffer = new byte[1500];
               
                DatagramPacket risposta = new DatagramPacket(buffer, buffer.length);
                
                sock.receive(risposta);
                 
                byte[] data = risposta.getData();
               
                s = new String(data, 0, risposta.getLength());
                 
                
                echo(risposta.getAddress().getHostAddress() + " : " + risposta.getPort() + " - " + s);
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