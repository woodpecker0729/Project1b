package project2;

import java.util.InputMismatchException;
import java.util.Scanner;


import project2.ver03.MenuChoice;
import project2.ver03.AccountManager;
import project2.ver03.MenuSelectException;

public class BankingSystemVer03 implements MenuChoice{
	static AccountManager am = new AccountManager();
	static Scanner scanner;
	static boolean exit=true;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		scanner = new Scanner(System.in);
		while(exit) {
			try {
			menuselect();
			}
			catch(MenuSelectException e) {
				System.out.println("1~5 사이의 정수를 입력하세요.");
			}
		}
	}
	public static void menuselect() throws MenuSelectException{
		try {
			am.showMenu();
			System.out.print("선택:");
			int menu = scanner.nextInt();
			scanner.nextLine();
			if(menu>5||menu<1) {
				throw new MenuSelectException();
			}
			switch(menu) {
				case MAKE:
					am.makeAccount();
					break;
				case DEPOSIT:
					am.deposit();
					break;
				case WITHDRAW:
					am.withdraw();
					break;
				case INQUIRE:
					am.inquire();
					break;
				case EXIT:
					System.out.println("프로그램을 종료하겠습니다.");
					exit=false;
					break;
			}
		}
		catch(InputMismatchException e) {
			scanner.nextLine();
			System.out.println("문자를 입력하셨습니다. 다시 입력하세요");
		}
		
	}

}
