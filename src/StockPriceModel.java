import java.util.*;
public class StockPriceModel {
	public static final int STOCK_MARKET_OPEN = 9;
	public static final int STOCK_MARKET_CLOSE = 16;
	private static final long WAIT = 1000 * 5; // 5 MINUTE REFRESH RATE. ALLOWS FOR GOOD API CALLS
			
	private List<StockPriceView> viewers;
	private List<StockEntry> stockEntry;
	private CompanyPriceQuote cpq;
	private CompanyProfile cp;
	private Calendar calendar;
	private double lastCloseQuote;
	private double dayMax;
	private double dayMin;

	public StockPriceModel(String ticker) {
		try {
			this.viewers = new ArrayList<StockPriceView>();
			this.cpq = new CompanyPriceQuote(ticker);
			this.cp = new CompanyProfile(ticker);
			this.calendar = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
			this.stockEntry = new ArrayList<StockEntry>();
			this.lastCloseQuote = cpq.getLastClose();
			this.dayMax = this.lastCloseQuote;
			this.dayMin = this.lastCloseQuote;
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	public double getLastCloseQuote() {
		return this.lastCloseQuote;
	}
	
	public double getLastQuote() {
		return this.cpq.getLastClose();
	}
	
	public List<StockEntry> getStockPrices() {
		return Collections.unmodifiableList(this.stockEntry);	
	}
	
	public String getCompanyName() {
		return this.cp.getCompanyName();
	}
	
	public String getCompanyTicker() {
		return this.cp.getCompanyTicker();
	}
	
	public String getCompanyDescription() {
		return this.cp.getCompanyDescription();
	}
	
	public double getDayMin() {
		return this.dayMin;
	}
	
	public double getDayMax() {
		return this.dayMax;
	}
	
	public void addViewer(StockPriceView view) {
		this.viewers.add(view);
	}
	
	public void run() {
		boolean found = false;
		while(true) {
			try {
				if (!(this.calendar.get(Calendar.HOUR_OF_DAY) > STOCK_MARKET_OPEN && this.calendar.get(Calendar.HOUR_OF_DAY) < STOCK_MARKET_CLOSE)){
					if (!found) {
						this.cpq.getLastClose();
						found = true;
					}
					
					if(this.stockEntry.isEmpty()) {
						this.stockEntry.add(new StockEntry(this.cpq.getPrice(), StockPriceModel.STOCK_MARKET_OPEN, 0));
					} else {
						this.stockEntry.get(0).price = this.cpq.getPrice();
					}
					
				} else {
					double currentPrice = this.cpq.getPrice();
					if (currentPrice > this.dayMax) {
						this.dayMax = currentPrice;
					} else if (currentPrice < this.dayMin) {
						this.dayMin = currentPrice;
					}
					
					StockEntry currentEntry = new StockEntry(currentPrice, this.calendar);
					this.stockEntry.add(currentEntry);
					for(int i = 0; i < this.stockEntry.size(); i++) {
						System.out.println(stockEntry);
					}
				}
				for(StockPriceView v: this.viewers) {
					v.notifyViewers();
				}
				Thread.sleep(WAIT);
			} catch (Exception e) {
				System.err.println(e);
			}
		}
	}
}