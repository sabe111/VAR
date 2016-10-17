package udp_sockets_aufgaben.aufgabe_1;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

public class EchoClient {
	private static final String HOST = "localhost";
	private static final int PORT = 4711;
	private static final int BUFSIZE = 508;
	private static final int TIMEOUT = 2000;

	public static void main(String[] args) {
		//Um ArrayOutOfBounds zu vermeiden: run Menü ausklappen ->run configurations -> arguments reiter
		//dann zu übertragende Daten eingeben
		//Grund: args[] liest die mitübergebenen Kommandozeilenparameter aus und speichert sie im Array. Keine Parameter ->array leer
		byte[] data = args[0].getBytes();
		try (DatagramSocket socket = new DatagramSocket()) {
			socket.setSoTimeout(TIMEOUT); // Zeit in ms, für wie lange ein read() auf socket blockiert. Bei timeout is java.net.SocketTimeoutException (TIMEOUT == 0 => blockiert für immer)
			InetAddress addr = InetAddress.getByName(HOST);
			DatagramPacket packetOut = new DatagramPacket(data, data.length, addr, PORT);
			socket.send(packetOut);
			DatagramPacket packetIn = new DatagramPacket(new byte[BUFSIZE], BUFSIZE);
			socket.receive(packetIn);
			String received = new String(packetIn.getData(), 0, packetIn.getLength());
			System.out.println("Received: " + received);
		} catch (SocketTimeoutException e) {
			System.err.println("Timeout: " + e.getMessage());
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}