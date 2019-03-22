package ImHungryTest;

import ImHungryServlet.RecipeServlet;
import ImHungryServlet.YelpRestaurantService;

import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.junit.Test;

public class BackendTest {
	
	@Test
	public void testYelpRestaurntQuery() throws UnsupportedEncodingException {
		YelpRestaurantService yrs = new YelpRestaurantService();
		assertTrue( yrs.getRestaurantInfo("Chinese", "5").contains("Northern Cafe"));
		assertTrue( yrs.getRestaurantInfo("Chinese", "5").contains("Los Angeles"));
				
	}
	
	@Test(expected=RuntimeException.class)
	public void testYelpRestaurntEmptyQuery() throws UnsupportedEncodingException {
		YelpRestaurantService yrs = new YelpRestaurantService();
		String json = yrs.getRestaurantInfo("", "");
	} 
	
	@Test
	public void testRecipeMalformed() throws UnirestException{
		RecipeServlet rs = new RecipeServlet(); 
		assertTrue(rs.getRecipeData("asdfasdfasdf", "10").contains("number"));
	}

	@Test
	public void testRecipeQuery() throws UnirestException{
		
		RecipeServlet rs = new RecipeServlet();
        assertTrue( rs.getRecipeData("soup", "3").contains("Red Lentil Soup"));
        assertTrue( rs.getRecipeData("soup", "3").contains("preparationMinutes"));
        assertTrue( rs.getRecipeData("soup", "3").contains("cookingMinutes"));
        assertTrue( rs.getRecipeData("soup", "3").contains("image"));
        assertTrue( rs.getRecipeData("soup", "3").contains("analyzedInstructions"));
	}

}

