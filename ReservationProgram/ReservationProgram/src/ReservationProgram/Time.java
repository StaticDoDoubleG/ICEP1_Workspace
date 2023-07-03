package ReservationProgram;

public class Time {
	public int year;
	public int month;
	public int day;
	public int hour;
	public int min;
	
	public Time(int year, int month, int day) {
		this(year, month, day, 0, 0);
	}
	public Time(int hour, int min) {
		this(0, 0, 0, hour, min);
	}
	public Time(int year, int month, int day, int hour, int min) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.min = min;
	}
	public String StringDate(String intervalstr) {
		String SD, MM, DD;
		if(year <= 99) {
			year += 2000;
		}
		switch(month) {
		case 1:
		case 2: 
		case 3: 
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 9: MM= "0"+month; break;
		default: MM = Integer.toString(month);
		}
		switch(day) {
		case 1:
		case 2: 
		case 3: 
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 9: DD= "0"+day; break;
		default: DD = Integer.toString(day);
		}
		SD = year + intervalstr + MM + intervalstr + DD;
		return SD;
	}

	public String StringTime() {
		String HHMM, HH, MM;
		switch(hour) {
		case 0:
		case 1:
		case 2: 
		case 3: 
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 9: HH="0"+hour; break;
		default: HH = Integer.toString(hour);
		}
		switch(min) {
		case 0:
		case 1:
		case 2: 
		case 3: 
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 9: MM="0"+min; break;
		default: MM = Integer.toString(min);
		}
		HHMM = HH + ":" + MM;
		return HHMM;
	}
}
