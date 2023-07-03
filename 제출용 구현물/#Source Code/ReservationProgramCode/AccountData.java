package ReservationProgram;

public class AccountData {
	public String id;
	public String pw;
	public String name;
	public String phonenumber;
	public int virtualCash;
	public int point;
	
	public AccountData(String id, String pw, String name, String phonenumber) {
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.phonenumber = phonenumber;
		this.virtualCash = 0;
		this.point = 0;
	}

	public AccountData(String id, String pw, String name, String phonenumber, int virtualCash, int point) {
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.phonenumber = phonenumber;
		this.virtualCash = virtualCash;
		this.point = point;
	}
	
}
