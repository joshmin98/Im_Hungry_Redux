package ImHungryTest;

import java.io.UnsupportedEncodingException;

import com.mashape.unirest.http.exceptions.UnirestException;

import org.junit.Test;

import ImHungryServlet.RecipeServlet;
import ImHungryServlet.YelpRestaurantService;
import junit.framework.Assert;

public class BackendTest {

        @Test
        public void testYelpRestaurntQuery() throws UnsupportedEncodingException {
                YelpRestaurantService yrs = new YelpRestaurantService();
                Assert.assertTrue(yrs.getRestaurantInfo("Chinese", "5", "8500").contains("Northern Cafe"));
                Assert.assertTrue(yrs.getRestaurantInfo("Chinese", "5", "8500").contains("Los Angeles"));

        }

        @Test(expected = RuntimeException.class)
        public void testYelpRestaurantEmptyQuery() throws UnsupportedEncodingException {
                YelpRestaurantService yrs = new YelpRestaurantService();
                String json = yrs.getRestaurantInfo("", "", "");
        }

        // @Test
        // public void testRecipeMalformed() throws UnirestException {
        //         RecipeServlet rs = new RecipeServlet();
        //         Assert.assertTrue(rs.getRecipeData("asdfasdfasdf", "10").contains("number"));
        // }

        // @Test
        // public void testRecipeQuery() throws UnirestException {

        //         RecipeServlet rs = new RecipeServlet();
        //         Assert.assertTrue(rs.getRecipeData("soup", "3").contains("Red Lentil Soup"));
        //         Assert.assertTrue(rs.getRecipeData("soup", "3").contains("preparationMinutes"));
        //         Assert.assertTrue(rs.getRecipeData("soup", "3").contains("cookingMinutes"));
        //         Assert.assertTrue(rs.getRecipeData("soup", "3").contains("image"));
        //         Assert.assertTrue(rs.getRecipeData("soup", "3").contains("analyzedInstructions"));
        // }

}
