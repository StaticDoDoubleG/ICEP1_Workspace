package ReservationProgram;

//import java.util.InputMismatchException;
import java.util.Scanner;

public class MainMenu {
	Scanner scanner = new Scanner(System.in);
	
	public int prompt() {

		//AccountDataFileManager
		
		int menuChoiceInput = 0;
		// boolean res = false;
		AccountData accountData = mainstream.accountDFM.getAccountData(mainstream.currentUserIndex);
		String userNameString = accountData.name;

		System.out.println(userNameString + "님 환영합니다. 수행할 작업을 선택하세요:");
		System.out.println("\t1. 좌석 예약하기");
		System.out.println("\t2. 내 예약 내역 확인하기");
		System.out.println("\t3. 좌석 예약 취소하기");
		System.out.println("\t4. 개인정보수정");
		System.out.println("\t5. 잔액 추가");
		System.out.println("\t6. 로그아웃");
		System.out.println("\t7. 종료");
		System.out.print("Choice: ");
		String input = scanner.nextLine();

		switch (input) {
		case "1":
		case "2":
		case "3":
		case "4":
		case "5":
		case "6":
		case "7":
			menuChoiceInput = Integer.parseInt(input);;
			return menuChoiceInput;
		default:
			return -2;
		}

	}
}
