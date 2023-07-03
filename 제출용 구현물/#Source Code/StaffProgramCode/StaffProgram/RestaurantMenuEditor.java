package StaffProgram;

import java.util.Map.Entry;

public class RestaurantMenuEditor {
	private String regex = "^[a-zA-Z가-힣ㄱ-ㅎㅏ-ㅣ0-9]{1,15}$";

	public int prompt() {

		System.out.println("수행할 작업을 선택하세요.\n" + "1. 메뉴 추가\n" + "2. 메뉴 삭제\n" + "3. 메인 메뉴로 돌아가기");
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

	public void printMenuInfo() {
		System.out.println("[메뉴 정보 수정]\n" + "[현재 메뉴 정보]");
		System.out.println(DBManager.currentRestaurantData.menu.size() + "개의 메뉴가 있습니다.");
		for (Entry<Integer, MenuItem> entry : DBManager.currentRestaurantData.menu.entrySet()) {
			int menuNum = entry.getKey();
			MenuItem menuItem = entry.getValue();
			System.out.println("\t-" + menuItem.name + ": " + menuItem.price + "원");
		}
	}

	public int addMenuItemPrompt() {
		System.out.print("[메뉴 추가] 추가하려는 메뉴의 이름을 입력하세요. 메뉴 이름은 중복될 수 없으며, 다음 규칙을 따라야 합니다.\n"
				+ "\t 1. 문자열의 길이는 1 이상 15 미만이어야 합니다.\n" + "\t 2. 문자열 안에 특수문자를 포함하지 않아야 합니다.\n"
				+ "\t 3. 탭과 개행이 포함되지 않아야 합니다.\n" + "(c 입력 시 수행할 작업 선택 메뉴로 돌아가기) :");

//		System.out.println("jj");
		String menuName = mainstream.scanner.nextLine();
		boolean menuNameExists = true;
		if (menuName.matches(regex)) {
			for (Entry<Integer, MenuItem> entry : DBManager.currentRestaurantData.menu.entrySet()) {
				int menuNum = entry.getKey();
				MenuItem menuItem = entry.getValue();
				String menuItemName = menuItem.name;
				if (menuItemName.equals(menuName)) {
					menuNameExists = true;
					break;
				} else {
					menuNameExists = false;
				}
			}

			if (menuName.equals("c")) {
				System.out.println("back");
				return -1;
			} else if (!menuNameExists) {
				return addPricePromt(menuName);
			} else {
				System.out.println("[오류] 메뉴 이름은 중복될 수 없습니다.");
				return addMenuItemPrompt();
			}
		} else {
			System.out.println("[오류] 규칙에 맞지 않는 입력입니다.");
			return addMenuItemPrompt();
		}
	}

	public int addPricePromt(String menuName) {
		System.out.print("[메뉴 추가] 추가하려는 메뉴의 가격을 입력하세요. " + "(c 입력 시 수행할 작업 선택 메뉴로 돌아가기) :");
		String inputValue = mainstream.scanner.nextLine();
		boolean inputValueIsInt = false;
		int price = -1;
		try {
			price = Integer.parseInt(inputValue);
			inputValueIsInt = true;
		} catch (NumberFormatException nfe) {
			inputValueIsInt = false;
		}
		if (inputValueIsInt) {
			if (price < 1) {
				System.out.println("[오류] 1 이상의 정수를 입력해 주세요.");
				return addPricePromt(menuName);
			} else {
				MenuItem newMI = new MenuItem(menuName, price);
				DBManager.currentRestaurantData.menu.put(DBManager.currentRestaurantData.menu.size() + 1, newMI);

				DBManager.saveRestaurantMenu();
				System.out.println("메뉴 추가에 성공했습니다");
			}
		} else if (inputValue.equals("c")) {
			return -1;
		} else {
			System.out.println("[오류] 1 이상의 정수를 입력해 주세요.");
			return addPricePromt(menuName);
		}
		return -1;

	}

	public int deleteMenuItemPrompt() {
		System.out.print("[좌석 삭제] 삭제하려는 메뉴의 이름을 입력하세요. (c 입력 시 수행할 작업 선택 메뉴로 돌아가기) :");

		String menuName = mainstream.scanner.nextLine();

		boolean menuNameExists = false;
		int mNum = -1;
		if (menuName.matches(regex)) {
			for (Entry<Integer, MenuItem> entry : DBManager.currentRestaurantData.menu.entrySet()) {
				int menuNum = entry.getKey();
				MenuItem menuItem = entry.getValue();
				String menuItemName = menuItem.name;
				if (menuItemName.equals(menuName)) {
					menuNameExists = true;
					mNum = menuNum;
					break;
				} else {
					menuNameExists = false;
				}
			}

			if (menuName.equals("c")) {
				System.out.println("back");
				return -1;
			} else if (menuNameExists) {
				DBManager.currentRestaurantData.menu.remove(mNum);
				DBManager.saveRestaurantMenu();
				System.out.println("메뉴를 삭제했습니다.");
				return 1;
			} else {
				System.out.println("[오류] 존재하지 않는 테이블 번호입니다.");
				return deleteMenuItemPrompt();
			}
		} else {
			System.out.println("[오류] 형식에 맞지 않는 입력입니다.");
			return addPricePromt(menuName);
		}
	}
}
