package model;

import java.net.UnknownHostException;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;

public class MongoDB{

	private Mongo mongoClient; 
	private DB database;
	private DBCollection dataSetColl;
	
	public MongoDB() {
		// TODO Auto-generated constructor stub
		
		try {
			this.mongoClient = new Mongo( "localhost" , 27017 );
			// Now connect to your databases
	         this.database = mongoClient.getDB( "opinionMinerDB" );
	        // this.dataSetColl = this.database.createCollection("dataSetColl", null);
			 System.out.println("Connect to database successfully");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
	}

//	public void clearCollections(){
//		
//		DBCursor curs = this.fetchedTweetsColl.find();
//		while(curs.hasNext()){
//			this.fetchedTweetsColl.remove(curs.next());
//		}
//	
//	}


	public void setMongoClient(MongoClient mongoClient) {
		this.mongoClient = mongoClient;
	}

	public DB getDatabase() {
		return database;
	}

	public void setDatabase(DB database) {
		this.database = database;
	}

}
