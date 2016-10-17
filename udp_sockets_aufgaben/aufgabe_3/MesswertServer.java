package udp_sockets_aufgaben.aufgabe_3;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MesswertServer {
	private static final int PORT = 4711;
	private static final int BUFSIZE = 508;

	public static void main(String[] args) {
		try (DatagramSocket socket = new DatagramSocket(PORT)) {
			DatagramPacket packetIn = new DatagramPacket(new byte[BUFSIZE], BUFSIZE);

			System.out.println("Server gestartet ...");

			while (true) {
				socket.receive(packetIn);
				String currentTime = new SimpleDateFormat("HH:mm:ss").format(new Date());
				//Empfangenes Byte Array wieder in double konvertieren
				double number = ByteBuffer.wrap(packetIn.getData()).getDouble();
				System.out.println(":"+packetIn.getSocketAddress()+" "+currentTime+" "+number);
				
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}