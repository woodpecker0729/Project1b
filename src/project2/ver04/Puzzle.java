package project2.ver04;

import java.util.Random;
import java.util.Scanner;

public class Puzzle {
	Scanner scanner = new Scanner(System.in);
	char[] puzzle = { '1', '2', '3', '4', '5', '6', '7', '8', 'x' };
	char[] puzzle2 = { '1', '2', '3', '4', '5', '6', '7', '8', 'x' };
	boolean end = true;
	Random random = new Random();
	int[] arr = new int[9];
	int idx = 8;

	public char[] move(char[] puz, int idx, String str) {
		char temp = puz[idx];
		switch (str) {
		case "a":
			puz[idx] = puz[idx + 1];
			puz[idx + 1] = temp;
			idx += 1;
			break;
		case "d":
			puz[idx] = puz[idx - 1];
			puz[idx - 1] = temp;
			idx -= 1;
			break;
		case "w":
			puz[idx] = puz[idx + 3];
			puz[idx + 3] = temp;
			idx += 3;
			break;
		case "s":
			puz[idx] = puz[idx - 3];
			puz[idx - 3] = temp;
			idx -= 3;
			break;
		}
		return puz;
	}

	public void showpuzz(char[] puzzle) {
		for (int i = 0; i < 9; i++) {
			if (i > 0 && i % 3 == 0)
				System.out.println();
			System.out.print(puzzle[i] + " ");
		}
		System.out.println();
	}

	public boolean end(char[] puz1, char[] puz2) {
		for (int i = 0; i < 9; i++) {
			if (!(puz1[i] == puz2[i]))
				return true;
		}
		System.out.println("--완성--");
		return false;
	}

	public void initialize() {
		end = true;
		for (int i = 0; i < 3; i++) {
			int rand = random.nextInt(4) + 1;
			String a = " ";
			switch (rand) {
			case 1:
				a = "a";
				break;
			case 2:
				a = "d";
				break;
			case 3:
				a = "w";
				break;
			case 4:
				a = "s";
				break;
			}
			switch (a) {
			case "a":
				if (idx == 2 || idx == 5 || idx == 8) i--;
				else puzzle = move(puzzle, idx, a);
				break;
			case "d":
				if (idx == 0 || idx == 3 || idx == 6) i--;
				else puzzle = move(puzzle, idx, a);
				break;
			case "w":
				if (idx == 6 || idx == 7 || idx == 8) i--;
				else puzzle = move(puzzle, idx, a);
				break;
			case "s":
				if (idx == 0 || idx == 1 || idx == 2) i--;
				else puzzle = move(puzzle, idx, a);
				break;
			}
			for (int j = 0; j < 9; j++) {
				if (puzzle[j] == 'x')
					idx = j;
			}
		}
		
		for (int i = 0; i < 9; i++) {
			if (puzzle[i] == 'x')
				idx = i;
			if (i > 0 && i % 3 == 0)
				System.out.println();
			System.out.print(puzzle[i] + " ");
		}
		System.out.println();
		
	}

	public void game() {
		// TODO Auto-generated method stub
		while (true) {
			initialize();
			while (end) {
				System.out.print("[이동] a:Left d:Right w:Up s:Down\n[종료] x:Exit\n 키를 입력해주세요:");
				String ch = scanner.nextLine();
				switch (ch) {
				case "a":
					if (idx == 2 || idx == 5 || idx == 8) System.out.println("******이동불가******");
					else puzzle = move(puzzle, idx, ch);
					break;
				case "d":
					if (idx == 0 || idx == 3 || idx == 6) System.out.println("******이동불가******");
					else puzzle = move(puzzle, idx, ch);
					break;
				case "w":
					if (idx == 6 || idx == 7 || idx == 8) System.out.println("******이동불가******");
					else puzzle = move(puzzle, idx, ch);
					break;
				case "s":
					if (idx == 0 || idx == 1 || idx == 2) System.out.println("******이동불가******");
					else puzzle = move(puzzle, idx, ch);
					break;
				case "x":
					System.out.println("게임을 종료합니다");
					return;
				default:
					System.out.println("다시입력하세요");
				}
				showpuzz(puzzle);
				for (int i = 0; i < 9; i++) {
					if (puzzle[i] == 'x')
						idx = i;
				}
				end = end(puzzle, puzzle2);
			}

			System.out.println("다시하시겠습니까? y입력 시 재시작, 나머지는 종료");
			String str2 = scanner.nextLine();
			if (str2.equals("y")) {
				end = true;

			} else {
				System.out.println("게임을 종료합니다.");
				return;
			}

		}
	}
}