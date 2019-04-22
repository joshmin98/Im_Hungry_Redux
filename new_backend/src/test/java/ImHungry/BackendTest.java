package ImHungry;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jayway.restassured.filter.session.SessionFilter;
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
    // Response response = get("/");
    // response.then()
    // .statusCode(200)
    // .contentType(ContentType.JSON);
    // }

    @Test
    public void testDefault() {
        Response response = get("/");
        response.then().statusCode(200);
    }

    @Test
    /* User searches for Query: Burgers, Limit: 5 results, Radius: 8500 m */
    public void testRestaurants() {
        Response response = get("/restaurants?query=burgers&limit=5&radius=5");
        System.out.println(response.toString());
        response.then().statusCode(200).contentType(ContentType.JSON);

    }

    // bad query should return an empty results
    @Test
    public void testRestaurantsBadQuery() {
        Response response = get("/restaurants?query=asdf&limit=5&radius=5");
        System.out.println(response.toString());
        response.then()
            .statusCode(200)
            .contentType(ContentType.JSON);
        assertEquals("[]", response.asString());
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


        // Compare actual results so that they match

        JsonArray result = (JsonArray) (new JsonParser()).parse(response.asString());
        JsonObject item0 = result.get(0).getAsJsonObject();
        String id0 = item0.get("id").getAsString();
        String name0 = item0.get("name").getAsString();
        assertEquals("DXFhzx94myitMxmBhsdz8A", id0);
        assertEquals("Traditions", name0);
        
        JsonObject item1 = result.get(1).getAsJsonObject();
        String id1 = item1.get("id").getAsString();
        String name1 = item1.get("name").getAsString();
        assertEquals("cgMqbKO7UGLfijRqfg9kjw", id1);
        assertEquals("The Habit Burger Grill", name1);

        JsonObject item2 = result.get(2).getAsJsonObject();
        String id2 = item2.get("id").getAsString();
        String name2 = item2.get("name").getAsString();
        assertEquals("dQI2N7qVsjrQ4BxnyR0pHg", id2);
        assertEquals("Wahlburgers", name2);

    }

    @Test
    public void testRecipes() {
        Response response = get("/recipes?query=pizza&limit=5");
        response.then().statusCode(200).contentType(ContentType.JSON);
    }

    @Test
    public void testListEmptyParam() {
        Response response = given().get("/list");
        response.then().statusCode(200);
    }

    @Test
    public void testListAdd() {
        SessionFilter sessionFilter = new SessionFilter();
        empty();
        given().filter(sessionFilter).get("/restaurants?query=pizza&limit=5&radius=5")
                .then().statusCode(200).contentType(ContentType.JSON);
        Response response = given().filter(sessionFilter)
                .get("list/add?listName=Favorites&id=v5Eiu0WaNhDXBSNsAdjmUw");
        response.then().statusCode(200);

        response = given().filter(sessionFilter)
                .get("list/add?listName=To Explore&id=v5Eiu0WaNhDXBSNsAdjmUw");
        response.then().statusCode(200);

        response = given().filter(sessionFilter).get("list/add?listName=Do Not Show&id=v5Eiu0WaNhDXBSNsAdjmUw");
        response.then().statusCode(200);

        // finditem coverage
        response = given().filter(sessionFilter).get("list/add?listName=Do Not Show&id=missing");
        response.then().statusCode(200);

        // item already in list
        response = given().filter(sessionFilter).get("list/add?listName=Favorites&id=v5Eiu0WaNhDXBSNsAdjmUw");
        response.then().statusCode(200);
    }

    @Test
    public void testListDelete() {
        SessionFilter sessionFilter = new SessionFilter();
        empty();
        given().filter(sessionFilter).get("/restaurants?query=pizza&limit=5&radius=5")// sessionId("testFavoritesList")
                .then().statusCode(200).contentType(ContentType.JSON);

        Response response = given().filter(sessionFilter).get("list/add?listName=Favorites&id=v5Eiu0WaNhDXBSNsAdjmUw");
        response.then().statusCode(200);

        response = given().filter(sessionFilter).get("list/delete?listName=Favorites&id=v5Eiu0WaNhDXBSNsAdjmUw");
        response.then().statusCode(200);

        response = given().filter(sessionFilter).get("/list?listName=Favorites");
        response.then().statusCode(200);
        JsonArray result = (JsonArray) (new JsonParser()).parse(response.asString());
        // assertEquals(0, result.size());

        // item not in list
        response = given().filter(sessionFilter).get("list/delete?listName=Favorites&id=missing");
        response.then().statusCode(200);
    }

    @Test
    public void testListMove() {
        SessionFilter sessionFilter = new SessionFilter();
        empty();
        given().filter(sessionFilter).get("/restaurants?query=pizza&limit=5&radius=5").then().statusCode(200)
                .contentType(ContentType.JSON);

        Response response = given().filter(sessionFilter).get("list/add?listName=Favorites&id=v5Eiu0WaNhDXBSNsAdjmUw");
        response.then().statusCode(200);

        response = given().filter(sessionFilter).get("/list?listName=Favorites");
        response.then().statusCode(200);
        JsonArray result = (JsonArray) (new JsonParser()).parse(response.asString());
        assertEquals(1, result.size());

        response = given().filter(sessionFilter)
                .get("list/move?listName=Favorites&id=v5Eiu0WaNhDXBSNsAdjmUw&moveList=To Explore");
        response.then().statusCode(200);

        response = given().filter(sessionFilter).get("/list?listName=Favorites");
        response.then().statusCode(200);
        result = (JsonArray) (new JsonParser()).parse(response.asString());
        // assertEquals(0, result.size());

        response = given().filter(sessionFilter).get("/list?listName=To Explore");
        response.then().statusCode(200);
        result = (JsonArray) (new JsonParser()).parse(response.asString());
        // assertEquals(1, result.size());

        // item not in list
        response = given().filter(sessionFilter)
                .get("list/move?listName=Favorites&id=missing&moveList=To Explore");
        response.then().statusCode(200);
    }

    @Test
    public void testListReorder() {
        SessionFilter sessionFilter = new SessionFilter();
        empty();
        given().filter(sessionFilter).get("/restaurants?query=pizza&limit=5&radius=5").then().statusCode(200)
                .contentType(ContentType.JSON);

        String id1 = "v5Eiu0WaNhDXBSNsAdjmUw", id2 = "559251";
        Response response = given().filter(sessionFilter).get("list/add?listName=Favorites&id=" + id1);
        response.then().statusCode(200);

        response = given().filter(sessionFilter).get("list/add?listName=Favorites&id=" + id2);
        response.then().statusCode(200);

        response = given().filter(sessionFilter).get("/list?listName=Favorites");
        response.then().statusCode(200);
        JsonArray result = (JsonArray) (new JsonParser()).parse(response.asString());
        assertEquals(id1, result.get(0).getAsJsonObject().get("id").getAsString());

        response = given().filter(sessionFilter).get("/reorder?listName=Favorites&oldPosition=0&newPosition=1");
        response.then().statusCode(200);

        response = get("/list?listName=Favorites");
        response.then().statusCode(200);
        result = (JsonArray) (new JsonParser()).parse(response.asString());

        // assertEquals(id2, result.get(0).getAsJsonObject().get("id").getAsString());

        // out of bounds index
        response = given().filter(sessionFilter)
                .get("/reorder?listName=Favorites&oldPosition=0&newPosition=10");
        response.then().statusCode(200);

        // reorder empty list
        response = given().filter(sessionFilter)
                .get("/reorder?listName=To Explore&oldPosition=0&newPosition=2");
        response.then().statusCode(200);
    }

    public void empty() {
        given().get("/reset?name=Favorites");
        given().get("/reset?name=To Explore");
        given().get("/reset?name=Do Not Show");
        given().get("/reset?name=Searches");
    }

    // Start of database service testing
    @Test
    public void dbRouteTest() {
        Response response = get("/db");
        response.then().statusCode(200);
    }

    // Start of user authentication testing
    @Test
    public void userLoginTest() {
        Response response = get("/user?email=test@usc.edu");
        response.then().statusCode(200);
        assertEquals("test@usc.edu", response.asString());

    }

    // Start of recently searched query test
    // @Test
    // public void recentlySearchTest() {
    //     empty();
    //     Response response = get("/restaurants?query=burger&limit=5&radius=5");
    //     response.then().statusCode(200);
    //     response = get("/restaurants?query=pizza&limit=5&radius=5");
    //     response.then().statusCode(200);
    //     response = get("/searches");
    //     response.then().statusCode(200).contentType(ContentType.JSON);
    //     assertEquals("[{\"query\":\"burger\",\"limit\":5,\"radius\":5},{\"query\":\"pizza\",\"limit\":5,\"radius\":5}]",
    //             response.asString());
    // }
    
    // @Test
    // public void recentlySearchTestEmpty() {
    //     empty();
    //     Response response = get("/searches");
    //     response.then().statusCode(200).contentType(ContentType.JSON);
    //     assertEquals("[]", response.asString());
    // }
    @Test
    public void recentlySearchTestEmpty() {
        empty();
        Response response = get("/searches");
        response.then()
            .statusCode(200)
            .contentType(ContentType.JSON);
        assertEquals("[]", response.asString());
    }

    // login test
    @Test
    public void loginTest() {
        Response response = get("/login?email=blah@usc.edu");
        response.then()
            .statusCode(200);
        response = get("/curruser");
        assertEquals("blah@usc.edu", response.asString());
    }

    // logout test
    @Test
    public void logoutTest() {
        Response response = get("/logout");
        response.then()
            .statusCode(200);
        response = get("/curruser");
        assertEquals("empty", response.asString());
    }

    // pagination test
    @Test
    public void paginationTest() {
        Response response = get("/restaurants?query=burger&limit=10&radius=5");
        response.then()
            .statusCode(200);

        JsonArray result = (JsonArray) (new JsonParser()).parse(response.asString());
        int currPage = 1;
        int counter = 1;
        for(int i = 0; i < result.size(); ++i) {
            JsonObject item = result.get(i).getAsJsonObject();
            int page = item.get("page").getAsInt();
            assertEquals(currPage, page);
            if (counter % 5 == 0) {
                currPage++;
            }
            counter++;
        }
    }

    @Test
    public void groceryListAdd() {
        SessionFilter sessionFilter = new SessionFilter();
        empty();
        given().filter(sessionFilter).get("/recipe?query=pizza&limit=5")
                .then().statusCode(200).contentType(ContentType.JSON);
        Response response = given().filter(sessionFilter)
                .get("list/add?listName=Grocery List&id=v5Eiu0WaNhDXBSNsAdjmUw");
        response.then().statusCode(200);

        response = given().filter(sessionFilter)
                .get("list/add?listName=Grocery List&id=v5Eiu0WaNhDXBSNsAdjmUw");
        response.then().statusCode(200);

        response = given().filter(sessionFilter)
        .get("list/add?listName=Grocery List&id=v5Eiu0WaNhDXBSNsAdjmUw");
        response.then().statusCode(200);

        // finditem coverage
        response = given().filter(sessionFilter)
        .get("list/add?listName=Grocery List&id=missing");
        response.then().statusCode(200);

        // item already in list
        response = given().filter(sessionFilter).
        get("list/add?listName=Grocery List&id=v5Eiu0WaNhDXBSNsAdjmUw");
        response.then().statusCode(200);
    }

    @Test
    public void groceryListDelete() {
        SessionFilter sessionFilter = new SessionFilter();
        empty();
        given().filter(sessionFilter).get("/recipe?query=pizza&limit=5")// sessionId("testFavoritesList")
                .then().statusCode(200).contentType(ContentType.JSON);

        Response response = given().filter(sessionFilter).get("list/add?listName=Grocery List&id=v5Eiu0WaNhDXBSNsAdjmUw");
        response.then().statusCode(200);

        response = given().filter(sessionFilter).get("list/delete?listName=Grocery List&id=v5Eiu0WaNhDXBSNsAdjmUw");
        response.then().statusCode(200);

        response = given().filter(sessionFilter).get("/list?listName=Grocery List");
        response.then().statusCode(200);
        JsonArray result = (JsonArray) (new JsonParser()).parse(response.asString());
        // assertEquals(0, result.size());

        // item not in list
        response = given().filter(sessionFilter).get("list/delete?listName=Grocery List&id=missing");
        response.then().statusCode(200);
    }
}
