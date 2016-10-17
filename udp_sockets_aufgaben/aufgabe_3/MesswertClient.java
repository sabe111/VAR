package udp_sockets_aufgaben.aufgabe_3;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;

public class MesswertClient {
	private static final String HOST = "localhost";
	private static final int PORT = 4711;
	private static final int BUFSIZE = 508;
	private static final int TIMEOUT = 2000;

	public static void main(String[] args) {
		
		
		try (DatagramSocket socket = new DatagramSocket()) {
			socket.setSoTimeout(TIMEOUT); // Zeit in ms, für wie lange ein read() auf socket blockiert. Bei timeout is java.net.SocketTimeoutException (TIMEOUT == 0 => blockiert für immer)
			InetAddress addr = InetAddress.getByName(HOST);
			
			System.out.println("Starte Übertragung");
			
			while(true){
				//Zufallszahlen erzeugen
				double random;
				random = Math.random()*50;
				
				//Erzeugte Double-Zahl konvertieren und in Bytearray speichern
				byte[] data = new byte[8];
				ByteBuffer.wrap(data).putDouble(random);
				
				DatagramPacket packetOut = new DatagramPacket(data, data.length, addr, PORT);	
				
				socket.send(packetOut);	
				//5 Sekunden warten
				Thread.sleep(5000);
			}
			
			
		} catch (SocketTimeoutException e) {
			System.err.println("Timeout: " + e.getMessage());
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}