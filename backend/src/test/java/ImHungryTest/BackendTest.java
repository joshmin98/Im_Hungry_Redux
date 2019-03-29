package ImHungryTest;


import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import javax.servlet.http.*;


import java.util.List;
import java.io.*;
// import java.io.UnsupportedEncodingException;

import com.mashape.unirest.http.exceptions.UnirestException;

import org.junit.Test;

import ImHungryServlet.RecipeServlet;
import ImHungryServlet.RestaurantServlet;

import ImHungryServlet.YelpRestaurantService;
import ImHungryServlet.RestaurantServlet;
import junit.framework.Assert;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.io.*;
import javax.servlet.http.*;

public class BackendTest {
        @Test
        public void testYelpServlet() throws Exception {
                HttpServletRequest request = mock(HttpServletRequest.class);       
                HttpServletResponse response = mock(HttpServletResponse.class);
                when(request.getParameter("query")).thenReturn("pizza");
                when(request.getParameter("numResults")).thenReturn("5");
                when(request.getParameter("radius")).thenReturn("8500");

                StringWriter stringWriter = new StringWriter();
                PrintWriter writer = new PrintWriter(stringWriter);
                when(response.getWriter()).thenReturn(writer);

                RestaurantServlet rs = new RestaurantServlet();
                rs.doGet(request, response);

                verify(request, atLeast(1)).getParameter("query"); //  verify param was called...
                verify(request, atLeast(1)).getParameter("numResults"); 
                verify(request, atLeast(1)).getParameter("radius");
                // writer.flush(); // it may not have been flushed yet...
                String returned = stringWriter.toString();
                // String result = "{\"businesses\":[{\"id\":\"_PC5sA2vqWW3pH1S9yqFPg\",\"alias\":\"rances-chicago-pizza-los-angeles-2\",\"name\":\"Rance's Chicago Pizza\",\"image_url\":\"https://s3-media2.fl.yelpcdn.com/bphoto/PWWbpmotuVv_iPQsJ_6n-A/o.jpg\",\"is_closed\":false,\"url\":\"https://www.yelp.com/biz/rances-chicago-pizza-los-angeles-2?adjust_creative=TDGLRk9p6uqlW-mfLx7Skw&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=TDGLRk9p6uqlW-mfLx7Skw\",\"review_count\":154,\"categories\":[{\"alias\":\"pizza\",\"title\":\"Pizza\"},{\"alias\":\"salad\",\"title\":\"Salad\"},{\"alias\":\"beerbar\",\"title\":\"Beer Bar\"}],\"rating\":4,\"coordinates\":{\"latitude\":34.0242256,\"longitude\":-118.2846996},\"transactions\":[\"delivery\",\"pickup\"],\"price\":\"$$\",\"location\":{\"address1\":\"835 W Jefferson Blvd\",\"address2\":\"Ste 1740\",\"city\":\"Los Angeles\",\"zip_code\":\"90089\",\"country\":\"US\",\"state\":\"CA\",\"display_address\":[\"835 W Jefferson Blvd\",\"Ste 1740\",\"Los Angeles, CA 90089\"]},\"phone\":\"+13233298000\",\"display_phone\":\"(323) 329-8000\",\"distance\":439.6164837612774},{\"id\":\"TDCDiaAsehZ0fuB65_yVQw\",\"alias\":\"pizza-studio-los-angeles-5\",\"name\":\"Pizza Studio\",\"image_url\":\"https://s3-media1.fl.yelpcdn.com/bphoto/woBEUeNYmlQ_rIr5EF4fVQ/o.jpg\",\"is_closed\":false,\"url\":\"https://www.yelp.com/biz/pizza-studio-los-angeles-5?adjust_creative=TDGLRk9p6uqlW-mfLx7Skw&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=TDGLRk9p6uqlW-mfLx7Skw\",\"review_count\":492,\"categories\":[{\"alias\":\"pizza\",\"title\":\"Pizza\"},{\"alias\":\"salad\",\"title\":\"Salad\"},{\"alias\":\"vegan\",\"title\":\"Vegan\"}],\"rating\":4,\"coordinates\":{\"latitude\":34.018666,\"longitude\":-118.281577},\"transactions\":[],\"price\":\"$\",\"location\":{\"address1\":\"3584 S Figueroa St\",\"address2\":\"\",\"address3\":\"\",\"city\":\"Los Angeles\",\"zip_code\":\"90007\",\"country\":\"US\",\"state\":\"CA\",\"display_address\":[\"3584 S Figueroa St\",\"Los Angeles, CA 90007\"]},\"phone\":\"+12137636124\",\"display_phone\":\"(213) 763-6124\",\"distance\":412.78404190711296},{\"id\":\"T1RfgUMYKW3HD55SEJILbQ\",\"alias\":\"braazo-pizza-los-angeles\",\"name\":\"Braazo Pizza\",\"image_url\":\"https://s3-media3.fl.yelpcdn.com/bphoto/h23pKAd393nUnSc2PSAOfw/o.jpg\",\"is_closed\":false,\"url\":\"https://www.yelp.com/biz/braazo-pizza-los-angeles?adjust_creative=TDGLRk9p6uqlW-mfLx7Skw&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=TDGLRk9p6uqlW-mfLx7Skw\",\"review_count\":369,\"categories\":[{\"alias\":\"pizza\",\"title\":\"Pizza\"},{\"alias\":\"salad\",\"title\":\"Salad\"},{\"alias\":\"bbq\",\"title\":\"Barbeque\"}],\"rating\":4.5,\"coordinates\":{\"latitude\":34.0438886,\"longitude\":-118.251571},\"transactions\":[\"delivery\",\"pickup\"],\"price\":\"$$\",\"location\":{\"address1\":\"100 1/2 W 7th St\",\"address2\":\"\",\"address3\":\"\",\"city\":\"Los Angeles\",\"zip_code\":\"90014\",\"country\":\"US\",\"state\":\"CA\",\"display_address\":[\"100 1/2 W 7th St\",\"Los Angeles, CA 90014\"]},\"phone\":\"+12136223390\",\"display_phone\":\"(213) 622-3390\",\"distance\":4049.3957724492657},{\"id\":\"r3P7aRD9HwskzoqnwxRhxg\",\"alias\":\"pizza-next-door-los-angeles\",\"name\":\"Pizza Next Door\",\"image_url\":\"https://s3-media4.fl.yelpcdn.com/bphoto/sdiqyZRXcRiP8n6YUOB-GQ/o.jpg\",\"is_closed\":false,\"url\":\"https://www.yelp.com/biz/pizza-next-door-los-angeles?adjust_creative=TDGLRk9p6uqlW-mfLx7Skw&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=TDGLRk9p6uqlW-mfLx7Skw\",\"review_count\":433,\"categories\":[{\"alias\":\"pizza\",\"title\":\"Pizza\"},{\"alias\":\"italian\",\"title\":\"Italian\"}],\"rating\":4,\"coordinates\":{\"latitude\":34.0473344953997,\"longitude\":-118.260447463435},\"transactions\":[],\"price\":\"$\",\"location\":{\"address1\":\"806 W 8th St\",\"address2\":\"Unit B\",\"address3\":\"\",\"city\":\"Los Angeles\",\"zip_code\":\"90017\",\"country\":\"US\",\"state\":\"CA\",\"display_address\":[\"806 W 8th St\",\"Unit B\",\"Los Angeles, CA 90017\"]},\"phone\":\"+12136226595\",\"display_phone\":\"(213) 622-6595\",\"distance\":3758.1966982750614},{\"id\":\"fmf9Gz1afO8X0aohHsyWZA\",\"alias\":\"blaze-fast-fired-pizza-los-angeles-4\",\"name\":\"Blaze Fast-Fire'd Pizza\",\"image_url\":\"https://s3-media2.fl.yelpcdn.com/bphoto/TkaNTr2QGRPAMaTdiLY1Eg/o.jpg\",\"is_closed\":false,\"url\":\"https://www.yelp.com/biz/blaze-fast-fired-pizza-los-angeles-4?adjust_creative=TDGLRk9p6uqlW-mfLx7Skw&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=TDGLRk9p6uqlW-mfLx7Skw\",\"review_count\":375,\"categories\":[{\"alias\":\"salad\",\"title\":\"Salad\"},{\"alias\":\"pizza\",\"title\":\"Pizza\"},{\"alias\":\"hotdogs\",\"title\":\"Fast Food\"}],\"rating\":4,\"coordinates\":{\"latitude\":34.0231904567859,\"longitude\":-118.279516334918},\"transactions\":[],\"price\":\"$\",\"location\":{\"address1\":\"3335 S Figueroa St\",\"address2\":\"\",\"address3\":\"\",\"city\":\"Los Angeles\",\"zip_code\":\"90007\",\"country\":\"US\",\"state\":\"CA\",\"display_address\":[\"3335 S Figueroa St\",\"Los Angeles, CA 90007\"]},\"phone\":\"+12138142485\",\"display_phone\":\"(213) 814-2485\",\"distance\":614.0020665809853}],\"total\":1200,\"region\":{\"center\":{\"longitude\":-118.2854,\"latitude\":34.0206}}}";
                String result = "{\"businesses\": [{\"id\": \"_PC5sA2vqWW3pH1S9yqFPg\", \"alias\": \"rances-chicago-pizza-los-angeles-2\", \"name\": \"Rance's Chicago Pizza\", \"image_url\": \"https://s3-media2.fl.yelpcdn.com/bphoto/PWWbpmotuVv_iPQsJ_6n-A/o.jpg\", \"is_closed\": false, \"url\": \"https://www.yelp.com/biz/rances-chicago-pizza-los-angeles-2?adjust_creative=TDGLRk9p6uqlW-mfLx7Skw&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=TDGLRk9p6uqlW-mfLx7Skw\", \"review_count\": 154, \"categories\": [{\"alias\": \"pizza\", \"title\": \"Pizza\"}, {\"alias\": \"salad\", \"title\": \"Salad\"}, {\"alias\": \"beerbar\", \"title\": \"Beer Bar\"}], \"rating\": 4.0, \"coordinates\": {\"latitude\": 34.0242256, \"longitude\": -118.2846996}, \"transactions\": [\"pickup\", \"delivery\"], \"price\": \"$$\", \"location\": {\"address1\": \"835 W Jefferson Blvd\", \"address2\": \"Ste 1740\", \"address3\": null, \"city\": \"Los Angeles\", \"zip_code\": \"90089\", \"country\": \"US\", \"state\": \"CA\", \"display_address\": [\"835 W Jefferson Blvd\", \"Ste 1740\", \"Los Angeles, CA 90089\"]}, \"phone\": \"+13233298000\", \"display_phone\": \"(323) 329-8000\", \"distance\": 439.6164837612774}, {\"id\": \"TDCDiaAsehZ0fuB65_yVQw\", \"alias\": \"pizza-studio-los-angeles-5\", \"name\": \"Pizza Studio\", \"image_url\": \"https://s3-media1.fl.yelpcdn.com/bphoto/woBEUeNYmlQ_rIr5EF4fVQ/o.jpg\", \"is_closed\": false, \"url\": \"https://www.yelp.com/biz/pizza-studio-los-angeles-5?adjust_creative=TDGLRk9p6uqlW-mfLx7Skw&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=TDGLRk9p6uqlW-mfLx7Skw\", \"review_count\": 492, \"categories\": [{\"alias\": \"pizza\", \"title\": \"Pizza\"}, {\"alias\": \"salad\", \"title\": \"Salad\"}, {\"alias\": \"vegan\", \"title\": \"Vegan\"}], \"rating\": 4.0, \"coordinates\": {\"latitude\": 34.018666, \"longitude\": -118.281577}, \"transactions\": [], \"price\": \"$\", \"location\": {\"address1\": \"3584 S Figueroa St\", \"address2\": \"\", \"address3\": \"\", \"city\": \"Los Angeles\", \"zip_code\": \"90007\", \"country\": \"US\", \"state\": \"CA\", \"display_address\": [\"3584 S Figueroa St\", \"Los Angeles, CA 90007\"]}, \"phone\": \"+12137636124\", \"display_phone\": \"(213) 763-6124\", \"distance\": 412.78404190711296}, {\"id\": \"T1RfgUMYKW3HD55SEJILbQ\", \"alias\": \"braazo-pizza-los-angeles\", \"name\": \"Braazo Pizza\", \"image_url\": \"https://s3-media3.fl.yelpcdn.com/bphoto/h23pKAd393nUnSc2PSAOfw/o.jpg\", \"is_closed\": false, \"url\": \"https://www.yelp.com/biz/braazo-pizza-los-angeles?adjust_creative=TDGLRk9p6uqlW-mfLx7Skw&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=TDGLRk9p6uqlW-mfLx7Skw\", \"review_count\": 369, \"categories\": [{\"alias\": \"pizza\", \"title\": \"Pizza\"}, {\"alias\": \"salad\", \"title\": \"Salad\"}, {\"alias\": \"bbq\", \"title\": \"Barbeque\"}], \"rating\": 4.5, \"coordinates\": {\"latitude\": 34.0438886, \"longitude\": -118.251571}, \"transactions\": [\"pickup\", \"delivery\"], \"price\": \"$$\", \"location\": {\"address1\": \"100 1/2 W 7th St\", \"address2\": \"\", \"address3\": \"\", \"city\": \"Los Angeles\", \"zip_code\": \"90014\", \"country\": \"US\", \"state\": \"CA\", \"display_address\": [\"100 1/2 W 7th St\", \"Los Angeles, CA 90014\"]}, \"phone\": \"+12136223390\", \"display_phone\": \"(213) 622-3390\", \"distance\": 4049.3957724492657}, {\"id\": \"r3P7aRD9HwskzoqnwxRhxg\", \"alias\": \"pizza-next-door-los-angeles\", \"name\": \"Pizza Next Door\", \"image_url\": \"https://s3-media4.fl.yelpcdn.com/bphoto/sdiqyZRXcRiP8n6YUOB-GQ/o.jpg\", \"is_closed\": false, \"url\": \"https://www.yelp.com/biz/pizza-next-door-los-angeles?adjust_creative=TDGLRk9p6uqlW-mfLx7Skw&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=TDGLRk9p6uqlW-mfLx7Skw\", \"review_count\": 433, \"categories\": [{\"alias\": \"pizza\", \"title\": \"Pizza\"}, {\"alias\": \"italian\", \"title\": \"Italian\"}], \"rating\": 4.0, \"coordinates\": {\"latitude\": 34.0473344953997, \"longitude\": -118.260447463435}, \"transactions\": [], \"price\": \"$\", \"location\": {\"address1\": \"806 W 8th St\", \"address2\": \"Unit B\", \"address3\": \"\", \"city\": \"Los Angeles\", \"zip_code\": \"90017\", \"country\": \"US\", \"state\": \"CA\", \"display_address\": [\"806 W 8th St\", \"Unit B\", \"Los Angeles, CA 90017\"]}, \"phone\": \"+12136226595\", \"display_phone\": \"(213) 622-6595\", \"distance\": 3758.1966982750614}, {\"id\": \"fmf9Gz1afO8X0aohHsyWZA\", \"alias\": \"blaze-fast-fired-pizza-los-angeles-4\", \"name\": \"Blaze Fast-Fire'd Pizza\", \"image_url\": \"https://s3-media2.fl.yelpcdn.com/bphoto/TkaNTr2QGRPAMaTdiLY1Eg/o.jpg\", \"is_closed\": false, \"url\": \"https://www.yelp.com/biz/blaze-fast-fired-pizza-los-angeles-4?adjust_creative=TDGLRk9p6uqlW-mfLx7Skw&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=TDGLRk9p6uqlW-mfLx7Skw\", \"review_count\": 375, \"categories\": [{\"alias\": \"salad\", \"title\": \"Salad\"}, {\"alias\": \"pizza\", \"title\": \"Pizza\"}, {\"alias\": \"hotdogs\", \"title\": \"Fast Food\"}], \"rating\": 4.0, \"coordinates\": {\"latitude\": 34.0231904567859, \"longitude\": -118.279516334918}, \"transactions\": [], \"price\": \"$\", \"location\": {\"address1\": \"3335 S Figueroa St\", \"address2\": \"\", \"address3\": \"\", \"city\": \"Los Angeles\", \"zip_code\": \"90007\", \"country\": \"US\", \"state\": \"CA\", \"display_address\": [\"3335 S Figueroa St\", \"Los Angeles, CA 90007\"]}, \"phone\": \"+12138142485\", \"display_phone\": \"(213) 814-2485\", \"distance\": 614.0020665809853}], \"total\": 1200, \"region\": {\"center\": {\"longitude\": -118.2854, \"latitude\": 34.0206}}}";
                System.out.println(returned);
                System.out.println(result);
                System.out.println(returned.equals(result));
                assertTrue(returned.length() == result.length());
        }

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
