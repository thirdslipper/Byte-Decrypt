/**
 * Author: Colin Koo
 * Professor: Davarpanah
 * 
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.zip.CRC32;

public class Ex2Client {
	
	static String fullMsg = "";
	public static void main(String[] args) throws UnknownHostException, IOException {
		try (Socket socket = new Socket("codebank.xyz", 38102)){
			System.out.println("Connected to: " + socket.getInetAddress() + ":" + socket.getPort() + "\n");
			
			PrintWriter pw = new PrintWriter(socket.getOutputStream());
			
			InputStream is = socket.getInputStream();
//			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String message1, message2;

			for (int i = 0; i < 100; ++i){
//				message1 = "";
//				message2 = "";
				message1 = Integer.toHexString(is.read());
				message2 = Integer.toHexString(is.read());
//				System.out.println(i + ": " + message1 + " and " + message2);
				fullMsg += message1 + message2;
			}
			
			byte bytes[] = fullMsg.toUpperCase().getBytes();
			System.out.println("Received bytes: " + fullMsg.toUpperCase() + "\n");
			
			CRC32 crc = new CRC32();
			crc.update(bytes);
			
			String crcResult = Integer.toHexString((int) crc.getValue()).toUpperCase();
			System.out.println("Generated CRC32: " + crcResult);

			pw.println(crcResult);
			
			int check = is.read();
			// doesn't work after this point
			if (check == 1){
				System.out.println("Reponse good.");
			}
			else if(check == 0){
				System.out.println("Response bad.");
			}
			else{
				System.out.println("No response");
			}
		}
	}
}