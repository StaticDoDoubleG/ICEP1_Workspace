package StaffProgram;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class ReservationData {
	public int userAccountIndex;
	public String reservDate;
	public String reservTime;
	public int guestCount;
	public int tableNum;
	public String reservNum;
	public String status;
	public int restourantIndex;
	public String orderData;
	public HashMap<Integer, Integer> order = new HashMap<>();

	public ReservationData(String userAccountIndex, String reservDate, String reservTime, String guestCount,
			String tableNum, String reservNum, String status, String restourantIndex, String orderData) {
		this.userAccountIndex = Integer.parseInt(userAccountIndex);
		this.reservDate = reservDate;
		this.reservTime = reservTime;
		this.guestCount = Integer.parseInt(guestCount);
		this.tableNum = Integer.parseInt(tableNum);
		this.reservNum = reservNum;
		this.status = status;
		this.restourantIndex = Integer.parseInt(restourantIndex);
		this.orderData = orderData;

		String[] orderItem = orderData.split("\t");
		for (int i = 0; i < orderItem.length; i++) {
			String[] item = orderItem[i].split(" ");
			String menuItemIndex = item[0];
			String menuItemQuantity = item[1];
			int index = Integer.parseInt(menuItemIndex);
			int quantity = Integer.parseInt(menuItemQuantity);
			order.put(index, quantity);
		}
	}

	public String printOrder() {
		// 해당 주문의 정보를 출룍합니다. 
		String str = "";
		str += DBManager.checkDate(reservDate, true) + " " + reservTime;
		str += "\n\t식당명: " + DBManager.currentRestaurantData.name;
		str += "\n\t주문한 메뉴:";
		
		for (Entry<Integer, Integer> entry : order.entrySet()) {
			int key = entry.getKey();
			int val = entry.getValue();
			str += "\n\t-" + DBManager.currentRestaurantData.menu.get(key).name + " " + val + "개";
		}
		str += "\n\t에약 인원: " + guestCount;
		str += "\n\t테이블 번호: " + tableNum;
		str += "\n\t예약 번호: " + reservNum;
		str += "\n\t예약 상태: " + status + "\n";
		return str;
	}
}
