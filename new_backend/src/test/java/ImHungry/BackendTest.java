package ImHungry;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

import org.junit.Rule;
import org.junit.Test;

import ro.pippo.test.PippoRule;
import ro.pippo.test.PippoTest;

public class BackendTest extends PippoTest {

  @Rule
  public PippoRule pippoRule = new PippoRule(new PippoApplication());

  @Test
  public void testHello() {
    Response response = get("/");
    response.then()
        .statusCode(200)
        .contentType(ContentType.HTML);
  }

  @Test
  public void testRestaurants() {
    Response response = get("/restaurants?query=test&numresults=5&radius=8500");
    response.then()
        .statusCode(200)
        .contentType(ContentType.JSON);
    // Add asserts for json here
  }

  @Test
  public void testRecipes() {
    Response response = get("/recipes?query=test&numResults=12");
    response.then()
        .statusCode(200)
        .contentType(ContentType.JSON);
    // Add asserts for json here
  }

}
