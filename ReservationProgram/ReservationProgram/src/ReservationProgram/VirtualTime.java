package ReservationProgram;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class VirtualTime {
	private Scanner sc = new Scanner(System.in);
	private int year, month, day, hour, min, sec = 0;
	private int[] MONTHS = {0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	private long start, end;
	private Time vt;
	public int prompt() {
		//날짜 입력
		boolean isDateRight = false;
		while(!isDateRight) {
			System.out.println("프로그램에서 사용할 가상 날짜를 입력하세요. 날짜 입력 형식은 다음 중 하나입니다.");
			System.out.println("YYYY/MM/DD\nYYYY.MM.DD\nYYYY-MM-DD\nYYYYMMDD\nYY/MM/DD\nYY.MM.DD\nYY-MM-DD\nYYMMDD");
			String vdstr;
			vdstr = sc.nextLine();
			vdstr = vdstr.replaceAll(" ", ""); //공백 제거
			//입력 규칙에 맞는지 확인! 아래 조건에 모두 부합하지 않을 시 돌아가기
			if((!this.split(vdstr)) && (!this.split(vdstr, "/")) && (!this.split(vdstr, "\\.")) && (!this.split(vdstr, "-"))) {
				System.out.println("[오류] 날짜 입력 규칙에 맞지 않습니다.\n");
				continue;
			}
			//존재하는 날짜인지 확인
			try {
				if(day <=0 || day > MONTHS[month]) {
					System.out.println("[오류] 존재하지 않는 날짜입니다.\n");
					continue;
				}else {
					if(month == 2 && !(isLeapYear(year)) && day == 29) {
						System.out.println("[오류] 존재하지 않는 날짜입니다.\n");
						continue;
					}
					isDateRight = true;
				}
			}catch(ArrayIndexOutOfBoundsException e) {
				System.out.println("[오류] 존재하지 않는 날짜입니다.\n");
				continue;
			}
//			if(month <= 0 || month >= MONTHS.length || day <= 0 || day >= 32) {
//				System.out.println("[오류] 존재하지 않는 날짜입니다.\n");
//				continue;
//			}else {
//				switch(month) {
//				case 1, 3, 5, 7, 8, 10, 12: isDateRight = true; break;
//				case 2:
//					if(day <= 28) {
//						isDateRight = true; break;
//					}else {
//						System.out.println("[오류] 존재하지 않는 날짜입니다.\n");
//						break;
//					}
//				case 4, 6, 9, 11:
//					if(day <= 30) {
//						isDateRight = true; break;
//					}else {
//						System.out.println("[오류] 존재하지 않는 날짜입니다.\n");
//						break;
//					}
//				}
//			}
		}
		//시간 입력
		boolean isTimeRight = false;
		while(!isTimeRight) {
			System.out.println("프로그램에서 사용할 가상 시각을 입력하세요. 시간 입력의 규칙은 다음과 같습니다.");
			System.out.println("1. 숫자, ':'로 이루어진 문자열입니다.\n2. HH24:MI(시간:분) 형식을 가집니다.");
			String vtstr;
			vtstr = sc.nextLine();
			vtstr = vtstr.replaceAll(" ", ""); //공백 제거
			//입력 규칙에 맞는지 확인
			if((!this.split(vtstr, ":"))) {
				System.out.println("[오류] 시간 입력 규칙에 맞지 않습니다.\n");
				continue;
			}
			//존재하는 시간인지 확인
			if(hour < 0 || hour >= 24 || min < 0 || min >= 60) {
				System.out.println("[오류] 존재하지 않는 시간입니다.\n");
				continue;
			}
			isTimeRight = !isTimeRight;
		}
		
		vt = new Time(year, month, day, hour, min);
		//가상 시간 입력이 모두 끝난 후에는 시간이 흐르기 시작
		start = System.currentTimeMillis();
		
		//LoginRegistrationChoice lrc = new LoginRegistrationChoice();
		return -1;
	}
	
	private boolean isLeapYear(int year) {
		 return ((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0));
	}
	
	public Time getTime() throws ParseException {
		end = System.currentTimeMillis();
		long elapsed = (end - start)/1000;
//		System.out.println(elapsed);
		start = System.currentTimeMillis(); //경과 시간이 초기화돼야 하므로 시작 시간을 다시 현재 시간으로 바꾸기
		
		normalize(elapsed);
		vt = new Time(year, month, day, hour, min);
		return vt;
	}
	
	private void normalize(long seconds) {
		
		long totalsec = sec + seconds;
		sec = (int) (totalsec % 60);
		int totalmin = min + (int) (totalsec / 60);
		min = totalmin % 60;
		int totalh = hour + totalmin / 60;
		hour = totalh % 24;
		int totalday = day + totalh / 24;
		
		Time temp = new Time(year, month, day);
		String dateStr = temp.StringDate("");
		dateStr = addDate(dateStr, totalday);
		split(dateStr);
		
	}
	
	private String addDate(String date, int day) {
		SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMdd");
        
		Calendar cal = Calendar.getInstance();
        
		Date dt;
		try {
			dt = dtFormat.parse(date);
			cal.setTime(dt);
			cal.add(Calendar.DATE,  day - this.day);
			return dtFormat.format(cal.getTime());
		} catch (ParseException e) {
			return null;
		}
	}
	
	private boolean split(String str) {
		//날짜: 구분자가 없을 시 길이는 6 혹은 8
		String[] temp = new String[3];
		if(str.length() == 6) {
			for(int i = 0, j = 0; i < 5; i += 2, j++) {
				temp[j] = str.substring(i, i + 2);
			}
			try {
				year = Integer.parseInt(temp[0]);
				month = Integer.parseInt(temp[1]);
				day = Integer.parseInt(temp[2]);
				return true;
			}catch(NumberFormatException e) {
				return false;
			}
		}
		else if(str.length() == 8){
			temp[0] = str.substring(0, 4);
			temp[1] = str.substring(4, 6);
			temp[2] = str.substring(6, 8);
			try {
				year = Integer.parseInt(temp[0]);
				month = Integer.parseInt(temp[1]);
				day = Integer.parseInt(temp[2]);
				return true;
			}catch(NumberFormatException e) {
				return false;
			}
		}
		else {
			return false;
		}
	}
	private boolean split(String str, String delimiter) {
		//구분자가 있을 시 길이는 5 혹은 8 혹은 10
		String delim = delimiter;
		if(delim == "\\.") {
			delim = ".";
		} //구분자가 \\.으로 들어오면 \.으로 인식해서 indexOf가 안 됨
		//시간 입력
		if(str.length() == 4 || str.length() == 5) {
			if(str.length() == 4) {
				if(str.indexOf(delim) != 1)
					return false;
			}
			else if(str.length() == 5){
				if(str.indexOf(delim) != 2)
					return false;
			}
			String[] temp = new String[2];
			temp = str.split(delimiter);
			try {
				hour = Integer.parseInt(temp[0]);
				min = Integer.parseInt(temp[1]);
				return true;
			}catch(NumberFormatException e) {
				return false;
			}
		}
		//날짜 입력: 구분자 있을 시 길이 8 혹은 10
		if(str.length() == 8) {
			if(str.indexOf(delim) != 2 || str.indexOf(delim, 3) != 5) {
				return false;
			}
			String[] temp = new String[3];
			temp = str.split(delimiter);
			try {
				year = Integer.parseInt(temp[0]);
				month = Integer.parseInt(temp[1]);
				day = Integer.parseInt(temp[2]);
				return true;
			}catch(NumberFormatException e) {
				return false;
			}
		}
		else if(str.length() == 10){
			if(str.indexOf(delim) != 4 || str.indexOf(delim, 5) != 7) {
				return false;
			}
			String[] temp = new String[3];
			temp = str.split(delimiter);
			try {
				year = Integer.parseInt(temp[0]);
				month = Integer.parseInt(temp[1]);
				day = Integer.parseInt(temp[2]);
				return true;
			}catch(NumberFormatException e) {
				return false;
			}
		}
		else {
			return false;
		}
	}
}
