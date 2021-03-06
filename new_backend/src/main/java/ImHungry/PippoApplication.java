package ImHungry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.pippo.core.Application;
import ro.pippo.core.route.RouteContext;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * A simple Pippo application.
 *
 * @see com.mycompany.PippoLauncher#main(String[])
 */
public class PippoApplication extends Application {

    private final static Logger log = LoggerFactory.getLogger(PippoApplication.class);
    private final DatabaseService db = new DatabaseService();
    // private String user = null;
    private String user = "test@usc.edu";

    @Override
    protected void onInit() {
        getRouter().ignorePaths("/favicon.ico");

        // send 'Hello World' as response
        GET("/", routeContext -> {
            routeContext = setHeaders(routeContext, "GET");
            routeContext.json().send("Hello World");
            log.info("HELLO WOrld");
        });

        GET("/list", routeContext -> {
            routeContext = setHeaders(routeContext, "GET");
            // log.info("list post");
            // Get parameters
            String listName = (routeContext.getParameter("listName") != null)
                    ? routeContext.getParameter("listName").toString()
                    : null;

            String response = (listName != null) ? db.getDataFromDatabase(this.user, listName)
                    : "{\"error\":\"missing listName parameter\"}";

            routeContext.json().send(response);
        });

        GET("/list/add", routeContext -> {
            routeContext = setHeaders(routeContext, "GET");
            log.info("list add");
            String listName = routeContext.getParameter("listName").toString();
            String id = routeContext.getParameter("id").toString();
            // log.info(listName + ": " + db.getDataFromDatabase("test@usc.edu", listName));
            log.info(listName + ": " + db.getDataFromDatabase(this.user, listName));

            
            JsonParser parser = new JsonParser();
            JsonObject item = null;

            JsonArray list = (JsonArray) parser.parse(db.getDataFromDatabase(this.user, listName));

            item = findItem(routeContext, id);

            if(!list.contains(item)) {
                log.info("added to " + listName);
                list.add(item);
            }
            
            // db.pushDataToDatabase("test@usc.edu", listName, list.toString());
            // log.info(db.getDataFromDatabase("test@usc.edu", listName));
            db.pushDataToDatabase(this.user, listName, list.toString());
            log.info(db.getDataFromDatabase(this.user, listName));
            routeContext.send("list/add");
        });

        GET("/list/delete", routeContext -> {
            routeContext = setHeaders(routeContext, "GET");
            log.info("list delete");
            String listName = routeContext.getParameter("listName").toString();
            String id = routeContext.getParameter("id").toString();
            
            JsonParser parser = new JsonParser();
            JsonObject item = null;

            JsonArray list = (JsonArray) parser.parse(db.getDataFromDatabase(this.user, listName));

            // get restaurant and recipe information of current query
            item = findItem(routeContext, id);

            if (list.contains(item)) {
                log.info("deleted from " + listName);
                list.remove(item);
            }
            // db.pushDataToDatabase("test@usc.edu", listName, list.toString());
            db.pushDataToDatabase(this.user, listName, list.toString());
            // routeContext.setSession(listName, list);
            routeContext.send("list/delete");
        });

        GET("/list/move", routeContext -> {
            routeContext = setHeaders(routeContext, "GET");
            log.info("list move");
            String listName = routeContext.getParameter("listName").toString();
            String id = routeContext.getParameter("id").toString();
            String moveList = routeContext.getParameter("moveList").toString();

            JsonParser parser = new JsonParser();
            JsonObject item = null;

            JsonArray list = (JsonArray) parser.parse(db.getDataFromDatabase(this.user, listName));

            // get restaurant and recipe information of current query
            item = findItem(routeContext, id);

            if (list.contains(item)) {
                JsonArray otherList = (JsonArray) parser.parse(db.getDataFromDatabase(this.user, moveList));

                otherList.add(item);
                list.remove(item);

                log.info("moved from " + listName + " to " + moveList);

                db.pushDataToDatabase(this.user, moveList, otherList.toString());
            }

            routeContext.setSession(listName, list);
            // db.pushDataToDatabase("test@usc.edu", listName, list.toString());
            db.pushDataToDatabase(this.user, listName, list.toString());
            routeContext.send("list/move");
        });

        GET("/reorder", routeContext -> {
            routeContext = setHeaders(routeContext, "GET");
            // Get parameters
            String listName = routeContext.getParameter("listName").toString();
            String oldPosition = routeContext.getParameter("oldPosition").toString();
            String newPosition = routeContext.getParameter("newPosition").toString();

            JsonParser parser = new JsonParser();

            JsonArray list = (JsonArray) parser.parse(db.getDataFromDatabase(this.user, listName));
            if (list.size() > 0) {
                // Reordering of list by removing item at current position
                // then adding it back at the new position
                int oldPos = Integer.parseInt(oldPosition);
                int newPos = Integer.parseInt(newPosition);

                try {
                    JsonElement item = list.get(oldPos);
                    list.set(oldPos, list.get(newPos));
                    list.set(newPos, item);
                } catch (IndexOutOfBoundsException iobe) {
                    log.error(iobe.getMessage());
                }
            }
            // db.pushDataToDatabase("test@usc.edu", listName, list.toString());
            db.pushDataToDatabase(this.user, listName, list.toString());
            routeContext.send("reorder");
        });

        GET("/restaurants", routeContext -> {
            routeContext = setHeaders(routeContext, "GET");
            YelpRestaurantService yelp = new YelpRestaurantService();

            String query = routeContext.getParameter("query").toString();
            String limit = routeContext.getParameter("limit").toString();
            String radius = routeContext.getParameter("radius").toString();
            // store the query in list of queries
            /*
             * {"query": "val", "limit": val, "radius": val }
             */

            JsonParser parser = new JsonParser();
            JsonArray searches = null;

            // get current user
            String user = db.getDataFromDatabase("current", "user");
            if(user.equals("empty")) {
                user = "test@usc.edu";
            }
            searches = (JsonArray) parser.parse(db.getDataFromDatabase(this.user, "Searches"));
            String search = "{\"query\":\"" + query + "\", \"limit\": " + limit + ", \"radius\": " + radius + "}";
            log.info(search);
            JsonElement terms = (JsonElement) parser.parse(search);
            searches.add(terms);
            db.pushDataToDatabase(user, "Searches", searches.toString());

            radius = ConvertfromMilestoMeters(radius);
            String restaurantJSONstring = "";
            try {
                restaurantJSONstring = (!query.isEmpty() || !limit.isEmpty() || !radius.isEmpty())
                        ? yelp.getRestaurantInfo(query, limit, radius)
                        : "{\"error\": \"Missing fields in request parameters\"}";
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            JsonObject restaurantsJO = (JsonObject) (new JsonParser()).parse(restaurantJSONstring);
            JsonArray restaurantsJA = restaurantsJO.get("businesses").getAsJsonArray();
            String restaurants = specifyType(restaurantsJA, "restaurant");
            routeContext.setSession("restaurants", restaurants);

            // testing with only one endpoint query: pizza, limit: 5, distance: 5
            // String recipes = "[{\"sustainable\":false,\"analyzedInstructions\":[{\"name\":\"\",\"steps\":[{\"number\":1,\"ingredients\":[],\"equipment\":[{\"image\":\"baking-sheet.jpg\",\"name\":\"baking sheet\",\"id\":404727},{\"image\":\"oven.jpg\",\"name\":\"oven\",\"temperature\":{\"number\":500,\"unit\":\"Fahrenheit\"},\"id\":404784}],\"step\":\"Preheat oven to 500 F degrees.Spray a baking sheet (15.25 x 10.25 inches) with cooking spray.\"},{\"number\":2,\"ingredients\":[{\"image\":\"pizza-dough.jpg\",\"name\":\"pizza dough\",\"id\":93610}],\"equipment\":[{\"image\":\"roasting-pan.jpg\",\"name\":\"baking pan\",\"id\":404646}],\"step\":\"Roll out the pizza dough and place it on the pizza baking dish.\"},{\"number\":3,\"ingredients\":[{\"image\":\"mozzarella.png\",\"name\":\"mozzarella\",\"id\":1026},{\"image\":\"parmesan.jpg\",\"name\":\"parmesan\",\"id\":1033},{\"image\":\"olive-oil.jpg\",\"name\":\"olive oil\",\"id\":4053},{\"image\":\"egg.png\",\"name\":\"egg\",\"id\":1123}],\"equipment\":[],\"step\":\"Drizzle the dough with a bit of olive oil.Arrange the mozzarella cheese evenly over the dough. Sprinkle with Parmesan cheese.Top with bacon and tomato. Crack 6 eggs on the pizza.\"},{\"number\":4,\"length\":{\"number\":10,\"unit\":\"minutes\"},\"ingredients\":[],\"equipment\":[],\"step\":\"Bake for 10 to 15 minutes or until the edge is golden brown.\"},{\"number\":5,\"ingredients\":[{\"image\":\"parsley.jpg\",\"name\":\"parsley\",\"id\":11297},{\"image\":\"fresh-chives.jpg\",\"name\":\"chives\",\"id\":11156}],\"equipment\":[],\"step\":\"Garnish with chives and parsley.\"}]}],\"glutenFree\":false,\"veryPopular\":true,\"healthScore\":5,\"title\":\"Breakfast Pizza\",\"diets\":[],\"aggregateLikes\":4235,\"sourceUrl\":\"http://www.jocooks.com/breakfast-2/breakfast-pizza/\",\"readyInMinutes\":25,\"creditsText\":\"Jo Cooks\",\"dairyFree\":false,\"servings\":6,\"vegetarian\":false,\"whole30\":false,\"creditText\":\"Jo Cooks\",\"id\":559251,\"preparationMinutes\":10,\"imageType\":\"jpg\",\"winePairing\":{\"productMatches\":[{\"score\":0.8073684210526315,\"price\":\"$24.49\",\"imageUrl\":\"https://spoonacular.com/productImages/440954-312x231.jpg\",\"averageRating\":0.86,\"link\":\"https://click.linksynergy.com/deeplink?id=*QCiIS6t4gA&mid=2025&murl=https%3A%2F%2Fwww.wine.com%2Fproduct%2Ffonterutoli-chianti-classico-1998%2F12434\",\"description\":\"Color: Deep purplish-red but bright and exceptionally concentrated.Bouquet: Extremely intense and complex with scents of cherries and raspberries accompanied by light toasty and spicy shadings.Flavor: The impact in the mouth is incisive but soft due to a substantial structure of tannins in which those that are soft and well rounded stand out. Acidity is fused with the wine's body and aids the transmission of pleasant sensations of warmth and strength. The wine features a long finish that is unusual for a regular Chianti Classico.Conclusions: Those who argue that Chianti Classico is a prickly and rough wine will change their minds after tasting this 1999, which is ready to drink now but will improve for at least five years more.Alcohol: 13.5% by volume\",\"id\":440954,\"title\":\"Fonterutoli Chianti Classico\",\"ratingCount\":6}],\"pairingText\":\"Breakfast Pizza works really well with Sangiovese, Barbera Wine, and Shiraz. The best wine for pizza depends on the toppings! Red sauce pizza will call for a red wine with some acidity, such as a barberan or sangiovese. Add pepperoni or sausage and you can go bolder with a syrah. The Fonterutoli Chianti Classico with a 4.3 out of 5 star rating seems like a good match. It costs about 24 dollars per bottle.\",\"pairedWines\":[\"sangiovese\",\"barbera wine\",\"shiraz\"]},\"cookingMinutes\":15,\"image\":\"https://spoonacular.com/recipeImages/559251-312x231.jpg\",\"veryHealthy\":false,\"vegan\":false,\"cheap\":false,\"dishTypes\":[\"lunch\",\"main course\",\"morning meal\",\"brunch\",\"main dish\",\"breakfast\",\"dinner\"],\"gaps\":\"no\",\"cuisines\":[\"mediterranean\",\"european\",\"italian\"],\"lowFodmap\":false,\"weightWatcherSmartPoints\":13,\"occasions\":[],\"spoonacularScore\":46,\"pricePerServing\":195.59,\"spoonacularSourceUrl\":\"https://spoonacular.com/breakfast-pizza-559251\",\"sourceName\":\"Jo Cooks\",\"ketogenic\":false},"
            //         + "{\"sustainable\":false,\"analyzedInstructions\":[{\"name\":\"\",\"steps\":[{\"number\":1,\"ingredients\":[],\"equipment\":[{\"image\":\"oven.jpg\",\"name\":\"oven\",\"temperature\":{\"number\":200,\"unit\":\"Celsius\"},\"id\":404784}],\"step\":\"Heat oven to 200C/180C fan/gas\"},{\"number\":2,\"length\":{\"number\":3,\"unit\":\"minutes\"},\"ingredients\":[{\"image\":\"flour-tortilla.jpg\",\"name\":\"tortilla\",\"id\":18364},{\"image\":\"tomato.png\",\"name\":\"tomato\",\"id\":11529}],\"equipment\":[{\"image\":\"baking-sheet.jpg\",\"name\":\"baking sheet\",\"id\":404727}],\"step\":\"Lay the tortillas on two baking sheets, brush sparingly with the oil then bake for 3 mins. Meanwhile chop the pepper and tomatoes and mix with the tomato pure, seasoning and herbs. Turn the tortillas over and spread with the tomato mixture, leaving the centre free from any large pieces of pepper or tomato.\"},{\"number\":3,\"length\":{\"number\":10,\"unit\":\"minutes\"},\"ingredients\":[{\"image\":\"flour-tortilla.jpg\",\"name\":\"tortilla\",\"id\":18364},{\"image\":\"egg.png\",\"name\":\"egg\",\"id\":1123}],\"equipment\":[{\"image\":\"oven.jpg\",\"name\":\"oven\",\"id\":404784}],\"step\":\"Break an egg into the centre then return to the oven for 10 mins or until the egg is just set and the tortilla is crispy round the edges.\"},{\"number\":4,\"ingredients\":[{\"image\":\"arugula-or-rocket-salad.jpg\",\"name\":\"arugula\",\"id\":11959},{\"image\":\"brown-onion.png\",\"name\":\"onion\",\"id\":11282}],\"equipment\":[],\"step\":\"Serve scattered with the rocket and onion.\"}]}],\"glutenFree\":true,\"veryPopular\":true,\"healthScore\":14,\"title\":\"Egg & rocket pizzas\",\"diets\":[\"gluten free\",\"dairy free\"],\"aggregateLikes\":1011,\"sourceUrl\":\"https://www.bbcgoodfood.com/recipes/egg-rocket-pizzas\",\"readyInMinutes\":30,\"creditsText\":\"BBC Good Food\",\"dairyFree\":true,\"servings\":2,\"vegetarian\":false,\"whole30\":false,\"creditText\":\"BBC Good Food\",\"id\":630293,\"preparationMinutes\":10,\"imageType\":\"jpg\",\"winePairing\":{\"productMatches\":[{\"score\":0.75,\"price\":\"$26.0\",\"imageUrl\":\"https://spoonacular.com/productImages/431618-312x231.jpg\",\"averageRating\":1,\"link\":\"https://www.amazon.com/2009-Madsen-Family-Cellars-Sangiovese/dp/B00I0F3FJA?tag=spoonacular-20\",\"description\":\"This wine has a pronounced nose with a hint of coffee while your mouth is invaded with spices; clove, licorice, and cinnamon.The palate features cherries and notes of clove which continue on to a smooth, long finish with subtle oak, slight pepper and berry flavors. The tartness of the wine works well with food as Sangiovese is a food wine, but can be enjoyed on its own as well. Columbia Valley AVA from the vineyards of Jordan Reed.\",\"id\":431618,\"title\":\"Madsen Family Cellars Sangiovese Wine\",\"ratingCount\":1}],\"pairingText\":\"Pizza works really well with Sangiovese, Barbera Wine, and Shiraz. The best wine for pizza depends on the toppings! Red sauce pizza will call for a red wine with some acidity, such as a barberan or sangiovese. Add pepperoni or sausage and you can go bolder with a syrah. The Madsen Family Cellars Sangiovese Wine with a 5 out of 5 star rating seems like a good match. It costs about 26 dollars per bottle.\",\"pairedWines\":[\"sangiovese\",\"barbera wine\",\"shiraz\"]},\"cookingMinutes\":20,\"image\":\"https://spoonacular.com/recipeImages/630293-312x231.jpg\",\"veryHealthy\":false,\"vegan\":false,\"cheap\":false,\"dishTypes\":[],\"gaps\":\"no\",\"cuisines\":[],\"lowFodmap\":false,\"weightWatcherSmartPoints\":4,\"occasions\":[],\"spoonacularScore\":78,\"pricePerServing\":156.93,\"spoonacularSourceUrl\":\"https://spoonacular.com/egg-rocket-pizzas-630293\",\"sourceName\":\"BBC Good Food\",\"ketogenic\":false},"
            //         + "{\"sustainable\":false,\"analyzedInstructions\":[{\"name\":\"\",\"steps\":[{\"number\":1,\"ingredients\":[{\"image\":\"yeast-granules.jpg\",\"name\":\"yeast\",\"id\":18375}],\"equipment\":[{\"image\":\"bowl.jpg\",\"name\":\"bowl\",\"id\":404783}],\"step\":\"For the dough, put the flour, yeast and 2 tsp salt into a large bowl and mix. Make a well.\"},{\"number\":2,\"length\":{\"number\":15,\"unit\":\"minutes\"},\"ingredients\":[{\"image\":\"water.png\",\"name\":\"water\",\"id\":14412}],\"equipment\":[{\"image\":\"wooden-spoon.jpg\",\"name\":\"wooden spoon\",\"id\":404732},{\"image\":\"bowl.jpg\",\"name\":\"bowl\",\"id\":404783}],\"step\":\"Mix the oil and water in a jug, then tip into the bowl. Use a wooden spoon to work the liquid into the flour  it will seem pretty wet. Set the bowl aside for 15 mins. Leaving the dough like this will save you from lengthy kneading later.\"},{\"number\":3,\"length\":{\"number\":2,\"unit\":\"minutes\"},\"ingredients\":[],\"equipment\":[{\"image\":\"bowl.jpg\",\"name\":\"bowl\",\"id\":404783}],\"step\":\"Turn the dough onto a well-floured surface, flour your hands, then knead it very gently for about 2 mins until it is fairly even, soft and bouncy. Return the dough to the bowl, cover with oiled cling film, then let it rise in a warm place (or in the fridge overnight) until doubled in size.\"},{\"number\":4,\"ingredients\":[],\"equipment\":[{\"image\":\"baking-sheet.jpg\",\"name\":\"baking sheet\",\"id\":404727},{\"image\":\"oven.jpg\",\"name\":\"oven\",\"temperature\":{\"number\":240,\"unit\":\"Celsius\"},\"id\":404784}],\"step\":\"When ready to cook, heat oven to 240C/220C fan/gas 9 or as hot as it will go, then put a baking sheet in on a high shelf. Dust another sheet with flour. Split the dough into 8, then roll 3 balls thinly into rough circles. Lift onto the floured sheet. Smear over a thin layer of the sauce, scatter over a few slices of tomato, season, then add sliced goats or grated Parmesan cheese if you want to. Slide the sheet on top of the heated sheet.\"},{\"number\":5,\"length\":{\"number\":12,\"unit\":\"minutes\"},\"ingredients\":[{\"image\":\"olive-oil.jpg\",\"name\":\"olive oil\",\"id\":4053}],\"equipment\":[],\"step\":\"Bake for 12 mins or until golden and crisp and the tomatoes are starting to caramelise around the edges. Top with any fresh toppings, then drizzle with more olive oil to serve.\"}]}],\"glutenFree\":false,\"veryPopular\":true,\"healthScore\":15,\"title\":\"Easy tomato pizzas\",\"diets\":[\"lacto ovo vegetarian\"],\"aggregateLikes\":425,\"sourceUrl\":\"https://www.bbcgoodfood.com/recipes/12389/easy-tomato-pizzas\",\"readyInMinutes\":90,\"creditsText\":\"BBC Good Food\",\"dairyFree\":false,\"servings\":8,\"vegetarian\":true,\"whole30\":false,\"creditText\":\"BBC Good Food\",\"id\":223993,\"preparationMinutes\":10,\"imageType\":\"jpg\",\"winePairing\":{\"productMatches\":[{\"score\":0.75,\"price\":\"$19.0\",\"imageUrl\":\"https://spoonacular.com/productImages/469504-312x231.jpg\",\"averageRating\":1,\"link\":\"https://www.amazon.com/Story-Winery-Sangiovese-California-Shenandoah/dp/B00NMXHFNC?tag=spoonacular-20\",\"description\":\"Aromas of raspberry, cherry, pie crust, tobacco and oak greet the nose. Dark cherries and raspberry predominate the palate with mild tannins and nice acidity. The finish is gentle and lasting Enjoy!\",\"id\":469504,\"title\":\"Story Winery, Sangiovese, Estate, Shenandoah Valley\",\"ratingCount\":1}],\"pairingText\":\"Pizza works really well with Sangiovese, Barbera Wine, and Shiraz. The best wine for pizza depends on the toppings! Red sauce pizza will call for a red wine with some acidity, such as a barberan or sangiovese. Add pepperoni or sausage and you can go bolder with a syrah. One wine you could try is Story Winery, Sangiovese, Estate, Shenandoah Valley. It has 5 out of 5 stars and a bottle costs about 19 dollars.\",\"pairedWines\":[\"sangiovese\",\"barbera wine\",\"shiraz\"]},\"cookingMinutes\":12,\"image\":\"https://spoonacular.com/recipeImages/223993-312x231.jpg\",\"veryHealthy\":false,\"vegan\":false,\"cheap\":false,\"dishTypes\":[],\"gaps\":\"no\",\"cuisines\":[],\"lowFodmap\":false,\"weightWatcherSmartPoints\":7,\"occasions\":[],\"spoonacularScore\":81,\"pricePerServing\":42.35,\"spoonacularSourceUrl\":\"https://spoonacular.com/easy-tomato-pizzas-223993\",\"sourceName\":\"BBC Good Food\",\"ketogenic\":false},"
            //         + "{\"sustainable\":false,\"analyzedInstructions\":[{\"name\":\"\",\"steps\":[{\"number\":1,\"length\":{\"number\":7,\"unit\":\"minutes\"},\"ingredients\":[{\"image\":\"honey.png\",\"name\":\"honey\",\"id\":19296},{\"image\":\"water.png\",\"name\":\"water\",\"id\":14412},{\"image\":\"yeast-granules.jpg\",\"name\":\"yeast\",\"id\":18375}],\"equipment\":[{\"image\":\"measuring-cup.jpg\",\"name\":\"measuring cup\",\"id\":404766}],\"step\":\"To make the dough, in a 2-cup liquid measuring cup, add warm water and yeast and honey, allow to sit for 5-7 minutes or until the mixture begins to puff.\"},{\"number\":2,\"ingredients\":[{\"image\":\"water.png\",\"name\":\"water\",\"id\":14412}],\"equipment\":[],\"step\":\"Add the oil and the room temperature water.\"},{\"number\":3,\"ingredients\":[],\"equipment\":[{\"image\":\"whisk.png\",\"name\":\"whisk\",\"id\":404661}],\"step\":\"Whisk gently to combine.\"},{\"number\":4,\"length\":{\"number\":3,\"unit\":\"minutes\"},\"ingredients\":[{\"image\":\"water.png\",\"name\":\"water\",\"id\":14412},{\"image\":\"salt.jpg\",\"name\":\"salt\",\"id\":2047}],\"equipment\":[{\"image\":\"stand-mixer.jpg\",\"name\":\"stand mixer\",\"id\":404665},{\"image\":\"bowl.jpg\",\"name\":\"bowl\",\"id\":404783}],\"step\":\"In the bowl of a stand mixer fit with the paddle attachment, combine salt, flours, and water mixture. When dough begins to form a ball, remove paddle attachment and replace with dough hook. With mixing speed on low, knead dough for 2-3 minutes or until a ball begins to form and the dough becomes smooth and elastic.\"},{\"number\":5,\"ingredients\":[{\"image\":\"olive-oil.jpg\",\"name\":\"olive oil\",\"id\":4053}],\"equipment\":[{\"image\":\"plastic-wrap.jpg\",\"name\":\"plastic wrap\",\"id\":404730},{\"image\":\"bowl.jpg\",\"name\":\"bowl\",\"id\":404783}],\"step\":\"Remove dough from bowl and place in a large well-greased bowl (I prefer using olive oil, but you can also use Pam). Cover bowl tightly with plastic wrap and allow to rise for 2 hours.\"},{\"number\":6,\"ingredients\":[],\"equipment\":[{\"image\":\"pan.png\",\"name\":\"frying pan\",\"id\":404645}],\"step\":\"To deflate dough, punch with fist. Turn onto a lightly floured surface and divide into two balls (depending on how big and how many pizzas you want to make I make 2). Form into pizza shells to fit the size of your pan.\"},{\"number\":7,\"ingredients\":[{\"image\":\"flour.png\",\"name\":\"all purpose flour\",\"id\":20081}],\"equipment\":[{\"image\":\"pan.png\",\"name\":\"frying pan\",\"id\":404645}],\"step\":\"Place on a lightly greased pan (or one that has been dusted with flour or cornmeal).\"},{\"number\":8,\"ingredients\":[{\"image\":\"olive-oil.jpg\",\"name\":\"olive oil\",\"id\":4053}],\"equipment\":[{\"image\":\"sauce-pan.jpg\",\"name\":\"sauce pan\",\"id\":404669}],\"step\":\"To make sauce, in a small saucepan heat olive oil until shiny.\"},{\"number\":9,\"length\":{\"number\":1,\"unit\":\"minutes\"},\"ingredients\":[],\"equipment\":[],\"step\":\"Add minced garlic, stir and cook for 1 minute.\"},{\"number\":10,\"ingredients\":[{\"image\":\"tomato-sauce-or-pasta-sauce.jpg\",\"name\":\"tomato sauce\",\"id\":11549},{\"image\":\"oregano.jpg\",\"name\":\"oregano\",\"id\":2027},{\"image\":\"basil.jpg\",\"name\":\"basil\",\"id\":2044},{\"image\":\"sugar-in-bowl.png\",\"name\":\"sugar\",\"id\":19335},{\"image\":\"salt.jpg\",\"name\":\"salt\",\"id\":2047}],\"equipment\":[],\"step\":\"Add tomato sauce, sugar, basil, oregano and salt.\"},{\"number\":11,\"length\":{\"number\":5,\"unit\":\"minutes\"},\"ingredients\":[],\"equipment\":[],\"step\":\"Heat for 5 minutes or until the sauce becomes fragrant.\"},{\"number\":12,\"ingredients\":[{\"image\":\"olive-oil.jpg\",\"name\":\"olive oil\",\"id\":4053},{\"image\":\"tomato.png\",\"name\":\"tomato\",\"id\":11529},{\"image\":\"salt.jpg\",\"name\":\"salt\",\"id\":2047}],\"equipment\":[],\"step\":\"To assemble the pizzas, spread sauce evenly over the pizza peel, leaving about 1 inches around the edge for the crust. Lightly brush the crust with olive oil (this will help it get nice and crisp) Lay slices of cheese over the sauce. Then place sliced tomatoes over top, drizzle with olive oil and sprinkle with a little salt.\"},{\"number\":13,\"length\":{\"number\":14,\"unit\":\"minutes\"},\"ingredients\":[],\"equipment\":[],\"step\":\"Bake for 12-14 minutes at 450 degrees.\"},{\"number\":14,\"length\":{\"number\":6,\"unit\":\"minutes\"},\"ingredients\":[],\"equipment\":[],\"step\":\"Remove, let cool for 5-6 minutes.\"},{\"number\":15,\"ingredients\":[{\"image\":\"fresh-basil.jpg\",\"name\":\"fresh basil\",\"id\":2044}],\"equipment\":[],\"step\":\"Place basil leaves over top, slice and serve. Repeat with the second ball of dough.\"}]}],\"glutenFree\":false,\"veryPopular\":true,\"healthScore\":29,\"title\":\"Neapolitan Pizza and Honey Whole Wheat Dough\",\"diets\":[\"lacto ovo vegetarian\"],\"aggregateLikes\":418,\"sourceUrl\":\"https://lifemadesimplebakes.com/2014/02/neapolitan-pizza-and-honey-whole-wheat-dough/\",\"readyInMinutes\":102,\"creditsText\":\"Life Made Simple\",\"dairyFree\":false,\"servings\":8,\"vegetarian\":true,\"whole30\":false,\"creditText\":\"Life Made Simple\",\"id\":481601,\"preparationMinutes\":90,\"imageType\":\"jpg\",\"winePairing\":{\"productMatches\":[{\"score\":0.8078048780487804,\"price\":\"$26.99\",\"imageUrl\":\"https://spoonacular.com/productImages/459422-312x231.jpg\",\"averageRating\":0.82,\"link\":\"https://click.linksynergy.com/deeplink?id=*QCiIS6t4gA&mid=2025&murl=https%3A%2F%2Fwww.wine.com%2Fproduct%2Fcecchi-chianti-classico-riserva-di-famiglia-2014%2F240545\",\"description\":\"Chianti Classico Riserva di Famiglia is certainly the most representative wine of Cecchi. Produced only in years in which the grapes reach the quality desired it comes from the area of Castellina in Chianti. The color of excellent limpidity is of an intense ruby red. The aroma reveals broad and ethereal notes of ripe fruit and spices. The structure is elegant.\",\"id\":459422,\"title\":\"Cecchi Chianti Classico Riserva di Famiglia\",\"ratingCount\":27}],\"pairingText\":\"Pizzan on the menu? Try pairing with Sangiovese, Barbera Wine, and Shiraz. The best wine for pizza depends on the toppings! Red sauce pizza will call for a red wine with some acidity, such as a barberan or sangiovese. Add pepperoni or sausage and you can go bolder with a syrah. One wine you could try is Cecchi Chianti Classico Riserva di Famiglia. It has 4.1 out of 5 stars and a bottle costs about 27 dollars.\",\"pairedWines\":[\"sangiovese\",\"barbera wine\",\"shiraz\"]},\"cookingMinutes\":12,\"image\":\"https://spoonacular.com/recipeImages/481601-312x231.jpg\",\"veryHealthy\":false,\"vegan\":false,\"cheap\":false,\"dishTypes\":[],\"gaps\":\"no\",\"cuisines\":[\"mediterranean\",\"european\",\"italian\"],\"lowFodmap\":false,\"weightWatcherSmartPoints\":9,\"occasions\":[],\"spoonacularScore\":93,\"pricePerServing\":50.6,\"spoonacularSourceUrl\":\"https://spoonacular.com/neapolitan-pizza-and-honey-whole-wheat-dough-481601\",\"sourceName\":\"Life Made Simple\",\"ketogenic\":false},"
            //         + "{\"sustainable\":false,\"analyzedInstructions\":[{\"name\":\"\",\"steps\":[{\"number\":1,\"ingredients\":[],\"equipment\":[{\"image\":\"oven.jpg\",\"name\":\"oven\",\"temperature\":{\"number\":180,\"unit\":\"Celsius\"},\"id\":404784}],\"step\":\"Preheat oven to fan 180C/conventional 200C/gas\"},{\"number\":2,\"ingredients\":[],\"equipment\":[],\"step\":\"Unroll the pastry onto a lightly floured surface and roll it out to 40 x 32cm.\"},{\"number\":3,\"ingredients\":[{\"image\":\"tomato-sauce-or-pasta-sauce.jpg\",\"name\":\"pasta sauce\",\"id\":10011549},{\"image\":\"cheddar-cheese.png\",\"name\":\"cheese\",\"id\":1041009},{\"image\":\"ham-whole.jpg\",\"name\":\"ham\",\"id\":10151}],\"equipment\":[],\"step\":\"Spread a layer of pasta sauce over it, leaving a 1cm border around the edges. Arrange the ham evenly on top, then scatter the grated cheese over.\"},{\"number\":4,\"length\":{\"number\":10,\"unit\":\"minutes\"},\"ingredients\":[],\"equipment\":[],\"step\":\"Starting at one of the short ends, roll the pastry up as tightly as possible. Chill in the fridge for 10 minutes or so.\"},{\"number\":5,\"ingredients\":[],\"equipment\":[{\"image\":\"chefs-knife.jpg\",\"name\":\"knife\",\"id\":404745}],\"step\":\"Take a very sharp knife and cut the roll into 12 equal slices, laying them flat on 2 non-stick baking trays as you go.\"},{\"number\":6,\"ingredients\":[{\"image\":\"egg.png\",\"name\":\"egg\",\"id\":1123}],\"equipment\":[],\"step\":\"Brush each pinwheel lightly with beaten egg and sprinkle over the herbs.\"},{\"number\":7,\"length\":{\"number\":25,\"unit\":\"minutes\"},\"ingredients\":[],\"equipment\":[],\"step\":\"Bake for 12-15 minutes until puffed and golden. Leave to stand for 5-10 minutes before serving.\"}]}],\"glutenFree\":false,\"veryPopular\":true,\"healthScore\":3,\"title\":\"Pizza puff pinwheels\",\"diets\":[],\"aggregateLikes\":411,\"sourceUrl\":\"https://www.bbcgoodfood.com/recipes/1463/pizza-puff-pinwheels\",\"readyInMinutes\":35,\"creditsText\":\"BBC Good Food\",\"dairyFree\":false,\"servings\":12,\"vegetarian\":false,\"whole30\":false,\"creditText\":\"BBC Good Food\",\"id\":222869,\"preparationMinutes\":20,\"imageType\":\"jpg\",\"winePairing\":{\"productMatches\":[{\"score\":0.75,\"price\":\"$19.0\",\"imageUrl\":\"https://spoonacular.com/productImages/469504-312x231.jpg\",\"averageRating\":1,\"link\":\"https://www.amazon.com/Story-Winery-Sangiovese-California-Shenandoah/dp/B00NMXHFNC?tag=spoonacular-20\",\"description\":\"Aromas of raspberry, cherry, pie crust, tobacco and oak greet the nose. Dark cherries and raspberry predominate the palate with mild tannins and nice acidity. The finish is gentle and lasting Enjoy!\",\"id\":469504,\"title\":\"Story Winery, Sangiovese, Estate, Shenandoah Valley\",\"ratingCount\":1}],\"pairingText\":\"Sangiovese, Barbera Wine, and Shiraz are great choices for Pizza. The best wine for pizza depends on the toppings! Red sauce pizza will call for a red wine with some acidity, such as a barberan or sangiovese. Add pepperoni or sausage and you can go bolder with a syrah. The Story Winery, Sangiovese, Estate, Shenandoah Valley with a 5 out of 5 star rating seems like a good match. It costs about 19 dollars per bottle.\",\"pairedWines\":[\"sangiovese\",\"barbera wine\",\"shiraz\"]},\"cookingMinutes\":15,\"image\":\"https://spoonacular.com/recipeImages/222869-312x231.jpg\",\"veryHealthy\":false,\"vegan\":false,\"cheap\":false,\"dishTypes\":[],\"gaps\":\"no\",\"cuisines\":[\"mediterranean\",\"european\",\"italian\"],\"lowFodmap\":false,\"weightWatcherSmartPoints\":7,\"occasions\":[],\"spoonacularScore\":40,\"pricePerServing\":48.34,\"spoonacularSourceUrl\":\"https://spoonacular.com/pizza-puff-pinwheels-222869\",\"sourceName\":\"BBC Good Food\",\"ketogenic\":false}]";
            // recipes = specifyType((JsonArray) (new JsonParser()).parse(recipes), "recipe");
            // routeContext.setSession("recipes", recipes);
            // log.info(recipes);
            routeContext.json().send(restaurants);
        });

        GET("/db", routeContext -> {
            DatabaseService db = new DatabaseService();
            log.info(db.getDataFromDatabase("test@usc.edu", "Favorites"));
            routeContext.json().send("db");
        });

        GET("/recipes", routeContext -> {
            routeContext = setHeaders(routeContext, "GET");
            RecipeService rs = new RecipeService();

            String query = routeContext.getParameter("query").toString();
            String limit = routeContext.getParameter("limit").toString();

            String recipeJSONstring = "";
            try {
                recipeJSONstring = (query != null && limit != null) ? rs.getRecipeData(query, limit)
                        : "{\"error\": \"Missing fields in request parameters\"}";
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            JsonObject recipesJO = (recipeJSONstring != null) ? (JsonObject) (new JsonParser()).parse(recipeJSONstring)
                    : null;
            JsonArray recipesJA = recipesJO.get("results").getAsJsonArray();
            String recipes = specifyType(recipesJA, "recipe");

            // testing purposes only
            //recipes = "[{\"sustainable\":false,\"analyzedInstructions\":[{\"name\":\"\",\"steps\":[{\"number\":1,\"ingredients\":[],\"equipment\":[{\"image\":\"baking-sheet.jpg\",\"name\":\"baking sheet\",\"id\":404727},{\"image\":\"oven.jpg\",\"name\":\"oven\",\"temperature\":{\"number\":500,\"unit\":\"Fahrenheit\"},\"id\":404784}],\"step\":\"Preheat oven to 500 F degrees.Spray a baking sheet (15.25 x 10.25 inches) with cooking spray.\"},{\"number\":2,\"ingredients\":[{\"image\":\"pizza-dough.jpg\",\"name\":\"pizza dough\",\"id\":93610}],\"equipment\":[{\"image\":\"roasting-pan.jpg\",\"name\":\"baking pan\",\"id\":404646}],\"step\":\"Roll out the pizza dough and place it on the pizza baking dish.\"},{\"number\":3,\"ingredients\":[{\"image\":\"mozzarella.png\",\"name\":\"mozzarella\",\"id\":1026},{\"image\":\"parmesan.jpg\",\"name\":\"parmesan\",\"id\":1033},{\"image\":\"olive-oil.jpg\",\"name\":\"olive oil\",\"id\":4053},{\"image\":\"egg.png\",\"name\":\"egg\",\"id\":1123}],\"equipment\":[],\"step\":\"Drizzle the dough with a bit of olive oil.Arrange the mozzarella cheese evenly over the dough. Sprinkle with Parmesan cheese.Top with bacon and tomato. Crack 6 eggs on the pizza.\"},{\"number\":4,\"length\":{\"number\":10,\"unit\":\"minutes\"},\"ingredients\":[],\"equipment\":[],\"step\":\"Bake for 10 to 15 minutes or until the edge is golden brown.\"},{\"number\":5,\"ingredients\":[{\"image\":\"parsley.jpg\",\"name\":\"parsley\",\"id\":11297},{\"image\":\"fresh-chives.jpg\",\"name\":\"chives\",\"id\":11156}],\"equipment\":[],\"step\":\"Garnish with chives and parsley.\"}]}],\"glutenFree\":false,\"veryPopular\":true,\"healthScore\":5,\"title\":\"Breakfast Pizza\",\"diets\":[],\"aggregateLikes\":4235,\"sourceUrl\":\"http://www.jocooks.com/breakfast-2/breakfast-pizza/\",\"readyInMinutes\":25,\"creditsText\":\"Jo Cooks\",\"dairyFree\":false,\"servings\":6,\"vegetarian\":false,\"whole30\":false,\"creditText\":\"Jo Cooks\",\"id\":559251,\"preparationMinutes\":10,\"imageType\":\"jpg\",\"winePairing\":{\"productMatches\":[{\"score\":0.8073684210526315,\"price\":\"$24.49\",\"imageUrl\":\"https://spoonacular.com/productImages/440954-312x231.jpg\",\"averageRating\":0.86,\"link\":\"https://click.linksynergy.com/deeplink?id=*QCiIS6t4gA&mid=2025&murl=https%3A%2F%2Fwww.wine.com%2Fproduct%2Ffonterutoli-chianti-classico-1998%2F12434\",\"description\":\"Color: Deep purplish-red but bright and exceptionally concentrated.Bouquet: Extremely intense and complex with scents of cherries and raspberries accompanied by light toasty and spicy shadings.Flavor: The impact in the mouth is incisive but soft due to a substantial structure of tannins in which those that are soft and well rounded stand out. Acidity is fused with the wine's body and aids the transmission of pleasant sensations of warmth and strength. The wine features a long finish that is unusual for a regular Chianti Classico.Conclusions: Those who argue that Chianti Classico is a prickly and rough wine will change their minds after tasting this 1999, which is ready to drink now but will improve for at least five years more.Alcohol: 13.5% by volume\",\"id\":440954,\"title\":\"Fonterutoli Chianti Classico\",\"ratingCount\":6}],\"pairingText\":\"Breakfast Pizza works really well with Sangiovese, Barbera Wine, and Shiraz. The best wine for pizza depends on the toppings! Red sauce pizza will call for a red wine with some acidity, such as a barberan or sangiovese. Add pepperoni or sausage and you can go bolder with a syrah. The Fonterutoli Chianti Classico with a 4.3 out of 5 star rating seems like a good match. It costs about 24 dollars per bottle.\",\"pairedWines\":[\"sangiovese\",\"barbera wine\",\"shiraz\"]},\"cookingMinutes\":15,\"image\":\"https://spoonacular.com/recipeImages/559251-312x231.jpg\",\"veryHealthy\":false,\"vegan\":false,\"cheap\":false,\"dishTypes\":[\"lunch\",\"main course\",\"morning meal\",\"brunch\",\"main dish\",\"breakfast\",\"dinner\"],\"gaps\":\"no\",\"cuisines\":[\"mediterranean\",\"european\",\"italian\"],\"lowFodmap\":false,\"weightWatcherSmartPoints\":13,\"occasions\":[],\"spoonacularScore\":46,\"pricePerServing\":195.59,\"spoonacularSourceUrl\":\"https://spoonacular.com/breakfast-pizza-559251\",\"sourceName\":\"Jo Cooks\",\"ketogenic\":false},"
            //       + "{\"sustainable\":false,\"analyzedInstructions\":[{\"name\":\"\",\"steps\":[{\"number\":1,\"ingredients\":[],\"equipment\":[{\"image\":\"oven.jpg\",\"name\":\"oven\",\"temperature\":{\"number\":200,\"unit\":\"Celsius\"},\"id\":404784}],\"step\":\"Heat oven to 200C/180C fan/gas\"},{\"number\":2,\"length\":{\"number\":3,\"unit\":\"minutes\"},\"ingredients\":[{\"image\":\"flour-tortilla.jpg\",\"name\":\"tortilla\",\"id\":18364},{\"image\":\"tomato.png\",\"name\":\"tomato\",\"id\":11529}],\"equipment\":[{\"image\":\"baking-sheet.jpg\",\"name\":\"baking sheet\",\"id\":404727}],\"step\":\"Lay the tortillas on two baking sheets, brush sparingly with the oil then bake for 3 mins. Meanwhile chop the pepper and tomatoes and mix with the tomato pure, seasoning and herbs. Turn the tortillas over and spread with the tomato mixture, leaving the centre free from any large pieces of pepper or tomato.\"},{\"number\":3,\"length\":{\"number\":10,\"unit\":\"minutes\"},\"ingredients\":[{\"image\":\"flour-tortilla.jpg\",\"name\":\"tortilla\",\"id\":18364},{\"image\":\"egg.png\",\"name\":\"egg\",\"id\":1123}],\"equipment\":[{\"image\":\"oven.jpg\",\"name\":\"oven\",\"id\":404784}],\"step\":\"Break an egg into the centre then return to the oven for 10 mins or until the egg is just set and the tortilla is crispy round the edges.\"},{\"number\":4,\"ingredients\":[{\"image\":\"arugula-or-rocket-salad.jpg\",\"name\":\"arugula\",\"id\":11959},{\"image\":\"brown-onion.png\",\"name\":\"onion\",\"id\":11282}],\"equipment\":[],\"step\":\"Serve scattered with the rocket and onion.\"}]}],\"glutenFree\":true,\"veryPopular\":true,\"healthScore\":14,\"title\":\"Egg & rocket pizzas\",\"diets\":[\"gluten free\",\"dairy free\"],\"aggregateLikes\":1011,\"sourceUrl\":\"https://www.bbcgoodfood.com/recipes/egg-rocket-pizzas\",\"readyInMinutes\":30,\"creditsText\":\"BBC Good Food\",\"dairyFree\":true,\"servings\":2,\"vegetarian\":false,\"whole30\":false,\"creditText\":\"BBC Good Food\",\"id\":630293,\"preparationMinutes\":10,\"imageType\":\"jpg\",\"winePairing\":{\"productMatches\":[{\"score\":0.75,\"price\":\"$26.0\",\"imageUrl\":\"https://spoonacular.com/productImages/431618-312x231.jpg\",\"averageRating\":1,\"link\":\"https://www.amazon.com/2009-Madsen-Family-Cellars-Sangiovese/dp/B00I0F3FJA?tag=spoonacular-20\",\"description\":\"This wine has a pronounced nose with a hint of coffee while your mouth is invaded with spices; clove, licorice, and cinnamon.The palate features cherries and notes of clove which continue on to a smooth, long finish with subtle oak, slight pepper and berry flavors. The tartness of the wine works well with food as Sangiovese is a food wine, but can be enjoyed on its own as well. Columbia Valley AVA from the vineyards of Jordan Reed.\",\"id\":431618,\"title\":\"Madsen Family Cellars Sangiovese Wine\",\"ratingCount\":1}],\"pairingText\":\"Pizza works really well with Sangiovese, Barbera Wine, and Shiraz. The best wine for pizza depends on the toppings! Red sauce pizza will call for a red wine with some acidity, such as a barberan or sangiovese. Add pepperoni or sausage and you can go bolder with a syrah. The Madsen Family Cellars Sangiovese Wine with a 5 out of 5 star rating seems like a good match. It costs about 26 dollars per bottle.\",\"pairedWines\":[\"sangiovese\",\"barbera wine\",\"shiraz\"]},\"cookingMinutes\":20,\"image\":\"https://spoonacular.com/recipeImages/630293-312x231.jpg\",\"veryHealthy\":false,\"vegan\":false,\"cheap\":false,\"dishTypes\":[],\"gaps\":\"no\",\"cuisines\":[],\"lowFodmap\":false,\"weightWatcherSmartPoints\":4,\"occasions\":[],\"spoonacularScore\":78,\"pricePerServing\":156.93,\"spoonacularSourceUrl\":\"https://spoonacular.com/egg-rocket-pizzas-630293\",\"sourceName\":\"BBC Good Food\",\"ketogenic\":false},"
            //      + "{\"sustainable\":false,\"analyzedInstructions\":[{\"name\":\"\",\"steps\":[{\"number\":1,\"ingredients\":[{\"image\":\"yeast-granules.jpg\",\"name\":\"yeast\",\"id\":18375}],\"equipment\":[{\"image\":\"bowl.jpg\",\"name\":\"bowl\",\"id\":404783}],\"step\":\"For the dough, put the flour, yeast and 2 tsp salt into a large bowl and mix. Make a well.\"},{\"number\":2,\"length\":{\"number\":15,\"unit\":\"minutes\"},\"ingredients\":[{\"image\":\"water.png\",\"name\":\"water\",\"id\":14412}],\"equipment\":[{\"image\":\"wooden-spoon.jpg\",\"name\":\"wooden spoon\",\"id\":404732},{\"image\":\"bowl.jpg\",\"name\":\"bowl\",\"id\":404783}],\"step\":\"Mix the oil and water in a jug, then tip into the bowl. Use a wooden spoon to work the liquid into the flour  it will seem pretty wet. Set the bowl aside for 15 mins. Leaving the dough like this will save you from lengthy kneading later.\"},{\"number\":3,\"length\":{\"number\":2,\"unit\":\"minutes\"},\"ingredients\":[],\"equipment\":[{\"image\":\"bowl.jpg\",\"name\":\"bowl\",\"id\":404783}],\"step\":\"Turn the dough onto a well-floured surface, flour your hands, then knead it very gently for about 2 mins until it is fairly even, soft and bouncy. Return the dough to the bowl, cover with oiled cling film, then let it rise in a warm place (or in the fridge overnight) until doubled in size.\"},{\"number\":4,\"ingredients\":[],\"equipment\":[{\"image\":\"baking-sheet.jpg\",\"name\":\"baking sheet\",\"id\":404727},{\"image\":\"oven.jpg\",\"name\":\"oven\",\"temperature\":{\"number\":240,\"unit\":\"Celsius\"},\"id\":404784}],\"step\":\"When ready to cook, heat oven to 240C/220C fan/gas 9 or as hot as it will go, then put a baking sheet in on a high shelf. Dust another sheet with flour. Split the dough into 8, then roll 3 balls thinly into rough circles. Lift onto the floured sheet. Smear over a thin layer of the sauce, scatter over a few slices of tomato, season, then add sliced goats or grated Parmesan cheese if you want to. Slide the sheet on top of the heated sheet.\"},{\"number\":5,\"length\":{\"number\":12,\"unit\":\"minutes\"},\"ingredients\":[{\"image\":\"olive-oil.jpg\",\"name\":\"olive oil\",\"id\":4053}],\"equipment\":[],\"step\":\"Bake for 12 mins or until golden and crisp and the tomatoes are starting to caramelise around the edges. Top with any fresh toppings, then drizzle with more olive oil to serve.\"}]}],\"glutenFree\":false,\"veryPopular\":true,\"healthScore\":15,\"title\":\"Easy tomato pizzas\",\"diets\":[\"lacto ovo vegetarian\"],\"aggregateLikes\":425,\"sourceUrl\":\"https://www.bbcgoodfood.com/recipes/12389/easy-tomato-pizzas\",\"readyInMinutes\":90,\"creditsText\":\"BBC Good Food\",\"dairyFree\":false,\"servings\":8,\"vegetarian\":true,\"whole30\":false,\"creditText\":\"BBC Good Food\",\"id\":223993,\"preparationMinutes\":10,\"imageType\":\"jpg\",\"winePairing\":{\"productMatches\":[{\"score\":0.75,\"price\":\"$19.0\",\"imageUrl\":\"https://spoonacular.com/productImages/469504-312x231.jpg\",\"averageRating\":1,\"link\":\"https://www.amazon.com/Story-Winery-Sangiovese-California-Shenandoah/dp/B00NMXHFNC?tag=spoonacular-20\",\"description\":\"Aromas of raspberry, cherry, pie crust, tobacco and oak greet the nose. Dark cherries and raspberry predominate the palate with mild tannins and nice acidity. The finish is gentle and lasting Enjoy!\",\"id\":469504,\"title\":\"Story Winery, Sangiovese, Estate, Shenandoah Valley\",\"ratingCount\":1}],\"pairingText\":\"Pizza works really well with Sangiovese, Barbera Wine, and Shiraz. The best wine for pizza depends on the toppings! Red sauce pizza will call for a red wine with some acidity, such as a barberan or sangiovese. Add pepperoni or sausage and you can go bolder with a syrah. One wine you could try is Story Winery, Sangiovese, Estate, Shenandoah Valley. It has 5 out of 5 stars and a bottle costs about 19 dollars.\",\"pairedWines\":[\"sangiovese\",\"barbera wine\",\"shiraz\"]},\"cookingMinutes\":12,\"image\":\"https://spoonacular.com/recipeImages/223993-312x231.jpg\",\"veryHealthy\":false,\"vegan\":false,\"cheap\":false,\"dishTypes\":[],\"gaps\":\"no\",\"cuisines\":[],\"lowFodmap\":false,\"weightWatcherSmartPoints\":7,\"occasions\":[],\"spoonacularScore\":81,\"pricePerServing\":42.35,\"spoonacularSourceUrl\":\"https://spoonacular.com/easy-tomato-pizzas-223993\",\"sourceName\":\"BBC Good Food\",\"ketogenic\":false},"
            //     + "{\"sustainable\":false,\"analyzedInstructions\":[{\"name\":\"\",\"steps\":[{\"number\":1,\"length\":{\"number\":7,\"unit\":\"minutes\"},\"ingredients\":[{\"image\":\"honey.png\",\"name\":\"honey\",\"id\":19296},{\"image\":\"water.png\",\"name\":\"water\",\"id\":14412},{\"image\":\"yeast-granules.jpg\",\"name\":\"yeast\",\"id\":18375}],\"equipment\":[{\"image\":\"measuring-cup.jpg\",\"name\":\"measuring cup\",\"id\":404766}],\"step\":\"To make the dough, in a 2-cup liquid measuring cup, add warm water and yeast and honey, allow to sit for 5-7 minutes or until the mixture begins to puff.\"},{\"number\":2,\"ingredients\":[{\"image\":\"water.png\",\"name\":\"water\",\"id\":14412}],\"equipment\":[],\"step\":\"Add the oil and the room temperature water.\"},{\"number\":3,\"ingredients\":[],\"equipment\":[{\"image\":\"whisk.png\",\"name\":\"whisk\",\"id\":404661}],\"step\":\"Whisk gently to combine.\"},{\"number\":4,\"length\":{\"number\":3,\"unit\":\"minutes\"},\"ingredients\":[{\"image\":\"water.png\",\"name\":\"water\",\"id\":14412},{\"image\":\"salt.jpg\",\"name\":\"salt\",\"id\":2047}],\"equipment\":[{\"image\":\"stand-mixer.jpg\",\"name\":\"stand mixer\",\"id\":404665},{\"image\":\"bowl.jpg\",\"name\":\"bowl\",\"id\":404783}],\"step\":\"In the bowl of a stand mixer fit with the paddle attachment, combine salt, flours, and water mixture. When dough begins to form a ball, remove paddle attachment and replace with dough hook. With mixing speed on low, knead dough for 2-3 minutes or until a ball begins to form and the dough becomes smooth and elastic.\"},{\"number\":5,\"ingredients\":[{\"image\":\"olive-oil.jpg\",\"name\":\"olive oil\",\"id\":4053}],\"equipment\":[{\"image\":\"plastic-wrap.jpg\",\"name\":\"plastic wrap\",\"id\":404730},{\"image\":\"bowl.jpg\",\"name\":\"bowl\",\"id\":404783}],\"step\":\"Remove dough from bowl and place in a large well-greased bowl (I prefer using olive oil, but you can also use Pam). Cover bowl tightly with plastic wrap and allow to rise for 2 hours.\"},{\"number\":6,\"ingredients\":[],\"equipment\":[{\"image\":\"pan.png\",\"name\":\"frying pan\",\"id\":404645}],\"step\":\"To deflate dough, punch with fist. Turn onto a lightly floured surface and divide into two balls (depending on how big and how many pizzas you want to make I make 2). Form into pizza shells to fit the size of your pan.\"},{\"number\":7,\"ingredients\":[{\"image\":\"flour.png\",\"name\":\"all purpose flour\",\"id\":20081}],\"equipment\":[{\"image\":\"pan.png\",\"name\":\"frying pan\",\"id\":404645}],\"step\":\"Place on a lightly greased pan (or one that has been dusted with flour or cornmeal).\"},{\"number\":8,\"ingredients\":[{\"image\":\"olive-oil.jpg\",\"name\":\"olive oil\",\"id\":4053}],\"equipment\":[{\"image\":\"sauce-pan.jpg\",\"name\":\"sauce pan\",\"id\":404669}],\"step\":\"To make sauce, in a small saucepan heat olive oil until shiny.\"},{\"number\":9,\"length\":{\"number\":1,\"unit\":\"minutes\"},\"ingredients\":[],\"equipment\":[],\"step\":\"Add minced garlic, stir and cook for 1 minute.\"},{\"number\":10,\"ingredients\":[{\"image\":\"tomato-sauce-or-pasta-sauce.jpg\",\"name\":\"tomato sauce\",\"id\":11549},{\"image\":\"oregano.jpg\",\"name\":\"oregano\",\"id\":2027},{\"image\":\"basil.jpg\",\"name\":\"basil\",\"id\":2044},{\"image\":\"sugar-in-bowl.png\",\"name\":\"sugar\",\"id\":19335},{\"image\":\"salt.jpg\",\"name\":\"salt\",\"id\":2047}],\"equipment\":[],\"step\":\"Add tomato sauce, sugar, basil, oregano and salt.\"},{\"number\":11,\"length\":{\"number\":5,\"unit\":\"minutes\"},\"ingredients\":[],\"equipment\":[],\"step\":\"Heat for 5 minutes or until the sauce becomes fragrant.\"},{\"number\":12,\"ingredients\":[{\"image\":\"olive-oil.jpg\",\"name\":\"olive oil\",\"id\":4053},{\"image\":\"tomato.png\",\"name\":\"tomato\",\"id\":11529},{\"image\":\"salt.jpg\",\"name\":\"salt\",\"id\":2047}],\"equipment\":[],\"step\":\"To assemble the pizzas, spread sauce evenly over the pizza peel, leaving about 1 inches around the edge for the crust. Lightly brush the crust with olive oil (this will help it get nice and crisp) Lay slices of cheese over the sauce. Then place sliced tomatoes over top, drizzle with olive oil and sprinkle with a little salt.\"},{\"number\":13,\"length\":{\"number\":14,\"unit\":\"minutes\"},\"ingredients\":[],\"equipment\":[],\"step\":\"Bake for 12-14 minutes at 450 degrees.\"},{\"number\":14,\"length\":{\"number\":6,\"unit\":\"minutes\"},\"ingredients\":[],\"equipment\":[],\"step\":\"Remove, let cool for 5-6 minutes.\"},{\"number\":15,\"ingredients\":[{\"image\":\"fresh-basil.jpg\",\"name\":\"fresh basil\",\"id\":2044}],\"equipment\":[],\"step\":\"Place basil leaves over top, slice and serve. Repeat with the second ball of dough.\"}]}],\"glutenFree\":false,\"veryPopular\":true,\"healthScore\":29,\"title\":\"Neapolitan Pizza and Honey Whole Wheat Dough\",\"diets\":[\"lacto ovo vegetarian\"],\"aggregateLikes\":418,\"sourceUrl\":\"https://lifemadesimplebakes.com/2014/02/neapolitan-pizza-and-honey-whole-wheat-dough/\",\"readyInMinutes\":102,\"creditsText\":\"Life Made Simple\",\"dairyFree\":false,\"servings\":8,\"vegetarian\":true,\"whole30\":false,\"creditText\":\"Life Made Simple\",\"id\":481601,\"preparationMinutes\":90,\"imageType\":\"jpg\",\"winePairing\":{\"productMatches\":[{\"score\":0.8078048780487804,\"price\":\"$26.99\",\"imageUrl\":\"https://spoonacular.com/productImages/459422-312x231.jpg\",\"averageRating\":0.82,\"link\":\"https://click.linksynergy.com/deeplink?id=*QCiIS6t4gA&mid=2025&murl=https%3A%2F%2Fwww.wine.com%2Fproduct%2Fcecchi-chianti-classico-riserva-di-famiglia-2014%2F240545\",\"description\":\"Chianti Classico Riserva di Famiglia is certainly the most representative wine of Cecchi. Produced only in years in which the grapes reach the quality desired it comes from the area of Castellina in Chianti. The color of excellent limpidity is of an intense ruby red. The aroma reveals broad and ethereal notes of ripe fruit and spices. The structure is elegant.\",\"id\":459422,\"title\":\"Cecchi Chianti Classico Riserva di Famiglia\",\"ratingCount\":27}],\"pairingText\":\"Pizzan on the menu? Try pairing with Sangiovese, Barbera Wine, and Shiraz. The best wine for pizza depends on the toppings! Red sauce pizza will call for a red wine with some acidity, such as a barberan or sangiovese. Add pepperoni or sausage and you can go bolder with a syrah. One wine you could try is Cecchi Chianti Classico Riserva di Famiglia. It has 4.1 out of 5 stars and a bottle costs about 27 dollars.\",\"pairedWines\":[\"sangiovese\",\"barbera wine\",\"shiraz\"]},\"cookingMinutes\":12,\"image\":\"https://spoonacular.com/recipeImages/481601-312x231.jpg\",\"veryHealthy\":false,\"vegan\":false,\"cheap\":false,\"dishTypes\":[],\"gaps\":\"no\",\"cuisines\":[\"mediterranean\",\"european\",\"italian\"],\"lowFodmap\":false,\"weightWatcherSmartPoints\":9,\"occasions\":[],\"spoonacularScore\":93,\"pricePerServing\":50.6,\"spoonacularSourceUrl\":\"https://spoonacular.com/neapolitan-pizza-and-honey-whole-wheat-dough-481601\",\"sourceName\":\"Life Made Simple\",\"ketogenic\":false},"
            //    + "{\"sustainable\":false,\"analyzedInstructions\":[{\"name\":\"\",\"steps\":[{\"number\":1,\"ingredients\":[],\"equipment\":[{\"image\":\"oven.jpg\",\"name\":\"oven\",\"temperature\":{\"number\":180,\"unit\":\"Celsius\"},\"id\":404784}],\"step\":\"Preheat oven to fan 180C/conventional 200C/gas\"},{\"number\":2,\"ingredients\":[],\"equipment\":[],\"step\":\"Unroll the pastry onto a lightly floured surface and roll it out to 40 x 32cm.\"},{\"number\":3,\"ingredients\":[{\"image\":\"tomato-sauce-or-pasta-sauce.jpg\",\"name\":\"pasta sauce\",\"id\":10011549},{\"image\":\"cheddar-cheese.png\",\"name\":\"cheese\",\"id\":1041009},{\"image\":\"ham-whole.jpg\",\"name\":\"ham\",\"id\":10151}],\"equipment\":[],\"step\":\"Spread a layer of pasta sauce over it, leaving a 1cm border around the edges. Arrange the ham evenly on top, then scatter the grated cheese over.\"},{\"number\":4,\"length\":{\"number\":10,\"unit\":\"minutes\"},\"ingredients\":[],\"equipment\":[],\"step\":\"Starting at one of the short ends, roll the pastry up as tightly as possible. Chill in the fridge for 10 minutes or so.\"},{\"number\":5,\"ingredients\":[],\"equipment\":[{\"image\":\"chefs-knife.jpg\",\"name\":\"knife\",\"id\":404745}],\"step\":\"Take a very sharp knife and cut the roll into 12 equal slices, laying them flat on 2 non-stick baking trays as you go.\"},{\"number\":6,\"ingredients\":[{\"image\":\"egg.png\",\"name\":\"egg\",\"id\":1123}],\"equipment\":[],\"step\":\"Brush each pinwheel lightly with beaten egg and sprinkle over the herbs.\"},{\"number\":7,\"length\":{\"number\":25,\"unit\":\"minutes\"},\"ingredients\":[],\"equipment\":[],\"step\":\"Bake for 12-15 minutes until puffed and golden. Leave to stand for 5-10 minutes before serving.\"}]}],\"glutenFree\":false,\"veryPopular\":true,\"healthScore\":3,\"title\":\"Pizza puff pinwheels\",\"diets\":[],\"aggregateLikes\":411,\"sourceUrl\":\"https://www.bbcgoodfood.com/recipes/1463/pizza-puff-pinwheels\",\"readyInMinutes\":35,\"creditsText\":\"BBC Good Food\",\"dairyFree\":false,\"servings\":12,\"vegetarian\":false,\"whole30\":false,\"creditText\":\"BBC Good Food\",\"id\":222869,\"preparationMinutes\":20,\"imageType\":\"jpg\",\"winePairing\":{\"productMatches\":[{\"score\":0.75,\"price\":\"$19.0\",\"imageUrl\":\"https://spoonacular.com/productImages/469504-312x231.jpg\",\"averageRating\":1,\"link\":\"https://www.amazon.com/Story-Winery-Sangiovese-California-Shenandoah/dp/B00NMXHFNC?tag=spoonacular-20\",\"description\":\"Aromas of raspberry, cherry, pie crust, tobacco and oak greet the nose. Dark cherries and raspberry predominate the palate with mild tannins and nice acidity. The finish is gentle and lasting Enjoy!\",\"id\":469504,\"title\":\"Story Winery, Sangiovese, Estate, Shenandoah Valley\",\"ratingCount\":1}],\"pairingText\":\"Sangiovese, Barbera Wine, and Shiraz are great choices for Pizza. The best wine for pizza depends on the toppings! Red sauce pizza will call for a red wine with some acidity, such as a barberan or sangiovese. Add pepperoni or sausage and you can go bolder with a syrah. The Story Winery, Sangiovese, Estate, Shenandoah Valley with a 5 out of 5 star rating seems like a good match. It costs about 19 dollars per bottle.\",\"pairedWines\":[\"sangiovese\",\"barbera wine\",\"shiraz\"]},\"cookingMinutes\":15,\"image\":\"https://spoonacular.com/recipeImages/222869-312x231.jpg\",\"veryHealthy\":false,\"vegan\":false,\"cheap\":false,\"dishTypes\":[],\"gaps\":\"no\",\"cuisines\":[\"mediterranean\",\"european\",\"italian\"],\"lowFodmap\":false,\"weightWatcherSmartPoints\":7,\"occasions\":[],\"spoonacularScore\":40,\"pricePerServing\":48.34,\"spoonacularSourceUrl\":\"https://spoonacular.com/pizza-puff-pinwheels-222869\",\"sourceName\":\"BBC Good Food\",\"ketogenic\":false}]";

            routeContext.setSession("recipes", recipes);


            routeContext.json().send(recipes);
            // routeContext.json().send(recipeJSONstring);
        });

        GET("/login", routeContext -> {
            routeContext = setHeaders(routeContext, "GET");
            String email = routeContext.getParameter("email").toString();
            // when loading a new user populate all collections with empty arrays
            log.info(db.getDataFromDatabase(email, "Favorites")); 
            if (db.getDataFromDatabase(email, "Favorites").equals("FAILED")) {
                db.pushDataToDatabase(email, "Favorites", "[]");
                db.pushDataToDatabase(email, "To Explore", "[]");
                db.pushDataToDatabase(email, "Do Not Show", "[]");
                db.pushDataToDatabase(email, "Searches", "[]");
                db.pushDataToDatabase(email, "Grocery", "[]");
                log.info("user created");
            } else {
                log.info("user exist");
            }
            
            routeContext.setSession("user", email);
            db.pushDataToDatabase("current", "user", email);
            this.user = email;
            routeContext.send(email + " logged in");
        });

        GET("/logout", routeContext -> {
            routeContext = setHeaders(routeContext, "GET");
            db.pushDataToDatabase("current", "user", "empty");
            this.user = "empty";
            routeContext.send("logged out");
        });

        GET("/searches", routeContext -> {
            routeContext = setHeaders(routeContext, "GET");
            String email = routeContext.getParameter("email").toString() != null 
                        ? routeContext.getParameter("email").toString()
                        : "empty";
            log.info(email);

            JsonParser parser = new JsonParser();
            log.info(this.user);
            JsonArray searches = (JsonArray) parser.parse(db.getDataFromDatabase(this.user, "Searches"));
            /*
             * get searches from db
             * String searches = db.getDataFromDatabase(user, "Searches");
             * if its not null send back 
             */
            // send back
            String response;
            if(searches != null) {
                log.info(searches.toString());
                response = searches.toString();
            }
            else {
                log.info("empty");
                response = "[]";
            }
            routeContext.json().send(response);
        });
        
        GET("/curruser", routeContext -> {
            String user = db.getDataFromDatabase("current", "user");
            // String u = routeContext.getSession("user");
            routeContext.send(this.user);
        });

        GET("/reset", routeContext -> {
            String name = routeContext.getParameter("name").toString();
            db.pushDataToDatabase("test@usc.edu", name, "[]");
            routeContext.send(name);
        });

        GET("/grocery", routeContext -> {
            routeContext = setHeaders(routeContext, "GET");
            String response = db.getDataFromDatabase(this.user, "Grocery");

            routeContext.json().send(response);
        });

        GET("/grocery/add", routeContext -> {
            routeContext = setHeaders(routeContext, "GET");
            String id = routeContext.getParameter("id").toString();

            JsonParser parser = new JsonParser();
            JsonObject item = null;
            item = findItem(routeContext, id);
            JsonArray groceryList = (JsonArray) parser.parse(db.getDataFromDatabase(this.user, "Grocery"));
            JsonArray ai = item.get("analyzedInstructions").getAsJsonArray();
            JsonArray steps = ai.get(0).getAsJsonObject().get("steps").getAsJsonArray();
            for(JsonElement el : steps) {
                JsonArray ingredients = el.getAsJsonObject().get("ingredients").getAsJsonArray();
                log.info(ingredients.toString());
                for(JsonElement i : ingredients) {
                    JsonObject groceryItem = new JsonObject();
                    groceryItem.addProperty("recipe", item.get("title").getAsString());
                    groceryItem.addProperty("name", i.getAsJsonObject().get("name").getAsString());
                    log.info(groceryItem.toString());
                    if(!groceryList.contains(groceryItem)) {
                        groceryList.add(groceryItem);
                    }
                }
            }
            log.info(groceryList.toString());
            db.pushDataToDatabase(this.user, "Grocery", groceryList.toString());
            // log.info(item.toString());
            routeContext.send("grocery add");
        });

        GET("/grocery/delete", routeContext -> {
            routeContext = setHeaders(routeContext, "GET");
            int idx = routeContext.getParameter("index").toInt();
            JsonParser parser = new JsonParser();
            JsonArray groceryList = (JsonArray) parser.parse(db.getDataFromDatabase(this.user, "Grocery"));
            groceryList.remove(idx);
            db.pushDataToDatabase(this.user, "Grocery", groceryList.toString());
            routeContext.send("deleted ingredient at " + idx);
        });
    }

    // specify type of item, restaurant or recipe
    private String specifyType(JsonArray result, String type) {
        log.info("specifyType");
        int page = 1;
        int counter = 1;
        for (JsonElement item : result) {
            item.getAsJsonObject().addProperty("type", type);
            item.getAsJsonObject().addProperty("page", page);
            if(counter % 5 == 0) {
                page++;
            }
            counter++;
        }

        return result.toString();
    }

    // private JsonObject findItem(JsonArray restaurants, JsonArray recipes, String id) {
    private JsonObject findItem(RouteContext routeContext, String id) {
        log.info("findItem");

        JsonParser parser = new JsonParser();
        String restaurantsString = routeContext.getSession("restaurants");
        JsonArray restaurants = (JsonArray) parser.parse(restaurantsString);
        String recipesString = routeContext.getSession("recipes");
        JsonArray recipes = (JsonArray) parser.parse(recipesString);

        for (JsonElement item : restaurants) {
            JsonObject i = item.getAsJsonObject();
            log.info(i.get("id").getAsString());
            if (i.get("id").getAsString().equals(id)) {
                log.info("found");
                return i;
            }
        }

        for (JsonElement item : recipes) {
            JsonObject i = item.getAsJsonObject();
            log.info(i.get("id").getAsString());
            if (i.get("id").getAsString().equals(id)) {
                log.info("found");
                return i;
            }
        }
        return null;
    }

    private RouteContext setHeaders(RouteContext routeContext, String method) {
        routeContext.getResponse().header("Access-Control-Allow-Origin", "http://localhost:3000");
        routeContext.getResponse().header("Access-Control-Allow-Credentials", "true");
        routeContext.getResponse().header("Access-Control-Allow-Methods", method);
        // routeContext.getResponse().header("Content-type", "application/json; charset=utf-8");

        return routeContext;
    }

    private String ConvertfromMilestoMeters(String radius) {
        int newRadius = Integer.parseInt(radius);
        newRadius = 1609 * newRadius;
        return Integer.toString(newRadius);
    }
}
