package com.mycompany;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.pippo.core.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * A simple Pippo application.
 *
 * @see com.mycompany.PippoLauncher#main(String[])
 */
public class PippoApplication extends Application {

    private final static Logger log = LoggerFactory.getLogger(PippoApplication.class);

    @Override
    protected void onInit() {
        getRouter().ignorePaths("/favicon.ico");

        // send 'Hello World' as response
        GET("/", routeContext -> routeContext.json().send("Hello World"));

        GET("/restaurants", routeContext -> {
            YelpRestaurantService yelp = new YelpRestaurantService();
            // String restaurantJSONstring = (term != null && limit != null && radius != null)
            //         ? yelp.getRestaurantInfo(term, limit, radius)
            //         : "{\"error\": \"Missing fields in request parameters\"}";
            String query = routeContext.getParameter("query").toString();
            String limit = routeContext.getParameter("limit").toString();
            String radius = routeContext.getParameter("radius").toString();
            String restaurantJSONstring = "";
            try {
                restaurantJSONstring = (query != null && limit != null && radius != null)
                                        ? yelp.getRestaurantInfo(query, limit, radius)
                                        : "{\"error\": \"Missing fields in request parameters\"}";
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
            
            // null)

            routeContext.json().send(restaurantJSONstring);
            // routeContext.json().send("Hello World");
        });

        GET("/recipes", routeContext -> {
            RecipeService rs = new RecipeService();

            String query = routeContext.getParameter("query").toString();
            String limit = routeContext.getParameter("limit").toString();

            String recipeJSONstring = "";
            try {
                recipeJSONstring = (query != null && limit != null)
                        ? rs.getRecipeData(query, limit)
                        : "{\"error\": \"Missing fields in request parameters\"}";
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            routeContext.json().send(recipeJSONstring);
        });

        // GET("/")

        // send a template as response
        GET("/template", routeContext -> {
            String message;

            String lang = routeContext.getParameter("lang").toString();
            if (lang == null) {
                message = getMessages().get("pippo.greeting", routeContext);
            } else {
                message = getMessages().get("pippo.greeting", lang);
            }

            routeContext.setLocal("greeting", message);
            routeContext.render("hello");
        });

        GET("/json", routeContext -> {
            routeContext.getResponse().header("Access-Control-Allow-Origin", "*");
            String limit = routeContext.getParameter("limit").toString();
            JsonObject jo = new JsonObject();
            jo.addProperty("one", "two");
            jo.addProperty("three", "two");
            jo.addProperty("two", "two");
            if(limit != null) {
                jo.addProperty("limit", limit);
            }

            // routeContext.setHeader("Access-Control-Allow-Origin", "*");
            // routeContext.setHeader("Access-Control-Allow-Methods", "GET");


            routeContext.json().send(jo.toString());
        });


        // GET("/", ro)
    }

}
