package dk.bhpark.bowling.exercise;

import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * This class represents one game. Data is retrieved via the REST interface, frames are created based on
 * the data, the results are checked via the REST interface, and results are written to the console.
 * <p>
 * <b>(C) Copyright Claus Jensen. 2016
 * @version 1.00 - 28/10/2016
 * @author Claus Jensen (claus@bhpark.dk)
 */

public class Game {
	
	/**
	 * All program logic is performed in the constructor for this class.
	 */
	@SuppressWarnings({ "unchecked" })
	public Game() {
		
		JSONArray series = null;
		String token = null;
		
		try {
			// retrieve and json input from a round
			URL url = new URL("http://37.139.2.74/api/points");
			HttpURLConnection urlConn = (HttpURLConnection)url.openConnection();
			JSONParser parser = new JSONParser();
			Object input = parser.parse(new InputStreamReader(urlConn.getInputStream()));
			JSONObject jsonObj = (JSONObject) input;
			series = (JSONArray)jsonObj.get("points");
			token = (String)jsonObj.get("token");
			urlConn.disconnect();
			
			// fill array with all balls (including the ball not run after strike
			int [] balls = new int[series.size() * 2];
			for (int i = 0; i < series.size(); i++) {
				JSONArray points = (JSONArray)series.get(i);
				balls[i * 2] = Integer.parseInt(points.get(0).toString());
				balls[i * 2 + 1] = Integer.parseInt(points.get(1).toString());
			}
			
			// create the frames
			int points = 0;
			Frame[] frames = new Frame[Math.min(series.size(), 10)];
			for (int i = 0; i < frames.length; i++) {
				frames[i] = new Frame(balls, i * 2, points);
				points = frames[i].getPoints();
				System.out.print(frames[i].toString());
			}
			
			// build result json structure
			JSONObject jsonResult = new JSONObject();
			jsonResult.put("token", token);
			JSONArray list = new JSONArray();
			for (int i = 0; i < frames.length; i++) list.add(frames[i].getPoints());
			jsonResult.put("points", list);			

			// post the json results
			url = new URL("http://37.139.2.74/api/points");
			urlConn = (HttpURLConnection)url.openConnection();
	        HttpURLConnection.setFollowRedirects(true);
			urlConn.setRequestMethod("POST");
			urlConn.setDoInput(true);
			urlConn.setDoOutput(true);
			urlConn.setRequestProperty("Content-Type", "application/json");
			PrintWriter out = new PrintWriter(urlConn.getOutputStream());
			out.println(jsonResult.toString());
			out.close();
			
			// handle response from post request
			System.out.println("   Response: " + urlConn.getResponseCode() + "  " + urlConn.getResponseMessage());			
			
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}
}
