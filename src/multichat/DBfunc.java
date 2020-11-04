package multichat;

import java.sql.Statement;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class DBfunc {
	Connection con;
	Statement stmt;
	PreparedStatement pst;
	public DBfunc() {
		// TODO Auto-generated constructor stub
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:orcl";
			String userid = "kosmo";
			String userpw = "1234";
			con = DriverManager.getConnection(url, userid, userpw);
		
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int insert(String name,String str) {
		try {
			String query = "INSERT INTO chat_talking VALUES (seq_chat.nextval,?,?,sysdate)";
			
			pst = con.prepareStatement(query);
			
			Scanner sc = new Scanner(System.in);

			pst.setString(1, name);
			pst.setString(2, str);
			
			int affected = pst.executeUpdate();
			
			pst = null;
			
			return affected;
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("대화내용 db저장 실패");
		}
		return -1;
	}
	
}