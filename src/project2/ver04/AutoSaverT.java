package project2.ver04;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;

public class AutoSaverT extends Thread {
	AccountManager am;
	public AutoSaverT(AccountManager am) {
		// TODO Auto-generated constructor stub
		this.am = am;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
//			try {
//				
//				PrintWriter out = new PrintWriter(new FileWriter("src/project2/AutoSaveAccount.txt"));
//				Iterator<Account> it = set.iterator();
//				while(it.hasNext()) {
//					Account acc = it.next();
//					if(acc instanceof NormalAccount) {
//						out.printf("계좌번호:%s,고객이름:%s,잔고:%s,기본이자:%d\n",acc.account,acc.name,acc.money,acc.getint());;
//					}
//					else if(acc instanceof HighCreditAccount) {
//						out.printf("계좌번호:%s,고객이름:%s,잔고:%s,기본이자:%d,신용등급:%s\n",acc.account,acc.name,acc.money,acc.getint(),acc.getint2());
//					}
//				}
//				out.close();
//				System.out.println("오토세이브");
//				sleep(5000);
//			}
//			catch(FileNotFoundException e) {
//				System.out.println("저장된 파일 없음");
//			}
//			catch(IOException e) {
//				System.out.println("뭔가없음");
//			} 
//			catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				System.out.println("자동 저장 중 오류 발생");
//			}
			try {
				am.save();
				sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("자동저장off됨");
				break;
			}
		}
	}
}
