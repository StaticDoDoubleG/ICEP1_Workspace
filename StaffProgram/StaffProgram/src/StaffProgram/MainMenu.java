package StaffProgram;

public class MainMenu {
	public int prompt() {
		int menuChoiceInput = 0;
		System.out.println(DBManager.currentRestaurantData.name + "으로 로그인 되었습니다.");
		System.out.println("\t1. 예약 날짜로 예약 내역 확인");
		System.out.println("\t2. 예약 번호로 예약 내역 확인");
		System.out.println("\t3. 좌석 정보 수정");
		System.out.println("\t4. 메뉴 정보 수정");
		System.out.println("\t5. 프로그램 종료");
		System.out.print("수행할 작업을 선택해주세요: ");
		String input = mainstream.scanner.nextLine();
		switch (input) {
		case "1":
		case "2":
		case "3":
		case "4":
		case "5":
			menuChoiceInput = Integer.parseInt(input);
			return menuChoiceInput;
		default:
			return -2;
		}
	}
}
