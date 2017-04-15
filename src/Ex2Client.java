import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.zip.CRC32;

public class Ex2Client {

	String[] receivedMsg = new String[100];
	public static void main(String[] args) throws UnknownHostException, IOException {
		try (Socket socket = new Socket("codebank.xyz", 38102)){
			System.out.println("Connected to: " + socket.getInetAddress() + ":" + socket.getPort());
			
			PrintWriter pw = new PrintWriter(socket.getOutputStream());
			BufferedReader br = new BufferedReader
					(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			

		}
	}
}
