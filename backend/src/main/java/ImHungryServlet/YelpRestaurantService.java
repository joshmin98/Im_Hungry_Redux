package ImHungryServlet;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Objects;
import java.util.Properties;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class YelpRestaurantService {

        private static String API_KEY;
        private static final String baseyelpSearchUrl = "https://api.yelp.com/v3/businesses/search?latitude=34.0206&longitude=-118.2854";

        // getRestaurantInfo(term, limit) - returns Yelp restaurant results as a JSON string. 

        public static String getRestaurantInfo(String term, String limit, String radius)
                        throws UnsupportedEncodingException {
                // returns a JSON String of Yelp results
                String jsonResult = getRestaurantJsonString(term, limit, radius);
                // Root restaurantInfoObject = toEntity(jsonResult);
                return jsonResult;
        }

        public YelpRestaurantService() {
                Properties prop = new Properties();
                try {
                        ClassLoader classLoader = YelpRestaurantService.class.getClassLoader();
                        URL res = Objects.requireNonNull(classLoader.getResource("config.properties"));
                        InputStream is = new FileInputStream(res.getFile());
                        prop.load(is);
                        API_KEY = prop.getProperty("YELP_API_KEY");
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }

        // getRestaurantJsonString(term, limit) - makes the HTTP request to the API and forms the JSON string
        private static String getRestaurantJsonString(String term, String limit, String radius)
                        throws UnsupportedEncodingException {
                String yelpurl = baseyelpSearchUrl + "term=" + term + "&limit=" + limit + "&radius=" + radius;

                if ((term != null && term != "") && (limit != null && limit != ""))
                        yelpurl = baseyelpSearchUrl + "&term=" + URLEncoder.encode(term, "utf-8") + "&limit="
                                        + URLEncoder.encode(limit, "utf-8") + "&radius="
                                        + URLEncoder.encode(radius, "utf-8");

                StringBuilder sb = new StringBuilder();
                HttpURLConnection conn = null;
                BufferedReader reader = null;

                try {
                        URL url = new URL(yelpurl);

                        conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");
                        conn.setRequestProperty("Accept", "application/json");
                        conn.setRequestProperty("Authorization", "Bearer " + API_KEY);

                        if (conn.getResponseCode() != 200) {
                                throw new RuntimeException(
                                                "HTTP GET Request Failed with Error code : " + conn.getResponseCode());
                        }
                        //Read the content from the defined connection
                        //Using IO Stream with Buffer raise highly the efficiency of IO
                        reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                        String output = null;

                        while ((output = reader.readLine()) != null)
                                sb.append(output);
                } catch (MalformedURLException e) {
                        e.printStackTrace();
                } catch (IOException e) {
                        e.printStackTrace();
                }

                String prettyJSON = convertPrettyJSON(sb.toString());
                System.out.println(prettyJSON);
                return prettyJSON;
        }

        // converts one line json format to pretty json format
        public static String convertPrettyJSON(String uglyJSON) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                JsonParser jp = new JsonParser();
                JsonElement je = jp.parse(uglyJSON);
                String prettyJsonString = gson.toJson(je);
                return prettyJsonString;
        }
}
