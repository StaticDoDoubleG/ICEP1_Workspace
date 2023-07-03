package ReservationProgram;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MyReservation {
	private ArrayList<ReservationData> reservationDataList;
	private int userAccountIndex;
    private RestaurantDataFileManager restaurantData;
    private VirtualTime virtualTime;
        
    ReservationDataFileManager reservationData1;
	MyReservation(int userAccountIndex, VirtualTime virtualTime,RestaurantDataFileManager restaurantData0) throws IOException {
		// int userAccountIndex1 = 7;
		this.userAccountIndex = userAccountIndex;
		reservationData1 = new ReservationDataFileManager();
		reservationData1.LoadDataFile(null);
		reservationDataList = reservationData1.getReservationData(this.userAccountIndex);
        RestaurantDataFileManager restaurantData1 = new RestaurantDataFileManager();
		this.restaurantData= restaurantData1;
		restaurantData.LoadDataFile();
		
		this.virtualTime = virtualTime;
	}

	public MyReservationData[] print() {
		reservationData1.fetchReservationStatus(virtualTime);
		reservationDataList = reservationData1.getReservationData(this.userAccountIndex);
		
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
		
		MyReservationData[] myReservationDataList = new MyReservationData[reservationDataList.size()];

		if (reservationDataList.size() != 0) {

			System.out.println("[내 예약 내역 확인]\r\n" + reservationDataList.size() + " 건의 예약 내역이 있습니다.\r\n");
			int count = 0;
			for (ReservationData reservationData : reservationDataList) {
				String temp = reservationData.time.StringDate("/");
				//temp = temp.replaceAll("(\\d{4})null(\\d{2})null(\\d{2})", "$1/$2/$2");
                        RestaurantData RestaurantData = restaurantData.getRestaurantData().get(reservationData.restaurantIndex);
			System.out.println((count + 1) + ". " + temp + " " + reservationData.time.StringTime());
		          System.out.println("식당명: " + RestaurantData.restaurantName);
		          System.out.println("주문한 메뉴:");
		          OrderedMenuInfo orderedMenu = reservationData.orderedMenu; 
		          MenuInfo menuInfo = RestaurantData.menuInfo;
		          for (int i = 0; i < orderedMenu.menuIndex.length; i++) {
		              String str = "";
		              str += menuInfo.menuName[orderedMenu.menuIndex[i]] + " ";
		              str += orderedMenu.orderedAmount[i];
		              System.out.println("-" + str + "개");
		          }
				System.out.println( "\n\t예약 인원:"
						+ reservationData.people + "\n\t테이블 번호:" + reservationData.tableNum + "\n\t예약 번호:"
						+ reservationData.reservationNum + "\n\t예약 상태: " + reservationData.status + "\n");

				MyReservationData myReservationData = new MyReservationData();
				myReservationData.indexInFile = count;
				myReservationData.data = reservationData;
				myReservationDataList[count] = myReservationData;
				count++;
			}
			
		} else {
			System.out.println("[내 예약 내역 확인]\r\n" + "예약 내역이 없습니다.\r\n" + "");
		}

		return myReservationDataList;
	}
}
