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
 
public class Udp_Server
{
    public static void main(String args[])
    {
    	    	
        DatagramSocket sock = null;
         
        try
        {
            
            sock = new DatagramSocket(11234);
             
            byte[] buffer = new byte[1000];
            
            DatagramPacket ingresso = new DatagramPacket(buffer, buffer.length);
                       
            echo("Creato server. Attendo Dati...");
                        
            while(true)
            {
                sock.receive(ingresso);
                byte[] data = ingresso.getData();
                String s = new String(data, 0, ingresso.getLength());
                 
                //Stampa i  dettagli dei dati in ingresso
                // 1) getAddress() indirizzo ip del client
                // 2) getPort() il numero di porta del client (assegnata dat sistema operativo del client)
                // 3) i dati inviati dal client e trasformati
                // in String
                echo(ingresso.getAddress().getHostAddress() + " : " + ingresso.getPort() + " - " + s);
                 
                s = "Server: " + s;
				// s.getBytes() trasforma il contenuto dell'oggetto di tipo String
				// in una sequenza d bytes.
				// Crea il Datagramma da inviare al client
                DatagramPacket dp = new DatagramPacket(s.getBytes() , s.getBytes().length , ingresso.getAddress() , ingresso.getPort());
                sock.send(dp);
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