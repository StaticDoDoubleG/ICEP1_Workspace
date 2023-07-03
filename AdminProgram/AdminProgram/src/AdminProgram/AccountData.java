package AdminProgram;

public class AccountData {
	public String id;
	public String pw;
	public String name;
	public String phonenumber;
	public String account;
	public String mileage;
	
	public AccountData() {
		
	}
	
	public AccountData(String id, String pw, String name, String phonenumber, String account, String mileage) {
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.phonenumber = phonenumber;
		this.account = account;
		this.mileage = mileage;
	}
}
