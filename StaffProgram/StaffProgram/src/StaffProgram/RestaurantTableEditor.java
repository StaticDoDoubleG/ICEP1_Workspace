package StaffProgram;

import java.util.Map.Entry;

public class RestaurantTableEditor {

	public void printTableInfo() {
		System.out.println("[좌석 정보 수정]\n"
				+ "[현재 좌석 정보]\n");
		System.out.println(DBManager.tablesDatas.size() + "개의 테이블이 있습니다.");
		for (Entry<Integer, Integer> entry : DBManager.tablesDatas.entrySet()) {
			int table = entry.getKey();
			int seats = entry.getValue();
			System.out.println("\t-" + table + "번 테이블: " + seats + "명");
		}
	}
	
	public int prompt() {

		System.out.println("수행할 작업을 선택하세요.\n" + "1. 좌석 추가\n" + "2. 좌석 삭제\n" + "3. 메인 메뉴로 돌아가기");
		String choice = mainstream.scanner.nextLine();
		int num = -1;
		boolean choiceIsInt = false;
		try {
			num = Integer.parseInt(choice);
			choiceIsInt = true;
		} catch (NumberFormatException nfe) {
			choiceIsInt = false;
		}
		if (choiceIsInt) {
			
			return num;
		} else {
			System.out.println("[오류] 1 이상 3 이하의 정수를 입력해 주세요\n");
			return prompt();
		}
		
	}

	public int addTablePrompt() {
		System.out.println("[좌석 추가] 추가하려는 좌석의 좌석 번호를 입력하세요. 좌석 번호는 중복될 수 없습니다. " + "\n(c 입력 시 수행할 작업 선택 메뉴로 돌아가기): ");

		String tableNum = mainstream.scanner.nextLine();
		boolean tableNumIsInt = false;
		int num = -1;
		try {
			num = Integer.parseInt(tableNum);
			tableNumIsInt = true;
		} catch (NumberFormatException nfe) {
			tableNumIsInt = false;
		}
		if (tableNumIsInt) {
			if (DBManager.tablesDatas.get(num) != null) {
				System.out.println("[오류] 테이블 번호는 중복될 수 없습니다.");
				return addTablePrompt();
			} else {
				return addSeatsPromt(num);
			}
		} else if (tableNum.equals("c")) {
			System.out.println("back");
			return -1;
		} else {
			System.out.println("[오류] 0 이상의 정수를 입력해 주세요.");
			return addTablePrompt();
		}
	}

	public int addSeatsPromt(int tableNum) {
		System.out.println("[좌석 추가] 추가하려는 좌석의 수용 가능 인원 수를 입력하세요. " + "(c 입력 시 수행할 작업 선택 메뉴로 돌아가기) :");
		String seatNum = mainstream.scanner.nextLine();
		boolean seatNumIsInt = false;
		int num = -1;
		try {
			num = Integer.parseInt(seatNum);
			seatNumIsInt = true;
		} catch (NumberFormatException nfe) {
			seatNumIsInt = false;
		}
		if (seatNumIsInt) {
			if (num < 1) {
				System.out.println("[오류] 1 이상의 정수를 입력해 주세요.");
				return addSeatsPromt(tableNum);
			} else {
				DBManager.tablesDatas.put(tableNum, num);
				DBManager.saveRestaurantTables();
			}
		} else if (seatNum.equals("c")) {
			return -1;
		} else {
			System.out.println("[오류] 1 이상의 정수를 입력해 주세요.");
			return addSeatsPromt(tableNum);
		}
		return -1;

	}

	public int deleteTablePrompt() {
		System.out.println("[좌석 삭제] 삭제하려는 좌석의 좌석 번호를 입력하세요. (c 입력 시 수행할 작업 선택 메뉴로 돌아가기) :");

		String tableNum = mainstream.scanner.nextLine();
		boolean tableNumIsInt = false;
		int num = -1;
		try {
			num = Integer.parseInt(tableNum);
			tableNumIsInt = true;
		} catch (NumberFormatException nfe) {
			tableNumIsInt = false;
		}
		if (tableNumIsInt) {
			if (DBManager.tablesDatas.get(num) != null) {
				DBManager.tablesDatas.remove(num);
				DBManager.saveRestaurantTables();
				System.out.println("좌석을 삭제했습니다.");
				return 1;
			} else {
				System.out.println("[오류] 존재하지 않는 테이블 번호입니다.");
				return deleteTablePrompt();
			}
		} else if (tableNum.equals("c")) {
			System.out.println("back");
			return -1;
		} else {
			System.out.println("[오류] 0 이상의 정수를 입력해 주세요.");
			return deleteTablePrompt();
		}
	}

}
