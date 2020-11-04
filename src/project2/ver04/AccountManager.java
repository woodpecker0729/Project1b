package project2.ver04;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

public class AccountManager {
	HashSet<Account> set = new HashSet<>();
	ObjectInputStream in = null;
	ObjectOutputStream out = null;
	Scanner scanner = new Scanner(System.in);
	Puzzle puzzle = new Puzzle();
	public void showMenu() {
		System.out.println("------------------------------Menu-------------------------------");
		System.out.println("1.계좌개설 2.입    금 3.출    금 4.계좌정보출력 5.저장옵션 6.슬라이딩퍼즐 7.프로그램종료");
		System.out.println("-----------------------------------------------------------------");
	}
	public void puzzle() {
		puzzle.game();
	}
	public AccountManager() {
		// TODO Auto-generated constructor stub
		try {
			
			in = new ObjectInputStream(new FileInputStream("src/project2/account.obj"));
			set=(HashSet<Account>)in.readObject();
		}
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("클래스없음");
		}
		catch(FileNotFoundException e) {
			System.out.println("저장된 파일 없음");
		}catch(IOException e) {
			e.printStackTrace();
		} 
		
	}
	public void save() {
		try {
			PrintWriter out = new PrintWriter(new FileWriter("src/project2/AutoSaveAccount.txt"));
			Iterator<Account> it = set.iterator();
			while(it.hasNext()) {
				Account acc = it.next();
				if(acc instanceof NormalAccount) {
					out.printf("계좌번호:%s,고객이름:%s,잔고:%s,기본이자:%d\n",acc.account,acc.name,acc.money,acc.getint());;
				}
				else if(acc instanceof HighCreditAccount) {
					out.printf("계좌번호:%s,고객이름:%s,잔고:%s,기본이자:%d,신용등급:%s\n",acc.account,acc.name,acc.money,acc.getint(),acc.getint2());
				}
			}
			out.close();
			
		}
		catch(FileNotFoundException e) {
			System.out.println("저장된 파일 없음");
		}
		catch(IOException e) {
			System.out.println("뭔가없음");
		} 
	}
	
	public void autosave(AutoSaverT auto) {
		
		System.out.println("1.자동저장On, 2.자동저장Off");
		try {
			int num = scanner.nextInt();
			scanner.nextLine();
			if(num==1) {
				if(auto.getState()==Thread.State.NEW) {
					auto.start();
					System.out.println("자동저장 ON");
				}
				else {
					System.out.println("이미 자동저장이 켜져있습니다");
				}
			}
			else if(num==2) {
				auto.interrupt();
			}
			else {
				System.out.println("잘못입력하셨습니다.");
			}
		}catch(InputMismatchException e) {
			scanner.nextLine();
			System.out.println("[취소]문자를 입력하셨습니다.");
		}
	}
	public void saveAccount() {
		try {
		out = new ObjectOutputStream(new FileOutputStream("src/project2/account.obj"));
		out.writeObject(set);
		out.close();
		}catch(FileNotFoundException e) {
			System.out.println("저장된 파일 없음");
		}catch(IOException e) {
			System.out.println("뭔가없음");
		}
	}
	public void makeAccount() {
		String accountnum, name,interest2;
		int money,interest;
		System.out.println("-------계좌선택------");
		System.out.println("1.보통계좌 2.신용신뢰계좌");
		System.out.println("-------------------");
		System.out.print("선택: ");
		try {
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
			System.out.print("기본이자%(정수형태로입력):");
			interest = scanner.nextInt();
			scanner.nextLine();
			NormalAccount newacc = new NormalAccount(accountnum,name,money,interest);
			if(!set.add(newacc)) {
				System.out.print("중복계좌발견됨. 덮어쓸까요? (y or n):");
				switch(scanner.nextLine()) {
				case "y":
					set.remove(newacc);
					set.add(newacc);
					System.out.println("계좌개설이 완료되었습니다.");
					break;
				case "n":
					System.out.println("계좌개설이 취소되었습니다.");
					break;
				default:
					System.out.println("잘못 입력하셨습니다. 취소되었습니다.");
					break;
				}
			}
		}
		if(accmenu==2) {
			System.out.print("기본이자%(정수형태로입력):");
			interest = scanner.nextInt();
			scanner.nextLine();
			System.out.print("신용등급(A,B,C등급): ");
			interest2 = scanner.nextLine();
			HighCreditAccount newacc2 = new HighCreditAccount(accountnum,name,money,interest,interest2);
			if(!set.add(newacc2)) {
				System.out.print("중복계좌발견됨. 덮어쓸까요? (y or n):");
				switch(scanner.nextLine()) {
				case "y":
					set.remove(newacc2);
					set.add(newacc2);
					System.out.println("계좌개설이 완료되었습니다.");
					break;
				case "n":
					System.out.println("계좌개설이 취소되었습니다.");
					break;
				default:
					System.out.println("잘못 입력하셨습니다. 취소되었습니다.");
					break;
				}
			}
		}
		}catch(InputMismatchException e) {
			scanner.nextLine();
			System.out.println("잘못입력하셨습니다.");
		}
	}
	public void deposit() {
		
		System.out.println("*******입   금*******");
		System.out.println("계좌번호와 입금할 금액 입력");
		System.out.print("계좌번호:");
		String acc1 = scanner.nextLine();
		System.out.print("입금액:");
		int money = 0;
		try {
			money = scanner.nextInt();
			scanner.nextLine();
			if(money<=0) {
				System.out.println("[취소]입금할 금액을 제대로 입력하세요.");
				return;
			}
			else if(money%500!=0) {
				System.out.println("[취소]입금은 500원단위로 가능합니다.");
				return;
			}
		}
		catch(InputMismatchException e) {
			scanner.nextLine();
			System.out.println("[취소]문자를 입력하셨습니다.");
			return;
		}
		Iterator<Account> ir = set.iterator();
		while(ir.hasNext()) {
			Account acc2 = ir.next();
			if(acc2.account.equals(acc1)) {
				acc2.depositMoney(money);
			}
		}
		System.out.println("입금이 완료되었습니다.");
	}
	public void withdraw() {
		// TODO Auto-generated method stub
		System.out.println("*******출   금*******");
		System.out.println("계좌번호와 출금할 금액 입력");
		System.out.print("계좌번호:");
		String acc2 = scanner.nextLine();
		System.out.print("출금액:");
		int money2 ;
		try {
			money2 = scanner.nextInt();
			scanner.nextLine();
				Iterator<Account> ir = set.iterator();
				while(ir.hasNext()) {
					Account acc3 = ir.next();
					if(acc3.account.equals(acc2)) {
						if(money2>acc3.money) {
							System.out.println("잔고가 부족합니다. 금액전체를 출금할까요? (예:y,아니오:n 입력) :");
							switch(scanner.nextLine()) {
								case "y": case "Y":
									System.out.println("전체 출금 완료");
									acc3.money = 0;
									return;
								case "n": case "N":
									System.out.println("취소되었습니다.");
									return;
								default:
									System.out.println("잘못 입력하셨습니다.");
									return;
							}
						}
						else if(money2<=0) {
							System.out.println("[취소]출금할 금액을 제대로 입력하세요.");
							return;
						}
						else if(money2%1000!=0) {
							System.out.println("[취소]출금은 1000원단위로 가능합니다.");
							return;
						}
						else acc3.withdrawMoney(money2);
					}
				}
				
			}
		catch(InputMismatchException e) {
			scanner.nextLine();
			System.out.println("[취소]문자를 입력하셨습니다.");
			return;
		}
		
	
		System.out.println("출금이 완료되었습니다.");
		
	}
	public void inquire() {
		Iterator<Account> it = set.iterator();
		while(it.hasNext()) {
			Account acc = it.next();
			acc.showAccInfo();
		}
//		for(int i=0;i<set.size();i++) {
//			accounts[i].showAccInfo();
//		}
		System.out.println("전체계좌정보 출력이 완료되었습니다.");
	}
}
