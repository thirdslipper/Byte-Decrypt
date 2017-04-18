/**
 * Author: Colin Koo
 * Professor: Davarpanah
 * 
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.zip.CRC32;

public class Ex2Client {
	
//	static String fullMsg = "";
	public static void main(String[] args) throws UnknownHostException, IOException {
		try (Socket socket = new Socket("codebank.xyz", 38102)){
			System.out.println("Connected to: " + socket.getInetAddress() + ":" + socket.getPort() + "\n");
			
			OutputStream os = socket.getOutputStream();
			InputStream is = socket.getInputStream();
			
			byte fullMsg[] = new byte[100];
			byte message1, message2;
			
			for (int i = 0; i < 100; i++){
				
				message1 = (byte) is.read();
				message2 = (byte) is.read();
				
				message1 <<= 4;
				fullMsg[i] = message1;
				fullMsg[i] =  (byte) (fullMsg[i] | message2);
			}
			
			System.out.print("Received bytes: ");
			for (int i = 0; i < fullMsg.length; ++i){
				System.out.print(Integer.toHexString(fullMsg[i] & 0xFF).toUpperCase());
//				System.out.print(Integer.toHexString(fullMsg[i] >> 4) + Integer.toHexString((fullMsg[i] << 4) >> 4));
			}
			
			CRC32 crc = new CRC32();
			crc.update(fullMsg);
			
			long crcResult = crc.getValue();
//			System.out.println("\ncrcResult: " +  crcResult);
			
//			byte crcBytes = (byte) crcResult;
//			System.out.println("crcBytes: " + Integer.toHexString((int) crcResult));
			System.out.println("\n\nGenerated CRC32: " + Integer.toHexString((int) crcResult));
			System.out.println("full string: " + Integer.toBinaryString((int) crcResult));
			
			for (int j = 3; j >= 0; j--){
				System.out.println(j + " : " + Integer.toBinaryString((int)crcResult >> (j*8)));
				os.write((byte)(crcResult >> j*8));
				System.out.println(Integer.toHexString((int)(crcResult >> j*8) & 0xFF));
			}
			
			int check = is.read();
			if (check == 1){//switch
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