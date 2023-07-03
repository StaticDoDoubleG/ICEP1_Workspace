package ReservationProgram;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class ReservationCancel {
	private ArrayList<ReservationData> reservationDataList;
	private int count = 0;// 사용자 예약 건수
	private Scanner s = new Scanner(System.in);
	Scanner sc = new Scanner(System.in);

	//2차 프로젝트에서 추가되는 멤버 및 상수
	public static final double pointRatio = 0.05;	//포인트 적립 비율
	
	private int userAccountIndex;
	private AccountDataFileManager accountData;
	private ReservationDataFileManager reservationData1;
	private RestaurantDataFileManager restaurantData;
	private VirtualTime virtualTime;
	/////////////
	
	ReservationCancel(int userAccountIndex, VirtualTime virtualTime, ReservationDataFileManager reservationData, AccountDataFileManager accountData, RestaurantDataFileManager restaurantData) {
		// int u=7;
		this.reservationData1 = reservationData;
		reservationDataList = reservationData1.getReservationData(userAccountIndex);
		this.userAccountIndex = userAccountIndex;
		this.accountData = accountData;
		this.restaurantData = restaurantData;
		this.virtualTime = virtualTime;
	}

	public int prompt() {
		reservationData1.fetchReservationStatus(virtualTime);
		reservationDataList = reservationData1.getReservationData(userAccountIndex);
		MyReservationData[] myReservationDataList = new MyReservationData[reservationDataList.size()];
		if (reservationDataList.size() == 0) {
			System.out.println("[예약 취소하기]\r\n" + "취소할 예약이 없습니다.\r\n" + "");
			return -1;
		} else {

			Comparator comp = (o1,o2)->{

				Time t1 = ((ReservationData)o1).time;
				Time t2 = ((ReservationData)o2).time;
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
				LocalDateTime dt1 = LocalDateTime.parse(t1.StringDate("-")+" "+t1.StringTime(), formatter);
				LocalDateTime dt2 = LocalDateTime.parse(t2.StringDate("-")+" "+t2.StringTime(), formatter);
				
				Duration duration = Duration.between(dt1, dt2);
				return (int)duration.getSeconds();
			};
			Collections.sort(reservationDataList, comp);
			count = 0;
			for (ReservationData reservationData : reservationDataList) {
				String temp = reservationData.time.StringDate("/");
				// temp = temp.replaceAll("(\\d{4})null(\\d{2})null(\\d{2})", "$1/$2/$2");

				System.out.println((count + 1) + "." + temp + " " + reservationData.time.StringTime());
				
				OrderedMenuInfo orderedMenu = reservationData.orderedMenu; 
		        MenuInfo menuInfo =this.restaurantData.getRestaurantData().get(reservationData.restaurantIndex).menuInfo;
				for (int a = 0; a < orderedMenu.menuIndex.length; a++) {
		        	String str = "";
		        	str += menuInfo.menuName[orderedMenu.menuIndex[a]] + " ";
		        	str += orderedMenu.orderedAmount[a];
		        	System.out.println("-" + str + "개");
		        }
				
				System.out.println("\n\t예약 인원:"
						+ reservationData.people + "\n\t테이블 번호:" + reservationData.tableNum + "\n\t예약 번호:"
						+ reservationData.reservationNum + "\n\t예약 상태: " + reservationData.status + "\n");
				MyReservationData myReservationData = new MyReservationData();
				myReservationData.indexInFile = count;
				myReservationData.data = reservationData;
				myReservationDataList[count] = myReservationData;
				count++;
			}
			System.out.print("[예약 취소하기]\r\n" + "취소하려는 예약의 순서를 입력하세요.\r\n" + "예약의 순서는 예약 날짜 앞에 표시된 번호를 말합니다.\r\n"
					+ "(c 입력 시 메인 메뉴로 돌아가기): ");
		}

		if (sc.hasNextInt()) {
			// 입력이 정수인 경우 처리
			int userInput = sc.nextInt();
			if (userInput >= 1 && userInput <= count) {

				int index = myReservationDataList[userInput - 1].indexInFile;
				MyReservationData Data = myReservationDataList[userInput - 1];
				if (!Data.data.status.equals("예정")) {
					System.out.println("[오류] 선택하신 예약은 만료되었거나 이미 취소된 예약입니다.\r\n");
					prompt();
				} else {
					int i = 0;
					while (i == 0) {
						i = 1;
						String temp = Data.data.time.StringDate("/");
						// temp = temp.replaceAll("(\\d{4})null(\\d{2})null(\\d{2})", "$1/$2/$2");

						/* System.out.println("식당명: " + RestaurantData.restaurantName);
		          System.out.println("주문한 메뉴:");
		          OrderedMenuInfo orderedMenu = reservationData.orderedMenu; 
		          MenuInfo menuInfo = RestaurantData.menuInfo;
		          for (int i = 0; i < orderedMenu.menuIndex.length; i++) {
		              String str = "";
		              str += menuInfo.menuName[orderedMenu.menuIndex[i]] + " ";
		              str += orderedMenu.orderedAmount[i];
		              System.out.println("-" + str + "개");
		          }*/
						System.out.println(index+1 + "." + temp + " " + Data.data.time.StringTime() + "\r\n\t");
						System.out.println("식당명: " + this.restaurantData.getRestaurantData().get(Data.data.restaurantIndex).restaurantName);
				        System.out.println("주문한 메뉴:");
				        OrderedMenuInfo orderedMenu = Data.data.orderedMenu; 
				        MenuInfo menuInfo =this.restaurantData.getRestaurantData().get(Data.data.restaurantIndex).menuInfo; 
				        for (int a = 0; a < orderedMenu.menuIndex.length; a++) {
				        	String str = "";
				        	str += menuInfo.menuName[orderedMenu.menuIndex[a]] + " ";
				        	str += orderedMenu.orderedAmount[a];
				        	System.out.println("-" + str + "개");
				        }
						System.out.println("예약 인원:"
								+ Data.data.people + "\r\n\t테이블 번호:" + Data.data.tableNum + "\r\n\t예약 번호:"
								+ Data.data.reservationNum + "\r\n\t예약 상태:" + Data.data.status);
						System.out.print("\r이 예약을 정말 취소하시겠습니까?(YES/NO):");
						String choice = s.nextLine();
						if (choice.equals("YES")) {
							//System.out.println("userindex:" + Data.data.userAccountIndex);
							//System.out.println("reservenum:" + Data.data.reservationNum);
							ReservationDataFileManager reservationData1 = new ReservationDataFileManager();
							reservationData1.CancelReservation(Data.data.reservationNum);// 사용자 의 예약번호 전달
							
							//totalMenuPrice 계산
							int totalMenuPrice = 0;
							int restaurantIndex = Data.data.restaurantIndex;		
							orderedMenu = Data.data.orderedMenu;
							
							menuInfo = this.restaurantData.getRestaurantData().get(restaurantIndex).menuInfo;
							for(int j=0;j<orderedMenu.menuIndex.length;j++) {
								totalMenuPrice+=menuInfo.menuPrice[orderedMenu.menuIndex[j]]*orderedMenu.orderedAmount[j];
							}
							accountData.ModifyAccountData(userAccountIndex, accountData.POINT, Integer.toString(accountData.getAccountData(userAccountIndex).point-(int)(Math.round(totalMenuPrice*pointRatio))));
							accountData.ModifyAccountData(userAccountIndex, accountData.VIRTUALCASH, Integer.toString(accountData.getAccountData(userAccountIndex).virtualCash+totalMenuPrice));

						} else if (choice.equals("NO")) {
							prompt();
						} else {
							System.out.println("[오류] YES, NO만 입력하세요");
							i = 0;
						}
					} // while
				}
			} else {
				System.out.println("[오류] 1 이상 " + count + " 이하의 정수를 입력하세요.\r\n");
			}

		} else {
			String userInput = sc.next();
			if (userInput.equals("c"))
				// sc.close();
				return -1;
		}

		// sc.close();

		return 0;
	}
}
