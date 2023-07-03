package ReservationProgram;

public class RestaurantData {
	public String restaurantName;
	public String restaurantpw;
	public MenuInfo menuInfo;

	public RestaurantData(String restaurantName, String restaurantpw, MenuInfo menuInfo) {
		this.restaurantName=restaurantName;
		this.restaurantpw=restaurantpw;
		this.menuInfo=menuInfo;
	}
}
