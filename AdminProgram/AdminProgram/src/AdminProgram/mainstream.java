package AdminProgram;

import java.util.Scanner;

public class mainstream {
	public static LoginProcess log = new LoginProcess(); 
	public static AdminAccessor admin = new AdminAccessor();
	public static AccountAccessor account = new AccountAccessor();
	public static ReservationAccessor reserve = new ReservationAccessor();
	public static RestaurantAccessor restaurant = new RestaurantAccessor();
	public static Scanner Input = new Scanner(System.in);
	
	public static void main(String[] args) {
		//admin.getClass().getResourceAsStream("admin.txt");
		admin.LoadFile();
		account.LoadFile();
		reserve.LoadFile();
		restaurant.LoadFile();
		//System.out.println("  " + admin.ID + " " + admin.PW);

		log.prompt();
		while(true) {
			clearScreen();
			System.out.println("[관리자 프로그램]");
			System.out.println("\t1. 식당 목록 수정");
			System.out.println("\t2. 계정 데이터 파일 수정");
			System.out.println("\t3. 관리자 계정 설정");
			System.out.println("\t4. 프로그램 종료");
			System.out.print("수행할 작업을 선택해주세요: ");
			String input = Input.nextLine();
			switch(input) {
			case "1":
				restaurant.prompt();
				break;
			case "2":{
				account.prompt();
				break;
			}
			case "3":{
				admin.Modify();
				break;
			}
			case "4":{
				System.out.println("\n프로그램을 종료합니다.");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
				}
				mainstream.clearScreen();
				System.exit(0);
				break;
			}
			default:
				continue;
			}
			//break;
		}
	}
	
	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
}
