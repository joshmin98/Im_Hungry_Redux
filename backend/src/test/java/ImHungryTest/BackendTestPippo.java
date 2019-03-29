package ImHungryTest;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

import org.junit.Rule;
import org.junit.Test;

import ro.pippo.test.PippoRule;
import ro.pippo.test.PippoTest;

public class BackendTest extends PippoTest {

  @Rule
  public PippoRule pippoRule = new PippoRule(new PippoApplication());

//   @Test
//   public void testHello() {
//     Response response = get("/");
//     response.then()
//         .statusCode(200)
//         .contentType(ContentType.HTML);
//   }

// Restaurant Servlet test with query
  @Test
  public void testRestaurants() {
    Response response = get("/restaurants?query=test&numresults=5&radius=8500");
    response.then()
        .statusCode(200)
        .contentType(ContentType.JSON);
    // Add asserts for json here
  }

// Restaurant Servlet test with empty parameters
  @Test (expected = RuntimeException.class)
  public void testRestaurantsEmpty() {
    Response response = get("/restaurants?query=\"\"&numresults=\"\"&radius=\"\" ");
    response.then()
        .statusCode(200)
        .contentType(ContentType.JSON);
    // Add asserts for json here
  }

// Recipe Servlet test
  @Test
  public void testRecipes() {
    Response response = get("/recipes?query=test&numResults=12");
    response.then()
        .statusCode(200)
        .contentType(ContentType.JSON);
    // Add asserts for json here
}
// Recipe Servlet test with empty parameters
  @Test
  public void testRecipesEmpty() {
    Response response = get("/recipes?query=\"\"&numResults=\"\" ");
    response.then()
        .statusCode(200)
        .contentType(ContentType.JSON);
    // Add asserts for json here
}

// // List management servlet test
//   @Test
//   public void testListManagement() {
//       // not sure what the endpoint is
//     Response response = get(<endpoint>);
//     response.then()
//         .statusCode(200)
//         .contentType(ContentType.JSON);
//     // Add asserts for json here
//   }

//   // List management servlet test with empty parameters
//   @Test
//   public void testListManagementEmpty() {
//       // not sure what the endpoint is
//     Response response = get(<endpoint>);
//     response.then()
//         .statusCode(200)
//         .contentType(ContentType.JSON);
//     // Add asserts for json here
//   }



}
