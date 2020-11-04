package project2.ver05;

import java.util.Scanner;

public class Account {
	public String account;
	public String name;
	
	public int money;
	
	
	public Account(String ac,String na,int mo) {
		// TODO Auto-generated constructor stub
		account = ac;
		name = na;
		money = mo;
	}
	
	public void depositMoney(int money) {
		this.money += money;
		
	}
	public void withdrawMoney(int money) {
		this.money -= money;
		
	}
	
	public void showAccInfo() {
		System.out.println("-----------------");
		System.out.println("계좌번호: "+account);
		System.out.println("고객이름: "+name);
		System.out.println("잔     고: "+money);
		System.out.println("-----------------");
	}
	
}
