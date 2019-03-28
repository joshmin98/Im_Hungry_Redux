package ImHungryServlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Servlet implementation class Results
 */
@WebServlet("/ResultsServlet")
public class ResultsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
    	response.setHeader("Access-Control-Allow-Methods", "GET");
        HttpSession session = request.getSession();
        String type = request.getParameter("type");
        System.out.println(type);
        // restaurants or recipes
        Object results = session.getAttribute(type);

            
        response.getWriter().println(results);
        // should be db
//        List<JSONObject> list = (List<JSONObject>) session.getAttribute(listName);

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
    	response.setHeader("Access-Control-Allow-Methods", "POST");
//    	String result
//    	try { 
//    		
//    		 result= java.net.URLDecoder.decode(request.getRequestURL().toString(), StandardCharsets.UTF_8.name());
//    	}
        String query = request.getParameter("searchVal");
        String limit = request.getParameter("limit");
        String radius = request.getParameter("distance");
        // response.getWriter().println(query+limit+radius);
        // response.getWriter().println(request.getRequestURL().toString());
        YelpRestaurantService yelp = new YelpRestaurantService();
       String restaurantJSONString = (query != null && limit != null && radius != null)
                                ? yelp.getRestaurantInfo(query, limit, radius)
                                : "{\"error\": \"Missing fields in request parameters\"}";
       String recipeJSONString = null; // call to recipe api
       // store in database
       
       JsonObject res = (new JsonParser()).parse(restaurantJSONString).getAsJsonObject();
       JsonObject idToRestaurants = idToRestaurants(res);
//        System.out.println(res.get("businesses"));
       
       HttpSession session = request.getSession();
       session.setAttribute("idToRestaurants", idToRestaurants);
       session.setAttribute("restaurants", restaurantJSONString);
       session.setAttribute("recipes", recipeJSONString);
       System.out.println(session.getAttribute("idToRestaurants"));
  
        
    }
    
    private JsonObject idToRestaurants(JsonObject restaurants) {
    	JsonObject result = new JsonObject();
//    	System.out.println(restaurants.get("businesses").toString());
    	JsonArray array = restaurants.get("businesses").getAsJsonArray();
    	for(JsonElement je : array) {
    		JsonObject jo = je.getAsJsonObject();
//    		System.out.println(jo);
    		result.add(jo.get("id").getAsString(), je);
    	}
    	System.out.println(result);
		return result;
    	
    }
    
    private JsonObject idToRecipes(JsonObject recipes) {
    	return recipes;
    }

}
