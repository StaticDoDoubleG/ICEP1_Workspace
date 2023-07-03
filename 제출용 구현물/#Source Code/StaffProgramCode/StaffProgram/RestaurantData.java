package StaffProgram;

import java.util.HashMap;

public class RestaurantData {
	public int index;
	public String name;
	public String password;
	public HashMap<Integer, MenuItem> menu = new HashMap<>();

	public RestaurantData(int index, String name, String password, HashMap<Integer, MenuItem> menu) {
		this.index = index;
		this.name = name;
		this.password = password;
		this.menu = menu;
	}
	
}

