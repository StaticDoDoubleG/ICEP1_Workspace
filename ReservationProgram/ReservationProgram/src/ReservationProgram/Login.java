package ReservationProgram;

import java.util.Scanner;

public class Login {
	AccountDataFileManager accountDFM = new AccountDataFileManager();
	Scanner scanner = new Scanner(System.in);
	String idInput;
	String passwordInput;

	public Login(AccountDataFileManager accountData) {
		this.accountDFM = accountData;
	}

	public int prompt() {
		boolean accountFound = false;
		do {
			mainstream.clearScreen();
			System.out.print("[로그인] 아이디를 입력하세요 (c 입력 시 초기 화면으로 돌아가기) : ");
			idInput = scanner.nextLine();
			if (idInput.equals("c") || idInput.equals("C")) {
				return -1;
			}else {
				System.out.print("[로그인] 비밀번호를 입력하세요 (c 입력 시 초기 화면으로 돌아가기) : ");
				passwordInput = scanner.nextLine();
				if (passwordInput.equals("c") || passwordInput.equals("C")) {
					return -1;
				}
			}
			//System.out.println("OverFlow");
			int numOfRec = accountDFM.getNumberOfRecords();
			//System.out.println("as " + numOfRec);
			int userIndex = -2;
			for (int i = 0; i < numOfRec; i++) {
				//System.out.println("as " + i);
				AccountData accountData = accountDFM.getAccountData(i);
				if (accountData.id.equals(idInput) && accountData.pw.equals(passwordInput)) {
					accountFound = true;
					userIndex = i;
					break;
				}
			}

			if (accountFound) {
				System.out.println("[로그인 성공]");
				return userIndex;
			} else {
				System.out.println("[로그인 실패] 입력하신 아이디, 비밀번호와 일치하는 계정을 찾을 수 없습니다.\n");
				accountFound = false;
			}
		} while (accountFound == false);
		return -1;
	}
}
