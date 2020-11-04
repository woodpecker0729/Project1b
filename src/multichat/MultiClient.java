package multichat;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MultiClient {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("이름을 입력하세요:");
		String s_name= scanner.nextLine();
		
		
		//PrintWriter out = null;
		//BufferedReader in = null;
		
		try {
			String ServerIP = "localhost";
			if(args.length > 0) {
				ServerIP = args[0];
			}
			Socket socket = new Socket(ServerIP,9999);
			System.out.println("서버와 연결되었습니다.");
			
			Thread receiver = new Receiver(socket);
			receiver.start();
			
			Thread sender = new Sender(socket, s_name);
			sender.start();
			if(receiver.getState()==Thread.State.TERMINATED||sender.getState()==Thread.State.TERMINATED) {
				System.exit(1);
			}
		}
		catch(Exception e) {
			System.out.println("예외발생[MultiClient]"+e);
		}
	}
}
