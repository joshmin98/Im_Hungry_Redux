package ImHungryTest;

// public class BackendTest extends Mockito {
//         @Test
//         public void testRestaurantServlet() throws Exception {
//                 HttpServletRequest request = mock(HttpServletRequest.class);       
//                 HttpServletResponse response = mock(HttpServletResponse.class);    

//                 when(request.getParameter("query")).thenReturn("Chinese");
//                 when(request.getParameter("numResults")).thenReturn("7");
//                 when(request.getParameter("radius")).thenReturn("8500");

//                 StringWriter stringWriter = new StringWriter();
//                 PrintWriter writer = new PrintWriter(stringWriter);
//                 when(response.getWriter()).thenReturn(writer);

//                 new RestaurantServlet().doGet(request, response);
//                 verify(request, atLeast(1)).getParameter("query"); // only if you want to verify username was called...
//                 writer.flush(); // it may not have been flushed yet...
//                 assertTrue(stringWriter.toString().contains(new YelpRestaurantService().getRestaurantInfo("Chinese", "7", "8500")));
//         }

//         @Test
//         public void testYelpRestaurntQuery() throws UnsupportedEncodingException {
//                 YelpRestaurantService yrs = new YelpRestaurantService();
//                 Assert.assertTrue(yrs.getRestaurantInfo("Chinese", "5", "8500").contains("Northern Cafe"));
//                 Assert.assertTrue(yrs.getRestaurantInfo("Chinese", "5", "8500").contains("Los Angeles"));

//         }

//         @Test(expected = RuntimeException.class)
//         public void testYelpRestaurantEmptyQuery() throws UnsupportedEncodingException {
//                 YelpRestaurantService yrs = new YelpRestaurantService();
//                 String json = yrs.getRestaurantInfo("", "", "");
//         }

//         // @Test
//         // public void testRecipeMalformed() throws UnirestException {
//         //         RecipeServlet rs = new RecipeServlet();
//         //         Assert.assertTrue(rs.getRecipeData("asdfasdfasdf", "10").contains("number"));
//         // }

//         // @Test
//         // public void testRecipeQuery() throws UnirestException {

//         //         RecipeServlet rs = new RecipeServlet();
//         //         Assert.assertTrue(rs.getRecipeData("soup", "3").contains("Red Lentil Soup"));
//         //         Assert.assertTrue(rs.getRecipeData("soup", "3").contains("preparationMinutes"));
//         //         Assert.assertTrue(rs.getRecipeData("soup", "3").contains("cookingMinutes"));
//         //         Assert.assertTrue(rs.getRecipeData("soup", "3").contains("image"));
//         //         Assert.assertTrue(rs.getRecipeData("soup", "3").contains("analyzedInstructions"));
//         // }

// }
