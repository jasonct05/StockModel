import java.util.Scanner;

import javax.swing.*;

public class StockPriceMain {
	public static void main(String[] args) {
		// input stock screener
		Scanner s = new Scanner(System.in);
		System.out.print("Please input a ticker: ");
		String ticker = s.nextLine();
		s.close();
		
		// create model
		StockPriceModel model = new StockPriceModel(ticker);
		
		// create frame for view and controller
	    JFrame frame = new JFrame("Stock Screener for " + model.getCompanyName());
	    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    StockPriceController controller = new StockPriceController(model);
	    frame.add(controller);
	    frame.pack();
	    frame.setResizable(false);
	    frame.setVisible(true);
	    model.run();
	}
}
