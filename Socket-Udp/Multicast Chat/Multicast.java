package Chat;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MulticastChat {

	private JFrame frmChat;
	private JTextField txtInvia;
	// la porta utilizzata da gruppo
	private int porta;
	// contiene le Api Java Multicast
	MulticastSocket socket;
	JTextArea txtArea;
	InetAddress gruppo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MulticastChat window = new MulticastChat();
					window.frmChat.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws IOException 
	 */
	public MulticastChat() throws IOException {
		gruppo = InetAddress.getByName("224.1.1.1");
		porta = Integer.parseInt("1234");
		socket = new MulticastSocket(porta);
		//indica il numero massimo di routers attraversati dal pacchetto.
		// quando il numero TTL = 0 viene scartato dal router
		// default = 1
		socket.setTimeToLive(1); 
		// unisce il processo allo specifico gruppo individuato
		// dall'indirizzo Ip comune di multicast e dal numero 
		// di porta noto
        socket.joinGroup(gruppo);
        // crea e lancia il Thread dedicato alla ricezione dei messaggi 
        // inviati al gruppo di multicast
        Thread t = new Thread(new Leggi(this, socket, gruppo, porta));
		t.start();
		initialize();
	}

	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmChat = new JFrame();
		frmChat.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				 try {
					socket.leaveGroup(gruppo);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
                 socket.close();
			}
		});
		frmChat.setTitle("Chat");
		frmChat.setBounds(100, 100, 519, 480);
		frmChat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmChat.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(27, 23, 448, 324);
		frmChat.getContentPane().add(scrollPane);
		
		txtArea = new JTextArea();
		scrollPane.setViewportView(txtArea);
		
		txtInvia = new JTextField();
		txtInvia.setBounds(27, 368, 270, 42);
		frmChat.getContentPane().add(txtInvia);
		txtInvia.setColumns(10);
		
		JButton btnNewButton = new JButton("Invia");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// peleva la stringa dalla TextFiled
				String messaggio = txtInvia.getText();
				//  Codifica la String in una sequenza di bytes  
				// e ritorna un array di bytes
				byte[] buffer = messaggio.getBytes();
				// crea il Datagram packet Udp
                DatagramPacket datagram = new DatagramPacket(buffer, buffer.length, gruppo, porta);
                try {
                	// e lo invia al gruppo
					socket.send(datagram);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton.setBounds(339, 368, 136, 42);
		frmChat.getContentPane().add(btnNewButton);
	}
}
