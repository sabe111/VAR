package udp_sockets_aufgaben.aufgabe_2;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

public class DaytimeClient {
	private static final String HOST = "localhost";
	private static final int PORT = 4711;
	private static final int BUFSIZE = 508;
	private static final int TIMEOUT = 2000;

	public static void main(String[] args) {
		//Senden eines "leeren" Datagramms
		//Array ist nur 1 groß und mit 0 gefüllt
		//Null nicht möglich da irgendwas im Array enthalten sein muss damit er von der Methode angenommen wird
		byte[] data = new byte[1];
		data[0] = 0;
		
		try (DatagramSocket socket = new DatagramSocket()) {
			socket.setSoTimeout(TIMEOUT); // Zeit in ms, für wie lange ein read() auf socket blockiert. Bei timeout is java.net.SocketTimeoutException (TIMEOUT == 0 => blockiert für immer)
			InetAddress addr = InetAddress.getByName(HOST);
			DatagramPacket packetOut = new DatagramPacket(data, data.length, addr, PORT);
			socket.send(packetOut);
			
			DatagramPacket packetIn = new DatagramPacket(new byte[BUFSIZE], BUFSIZE);
			//Empfangen des Pakets vom Server
			socket.receive(packetIn);
			//Übertragenen Bytecode wieder in String umwandeln um Datum korrekt anzeigen zu können
			//Encoding ist das gleiche mit dem von String->byte umgewandelt wurde
			String received = new String(packetIn.getData(),"ISO-8859-1");
			System.out.println("Received date: " + received);
		} catch (SocketTimeoutException e) {
			System.err.println("Timeout: " + e.getMessage());
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}