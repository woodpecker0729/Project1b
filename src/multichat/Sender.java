package multichat;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLEncoder;
import java.util.Scanner;

public class Sender extends Thread{
	Socket socket;
	PrintWriter out =null;
	String name;
	
	public Sender(Socket socket,String name) {
		this.socket = socket;
		try {
			out = new PrintWriter(this.socket.getOutputStream(), true);
			this.name = name;
		}
		catch(Exception e) {
			System.out.println("예외sender생성자:"+e);
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		
		try {
			out.println(URLEncoder.encode(name,"UTF-8"));
			while(out!=null) {
				try {
					String s2 = s.nextLine();
					if(s2.equalsIgnoreCase("Q")){
						break;
					}
					else {
						out.println(URLEncoder.encode(s2,"UTF-8"));
					}
				}
				catch(UnsupportedEncodingException e) {
					
				}
				catch( Exception e) {
					System.out.println("예외sender/run1:"+e);
				}
			}
			out.close();
			socket.close();
		}
		catch(Exception e) {
			System.out.println("예외sender/run2:"+e);
		}
	}
}
