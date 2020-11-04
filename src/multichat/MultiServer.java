package multichat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;


public class MultiServer implements Connection{

	//멤버변수
	static ServerSocket serverSocket = null;
	static Socket socket =null;
	Map<String, PrintWriter> clientMap;
	Set<String> blacklist = new HashSet<String>();
	Set<String> pWords = new HashSet<String>();
	DBfunc db = new DBfunc();
	public MultiServer() {
		clientMap = new HashMap<String, PrintWriter>();
		Collections.synchronizedMap(clientMap);
	}
	
	//채팅 서버 초기화
	public void init() {
		try {
			/*
			9999번 포트를 설정하여 서버객체를 생성하고 클라이언트의
			접속을 대기한다.
			 */
			String[] black = {"lee","chan"};
			String[] word = {"금지어1","금지어2"};
			for(int i=0; i<black.length;i++) {
				blacklist.add(black[i]);
			}
			for(int i=0; i<word.length;i++) {
				pWords.add(word[i]);
			}
			serverSocket = new ServerSocket(9999);
			System.out.println("서버가 시작되었습니다.");
			
			while(true) {
				
				socket = serverSocket.accept();
				System.out.println(socket.getInetAddress()+"(클라이언트)의"
						+socket.getPort()+" 포트를 통해"
						+socket.getLocalAddress()+"(서버)의"
						+socket.getLocalPort()+"포트로 연결됨");
				
				/*
				클라이언트의 메세지를 모든 클라이언트에게 전달하기 위한
				쓰레드 생성 및 시작. 한명당 하나씩의 쓰레드가 생성된다.
				 */
				Thread mst = new MultiServerT(socket);
				mst.start();
				
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				serverSocket.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		MultiServer ms = new MultiServer();
		ms.init();
	}
	public void showUserList(String name) {
		int cnt = 0;
		Iterator<String> it2 = clientMap.keySet().iterator();
		String clientlist = "";
		while(it2.hasNext()) {
			String list = it2.next();
			if(!list.equals(name)) {
				clientlist+=list+" ";
				cnt++;
			}
			
		}
		Iterator<String> it = clientMap.keySet().iterator();
		while(it.hasNext()) {
			try {
				String clientName = it.next();
				if(clientName.equals(name)) {
					PrintWriter it_out =(PrintWriter)clientMap.get(clientName);
					if(cnt>0) {
					it_out.println(URLEncoder.encode("---현재접속자(사용자포함):"+clientMap.size()+"명---","UTF-8"));
					it_out.println(URLEncoder.encode(clientlist,"UTF-8"));
					break;
					}
					else {
						it_out.println(URLEncoder.encode("사용자는 당신 하나뿐이야","UTF-8"));
					}
				}
				
			}
			catch(Exception e) {
				System.out.println("예외:"+e);
			}
		}
	}
	//접속된 모든 클라이언트에게 메세지를 전달하는 메소드
	public void sendAllMsg(String name,String msg,String flag) {
		
		//Map에 저장된 객체의 키값(접속자명)을 먼저 얻어온다.
		Iterator<String> it = clientMap.keySet().iterator();
		
		//저장된 객체(클라이언트)의 갯수만큼 반복한다.
		while(it.hasNext()) {
			try {
				
				//컬렉션의 key는 클라이언트의 접속자명이다.
				String clientName = it.next();
				//각 클라이언트의 PrintWriter객체를 얻어온다.
				PrintWriter it_out =(PrintWriter)clientMap.get(clientName);
				
				if(flag.equals("One")) {
					//flag가 One이면 해당 클라이언트 한명에게만 전송
					if(name.equals(clientName)){
						//컬렉션에 저장된 접소자명과 일치하는경우 메세지 전송
						it_out.println(URLEncoder.encode("[귓속말]"+msg,"UTF-8"));
					}
				}
				else {
					//그 외에는 모든 클라이언트에게 전송
					if(name.equals("")) {
						//접속, 퇴장에서 사용되는 부분
						it_out.println(URLEncoder.encode(msg,"UTF-8"));
					}
					else {
						//메세지를 보낼때 사용되는 부분
						it_out.println(URLEncoder.encode("["+name+"]:"+msg,"UTF-8"));
					}
				}
				
			}
			catch(Exception e) {
				System.out.println("예외:"+e);
			}
		}
	}
	
	//내부클래스
	/*
	init()에 기술되었던 스트림을 생성후 메세지를 읽기/쓰기 하던 부분이
	해당 내부클래스로 이동되었다.
	 */
	class MultiServerT extends Thread{
		
		//멤버변수
		Socket socket;
		PrintWriter out =null;
		BufferedReader in = null;
		boolean connect = true;
		boolean fix = false;
		//생성자 : socket을 기반으로 입출력 스트림 생성
		public MultiServerT(Socket socket) {
			this.socket=socket;
			try {
				out = new PrintWriter(this.socket.getOutputStream(),true);
				in = new BufferedReader(new InputStreamReader(this.socket.getInputStream(),"UTF-8"));
			}
			catch(Exception e) {
				System.out.println("예외:"+e);
			}
		}

		@Override
		public void run() {
			String name = "";
			String s = "";
			try {
				//클라이언트의 이름을 읽어와서 저장
				name = in .readLine();
				name = URLDecoder.decode(name,"UTF-8");
				/*
				방금 접속한 클라이언트를 제외한 나머지에게 사용자의
				입장을 알려준다.
				 */
				if(clientMap.containsKey(name)) {
					out.println(URLEncoder.encode("중복된 대화명이 있습니다.","UTF-8"));
					System.out.println("[퇴장]"+socket.getInetAddress()+":"+socket.getPort()+" (중복된 대화명)");
					in = null;
					connect=false;
				}
				else if(blacklist.contains(name)) {
					out.println(URLEncoder.encode("접속이 거부되었습니다.","UTF-8"));
					System.out.println("[퇴장]"+socket.getInetAddress()+":"+socket.getPort()+" (블랙리스트)");
					in = null;
					connect=false;
				}
				else if(clientMap.size()==MAX) {
					out.println(URLEncoder.encode("인원이 초과되어 접속이 해제되었습니다.","UTF-8"));
					System.out.println("[퇴장]"+socket.getInetAddress()+":"+socket.getPort()+" (인원초과)");
					in = null;
					connect=false;
				}
				else {
				sendAllMsg("", name + "님이 입장하셨습니다.","All");
				
				//현재 접속자의 정보를 HashMap에 저장한다.
				clientMap.put(name,out);
				HashSet<String> block = new HashSet<>();
				
				//HashMap에 저장된 객체의 수로 접속자를 파악할수 있다.
				System.out.println(name + " 접속");
				System.out.println("현재 접속자 수는"+clientMap.size()+"명 입니다");
				}
				String fixname = "";
				//입력한 메세지는 모든 클라이언트에게 Echo된다.
				
				while(in!=null) {
					s=in.readLine();
					if(s==null) break;
					s=URLDecoder.decode(s,"UTF-8");	
					System.out.println(name+" >> "+ s);
					
					/*
					클라이언트가 전송한 메세지가 명령어인지 판단한다.
					 */
					Iterator it = pWords.iterator();
					Boolean pword = false;
					String str = "";
					
					while(it.hasNext()) {
						str = (String)it.next();
						if(s.contains(str)) {
							out.println(URLEncoder.encode("금칙어가 있습니다 ("+str+")","UTF-8"));
							pword=true;
							break;
						}
					}
						if (pword){
							pword=false;
							continue;
						}
							if(s!=null) {
								if(s.charAt(0)=='/') {
									//만약 /로 시작한다면 명령어이다.
									/*
									귓속말은 아래와 같이 전송하게된다.
									-> /to 대화명 대화내용
										: 다라서 split()을 통해 space로 문자열을 분리한다.
									 */
									if(s.startsWith("/fixto")&&s.charAt(6)==' ') {
									    StringTokenizer strt = new StringTokenizer(s);
									    strt.nextToken();
									    fixname = strt.nextToken();
									    if(clientMap.containsKey(fixname)) {
									    	if(fix==false) {
												fix = true;
												out.println(URLEncoder.encode(fixname+"님에게 귓속말 고정되었습니다.","UTF-8"));
												
											}
									    	else {
									    		out.println(URLEncoder.encode("이미 고정되어 있습니다.","UTF-8"));
									    		
									    	}
									    }
									    else {
									    	out.println(URLEncoder.encode("해당 대화명을 가진 사용자가 없습니다.","UTF-8"));
									    	
									    }
									}
									else if(s.startsWith("/unfixto")) {
										fix = false;
										fixname = "";
										out.println(URLEncoder.encode("귓속말 고정이 해제되었습니다.","UTF-8"));
										
									}
									else if(s.startsWith("/list")) {
										showUserList(name);
										
									}
									else if(s.startsWith("/block")) {
										String[] strArr = s.split(" ");
										out.println(URLEncoder.encode("(block)"+strArr[1]+"을(를) 차단하였습니다","UTF-8"));
									}
									else if(s.startsWith("/unblock")) {
										String[] strArr = s.split(" ");
										out.println(URLEncoder.encode("(unblock)"+strArr[1]+"을(를) 차단 해제하였습니다","UTF-8"));
									}
									else {
										String[] strArr = s.split(" ",3);
										if(strArr[0].equals("/to")) {
											//함수 호출시 One이면 한명에게만 메세지 전송
											sendAllMsg(strArr[1],strArr[2],"One");
											
										}
										else {
											out.println(URLEncoder.encode("존재하지 않는 명령어입니다.","UTF-8"));
											
										}
									}
									
								}
								else {
									//만약 /로 시작하지 않으면 일반 메세지이다.
									if(fix) {
										sendAllMsg(fixname,s,"One");
										
									}
									else {
									sendAllMsg(name,s,"All");// All이면 전체전송
									db.insert(name, s);
									
									}
								}
								
							}
						}
//					}
				
			}
			catch(Exception e) {
				System.out.println("예외:"+e);
				e.printStackTrace();
			}
			finally {
				/*
				클라이언트가 접속을 종료하면 Socket예외가 발생하게 되어
				finally절로 진입하게된다. 이때 "대화명"을 통해 정보를
				삭제한다.
				 */
				if(connect) {
				clientMap.remove(name);
				sendAllMsg("", name + "님이 퇴장하셨습니다.","All");
				//퇴장하는 클라이언트의 쓰레드명을 보여준다.
				System.out.println(name + " [" +Thread.currentThread().getName() + "] 퇴장");
				System.out.println("현재 접속자 수는"+clientMap.size()+"명 입니다.");
				}
				try {
					if(in != null)
					in.close();
					out.close();
					socket.close();
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
		}

	}
}


