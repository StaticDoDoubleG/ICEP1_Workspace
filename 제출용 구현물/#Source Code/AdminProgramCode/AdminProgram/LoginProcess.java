package AdminProgram;

public class LoginProcess extends mainstream{
	String id, pw;
	
	public int prompt() {
		int attempt = 0;
		boolean accountFound = false;
		do {
			mainstream.clearScreen();
			System.out.print("[관리자 로그인] 아이디를 입력하세요 (c 입력 시 프로그램 종료) : ");
			id = Input.nextLine();
			if (id.equals("c") || id.equals("C")) {
				System.out.println("\n프로그램을 종료합니다.");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
				}
				mainstream.clearScreen();
				System.exit(0);
			}else {
				System.out.print("[관리자 로그인] 비밀번호를 입력하세요 (c 입력 시 프로그램 종료) : ");
				pw = Input.nextLine();
				if (pw.equals("c") || pw.equals("C")) {
					System.out.println("\n프로그램을 종료합니다.");
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
					}
					mainstream.clearScreen();
					System.exit(0);
				}
			}
			if(id.compareTo(admin.ID) == 0 && pw.compareTo(admin.PW) ==0) {
				//System.out.println("[로그인 성공]");
				accountFound = true;
			}else {
				System.out.println("\n[로그인 " + (attempt + 1) + "회 실패] " + "\u001B[31m" +  "입력하신 아이디, 비밀번호는 관리자 계정의 아이디, 비밀번호와 일치하지 않습니다.\n" + "\u001B[0m");
			}
			
			if (accountFound) {
				System.out.println("[로그인 성공]");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
				return 1;
			} else {
				attempt++;
				if(attempt > 5) {
					System.out.println("\u001B[31m" + "[경고] 아이디/비밀번호 불일치 횟수가 5회를 넘었습니다. 보안을 위하여 프로그램을 종료합니다." + "\u001B[0m");
					System.exit(0);
				}
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}
		} while (accountFound == false);
		return -1;
	}
}
