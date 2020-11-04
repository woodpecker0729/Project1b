package multichat2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.net.URLDecoder;
import java.util.HashSet;

public class Receiver extends Thread {
	Socket socket;
	BufferedReader in = null;
	HashSet<String> blockname = new HashSet<>();
	public Receiver(Socket socket) {
		this.socket = socket;
		
		try {
			in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			
		}
		catch(Exception e) {
			System.out.println("예외receiver생성자:"+e);
		}
	}
	@Override
	public void run() {
		while(in != null) {
			try {
				String str = in.readLine();
				str=URLDecoder.decode(str,"UTF-8");
				
				if(str.startsWith("(block)")) {
					String name = str.substring(7, str.indexOf("을"));
					if(!blockname.contains(name)) {
					System.out.println(">>" + str);
					blockname.add(name);
					}
					else {
						System.out.println("이미 차단된 사용자입니다.");
					}
				}
				else if(str.startsWith("(unblock)")) {
					String name = str.substring(9, str.indexOf("을"));
					if(blockname.contains(name)) {
					System.out.println(">>" + str);
					blockname.remove(name);
					}
					else {
						System.out.println("불가능합니다.");
					}
				}
				else if(str.charAt(0)=='[') {
					String name1 = str.substring(1,str.indexOf(']'));
					if(blockname.contains(name1)) {
						System.out.println("차단된 사용자로부터의 메세지 입니다.");
					}
					else
						System.out.println(">>" + str);
				}
				else
					System.out.println(">>" + str);
				
			}
			catch(SocketException ne) {
				System.out.println("SocketException");
				break;
			}
			catch(Exception e) {
				System.out.println("예외receiver/run1:"+e);
				break;
			}
		}
		try {
			in.close();
		}
		catch(Exception e) {
			System.out.println("예외receiver/run2:"+e);
		}
	}
}
