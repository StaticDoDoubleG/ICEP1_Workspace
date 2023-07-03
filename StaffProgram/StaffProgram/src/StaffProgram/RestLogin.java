package StaffProgram;

public class RestLogin {

	public int prompt() {
		while (true) {
			System.out.println("[식당 관계자 전용 프로그램] 로그인하려는 식당의 이름을 입력해주세요.");
			String restaurantName = mainstream.scanner.nextLine();
			Boolean restExists = DBManager.checkRestaurant(restaurantName);
			if (!restExists) {
				System.out.println("[오류] 입력하신 식당 이름을 찾을 수 없습니다.");
				continue;
			}

			System.out.println("비밀번호를 입력해주세요");
			String password = mainstream.scanner.nextLine();
			
			Boolean pwValid = DBManager.checkPassword(restaurantName, password);

			if (!pwValid) {
				System.out.println("[오류] 비밀번호가 입력과 일치하지 않습니다.");
				continue;
			}
			int rIndex = DBManager.getRIndexByName(restaurantName);

			return rIndex;
		}

	}
}
