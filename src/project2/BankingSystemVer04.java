package project2;

import java.util.InputMismatchException;
import java.util.Scanner;


import project2.ver04.MenuChoice;
import project2.ver04.AccountManager;
import project2.ver04.AutoSaverT;
import project2.ver04.MenuSelectException;

public class BankingSystemVer04 implements MenuChoice{
	static AccountManager am = new AccountManager();
	static Scanner scanner;
	static boolean exit=true;
	static AutoSaverT auto = null; 
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		auto = new AutoSaverT(am); 
		auto.setDaemon(true);
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
			if(menu>7||menu<1) {
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
				case AUTOSAVE:
					if(auto.getState()==Thread.State.TERMINATED) {
						auto = new AutoSaverT(am);
						auto.setDaemon(true);
					}
					am.autosave(auto);
					break;
				case PUZZLE:
					am.puzzle();
					break;
				case EXIT:
					am.saveAccount();
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
