package ImHungry;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jayway.restassured.filter.session.SessionFilter;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

import org.apache.http.cookie.SetCookie;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.ArrayList;

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
  public void testDefault() {
    Response response = get("/");
        response.then()
        .statusCode(200);
  }

  @Test
  /* User searches for Query: Burgers, Limit: 5 results, Radius: 8500 m */
  public void testRestaurants() {
    Response response = get("/restaurants?query=burgers&limit=5&radius=5");
    System.out.println(response.toString());
    response.then()
        .statusCode(200)
        .contentType(ContentType.JSON); 
    // assertEquals(yelp.getRestaurantInfo("burgers", "5", "8500"), response.asString());
    // assertEquals("[{\"id\":\"DXFhzx94myitMxmBhsdz8A\",\"alias\":\"traditions-los-angeles\",\"name\":\"Traditions\",\"image_url\":\"https://s3-media4.fl.yelpcdn.com/bphoto/ushDMhbnoOhefTJQf1z5mQ/o.jpg\",\"is_closed\":false,\"url\":\"https://www.yelp.com/biz/traditions-los-angeles?adjust_creative=TDGLRk9p6uqlW-mfLx7Skw&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=TDGLRk9p6uqlW-mfLx7Skw\",\"review_count\":116,\"categories\":[{\"alias\":\"divebars\",\"title\":\"Dive Bars\"},{\"alias\":\"sportsbars\",\"title\":\"Sports Bars\"},{\"alias\":\"burgers\",\"title\":\"Burgers\"}],\"rating\":3.5,\"coordinates\":{\"latitude\":34.019963,\"longitude\":-118.286161},\"transactions\":[],\"price\":\"$$\",\"location\":{\"address1\":\"3607 Trousdale Pkwy\",\"address3\":\"\",\"city\":\"Los Angeles\",\"zip_code\":\"90089\",\"country\":\"US\",\"state\":\"CA\",\"display_address\":[\"3607 Trousdale Pkwy\",\"Los Angeles, CA 90089\"]},\"phone\":\"+12138213445\",\"display_phone\":\"(213) 821-3445\",\"distance\":99.67984174213088,\"type\":\"restaurant\"},{\"id\":\"cgMqbKO7UGLfijRqfg9kjw\",\"alias\":\"the-habit-burger-grill-los-angeles\",\"name\":\"The Habit Burger Grill\",\"image_url\":\"https://s3-media1.fl.yelpcdn.com/bphoto/rhkud8IzJLSDm8pNt1ZK1g/o.jpg\",\"is_closed\":false,\"url\":\"https://www.yelp.com/biz/the-habit-burger-grill-los-angeles?adjust_creative=TDGLRk9p6uqlW-mfLx7Skw&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=TDGLRk9p6uqlW-mfLx7Skw\",\"review_count\":58,\"categories\":[{\"alias\":\"burgers\",\"title\":\"Burgers\"},{\"alias\":\"salad\",\"title\":\"Salad\"},{\"alias\":\"sandwiches\",\"title\":\"Sandwiches\"}],\"rating\":2.5,\"coordinates\":{\"latitude\":34.0200721451021,\"longitude\":-118.286443501711},\"transactions\":[],\"price\":\"$\",\"location\":{\"address1\":\"3607 Trousdale Pkwy\",\"address2\":\"\",\"address3\":\"\",\"city\":\"Los Angeles\",\"zip_code\":\"90089\",\"country\":\"US\",\"state\":\"CA\",\"display_address\":[\"3607 Trousdale Pkwy\",\"Los Angeles, CA 90089\"]},\"phone\":\"+15626336364\",\"display_phone\":\"(562) 633-6364\",\"distance\":112.66805356899862,\"type\":\"restaurant\"},{\"id\":\"dQI2N7qVsjrQ4BxnyR0pHg\",\"alias\":\"wahlburgers-los-angeles-2\",\"name\":\"Wahlburgers\",\"image_url\":\"https://s3-media3.fl.yelpcdn.com/bphoto/HSW1ZLSdq4dDgBSo9FJwGw/o.jpg\",\"is_closed\":false,\"url\":\"https://www.yelp.com/biz/wahlburgers-los-angeles-2?adjust_creative=TDGLRk9p6uqlW-mfLx7Skw&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=TDGLRk9p6uqlW-mfLx7Skw\",\"review_count\":176,\"categories\":[{\"alias\":\"burgers\",\"title\":\"Burgers\"},{\"alias\":\"tradamerican\",\"title\":\"American (Traditional)\"}],\"rating\":3.5,\"coordinates\":{\"latitude\":34.0242256,\"longitude\":-118.2846996},\"transactions\":[],\"price\":\"$$\",\"location\":{\"address1\":\"835 W Jefferson Blvd\",\"address2\":\"Unit 1710\",\"city\":\"Los Angeles\",\"zip_code\":\"90007\",\"country\":\"US\",\"state\":\"CA\",\"display_address\":[\"835 W Jefferson Blvd\",\"Unit 1710\",\"Los Angeles, CA 90007\"]},\"phone\":\"+12135365962\",\"display_phone\":\"(213) 536-5962\",\"distance\":439.6164837612774,\"type\":\"restaurant\"},{\"id\":\"87cOHd188XjyKAkIiPHdyw\",\"alias\":\"natural-history-museum-grill-los-angeles\",\"name\":\"Natural History Museum Grill\",\"image_url\":\"https://s3-media3.fl.yelpcdn.com/bphoto/QQHztj1af_CFBERYbCgDcw/o.jpg\",\"is_closed\":false,\"url\":\"https://www.yelp.com/biz/natural-history-museum-grill-los-angeles?adjust_creative=TDGLRk9p6uqlW-mfLx7Skw&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=TDGLRk9p6uqlW-mfLx7Skw\",\"review_count\":52,\"categories\":[{\"alias\":\"burgers\",\"title\":\"Burgers\"},{\"alias\":\"sandwiches\",\"title\":\"Sandwiches\"},{\"alias\":\"tradamerican\",\"title\":\"American (Traditional)\"}],\"rating\":3.0,\"coordinates\":{\"latitude\":34.0172114571928,\"longitude\":-118.289133757353},\"transactions\":[],\"price\":\"$$\",\"location\":{\"address1\":\"900 Exposition Blvd\",\"address2\":\"\",\"address3\":\"\",\"city\":\"Los Angeles\",\"zip_code\":\"90037\",\"country\":\"US\",\"state\":\"CA\",\"display_address\":[\"900 Exposition Blvd\",\"Los Angeles, CA 90037\"]},\"phone\":\"+12137633250\",\"display_phone\":\"(213) 763-3250\",\"distance\":510.2813414569061,\"type\":\"restaurant\"},{\"id\":\"9mHXsA466j-WfqitLT9_Ag\",\"alias\":\"trimana-los-angeles-43\",\"name\":\"Trimana\",\"image_url\":\"https://s3-media1.fl.yelpcdn.com/bphoto/xTUq-zS21vLEjrL3fePTDA/o.jpg\",\"is_closed\":false,\"url\":\"https://www.yelp.com/biz/trimana-los-angeles-43?adjust_creative=TDGLRk9p6uqlW-mfLx7Skw&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=TDGLRk9p6uqlW-mfLx7Skw\",\"review_count\":71,\"categories\":[{\"alias\":\"mexican\",\"title\":\"Mexican\"},{\"alias\":\"burgers\",\"title\":\"Burgers\"},{\"alias\":\"pizza\",\"title\":\"Pizza\"}],\"rating\":4.0,\"coordinates\":{\"latitude\":34.015164,\"longitude\":-118.2858663},\"transactions\":[],\"price\":\"$\",\"location\":{\"address1\":\"700 Exposition Park Dr\",\"address2\":\"\",\"address3\":\"\",\"city\":\"Los Angeles\",\"zip_code\":\"90037\",\"country\":\"US\",\"state\":\"CA\",\"display_address\":[\"700 Exposition Park Dr\",\"Los Angeles, CA 90037\"]},\"phone\":\"+12137481226\",\"display_phone\":\"(213) 748-1226\",\"distance\":516.710750758593,\"type\":\"restaurant\"}]",
    //                 response.asString());
    
  }

  @Test
  public void testRestaurantRadius() {
    Response response = get("/restaurants?query=burgers&limit=5&radius=0");
    response.then()
        .statusCode(200)
        .contentType(ContentType.JSON); 
    
    response = get("/restaurants?query=burgers&limit=5&radius=5");
    response.then()
        .statusCode(200)
        .contentType(ContentType.JSON); 
    assertEquals(4836, response.asString().length());

  }

