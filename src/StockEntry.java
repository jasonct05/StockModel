import java.util.Calendar;

public class StockEntry {
	public double price;
	public int hour;
	public int min;
	
	public StockEntry(double price, Calendar c) {
		this.price = price;
		this.hour = c.get(Calendar.HOUR);
		this.min = c.get(Calendar.MINUTE);
	}
	
	public StockEntry(double price, int hour, int min) {
		this.price = price;
		this.hour = hour;
		this.min = min;
	}
	
	@Override
	public String toString() {
		return "price : " + this.price + ", time = " + this.hour + ":" + this.min;
	}
}