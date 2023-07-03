package ReservationProgram;

import java.util.InputMismatchException;
import java.util.Scanner;

public class LoginRegistrationChoice {
	
	private Scanner sc = new Scanner(System.in);
	
	public int prompt() {
		int c = 0;
		while(c < 1 || c > 3) {
			System.out.println("\n[식당 좌석 예약 프로그램]");
			System.out.println("수행할 작업을 선택하세요.");
			System.out.println("\t1. 로그인\n\t2. 회원가입\n\t3. 프로그램 종료");
			try{
				c = sc.nextInt();
			}catch(InputMismatchException e) {
				sc.nextLine();
			}
			if(c == 1 || c == 2 || c == 3)
				return c;
			else {
				System.out.println("[오류] 1 이상 3 이하의 정수를 입력해 주세요.\n");
			}
		}
		return -2;
	}
	
}
