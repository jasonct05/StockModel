import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.swing.*;

public class StockPriceView extends JPanel{
	private static final long serialVersionUID = 1L;
	private static final double PERCENTAGE = 0.10;
	private static final double BUFFER = 0.05;
	
	private StockPriceModel m;
	private int width;
	private int height;
	private int bottomPadding;
	private int leftPadding;
	private int rightPadding;
	private double maxShownPrice;
	private double minShownPrice;
	private double percentageView;
	private Calendar c;
	
	public StockPriceView(StockPriceModel m, int width, int height) {
		this.m = m;
		this.width = width;
		this.height = height;
		this.leftPadding = (int) (height * 0.1);
		this.bottomPadding = (int) (width * 0.05);
		this.percentageView = StockPriceView.PERCENTAGE;
		this.maxShownPrice = this.m.getLastCloseQuote() * (1 + this.percentageView);
		this.minShownPrice = this.m.getLastCloseQuote() * (1 - this.percentageView);
		int difference = StockPriceModel.STOCK_MARKET_CLOSE - StockPriceModel.STOCK_MARKET_OPEN + 1;
		this.rightPadding = ((this.width - this.leftPadding) / difference) * (difference - 1) + this.leftPadding;
		this.c = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
	}
	
	public void notifyViewers() {
		double maxDifference = Math.max(Math.abs(this.m.getDayMax() - this.m.getLastCloseQuote()), Math.abs(this.m.getDayMin() - this.m.getLastCloseQuote()));
		double percentage = maxDifference / this.m.getLastCloseQuote();
		this.percentageView = percentage + StockPriceView.BUFFER;
		this.maxShownPrice = this.m.getLastCloseQuote() * (1 + percentageView);
		this.minShownPrice = this.m.getLastCloseQuote() * (1 - percentageView);
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		this.drawBackground(g);
		this.drawLastClose(g);
		this.drawStatistics(g);
		this.drawTrend(g);
	}
	
	private void drawStatistics(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.LIGHT_GRAY);
		g2.fillRect(this.rightPadding - 80, 0, this.width - (this.rightPadding - this.bottomPadding * 2), 100);
		g2.setColor(Color.BLACK);
		g2.drawString("Current Time:  " + c.get(Calendar.HOUR) + ":" + c.get(Calendar.MINUTE), this.rightPadding - this.bottomPadding, 20);
		g2.drawString("Current Price:  " + this.m.getLastQuote(), this.rightPadding - this.bottomPadding, 40);
		g2.drawString("Max Price:       " + this.m.getDayMax(), this.rightPadding - this.bottomPadding, 60);
		g2.drawString("Min Price:        " + this.m.getDayMin(), this.rightPadding - this.bottomPadding, 80);
	}
	
	private void drawTrend(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		List<StockEntry> currentList = this.m.getStockPrices();
		for(int i = 0; i < currentList.size() - 1; i++) {
			StockEntry start = currentList.get(i);
			StockEntry end = currentList.get(i + 1);
			g2.drawLine(this.getX(start.hour, start.min), this.getY(start.price), this.getX(end.hour, end.min), this.getY(end.price));
		}
	}
	private void drawBackground(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.drawLine(0, this.height - this.bottomPadding, this.width, this.height - this.bottomPadding);
		g2.drawLine(this.leftPadding, 0, this.leftPadding, this.height - this.bottomPadding);

		int difference = StockPriceModel.STOCK_MARKET_CLOSE - StockPriceModel.STOCK_MARKET_OPEN + 1;
		for(int i = 0; i < difference; i++) {
			g2.drawLine(((this.width - this.leftPadding) / difference) * i + this.leftPadding, 0, ((this.width - this.leftPadding) / difference) * i + this.leftPadding, this.height - this.bottomPadding);
			g2.drawString(i + StockPriceModel.STOCK_MARKET_OPEN + ":00", (this.width - this.leftPadding) / difference * i + this.leftPadding, this.height - 20);
		}
	}
	
	private void drawLastClose(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.RED);
		int pixel = this.getY(this.m.getLastCloseQuote());
		g2.drawString("LC = " + this.m.getLastCloseQuote(), 0, pixel);
		g2.drawLine(this.leftPadding, pixel, this.width, pixel);
		g2.setColor(Color.BLACK);
	}
	
	private int getY(double price) {
		double percentage = (price - this.minShownPrice) / (this.maxShownPrice - this.minShownPrice);
		return (int) Math.round(percentage * (this.height - this.bottomPadding));
	}
	
	private int getX(int h, int m) {
		double offset = h * 60 + m;
		double fraction = (offset - (StockPriceModel.STOCK_MARKET_OPEN * 60))/((StockPriceModel.STOCK_MARKET_CLOSE - StockPriceModel.STOCK_MARKET_OPEN) * 60);
		return (int) Math.round((fraction * (this.rightPadding - this.leftPadding)) + this.leftPadding);
	}
} 
