package ImHungryServlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

/**
 * Servlet implementation class DatabaseServlet
 */
public class DatabaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DatabaseServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		FileInputStream serviceAccount = new FileInputStream("/home/joshmin/firebase.json");

		FirebaseOptions options = new FirebaseOptions.Builder()
		    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
		    .setDatabaseUrl("https://csci310project2-e2908.firebaseio.com/")
		    .build();

		FirebaseApp.initializeApp(options);
		
		Firestore db = FirestoreClient.getFirestore();
		
		Map<String, String> myMap = new HashMap<String, String>() {{
	        put("a", "1");
	        put("b", "2");
	    }};
		ApiFuture<WriteResult> future = db.collection("Users").document("TestData").set(myMap);
		
		
		DocumentReference docRef = db.collection("Users").document("TestData");
		ApiFuture<DocumentSnapshot> futureTwo = docRef.get();
		DocumentSnapshot document;
		try {
			document = futureTwo.get();
			if(document.exists()) {
				Map<String, Object> myTestMap = document.getData();
				response.getWriter().append("Data found.").append(request.getContextPath());
			} 
			else {
				System.out.println("Document does not exist. ");
				response.getWriter().append("Could not find data.").append(request.getContextPath());
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
