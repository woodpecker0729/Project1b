package project2.ver04;

import java.io.Serializable;
import java.util.Scanner;

public abstract class Account implements Serializable{
	public String account;
	public String name;
	public int money;
	
	@Override
	public int hashCode() {
		return account.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		Account acc = (Account) obj;
		if(acc.account.equals(this.account)) {
			return true;
		}
		else return false;
	}
	int getint() {return 1;};
	String getint2() {return "";};
	public void depositMoney(int money) {}
	public void withdrawMoney(int money) {
		this.money -= money;
	}
	public void showAccInfo() {}
	
}
