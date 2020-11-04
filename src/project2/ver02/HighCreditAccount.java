package project2.ver02;

public class HighCreditAccount extends Account {
	public int interest;
	public String interest2;
	public HighCreditAccount(String ac, String na, int mo, int in, String in2) {
		super(ac, na, mo);
		interest=in;
		interest2=in2;
	}
	@Override
	public void depositMoney(int money) {
		int in2=0;
		switch(interest2) {
		case "A": in2 = A;
		break;
		case "B": in2 = B;
		break;			
		case "C": in2 = C;
		break;
		}
		this.money += (this.money*((double)interest/100))+(this.money*((double)in2/100))+money;
		System.out.println("입금이 완료되었습니다.");
	}
	@Override
	public void showAccInfo() {
		System.out.println("-----------------");
		System.out.println("계좌번호: "+account);
		System.out.println("고객이름: "+name);
		System.out.println("잔     고: "+money);
		System.out.println("기본이자: "+interest+"%");
		System.out.println("신용등급: "+interest2);
		System.out.println("-----------------");
	}

	
}
