import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.zip.CRC32;

public class Ex2Client {
	
	static String fullMsg = "";
	public static void main(String[] args) throws UnknownHostException, IOException {
		try (Socket socket = new Socket("codebank.xyz", 38102)){
			System.out.println("Connected to: " + socket.getInetAddress() + ":" + socket.getPort());
			
			PrintWriter pw = new PrintWriter(socket.getOutputStream());
//			BufferedReader br = new BufferedReader
//					(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			InputStreamReader isr = new InputStreamReader(socket.getInputStream());
			
			String message1, message2;
			for (int i = 0; i < 100; ++i){
				message1 = "";
				message2 = "";
				
				message1 += Integer.toHexString(isr.read());
				message2 += Integer.toHexString(isr.read());
				
				//System.out.println(i + ": " + message1 + " and " + message2);
				fullMsg += message1 + message2;
			}
			fullMsg.toUpperCase();
			System.out.println("Received bytes: " + fullMsg);

			
		}
	}
}