//    @Test
//   public void testRestaurantsEmpty() {
//     Response response = get("/restaurants?query=&limit=&radius=");
//     response.then()
//         .statusCode(200)
//         .contentType(ContentType.JSON);
//     // Add asserts for json here
//   }

  @Test
  public void testRecipes() {
    Response response = get("/recipes?query=pizza&limit=5");
    response.then()
        .statusCode(200)
        .contentType(ContentType.JSON);
  }

  @Test
  public void testListEmptyParam() {
    Response response = given().get("/list");
    response.then()
        .statusCode(200);
  }

  @Test
  public void testListAdd() {
    SessionFilter sessionFilter = new SessionFilter();
    given()
        .filter(sessionFilter)
        .get("/restaurants?query=pizza&limit=5&radius=5")// sessionId("testFavoritesList")
        .then().statusCode(200)
        .contentType(ContentType.JSON);
    Response response = given()
        .filter(sessionFilter)
        // .queryParam("listName", "Favorites")
        // .queryParam("id", "v5Eiu0WaNhDXBSNsAdjmUw")
        // .post("/list/add");
        .get("list/add?listName=Favorites&id=v5Eiu0WaNhDXBSNsAdjmUw");
    response.then()
        .statusCode(200);

    response = given()
        .filter(sessionFilter)
        // .queryParam("listName", "To Explore")
        // .queryParam("id", "v5Eiu0WaNhDXBSNsAdjmUw")
        // .post("/list/add");
        .get("list/add?listName=To Explore&id=v5Eiu0WaNhDXBSNsAdjmUw");
    response.then()
        .statusCode(200);

    response = given()
        .filter(sessionFilter)
        // .queryParam("listName", "Do Not Show")
        // .queryParam("id", "v5Eiu0WaNhDXBSNsAdjmUw")
        // .post("/list/add");
        .get("list/add?listName=Do Not Show&id=v5Eiu0WaNhDXBSNsAdjmUw");
    response.then()
        .statusCode(200);

    // // // finditem coverage
    response = given()
        .filter(sessionFilter)
        // .queryParam("listName", "Do Not Show")
        // .queryParam("id", "missing")
        // .post("/list/add");
        .get("list/add?listName=Do Not Show&id=missing");
    response.then()
        .statusCode(200);

    // // // item already in list
    response = given()
        .filter(sessionFilter)
        // .queryParam("listName", "Favorites")
        // .queryParam("id", "v5Eiu0WaNhDXBSNsAdjmUw")
        // .post("/list/add");
        .get("list/add?listName=Favorites&id=v5Eiu0WaNhDXBSNsAdjmUw");
    response.then()
        .statusCode(200);
  }

  @Test
  public void testListDelete() {
    SessionFilter sessionFilter = new SessionFilter();
    given()
        .filter(sessionFilter)
        .get("/restaurants?query=pizza&limit=5&radius=5")// sessionId("testFavoritesList")
        .then().statusCode(200)
        .contentType(ContentType.JSON);

    Response response = given()
        .filter(sessionFilter)
        // .queryParam("listName", "Favorites")
        // .queryParam("id", "v5Eiu0WaNhDXBSNsAdjmUw")
        // .post("/list/add");
        .get("list/add?listName=Favorites&id=v5Eiu0WaNhDXBSNsAdjmUw");
    response.then()
        .statusCode(200);

    response = given()
        .filter(sessionFilter)
        // .queryParam("listName", "Favorites")
        // .queryParam("id", "v5Eiu0WaNhDXBSNsAdjmUw")
        // .post("/list/delete");
        .get("list/delete?listName=Favorites&id=v5Eiu0WaNhDXBSNsAdjmUw");
    response.then()
        .statusCode(200);
    
    response = given()
        .filter(sessionFilter)
        .get("/list?listName=Favorites");
    response.then()
        .statusCode(200);
    JsonArray result = (JsonArray) (new JsonParser()).parse(response.asString());
    assertEquals(0, result.size());

    // item not in list
    response = given()
        .filter(sessionFilter)
        // .queryParam("listName", "Favorites")
        // .queryParam("id", "missing")
        // .post("/list/delete");
        .get("list/delete?listName=Favorites&id=missing");
    response.then()
        .statusCode(200);
  }

  @Test
  public void testListMove() {
    SessionFilter sessionFilter = new SessionFilter();
    given()
        .filter(sessionFilter)
        .get("/restaurants?query=pizza&limit=5&radius=5")
        .then().statusCode(200)
        .contentType(ContentType.JSON);

    Response response = given()
        .filter(sessionFilter)
        // .queryParam("listName", "Favorites")
        // .queryParam("id", "v5Eiu0WaNhDXBSNsAdjmUw")
        // .post("/list/add");
        .get("list/add?listName=Favorites&id=v5Eiu0WaNhDXBSNsAdjmUw");
    response.then()
        .statusCode(200);

    response = given()
        .filter(sessionFilter)
        .get("/list?listName=Favorites");
    response.then()
        .statusCode(200);
    JsonArray result = (JsonArray) (new JsonParser()).parse(response.asString());
    assertEquals(1, result.size());

    response = given()
        .filter(sessionFilter)
        // .queryParam("listName", "Favorites")
        // .queryParam("id", "v5Eiu0WaNhDXBSNsAdjmUw")
        // .queryParam("moveList", "To Explore")
        // .post("/list/move");
        .get("list/move?listName=Favorites&id=v5Eiu0WaNhDXBSNsAdjmUw&moveList=To Explore");
    response.then()
        .statusCode(200);
    
    response = given()
        .filter(sessionFilter)
        .get("/list?listName=Favorites");
    response.then()
        .statusCode(200);
    result = (JsonArray) (new JsonParser()).parse(response.asString());
    assertEquals(0, result.size());

    response = given().filter(sessionFilter).get("/list?listName=To Explore");
    response.then().statusCode(200);
    result = (JsonArray) (new JsonParser()).parse(response.asString());
    assertEquals(1, result.size());

    // item not in list
    response = given()
        .filter(sessionFilter)
        // .queryParam("listName", "Favorites")
        // .queryParam("id", "missing")
        // .queryParam("moveList", "To Explore")
        // .post("/list/move");
        .get("list/move?listName=Favorites&id=missing&moveList=To Explore");
    response.then()
        .statusCode(200);
  }

