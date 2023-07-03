package StaffProgram;

import java.util.ArrayList;
import java.util.Scanner;

public class mainstream {

	public static RestLogin restLogin = new RestLogin();
	public static MainMenu mainMenu = new MainMenu();
	public static RestaurantTableEditor rEditor = new RestaurantTableEditor();
	public static RestaurantMenuEditor mEditor = new RestaurantMenuEditor();
	public static int currentRestaurantIndex = -1;
	public static int menuChoice, tableEditChoice = 0, menuEditChoice = 0;
	public static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {

		DBManager.restaurantDataInit();

		while (true) {
			int loginResult = restLogin.prompt();
			if (loginResult >= 0) {
				currentRestaurantIndex = loginResult;
				DBManager.restaurantReservDataInit();
				DBManager.restaurantTablesDataInit();
				break;
			} else {
				continue;
			}
		}
		while (true) {
			menuChoice = mainMenu.prompt();
			switch (menuChoice) {
			case 1: {
				boolean isC = false;
				while (true) {
					System.out.print("[예약 내역 확인] 예약 내역 확인을 원하는 날짜를 입력해주세요. 날짜 입력 형식은 다음 중 하나입니다.\n" + "YYYY/MM/DD\n"
							+ "YYYY.MM.DD\n" + "YYYY-MM-DD\n" + "YYYYMMDD\n" + "YY/MM/DD\n" + "YY.MM.DD\n"
							+ "YY-MM-DD\n" + "YYMMDD\n" + "(c 입력 시 메인 메뉴로 돌아가기): ");
					String inputDate = mainstream.scanner.nextLine();
					if (inputDate.equals("c")) {
						isC = true;
						break;
					}
					ArrayList<ReservationData> reservationData = DBManager.getReservationsByDate(inputDate);

					int i = 1;

					if (reservationData != null) {
						if (reservationData.isEmpty()) {
							System.out.println("[" + DBManager.checkDate(inputDate, true) + "]의 예약 내역 확인]\n"
									+ "해당하는 예약 내역이 없습니다.\n");
						} else {
							for (ReservationData reservation : reservationData) {
								String dateString = DBManager.checkDate(reservation.reservDate, true);
								if (dateString == null) {
									break;
								} else {
									System.out.println(i + "." + reservation.printOrder());
									i++;
								}
							}
						}
						continue;
					} else {
						continue;
					}

				}
				if (isC) {
					continue;
				}
			}
			case 2: {
				ArrayList<ReservationData> reservationData = DBManager.getReservationsByNum();
				int i = 1;

				if (reservationData != null) {
					if (reservationData.isEmpty()) {
						System.out.println("해당하는 예약 내역이 없습니다\n");
					} else {
						for (ReservationData reservation : reservationData) {
							System.out.println(i + "." + reservation.printOrder());
							i++;
						}
					}
					continue;
				} else {
					continue;
				}
			}
			case 3: {
				rEditor.printTableInfo();
				while (tableEditChoice < 1 || tableEditChoice > 3) {
					tableEditChoice = rEditor.prompt();
				}
				switch (tableEditChoice) {
				case 1: {
					// add Table
					rEditor.addTablePrompt();
					tableEditChoice = -1;
					break;
				}
				case 2: {
					// delete Table
					rEditor.deleteTablePrompt();
					tableEditChoice = -1;
					break;
				}
				case 3: {
					tableEditChoice = -1;
					break;
				}
				}

				continue;
			}
			case 4: {
				mEditor.printMenuInfo();
				while (menuEditChoice < 1 || menuEditChoice > 3) {
					menuEditChoice = mEditor.prompt();
				}
				switch (menuEditChoice) {
				case 1: {
					// add Table
					mEditor.addMenuItemPrompt();
					menuEditChoice = -1;
					break;
				}
				case 2: {
					// delete Table
					mEditor.deleteMenuItemPrompt();
					menuEditChoice = -1;
					break;
				}
				case 3: {
					break;
				}
				}

				continue;
			}
			case 5: {
				System.out.println("[식당 관계자 전용 프로그램] 프로그램을 종료합니다.");
				System.exit(0);
				break;
			}
			default:
				System.out.println("\n[오류] 1 이상 5 이하의 정수를 입력해 주세요\n");
				continue;
			}
			break;

		}
	}

}
