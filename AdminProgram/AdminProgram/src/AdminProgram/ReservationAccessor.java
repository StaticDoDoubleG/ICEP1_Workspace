package AdminProgram;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class ReservationAccessor{

	public ArrayList<ReservationData> list = new ArrayList<>();
	
	public Boolean LoadFile() {	
		File DataFile = new File("Reservation.txt");
		try {
			Scanner scan = new Scanner(DataFile, "UTF-8");
			while(scan.hasNextLine()) {
				ReservationData data = new ReservationData();
				data.userAccountIndex = scan.nextLine(); //사용자 인덱스
				data.date = scan.nextLine(); //예약 날짜
				data.time = scan.nextLine(); //예약 시간
				data.people = scan.nextLine(); //예약 인원수
				data.tableNum = scan.nextLine(); //예약 좌석
				data.reservationNum = scan.nextLine(); //예약 번호
				data.status = scan.nextLine(); //예약 상태
				data.restaurant = Integer.parseInt(scan.nextLine()); //식당 이름
				data.menu = scan.nextLine(); // 메뉴
				list.add(data);
			}
			scan.close();		
			return true;		
		} catch (FileNotFoundException e) {
			try {
				DataFile.createNewFile();
				System.out.println("예약 데이터 파일이 생성되었습니다.");
				return true;
			} catch (IOException e1) { //이럴 경우가 없으므로 넘어가세요.
				System.out.println("예약 데이터 파일을 생성할 수 없습니다. Reservationdata.txt라는 이름을 가진 파일이 현재 디렉토리에 있는지 확인해주세요.");
				return false;
			}
		}
	}
	
	public void amendFile() { //예약 데이터 파일에 덮어쓰기
		File DataFile = new File("Reservation.txt");
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(DataFile), StandardCharsets.UTF_8));
			Iterator<ReservationData> ResIt = list.iterator();
			ReservationData tempRVD = new ReservationData();
			String allStr = "";
			while(ResIt.hasNext()) {
				tempRVD = ResIt.next();
				allStr += tempRVD.userAccountIndex + "\r\n";
				allStr += tempRVD.date + "\r\n";
				allStr += tempRVD.time + "\r\n";
				allStr += tempRVD.people + "\r\n";
				allStr += tempRVD.tableNum + "\r\n";
				allStr += tempRVD.reservationNum + "\r\n";
				allStr += tempRVD.status + "\r\n";
				allStr += tempRVD.restaurant + "\r\n";
				allStr += tempRVD.menu + "\r\n";
				//System.out.println(allStr);
			}
			writer.write(allStr);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void amend(int index) { //삭제된 식당을 예약한 건을 삭제
		int i = 0;
		//ArrayList<Integer> multiDel = new ArrayList<>();
		for(i = list.size() - 1; i >= 0; i--) {
			System.out.println("반복 횟수 " + i);
			if(index == list.get(i).restaurant) {
				System.out.println("지움 " + list.get(i).reservationNum);
				//multiDel.add(i);
				list.remove(i); //예약 데이터 객체에서의 삭제
				if(i == 0) { //기존에 식당이 하나만 있었던 경우 예약 데이터 파일 초기화
					list.clear();
				}
				for(int j = i; j < list.size(); j++) { //식당이 두개 이상이었던 경우 식당 인덱스를 하나씩 당김
					list.get(j).restaurant = list.get(j).restaurant - 1;
				}
			}
		}
		amendFile();
		//mainstream.reserve.LoadFile();
	}
}
