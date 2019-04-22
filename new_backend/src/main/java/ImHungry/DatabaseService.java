package ImHungry;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
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

import java.util.Objects;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URL;

public class DatabaseService {
	private static Firestore database; 
	private static Boolean databaseInitialized = false;
 
	/**
     * Default constructor.
	 * @throws FileNotFoundException 
     */
    public DatabaseService() {
    	if(databaseInitialized == false) {
    		initializeDatabase();
    	}
    	// String json = "{\"ID\":2,\"Name\":\"Patrick Pacheco\",\"age\":21,\"location\":\"Los Angeles\"}";
			// pushDataToDatabase("ppacheco@usc.edu", "Test", json);
    }
    
    private void initializeDatabase() {
		// TODO Auto-generated method stub
    	FileInputStream serviceAccount = null;
		try {
                  ClassLoader classLoader = DatabaseService.class.getClassLoader();
                  URL res = Objects.requireNonNull(classLoader.getResource("firebase.json"));
                      serviceAccount = new FileInputStream(res.getFile());
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException ioe) {
                  ioe.printStackTrace();
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
    
    public String getDataFromDatabase(String email, String something) {
    	DocumentReference docRef = database.collection(email).document(something);
		ApiFuture<DocumentSnapshot> futureTwo = docRef.get();
		DocumentSnapshot document;
		try {
			document = futureTwo.get();
			if(document.exists()) {
				//Map<String, Object> myTestMap = document.getData();
				String testString = document.getString("Object1");
				System.out.println(testString);
				return testString;
			} 
			else {
				System.out.println("Document does not exist. ");
				return "FAILED";
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "FAILED";
    }
    
    public void pushDataToDatabase(String email, String something, String inputJSON) {
    	
    	//convert JSON string to HashMap
    	Map<String, Object> map = new HashMap<String, Object>();
    	try {
			ObjectMapper mapper = new ObjectMapper();
			
			// convert JSON string to Map
			//map = mapper.readValue(json, new TypeReference<Map<String, String>>(){});
			map = mapper.readValue(inputJSON, new TypeReference<Map<String, String>>(){});
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	Map<String, String> myMap = new HashMap<String, String>() {{
	        put("Object1", inputJSON);
	        put("Object2", "Thai");
	    }};
	    
	    //Push the HashMap to the database
		ApiFuture<WriteResult> future = database.collection(email).document(something).set(myMap);
				//.update(map); 
				
    }
}

