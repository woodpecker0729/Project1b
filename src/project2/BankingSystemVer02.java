package project2;

import java.util.InputMismatchException;
import java.util.Scanner;

import project2.ver02.Account;
import project2.ver02.MenuChoice;
import project2.ver02.AccountManager;
public class BankingSystemVer02 implements MenuChoice{
	
	static Scanner scanner;
	static boolean exit=true;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AccountManager am = new AccountManager();
		scanner = new Scanner(System.in);
		while(exit) {
			
			am.showMenu();
			System.out.print("선택:");
			int menu = scanner.nextInt();
			scanner.nextLine();
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
	}
}

