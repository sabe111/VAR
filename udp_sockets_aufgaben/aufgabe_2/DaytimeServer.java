package udp_sockets_aufgaben.aufgabe_2;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DaytimeServer {
	private static final int PORT = 4711;
	private static final int BUFSIZE = 508;

	public static void main(String[] args) {
		try (DatagramSocket socket = new DatagramSocket(PORT)) {
			DatagramPacket packetIn = new DatagramPacket(new byte[BUFSIZE], BUFSIZE);
			DatagramPacket packetOut = new DatagramPacket(new byte[BUFSIZE], BUFSIZE);
			
			//Speichern der Systemzeit (Ausgabe in Milliekunden) im gewünschten Format
			String currentTime = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date());
			
			System.out.println("Server gestartet ...");

			while (true) {
				socket.receive(packetIn);
				System.out.println("Received data from: " + packetIn.getSocketAddress());	
				System.out.println("Sending Time: "+currentTime);
				
				//Umwandeln der gespeicherten Zeit von String in Bytecode mit gewünschtem Encoding (ISO...)
				packetOut.setData(currentTime.getBytes("ISO-8859-1"));
				//Array auf Länge des Zeitstrings stellen 
				packetOut.setLength(currentTime.length());
				packetOut.setSocketAddress(packetIn.getSocketAddress());
				socket.send(packetOut);
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}
	
}