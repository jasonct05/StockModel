import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CompanyProfile {
   private static final String USERNAME = "bb80cd70eea1a566cbd597ca376dd980";
   private static final String PASSWORD = "bb5f569e5b1e9b62044e70f03647defa";
   private static final String REQUEST_COMPANY_URL = "https://api.intrinio.com/companies?ticker=(?)";
   
   private JSONObject kv;
   
   public CompanyProfile(String tick) throws IOException, ParseException {
      // open connection
      String requestCompanyURL = this.parseQuery(tick, CompanyProfile.REQUEST_COMPANY_URL);
      URL url = new URL(requestCompanyURL);
      URLConnection uc = url.openConnection();
      String userpass = USERNAME + ":" + PASSWORD;
      byte[] base = Base64.getEncoder().encode(userpass.getBytes());
      String basicAuth = "Basic " + new String(base);
      uc.setRequestProperty("Authorization", basicAuth);
      
      // build string
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
      
      this.kv = (JSONObject) obj;
   }
   
   private String parseQuery(String tick, String URL) {
      StringBuilder requestURL = new StringBuilder(URL);
      int i = requestURL.indexOf("(?)");
      requestURL.delete(i, i + 3);
      requestURL.insert(i, tick);
      return requestURL.toString();
   }
   
   public String getCompanyName() {
	   return (String) this.kv.get("name");
   }
   
   public String getCompanyTicker() {
	   return (String) this.kv.get("ticker");
   }
   
   public String getCompanyDescription() {
	   return (String) this.kv.get("short_description");
   }
   
   @Override
   public String toString() {
	   return "Name: " + this.kv.get("name") + 
			   "\nTicker: " + this.kv.get("ticker") + 
			   "\nDescription: " + this.kv.get("short_description") +
	   		   "\nSector: " + this.kv.get("sector") + 
	   		   "\nIndustry Category: " + this.kv.get("industry_category") +
	   		   "\nIndustry Group: " + this.kv.get("industry_group");
   }
}
