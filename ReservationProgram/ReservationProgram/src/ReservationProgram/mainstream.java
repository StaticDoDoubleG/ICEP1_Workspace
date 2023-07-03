package ReservationProgram;

import java.io.IOException;

public class mainstream {
	public static MainMenu mainMenu = new MainMenu();
	public static LoginRegistrationChoice loginRegistrationChoice = new LoginRegistrationChoice();
	static AccountDataFileManager accountDFM = new AccountDataFileManager();
	static TableDataFileManager tableDFM = new TableDataFileManager();
	static ReservationDataFileManager reservationDFM = new ReservationDataFileManager();
	static RestaurantDataFileManager restaurantDFM = new RestaurantDataFileManager();
	
	static int currentUserIndex = -1;

	public static void main(String[] args) {
		accountDFM.LoadDataFile("Account.txt");
		tableDFM.LoadDataFile("tabledata.txt");
		reservationDFM.LoadDataFile("Reservation.txt");
		restaurantDFM.LoadDataFile();
		VirtualTime vt = new VirtualTime();
		vt.prompt();
		int userChoice, menuChoice;
		
		while(true) {
			userChoice = loginRegistrationChoice.prompt();
			if (userChoice == 1) {
				Login login = new Login(accountDFM);
				int loginResult = login.prompt();
				if (loginResult >= 0) {
					currentUserIndex = loginResult;
					//System.out.println("사용자 인덱스: " + currentUserIndex);
					userChoice = 1;
				} else {
					//userChoice = loginResult;
					continue;
				}
			} else if (userChoice == 2) {
				Registration registration = new Registration(accountDFM);
				//userChoice = registration.prompt();
				registration.prompt();
				continue;
			} else if (userChoice == 3) {
				System.out.println("식당 좌석 예약 프로그램을 종료합니다.");
				return;
			} else {
				continue;
			}
			while(true) {
				menuChoice = mainMenu.prompt();
				switch (menuChoice) {
				case 1: {
					//좌석 예약하기 reserve
					Reservation reservation = new Reservation(currentUserIndex, vt, accountDFM, reservationDFM, tableDFM, restaurantDFM);
					reservation.prompt();
					continue;
				}
				case 2: {
					//내 예약 내역 확인하기 check reservations
					try {
						MyReservation check = new MyReservation(currentUserIndex, vt, restaurantDFM);
						check.print();
					} catch (IOException e) {
						System.out.println("[내 예약 내역 확인]\r\n"
								+ "예약 내역이 없습니다.\r\n"+ "");
					}
					continue;
				}
				case 3: {
					//좌석 예약 취소하기 cancel reservation
					ReservationCancel reservationCancel = new ReservationCancel(currentUserIndex, vt, reservationDFM, accountDFM, restaurantDFM);
					reservationCancel.prompt();
					continue;
				}
				case 4: {
					PersonalDataModification personalDataModification = new PersonalDataModification(currentUserIndex, accountDFM);
					personalDataModification.prompt();
					menuChoice = 4;
					continue;
				}
				case 5: {
					PersonalDataModification personalDataModification = new PersonalDataModification(currentUserIndex, accountDFM);
					personalDataModification.vCashPrompt();
					continue;
				}
				case 6: {
					break;
				}
				case 7:{
					System.exit(0);
					break;
				}
				default:
					System.out.println("\n[오류] 1 이상 6 이하의 정수를 입력해 주세요\n");
					continue;
				}
				break;
			}
		}
	}
	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

}

