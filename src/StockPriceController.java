import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Calendar;
import java.util.TimeZone;

import javax.swing.*;

public class StockPriceController extends JPanel{

	private static final long serialVersionUID = 1L;
	private StockPriceModel m;
	private StockPriceView v;
	private Calendar c;
	
	private static final int width = 1000;
	private static final int height = 500;
	
	public StockPriceController(StockPriceModel m) {
		// set up
		this.c = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
		this.m = m;
		this.v = new StockPriceView(m, width, height);
		this.m.addViewer(v);
		
		this.setLayout(new BorderLayout());
		
		JLabel title = new JLabel(this.m.getCompanyName() + " (" + this.m.getCompanyTicker() + ") Stock Screener " +
									this.c.get(Calendar.DAY_OF_MONTH) + "/" + this.c.get(Calendar.MONTH) + "/" +this.c.get(Calendar.YEAR));
		JPanel titlePanel = new JPanel();
		titlePanel.add(title);
		this.add(titlePanel, BorderLayout.NORTH);
		
		this.v.setPreferredSize(new Dimension(StockPriceController.width, StockPriceController.height));
		this.v.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.add(this.v, BorderLayout.CENTER);
	}
}
