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



import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Servant implements Runnable {
	private DatagramPacket dp = null;
	private DatagramSocket sock = null;
	DatagramPacket dpClient = null;
	String s = null;
	int x;
	
	public Servant(DatagramSocket sock, DatagramPacket dpClient, int x) {
		super();
		this.sock = sock;
		this.dpClient = dpClient;
		this.x = x;
	}
	
	public void run() {
		 s = "Server: Thread Servant " + x;;
			 
		 echo(dpClient.getAddress().getHostAddress() + " : " + dpClient.getPort() + " - " + s);
         DatagramPacket dp = new DatagramPacket(s.getBytes() , s.getBytes().length , dpClient.getAddress() , dpClient.getPort());
         
		try {
			sock.send(dp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void echo(String msg)
    {
        System.out.println(msg);
    }

}
