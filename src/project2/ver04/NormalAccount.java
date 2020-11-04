package project2.ver04;

public class NormalAccount extends Account {
	public int interest;
	public NormalAccount(String ac, String na, int mo,int in) {
		account = ac;
		name = na;
		money = mo;
		interest = in;
		// TODO Auto-generated constructor stub
	}
	@Override
	public void depositMoney(int money) {
		this.money += (this.money*((double)interest/100))+money;
	}
	@Override
	public void showAccInfo() {
		System.out.println("-----------------");
		System.out.println("계좌번호: "+account);
		System.out.println("고객이름: "+name);
		System.out.println("잔     고: "+money);
		System.out.println("기본이자: "+interest+"%");
		System.out.println("-----------------");
	}
	public int getint() {
		return interest;
	}
}
