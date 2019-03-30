package ImHungry;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


import ro.pippo.test.PippoRule;
import ro.pippo.test.PippoTest;

public class BackendTest extends PippoTest {

  @Rule
  public PippoRule pippoRule = new PippoRule(new PippoApplication());

  // @Test
  // public void testHello() {
  //   Response response = get("/");
  //   response.then()
  //       .statusCode(200)
  //       .contentType(ContentType.JSON);
  // }

  @Test
  /* User searches for Query: Burgers, Limit: 5 results, Radius: 8500 m */
  public void testRestaurants() {
    Response response = get("/restaurants?query=burgers&limit=5&radius=8500");
    System.out.println(response.toString());
    response.then()
        .statusCode(200)
        .contentType(ContentType.JSON); 
      assertEquals("{\"businesses\": [{\"id\": \"_yVWZKwr_9ne_7ShMscWjA\", \"alias\": \"burger-plaza-grill-los-angeles\", \"name\": \"Burger Plaza Grill\", \"image_url\": \"https://s3-media1.fl.yelpcdn.com/bphoto/t6R8d01_EEBVVsPh38q_rw/o.jpg\", \"is_closed\": false, \"url\": \"https://www.yelp.com/biz/burger-plaza-grill-los-angeles?adjust_creative=TDGLRk9p6uqlW-mfLx7Skw&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=TDGLRk9p6uqlW-mfLx7Skw\", \"review_count\": 69, \"categories\": [{\"alias\": \"burgers\", \"title\": \"Burgers\"}, {\"alias\": \"sandwiches\", \"title\": \"Sandwiches\"}], \"rating\": 4.5, \"coordinates\": {\"latitude\": 34.01744, \"longitude\": -118.2783}, \"transactions\": [\"pickup\", \"delivery\"], \"price\": \"$\", \"location\": {\"address1\": \"3655 S Grand Ave\", \"address2\": \"\", \"address3\": \"\", \"city\": \"Los Angeles\", \"zip_code\": \"90007\", \"country\": \"US\", \"state\": \"CA\", \"display_address\": [\"3655 S Grand Ave\", \"Los Angeles, CA 90007\"]}, \"phone\": \"+12137659787\", \"display_phone\": \"(213) 765-9787\", \"distance\": 732.1111160793129}, {\"id\": \"dxLiwx8Ghcm0AWNph0xrVw\", \"alias\": \"the-dragon-and-meeple-los-angeles\", \"name\": \"The Dragon and Meeple\", \"image_url\": \"https://s3-media1.fl.yelpcdn.com/bphoto/i-Q2ldUcSgvXaRZdMcpfGg/o.jpg\", \"is_closed\": false, \"url\": \"https://www.yelp.com/biz/the-dragon-and-meeple-los-angeles?adjust_creative=TDGLRk9p6uqlW-mfLx7Skw&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=TDGLRk9p6uqlW-mfLx7Skw\", \"review_count\": 4, \"categories\": [{\"alias\": \"tabletopgames\", \"title\": \"Tabletop Games\"}, {\"alias\": \"burgers\", \"title\": \"Burgers\"}], \"rating\": 5.0, \"coordinates\": {\"latitude\": 34.01619, \"longitude\": -118.28168}, \"transactions\": [], \"location\": {\"address1\": \"3742 S Flower St\", \"address2\": \"\", \"address3\": null, \"city\": \"Los Angeles\", \"zip_code\": \"90007\", \"country\": \"US\", \"state\": \"CA\", \"display_address\": [\"3742 S Flower St\", \"Los Angeles, CA 90007\"]}, \"phone\": \"+12139735029\", \"display_phone\": \"(213) 973-5029\", \"distance\": 569.2009519733493}, {\"id\": \"Q3pwRtT8v8oTCKFEYJgCbg\", \"alias\": \"cassells-hamburgers-los-angeles-2\", \"name\": \"Cassell's Hamburgers\", \"image_url\": \"https://s3-media2.fl.yelpcdn.com/bphoto/8N68gTRLQEdU74GU-BqdTw/o.jpg\", \"is_closed\": false, \"url\": \"https://www.yelp.com/biz/cassells-hamburgers-los-angeles-2?adjust_creative=TDGLRk9p6uqlW-mfLx7Skw&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=TDGLRk9p6uqlW-mfLx7Skw\", \"review_count\": 1366, \"categories\": [{\"alias\": \"burgers\", \"title\": \"Burgers\"}, {\"alias\": \"breakfast_brunch\", \"title\": \"Breakfast & Brunch\"}, {\"alias\": \"newamerican\", \"title\": \"American (New)\"}], \"rating\": 4.0, \"coordinates\": {\"latitude\": 34.0633354187012, \"longitude\": -118.300720214844}, \"transactions\": [\"pickup\", \"delivery\"], \"price\": \"$$\", \"location\": {\"address1\": \"3600 W 6th St\", \"address2\": null, \"address3\": \"\", \"city\": \"Los Angeles\", \"zip_code\": \"90020\", \"country\": \"US\", \"state\": \"CA\", \"display_address\": [\"3600 W 6th St\", \"Los Angeles, CA 90020\"]}, \"phone\": \"+12133875502\", \"display_phone\": \"(213) 387-5502\", \"distance\": 4962.62992846366}, {\"id\": \"cyTW8Ee1wTGbZKWKQFgLWg\", \"alias\": \"burgerim-los-angeles-15\", \"name\": \"Burgerim\", \"image_url\": \"https://s3-media3.fl.yelpcdn.com/bphoto/QO4Owwr5nDka7Vwc5JZ0TQ/o.jpg\", \"is_closed\": false, \"url\": \"https://www.yelp.com/biz/burgerim-los-angeles-15?adjust_creative=TDGLRk9p6uqlW-mfLx7Skw&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=TDGLRk9p6uqlW-mfLx7Skw\", \"review_count\": 1, \"categories\": [{\"alias\": \"hotdogs\", \"title\": \"Fast Food\"}, {\"alias\": \"burgers\", \"title\": \"Burgers\"}, {\"alias\": \"chicken_wings\", \"title\": \"Chicken Wings\"}], \"rating\": 4.0, \"coordinates\": {\"latitude\": 34.0318784, \"longitude\": -118.2841149}, \"transactions\": [\"pickup\", \"delivery\"], \"location\": {\"address1\": \"2595 S Hoover St\", \"address2\": null, \"address3\": \"\", \"city\": \"Los Angeles\", \"zip_code\": \"90007\", \"country\": \"US\", \"state\": \"CA\", \"display_address\": [\"2595 S Hoover St\", \"Los Angeles, CA 90007\"]}, \"phone\": \"+12135365538\", \"display_phone\": \"(213) 536-5538\", \"distance\": 1282.6547244661592}, {\"id\": \"vpe7aCGjaAjoCLC_qIDXHA\", \"alias\": \"burger-cart-los-angeles\", \"name\": \"Burger Cart\", \"image_url\": \"\", \"is_closed\": false, \"url\": \"https://www.yelp.com/biz/burger-cart-los-angeles?adjust_creative=TDGLRk9p6uqlW-mfLx7Skw&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=TDGLRk9p6uqlW-mfLx7Skw\", \"review_count\": 1, \"categories\": [{\"alias\": \"foodtrucks\", \"title\": \"Food Trucks\"}, {\"alias\": \"burgers\", \"title\": \"Burgers\"}], \"rating\": 5.0, \"coordinates\": {\"latitude\": 34.0476938856064, \"longitude\": -118.335749544203}, \"transactions\": [], \"location\": {\"address1\": \"4550 W Pico Blvd\", \"address2\": \"Ste D-101\", \"address3\": null, \"city\": \"Los Angeles\", \"zip_code\": \"90019\", \"country\": \"US\", \"state\": \"CA\", \"display_address\": [\"4550 W Pico Blvd\", \"Ste D-101\", \"Los Angeles, CA 90019\"]}, \"phone\": \"\", \"display_phone\": \"\", \"distance\": 5531.920636020443}], \"total\": 1700, \"region\": {\"center\": {\"longitude\": -118.2854, \"latitude\": 34.0206}}}", response.asString());
  }

   @Test
  public void testRestaurantsEmpty() {
    Response response = get("/restaurants?query=&limit=&radius=");
    response.then()
        .statusCode(200)
        .contentType(ContentType.JSON);
    // Add asserts for json here
  }

  @Test
  public void testRecipes() {
    Response response = get("/recipes?query=test&limit=12");
    response.then()
        .statusCode(200)
        .contentType(ContentType.JSON);
    // Add asserts for json here
  }

}
