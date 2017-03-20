
public class TestStock {
	public static void main(String[] args) {
		try {
			CompanyProfile c = new CompanyProfile("NFLX");
			System.out.println(c);
			CompanyPriceQuote cprice = new CompanyPriceQuote("NFLX");
			System.out.println(cprice.getLastClose());
			while(true) {
				Thread.sleep(1000);
				System.out.println("Current Stock Price: " + cprice.getPrice());
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
