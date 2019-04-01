package ImHungry;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.api.services.storage.Storage.Buckets.List;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

public class DatabaseService {
	private static Firestore database; 
	private static Boolean databaseInitialized;
 
	/**
     * Default constructor.
	 * @throws FileNotFoundException 
     */
    public DatabaseService() {
    	
    	databaseInitialized = false;
    	if(databaseInitialized == false) {
    		initializeDatabase();
    	}
		pushDataToDatabase();
    }
    
    private void initializeDatabase() {
		// TODO Auto-generated method stub
    	FileInputStream serviceAccount = null;
		try {
			serviceAccount = new FileInputStream("/Users/tanayshah/Downloads/csci310project2-e2908-firebase-adminsdk-zz730-a1a036dd8d.json");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		FirebaseOptions options = null;
		try {
			options = new FirebaseOptions.Builder()
			    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
			    .setDatabaseUrl("https://csci310project2-e2908.firebaseio.com/")
			    .build();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FirebaseApp firebaseApp;
		
		firebaseApp = FirebaseApp.initializeApp(options);
		
		database = FirestoreClient.getFirestore();
		databaseInitialized = true;
	}
    
    public void getDataFromDatabase() {
    	DocumentReference docRef = database.collection("tanaynsh@usc.edu").document("Search Terms");
		ApiFuture<DocumentSnapshot> futureTwo = docRef.get();
		DocumentSnapshot document;
		try {
			document = futureTwo.get();
			if(document.exists()) {
				//Map<String, Object> myTestMap = document.getData();
				String testString = document.getString("a");
				System.out.println(testString);
			} 
			else {
				System.out.println("Document does not exist. ");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void pushDataToDatabase() {
    	Map<String, String> myMap = new HashMap<String, String>() {{
	        put("1", "Italian");
	        put("2", "Burgers");
	    }};
		ApiFuture<WriteResult> future = database.collection("tanaynsh@usc.edu").document("Search Terms").set(myMap);
    }
}
