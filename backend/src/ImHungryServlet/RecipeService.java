package com.mycompany;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Objects;
import java.util.Properties;

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
 * Servlet implementation class RecipeService
 */

public class RecipeService extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String API_KEY = "33ca31a454msh8ddf7f5cdb4d34ap11c0e7jsn2c4f2d940e5b";

    /**
     * Default constructor.
     */
    // public RecipeService() {

    //     Properties prop = new Properties();
    //     try {
    //         ClassLoader classLoader = RecipeServlet.class.getClassLoader();
    //         URL res = Objects.requireNonNull(classLoader.getResource("config.properties"));
    //         InputStream is = new FileInputStream(res.getFile());
    //         prop.load(is);
    //         API_KEY = prop.getProperty("YELP_API_KEY");
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }

    public String getRecipeData(String query, String num) {

        String URL = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/";

        URL += "recipes/searchComplex?number=" + num + "&offset=0" + "&limitLicense=false" + "&query=" + query
                + "&instructionsRequired=true" + "&addRecipeInformation=true";
        HttpResponse<JsonNode> jsonResponse = null;

        try {
            jsonResponse = Unirest.get(URL).header("X-RapidAPI-Key", API_KEY).asJson();
        } catch (UnirestException ue) {
            System.out.println("Unirest Exception");
        } finally {
        }

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
}
