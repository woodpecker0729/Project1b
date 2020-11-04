package project2.ver02;

import java.util.Scanner;

import project2.ver02.Account;

public class AccountManager {
	public Account[] account = new Account[50];
	public int cntAcc = 0;
	Scanner scanner = new Scanner(System.in);
	public void showMenu() {
		System.out.println("-----Menu-----");
		System.out.println("1.계좌개설\n2.입    금\n3.출    금\n4.계좌정보출력\n5.프로그램종료");
	}
	
	public void makeAccount() {
		if(cntAcc>=50) {
			System.out.println("계좌개설 불가");
			return;
		}
		String accountnum, name,interest2;
		int money,interest;
		System.out.println("-----계좌선택-----");
		System.out.print("1.보통계좌\n2.신용신뢰계좌\n선택");
		
		int accmenu = scanner.nextInt();
		scanner.nextLine();
		
		System.out.println("***신규계좌개설***");
		System.out.print("계좌번호:");
		accountnum = scanner.nextLine();
		System.out.print("고객이름:");
		name = scanner.nextLine();
		System.out.print("잔고:");
		money = scanner.nextInt();
		scanner.nextLine();
		if(accmenu==1) {
			System.out.println("기본이자%(정수형태로입력):");
			interest = scanner.nextInt();
			scanner.nextLine();
			account[cntAcc] = new NormalAccount(accountnum,name,money,interest);
		}
		if(accmenu==2) {
			System.out.print("기본이자%(정수형태로입력):");
			interest = scanner.nextInt();
			scanner.nextLine();
			System.out.print("신용등급(A,B,C등급): ");
			interest2 = scanner.nextLine();
			account[cntAcc] = new HighCreditAccount(accountnum,name,money,interest,interest2);
		}
		
		cntAcc++;
	}
	public void deposit() {
		
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
			}
		}
		System.out.println("입금이 완료되었습니다.");
	}

	public void withdraw() {
		// TODO Auto-generated method stub
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
			}
		}
		System.out.println("출금이 완료되었습니다.");
		
	}
	public void inquire() {
		for(int i=0;i<cntAcc;i++) {
			account[i].showAccInfo();
		}
		System.out.println("전체계좌정보 출력이 완료되었습니다.");
	}
}
