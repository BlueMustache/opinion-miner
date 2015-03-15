package strategy;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import model.MongoDB;
import model.TwitterDataSubject;

public class UpdateMongoDBStrategy implements ProcessStrategy{
	
	private MongoDB mongoDB;
	private DB database;
	
	public UpdateMongoDBStrategy() {
		// TODO Auto-generated constructor stub
		this.mongoDB = new MongoDB();
	}

	@Override
	public void runProcess(TwitterDataSubject subject) {
		// TODO Auto-generated method stub
		this.database = this.mongoDB.getDatabase();
		DBCollection collection = database.getCollection("twitterDataDB");
		ArrayList<JSONObject> mongoDataStore = subject.getMongoDataStore();
		
		
		for(JSONObject obj :mongoDataStore ){
//			System.out.println(obj.toString());
			DBObject dbObject = (DBObject) JSON.parse(obj.toJSONString());
			collection.insert(dbObject);
		}
		System.out.println("Mongo db Entrys");
		DBCursor cursorDoc = collection.find();
		while (cursorDoc.hasNext()) {
			System.out.println(cursorDoc.next());
		}
	}

}
