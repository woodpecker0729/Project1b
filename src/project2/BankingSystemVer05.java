package project2;

import java.util.Scanner;

import project2.ver05.Account;
import project2.ver05.MenuChoice;
import project2.ver05.DBfunc;

public class BankingSystemVer05 implements MenuChoice{
	static Account[] account = new Account[50];
	static int cntAcc=0;
	static Scanner scanner;
	static boolean exit=true;
	static DBfunc db = new DBfunc();
	
	
	static void showMenu() {
		System.out.println("-----Menu-----");
		System.out.println("1.계좌계설\n2.입    금\n3.출    금\n4.계좌정보출력\n5.프로그램종료");
	}
	
	static void makeAccount() {
		if(cntAcc>=50) {
			System.out.println("계좌개설 불가");
			return;
		}
		System.out.println("***신규계좌개설***");
		System.out.print("계좌번호:");
		String accountnum = scanner.nextLine();
		System.out.print("고객이름:");
		String name = scanner.nextLine();
		System.out.print("잔고:");
		int money = scanner.nextInt();
		
		if(db.insert(accountnum,name,money)>0)
		{
		scanner.nextLine();
		account[cntAcc] = new Account(accountnum,name,money);
		cntAcc++;
		}
	}
	static void deposit() {
		System.out.println("***입   금***");
		System.out.println("계좌번호와 입금할 금액 입력");
		System.out.print("계좌번호:");
		String acc1 = scanner.nextLine();
		System.out.print("입금액:");
		int money = scanner.nextInt();
		scanner.nextLine();

		
		for(int i=0;i<cntAcc;i++) {
			if(account[i].account.equals(acc1)) {
				account[i].depositMoney(money);
				money=account[i].money;
				break;
			}
		}
		db.update(acc1,money);
		System.out.println("입금이 완료되었습니다.");
	}
	static void withdraw() {
		System.out.println("***출   금***");
		System.out.println("계좌번호와 출금할 금액 입력");
		System.out.print("계좌번호:");
		String acc2 = scanner.nextLine();
		System.out.print("출금액:");
		int money2 = scanner.nextInt();
		scanner.nextLine();
		for(int i=0;i<cntAcc;i++) {
			if(account[i].account.equals(acc2)) {
				account[i].withdrawMoney(money2);
				money2=account[i].money;
				break;
			}
		}
		db.update(acc2, money2);
		System.out.println("출금이 완료되었습니다.");
		
	}
	static void inquire() {
		db.select();
		System.out.println("전체계좌정보 출력이 완료되었습니다.");
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		scanner = new Scanner(System.in);
		account=db.init();
		cntAcc=db.getcnt();
		while(exit) {
			showMenu();
			System.out.print("선택:");
			int menu = scanner.nextInt();
			scanner.nextLine();
			switch(menu) {
				case MAKE:
					makeAccount();
					break;
				case DEPOSIT:
					deposit();
					break;
				case WITHDRAW:
					withdraw();
					break;
				case INQUIRE:
					inquire();
					break;
				case EXIT:
					System.out.println("프로그램을 종료하겠습니다.");
					exit=false;
					break;
				
			}
		}
	}

}