//   @Test
//   public void testListBad() {
//     SessionFilter sessionFilter = new SessionFilter();
//     given()
//         .filter(sessionFilter)
//         .get("/restaurants?query=pizza&limit=5&radius=8500")// sessionId("testFavoritesList")
//         .then().statusCode(200)
//         .contentType(ContentType.JSON);
//     Response response = given()
//         .filter(sessionFilter)
//         .queryParam("listName", "Favorites")
//         .queryParam("id", "v5Eiu0WaNhDXBSNsAdjmUw")
//         .queryParam("action", "bad")
//         .post("/list");
//     response.then()
//         .statusCode(200);
//   }

@Test
  public void testListReorder() {
    SessionFilter sessionFilter = new SessionFilter();
    given()
        .filter(sessionFilter)
        .get("/restaurants?query=pizza&limit=5&radius=5")
        .then().statusCode(200)
        .contentType(ContentType.JSON);

    String id1 = "v5Eiu0WaNhDXBSNsAdjmUw",
          id2 = "559251";
    Response response = given()
        .filter(sessionFilter)
        // .queryParam("listName", "Favorites")
        // .queryParam("id", id1)
        // .post("/list/add");
        .get("list/add?listName=Favorites&id=" + id1);
    response.then()
        .statusCode(200);

    response = given()
        .filter(sessionFilter)
        // .queryParam("listName", "Favorites")
        // .queryParam("id", id2)
        // .post("/list/add");
        .get("list/add?listName=Favorites&id=" + id2);
    response.then()
        .statusCode(200);
    
    response = given()
        .filter(sessionFilter)
        .get("/list?listName=Favorites");
    response.then()
        .statusCode(200);
    JsonArray result = (JsonArray) (new JsonParser()).parse(response.asString());
    assertEquals(id1, result.get(0).getAsJsonObject().get("id").getAsString());

    response = given()
        .filter(sessionFilter)
        // .queryParam("listName", "Favorites")
        // .queryParam("oldPosition", 0)
        // .queryParam("newPosition", 1)
        // .post("/reorder");
        .get("/reorder?listName=Favorites&oldPosition=0&newPosition=1");
    response.then()
        .statusCode(200);

    response = given()
        .filter(sessionFilter)
        .get("/list?listName=Favorites");
    response.then()
        .statusCode(200);
    result = (JsonArray) (new JsonParser()).parse(response.asString());
    assertEquals(id2, result.get(0).getAsJsonObject().get("id").getAsString());

    // out of bounds index
    response = given()
        .filter(sessionFilter)
        // .queryParam("listName", "Favorites")
        // .queryParam("oldPosition", 0)
        // .queryParam("newPosition", 10)
        // .post("/reorder");
        .get("/reorder?listName=Favorites&oldPosition=0&newPosition=10");
    response.then()
        .statusCode(200);

    // reorder empty list
    response = given()
        .filter(sessionFilter)
        // .queryParam("listName", "To Explore")
        // .queryParam("oldPosition", 0)
        // .queryParam("newPosition", 2)
        // .post("/reorder");
        .get("/reorder?listName=To Explore&oldPosition=0&newPosition=2");
    response.then()
        .statusCode(200);
  }

  // Start of database service testing
  @Test
   public void dbRouteTest() {
      Response response = get("/db");
      response.then()
        .statusCode(200);
      //DatabaseService db = new DatabaseService();
      
  }
 
    // Start of user authentication testing
    @Test
    public void userLoginTest() {
        Response response = get("/user?email=test@usc.edu");
        response.then()
            .statusCode(200);
            assertEquals("test@usc.edu",
                            response.asString());
                
    }

    // Start of recently searched query test 
    @Test
    public void recentlySearchTestEmpty() {
        Response response = get("/searches?email=test@usc.edu");
        response.then()
            .statusCode(200);
            assertEquals("[]", response.asString());
    }

    @Test
    public void recentlySearchTest() {
        SessionFilter sessionFilter = new SessionFilter();
        given()
            .filter(sessionFilter)
            .get("/restaurants?query=pizza&limit=5&radius=5")
            .then().statusCode(200)
            .contentType(ContentType.JSON);

        Response response = given().filter(sessionFilter).get("/searches?email=test@usc.edu");
        response.then().statusCode(200);
        assertEquals("[{\"query\":\"pizza\",\"limit\":5,\"radius\":5}]", response.asString());

    }

    





    


}
