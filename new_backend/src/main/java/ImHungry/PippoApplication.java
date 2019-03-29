package ImHungry;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ro.pippo.core.Application;

/**
 * A simple Pippo application.
 *
 * @see ImHungry.PippoLauncher#main(String[])
 */

public class PippoApplication extends Application {

  private final static Logger log = LoggerFactory.getLogger(PippoApplication.class);
  private static String YELP_API_KEY;
  private static String RECIPE_API_KEY;

  @Override
  protected void onInit() {
    getRouter().ignorePaths("/favicon.ico");

    /**
     * Test route
     */
    GET("/", routeContext -> routeContext.send("Hello World"));
    
    /**
     * Restaurant API route
     */
    GET("/restaurants", routeContext -> {
        String query = routeContext.getParameter("query").toString();
        String numResults = routeContext.getParameter("numResults").toString();
        String radius = routeContext.getParameter("radius").toString();

        String URL =
            "https://api.yelp.com/v3/businesses/search?latitude=34.0206&longitude=-118.2854";
        URL += "&term=" + query +
               "&limit=" + numResults +
               "&radius=" + radius;
        HttpResponse<JsonNode> jsonResponse = null;
        try {
          jsonResponse = Unirest.get(URL)
                         .header("Authorization", "Bearer " + YELP_API_KEY)
                         .asJson();
        } catch (UnirestException e) {
          // TODO: Better error handling
          e.printStackTrace();
        }
        routeContext.json().send(jsonResponse.getBody().toString());
      });
    
    /**
     * Recipe API route
     */
    GET("/recipes", routeContext -> {
        String query = routeContext.getParameter("query").toString();
        String numResults = routeContext.getParameter("numResults").toString();
            
        String URL = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/";
        URL += "recipes/searchComplex?number=" + numResults +
               "&offset=0" + "&limitLicense=false" +
               "&query=" + query +
               "&instructionsRequired=true" +
               "&addRecipeInformation=true";
        HttpResponse<JsonNode> jsonResponse = null;
        try {
          jsonResponse = Unirest.get(URL)
                         .header("X-RapidAPI-Key", YELP_API_KEY)
                         .asJson();
        } catch (UnirestException e) {
          // TODO: Better error handling on failing request
          e.printStackTrace();
        }
        routeContext.json().send(jsonResponse.getBody().toString());
      });
  }

}
