package ImHungryServlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RestaurantServlet
 */
@WebServlet("/RestaurantServlet")
public class RestaurantServlet extends HttpServlet {
        private static final long serialVersionUID = 1L;

        /**
         * Default constructor. 
         */
        public RestaurantServlet() {
                super();
        }

        /**
         * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
         */
        public void doGet(HttpServletRequest request, HttpServletResponse response)
                        throws ServletException, IOException {
                YelpRestaurantService yelp = new YelpRestaurantService();

                // Get queries
                String term = request.getParameter("query");
                String limit = request.getParameter("numResults");
                String radius = request.getParameter("radius");

                // contains restaurant JSON string results
                String restaurantJSONstring = (term != null && limit != null && radius != null)
                                ? yelp.getRestaurantInfo(term, limit, radius)
                                : "{\"error\": \"Missing fields in request parameters\"}";
                response.setContentType("application/json");
                response.getWriter().append(restaurantJSONstring).flush();
        }

        /**
         * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
         */
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                        throws ServletException, IOException {
                doGet(request, response);
        }

}
