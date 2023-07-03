package StaffProgram;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

public class DBManager {

	public static HashMap<Integer, RestaurantData> restaurantList = new HashMap<>();
	public static RestaurantData currentRestaurantData;
	public static ArrayList<ReservationData> reservationList = new ArrayList<>();
	public static HashMap<Integer, Integer> tablesDatas = new HashMap<>();

	public static void restaurantDataInit() {

		File file = new File("Restaurant.txt");
		try {
			Scanner sc = new Scanner(file, "UTF-8");
			int restaurantIndex = 0;

			while (sc.hasNextLine()) {

				String rName = sc.nextLine();
				String rPassword = sc.nextLine();
				String rMenu = sc.nextLine();
				HashMap<Integer, MenuItem> menu = new HashMap<>();
				if (rMenu!="") {
					String[] parts = rMenu.split("\t");
					for (int i = 0; i < parts.length; i++) {
						String item = parts[i];
						String[] itemData = item.split("=");
						String menuItemName = itemData[0];
						int price = Integer.parseInt(itemData[1]);
						menu.put(i, new MenuItem(menuItemName, price));

					}
				}
				RestaurantData restaurantData = new RestaurantData(restaurantIndex, rName, rPassword, menu);
				restaurantList.put(restaurantIndex, restaurantData);
				restaurantIndex++;
			}
			sc.close();
		} catch (FileNotFoundException e) {
			try {
				file.createNewFile();
				//System.out.println("RestaurantData.txt 파일이 존재하지 않습니다. 파일을 새로 생성합니다.\n");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public static void restaurantReservDataInit() {
		File file = new File("Reservation.txt");
		try {
			Scanner sc = new Scanner(file, "UTF-8");
			int rIndex = currentRestaurantData.index;
			while (sc.hasNextLine()) {

				String userAccountIndex = sc.nextLine();
				String reservDate = sc.nextLine();
				String reservTime = sc.nextLine();
				String guestCount = sc.nextLine();
				String tableNum = sc.nextLine();
				String reservNum = sc.nextLine();
				String status = sc.nextLine();
				String restourantIndex = sc.nextLine();
				String orderData = sc.nextLine();
				if (Integer.parseInt(restourantIndex) == rIndex) {
					ReservationData reservationData = new ReservationData(userAccountIndex, reservDate, reservTime,
							guestCount, tableNum, reservNum, status, restourantIndex, orderData);
					reservationList.add(reservationData);
				}

			}
			sc.close();
		} catch (FileNotFoundException e) {
			try {
				file.createNewFile();
				//System.out.println("Reservation.txt 파일이 존재하지 않습니다. 파일을 새로 생성합니다.\n");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public static boolean restaurantTablesDataInit() {
		boolean fileIsFound = false;
		try {
			File file = new File("Table_"+currentRestaurantData.name + ".txt");
			if (file.exists()) {
				Scanner sc = new Scanner(file, "UTF-8");
				if (sc.hasNext()) {
					int tableNumber = sc.nextInt();
					fileIsFound = true;
					for (int i = 0; i < tableNumber + 1; i++) {
						String s = sc.nextLine();
						if (i == 0) {
							continue;
							
						}
						String[] parts = s.split(" ");
						tablesDatas.put(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));

					}
				} else {
					//System.out.println("Table_"+currentRestaurantData.name + ".txt File Is Empty!");
				}
				sc.close();

			} else {
				file.createNewFile();
				//System.out.println("Table_"+currentRestaurantData.name + ".txt 파일이 존재하지 않습니다. 파일을 새로 생성하였습니다.\n");
				return true;

			}

		} catch (FileNotFoundException e) {
			System.out.println("Table_"+currentRestaurantData.name + ".txt File not Found");

		} catch (IOException e) {
			System.out.println("\nException");
		}

		return true;
	}

	public static boolean checkRestaurant(String name) {
		boolean result = false;
		for (Entry<Integer, RestaurantData> entry : restaurantList.entrySet()) {
			RestaurantData val = entry.getValue();
			if (val.name.equals(name)) {
				result = true;
				break;
			}
		}
		return result;
	}

	public static boolean checkPassword(String name, String pw) {
		boolean result = false;
		for (Entry<Integer, RestaurantData> entry : restaurantList.entrySet()) {
			RestaurantData val = entry.getValue();
			if (val.name.equals(name) && val.password.equals(pw)) {
				currentRestaurantData = val;
				result = true;
				break;
			}
		}
		return result;
	}

	public static int getRIndexByName(String name) {
		int index = -1;
		for (Entry<Integer, RestaurantData> entry : restaurantList.entrySet()) {
			Integer key = entry.getKey();
			RestaurantData val = entry.getValue();
			if (val.name.equals(name)) {
				index = key;
				break;
			}
		}
		return index;
	}

	public static ArrayList<ReservationData> getReservationsByDate(String inputDate) {
		String dateString = checkDate(inputDate, false);
		if (dateString == null) {
			return null;
		} else {
			ArrayList<ReservationData> reservationsByNum = new ArrayList<>();
			if (!reservationList.isEmpty()) {
				for (ReservationData reservationData : reservationList) {
					if (reservationData.reservDate.equals(dateString)) {
						reservationsByNum.add(reservationData);
					}
				}
			}
			return reservationsByNum;
		}
	}

	public static String checkDate(String inputDate, boolean forPrinting) {
			DateTimeFormatter dtf = new DateTimeFormatterBuilder()
					.appendOptional(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
					.appendOptional(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
					.appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
					.appendOptional(DateTimeFormatter.ofPattern("yyyyMMdd"))
					.appendOptional(DateTimeFormatter.ofPattern("yy/MM/dd"))
					.appendOptional(DateTimeFormatter.ofPattern("yy.MM.dd"))
					.appendOptional(DateTimeFormatter.ofPattern("yy-MM-dd"))
					.appendOptional(DateTimeFormatter.ofPattern("yyMMdd")).toFormatter();
			LocalDate ld = null;
			boolean formatSucces = false;
			try {
				ld = LocalDate.parse(inputDate, dtf);
				formatSucces = true;
			} catch (DateTimeParseException e) {
			}

			if (!formatSucces || ld == null) {
				System.out.println("[오류] 날짜 입력 규칙에 맞지 않는 입력입니다. 다시 입력해주세요.\n");
				return null;
			} else {

				if (forPrinting) {
					DateTimeFormatter outputFormat2 = DateTimeFormatter.ofPattern("yyyy/MM/dd");
					return ld.format(outputFormat2);
				} else {
					DateTimeFormatter outputFormat1 = DateTimeFormatter.ofPattern("yyyyMMdd");
					return ld.format(outputFormat1);
				}
			}
	}

	public static ArrayList<ReservationData> getReservationsByNum() {
		System.out.print("[예약 내역 확인] 예약 내역 확인을 원하는 예약 번호를 입력해주세요. (c 입력 시 메인 메뉴로 돌아가기):");
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
			if (num < 1 || num > 999) {
				System.out.println("[오류] 예약 번호는 1 이상 999 이하의 정수여야 합니다.\n");
				return getReservationsByNum();
			} else {
				ArrayList<ReservationData> reservationsByNum = new ArrayList<>();
				if (!reservationList.isEmpty()) {
					for (ReservationData reservationData : reservationList) {
						if (reservationData.reservNum.equals(choice)) {
							reservationsByNum.add(reservationData);
						}
					}
				}
				return reservationsByNum;
			}
		} else {
			if (choice.equals("c")) {
				return null;
			} else {
				System.out.println("[오류] 예약 번호는 1 이상 999 이하의 정수여야 합니다.\n");
			}
			return getReservationsByNum();
		}

	}

	public static void saveRestaurantTables() {
		File file = new File(currentRestaurantData.name + ".txt");
		if (file.exists()) {
			file.delete();
		}
		try {
			file.createNewFile();
			//PrintWriter writer = new PrintWriter(file);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
			String allStr = "";
			allStr += tablesDatas.size() + "\r\n";
			for (Entry<Integer, Integer> entry : tablesDatas.entrySet()) {
				int tableNum = entry.getKey();
				int seats = entry.getValue();

				allStr += tableNum + " " + seats + "\r\n";

			}
			writer.write(allStr);
			writer.flush();
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void saveRestaurantMenu() {
		File file = new File("Restaurant.txt");
		if (file.exists()) {
			file.delete();
		}
		try {
			file.createNewFile();
			//PrintWriter writer = new PrintWriter(file);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
			String allStr = "";
			for (Entry<Integer, RestaurantData> entry : restaurantList.entrySet()) {
				int restaurantId = entry.getKey();
				RestaurantData restaurantData = entry.getValue();
				String str = "";
				String tab = "";
				if (restaurantId == currentRestaurantData.index) {
					for (Entry<Integer, MenuItem> ent : currentRestaurantData.menu.entrySet()) {

						int key = ent.getKey();
						MenuItem val = ent.getValue();
						str += tab + val.name + "=" + val.price;
						tab = "\t";
					}
				} else {

					for (Entry<Integer, MenuItem> ent : restaurantData.menu.entrySet()) {

						int key = ent.getKey();
						MenuItem val = ent.getValue();
						str += tab + val.name + "=" + val.price;
						tab = "\t";
					}
				}
				allStr += restaurantData.name + "\r\n" + restaurantData.password + "\r\n" + str + "\r\n";
				//writer.write(restaurantData.name + "\n" + restaurantData.password + "\n" + str + "\n");
			}
			writer.write(allStr);
			writer.flush();
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

