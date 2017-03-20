import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CompanyPriceQuote {
   private static final String USERNAME = "bb80cd70eea1a566cbd597ca376dd980";
   private static final String PASSWORD = "bb5f569e5b1e9b62044e70f03647defa";
   private static final String REQUEST_PRICE_URL = "https://api.intrinio.com/data_point?identifier=(?)&item=last_price";
   private static final String REQUEST_CLOSE_PRICE = "https://api.intrinio.com/data_point?identifier=(?)&item=adj_close_price";
   
   private String basicAuth;
   private URL url;
   private double lastClose;
   
   public CompanyPriceQuote(String tick) throws IOException, ParseException {
	   String requestURL = this.parseQuery(tick, REQUEST_PRICE_URL);
	   this.url = new URL(requestURL);
	   String userpass = USERNAME + ":" + PASSWORD;
	   byte[] base = Base64.getEncoder().encode(userpass.getBytes());
	   this.basicAuth = "Basic " + new String(base);
	   
	   this.lastClose = this.lastClose(tick);
   }
   
   private double lastClose(String tick) throws IOException, ParseException {
	   String requestURL = this.parseQuery(tick, REQUEST_CLOSE_PRICE);
	   URL closeUrl = new URL(requestURL);
	   String userpass = USERNAME + ":" + PASSWORD;
	   byte[] base = Base64.getEncoder().encode(userpass.getBytes());
	   this.basicAuth = "Basic " + new String(base);
	   
	   URLConnection uc = closeUrl.openConnection();
	   uc.setRequestProperty("Authorization", this.basicAuth);
	   
	   InputStream in = uc.getInputStream();
	   StringBuilder sb = new StringBuilder();
      
	   // reads till the end of the stream
	   int i;
	   while((i = in.read())!=-1) {
		   sb.append((char)i);
	   }
      
	   String ticker = sb.toString();
      
	   // parse into JSON
	   JSONParser parser = new JSONParser();
	   Object obj = parser.parse(ticker);
	   
	   JSONObject price = (JSONObject) obj;
	   return (double) price.get("value");
   }
   
   private String parseQuery(String tick, String URL) {
	   StringBuilder requestURL = new StringBuilder(URL);
	   int i = requestURL.indexOf("(?)");
	   requestURL.delete(i, i + 3);
	   requestURL.insert(i, tick);
	   return requestURL.toString();
   }
   
   public double getPrice() throws IOException, ParseException {
	   URLConnection uc = this.url.openConnection();
	   uc.setRequestProperty("Authorization", this.basicAuth);
	   
      InputStream in = uc.getInputStream();
      StringBuilder sb = new StringBuilder();
      
      // reads till the end of the stream
      int i;
      while((i = in.read())!=-1) {
         sb.append((char)i);
      }
      
      String ticker = sb.toString();
      
      // parse into JSON
      JSONParser parser = new JSONParser();
      Object obj = parser.parse(ticker);
      
      JSONObject price = (JSONObject) obj;
      return (double) price.get("value");
   }
   
   public double getLastClose() {
	   return this.lastClose;
   }
   
}
