package Chat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Leggi implements Runnable {
	
	private MulticastSocket socket;
    private InetAddress gruppo;
    private int porta;
    MulticastChat chatta;
    // costruttore
    Leggi(MulticastChat chatta, MulticastSocket socket, InetAddress gruppo, int porta)
    {
        this.socket = socket;
        this.gruppo = gruppo;
        this.porta = porta;
        this.chatta = chatta;
    }
     
    @Override
    public void run()
    {
       while(true)
       {
           byte[] buffer = new byte[1024];
           // crea il Datagram packet Udp
           DatagramPacket datagram = new DatagramPacket(buffer, buffer. length, gruppo, porta);
           String messaggio;
       
           try {
        	   // legge il messaggio inviato da altri processi e inviati al gruppo
		       socket.receive(datagram);
		       // preleva la stringa in codifica UTF-8 dal datagramma 
		       messaggio = new String(buffer,0,datagram.getLength(),"UTF-8");
		       // e la aggiunge alla TextArea per la visualizzazione
		       chatta.txtArea.append(messaggio + "\n");
           	} catch (UnsupportedEncodingException e) {
           			e.printStackTrace();
           	} catch (IOException e) {
           		e.printStackTrace();
           	}    
       }    
       
    }
}

