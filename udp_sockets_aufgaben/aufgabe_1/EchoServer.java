package udp_sockets_aufgaben.aufgabe_1;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class EchoServer {
	private static final int PORT = 4711;
	private static final int BUFSIZE = 508;

	public static void main(String[] args) {
		try (DatagramSocket socket = new DatagramSocket(PORT)) {
			DatagramPacket packetIn = new DatagramPacket(new byte[BUFSIZE], BUFSIZE);
			DatagramPacket packetOut = new DatagramPacket(new byte[BUFSIZE], BUFSIZE);

			System.out.println("Server gestartet ...");

			while (true) {
				socket.receive(packetIn);
				System.out.println("Received: " + packetIn.getLength() + " bytes");
				packetOut.setData(packetIn.getData());
				packetOut.setLength(packetIn.getLength());
				packetOut.setSocketAddress(packetIn.getSocketAddress());
				socket.send(packetOut);
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}