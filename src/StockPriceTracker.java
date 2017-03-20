//import java.io.IOException;
//import java.net.URL;
//import java.net.URLConnection;
//import java.util.Base64;
//
//import StockModel.WebsocketClientEndpoint;
//
//import java.net.URI;
//import java.net.URISyntaxException;
//
//
//public class StockPriceTracker {
//	private static final String USERNAME = "bb80cd70eea1a566cbd597ca376dd980";
//	private static final String PASSWORD = "bb5f569e5b1e9b62044e70f03647defa";
//	private static final String WEBSOCKET = "wss://realtime.intrinio.com/socket/websocket?vsn=1.0.0&token=#(?)";
//	
//	private static WebsocketClientEndpoint clientEndPoint;
//	
//	public StockPriceTracker() throws IOException {
//	    byte[] base = Base64.getEncoder().encode(userpass.getBytes());
//	    String basicAuth = "Basic " + new String(base);
//		try {
//            // open websocket
//            this.clientEndPoint = new WebsocketClientEndpoint(new URI("wss://realtime.intrinio.com/socket/websocket?vsn=1.0.0&token=#{token}"));
//
//            // add listener
//            clientEndPoint.addMessageHandler(new WebsocketClientEndpoint.MessageHandler() {
//                public void handleMessage(String message) {
//                    System.out.println(message);
//                }
//            });
//
//            // send message to websocket
//            clientEndPoint.sendMessage("{'event':'addChannel','channel':'ok_btccny_ticker'}");
//
//            // wait 5 seconds for messages from websocket
//            Thread.sleep(5000);
//
//        } catch (InterruptedException ex) {
//            System.err.println("InterruptedException exception: " + ex.getMessage());
//        } catch (URISyntaxException ex) {
//            System.err.println("URISyntaxException exception: " + ex.getMessage());
//        }
//	}
//	
//	private String parseQuery(String tick, String URL) {
//		StringBuilder requestURL = new StringBuilder(URL);
//		int i = requestURL.indexOf("(?)");
//		requestURL.delete(i, i + 3);
//		requestURL.insert(i, tick);
//		return requestURL.toString();
//	}
//}
