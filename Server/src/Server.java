import com.sun.net.httpserver.*;
import netscape.javascript.JSObject;
import sun.net.httpserver.HttpServerImpl;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.*;

public class Server {
	
	private static Map<String,String> data = new HashMap<>();
	static  {
		Server.data.put("Java","1996");
		Server.data.put("C","1972");
		Server.data.put("C++","1985");
		Server.data.put("Python","1991");
		Server.data.put("Javascript","1995");
	}
	private class RequestHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange httpExchange) throws IOException{
			String response = "Request Received";
			String method = httpExchange.getRequestMethod();
			try {
				if (method.equals("GET")) {
					
					URI uri = httpExchange.getRequestURI();
					String query = uri.getRawQuery();
					String value = query.substring(query.indexOf("=")+1);
					response = Server.data.get(value);
					
					System.out.println("Queried for " + value);
					
				} else if (method.equals("POST")) {
					
					InputStream inStream = httpExchange.getRequestBody();
					Scanner scanner = new Scanner(inStream);
					String postData = scanner.nextLine();
					String language = postData.substring(0,postData.indexOf(",")),
							year = postData.substring(postData.indexOf(",")+1);
					Server.data.put(language,year);
					response = "data posted";
					
					System.out.println("Posted entry {" + language + ", " + year + "}");
					
				} else {
					throw new Exception("Not Valid Request Method");
				}
			}catch (Exception e){
				System.out.println("An erroneous request");
				response = e.toString();
				e.printStackTrace();
			}
			
			Headers responseHeaders = httpExchange.getResponseHeaders();
			responseHeaders.add("Access-Control-Allow-Origin", "*");
			responseHeaders.add("Access-Control-Allow-Headers","origin, content-type, accept, authorization");
			responseHeaders.add("Access-Control-Allow-Credentials", "true");
			responseHeaders.add("Access-Control-Allow-Methods", "GET, POST");
			httpExchange.sendResponseHeaders(200, response.length());
			OutputStream outStream = httpExchange.getResponseBody();
			outStream.write(response.getBytes());
			outStream.close();
		}
	}
	
	private void runServer() throws IOException {
		HttpServer server = HttpServerImpl.create(new InetSocketAddress(8080),0);
		HttpContext context = server.createContext("/");
		context.setHandler(new RequestHandler());
		server.start();
	}
	
	
	public static void main(String[] args){
		System.out.println("Server started .....");
		try {
			Server solution = new Server();
			solution.runServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
