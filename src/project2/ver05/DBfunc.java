package project2.ver05;

import java.sql.Statement;
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
	public int insert(String account,String name,int money) {
		try {
			String query = "INSERT INTO banking_tb VALUES (seq_banking.nextval,?,?,?)";
			
			pst = con.prepareStatement(query);
			
			Scanner sc = new Scanner(System.in);

			pst.setString(1, account);
			pst.setString(2, name);
			pst.setInt(3, money);
			
			int affected = pst.executeUpdate();
			
			pst = null;
			
			return affected;
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("중복발견 계좌개설실패");
		}
		return -1;
	}
	public int update(String account,int money) {
		try {
			String sql = "UPDATE banking_tb SET money=? WHERE account=?";
			
			pst = con.prepareStatement(sql);
			
			Scanner sc = new Scanner(System.in);
			
			pst.setString(2, account);
			pst.setInt(1, money);
			
			int affected = pst.executeUpdate();
			
			pst = null;
			
			return affected;
		}catch (Exception e) {
			// TODO: handle exception
		}
		return -1;
	}
	public void select() {
		try {
			String sql = "SELECT * FROM banking_tb";
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
	
				String acc = rs.getString(2);
				String name = rs.getString(3);
				int money = rs.getInt(4);
				
				System.out.println("---------------");
				System.out.println("계좌번호:"+acc);
				System.out.println("이     름:"+name);
				System.out.println("금     액:"+money);
				System.out.println("---------------");
			}
			
			stmt = null;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public int getcnt() {
		try {

		int cnt=0;
		String sql = "SELECT * FROM banking_tb";
		stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		
		while(rs.next()) {
			cnt++;
		}
			return cnt;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return -1;
		
	}
	public Account[] init() {
		try {
			String sql = "SELECT * FROM banking_tb";
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			int cnt=0;
			Account[] acc1 = new Account[50];
			while(rs.next()) {
	
				String acc = rs.getString(2);
				String name = rs.getString(3);
				int money = rs.getInt(4);
				
				acc1[cnt] = new Account(acc,name,money);
				
				cnt++;	
			}
	
			stmt = null;
			return acc1;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}	
}	
