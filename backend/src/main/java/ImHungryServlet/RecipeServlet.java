package ImHungryServlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;


/**
 * Servlet implementation class RecipeServlet
 */

@WebServlet(name="RecipeServlet", urlPatterns = "/RecipeServlet")
public class RecipeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public RecipeServlet(){
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("application/json");
		
		String query = request.getParameter("query");
		String num = request.getParameter("numResults");
		
		getRecipeData(query, num);
		
	}
	
	public String getRecipeData(String query, String num) {
		
		String API_KEY = "885e38805emsh424ffd2e4016f98p1cb3efjsn55402a4c6758";
		String URL = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/";
		
		URL += "recipes/searchComplex?query=" + query + "&ranking=2&addRecipeInformation=true";
		URL += "&limitLicense=true&offset=0&number=" + num;

		HttpResponse<JsonNode> jsonResponse = null;

		try {
			jsonResponse = Unirest.get(URL).header("X-RapidAPI-Key", API_KEY).asJson();
		}catch(UnirestException ue){
			System.out.println("Unirest Exception");
		}finally {}
		
		JsonParser parser = new JsonParser();
		JsonObject recipe = parser.parse(jsonResponse.getBody().toString()).getAsJsonObject();

		String prettyJson = convertPrettyJSON(jsonResponse.getBody().toString());
		
		return prettyJson;
		
		
	}
	
	public static String convertPrettyJSON(String u) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(u);
		String prettyJson = gson.toJson(je);
		return prettyJson;
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
