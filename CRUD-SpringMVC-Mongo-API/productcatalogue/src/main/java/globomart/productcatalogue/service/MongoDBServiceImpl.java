package globomart.productcatalogue.service;

import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
/**
 * Connection related services
 * @author ipseeta
 *
 */
@Service
public class MongoDBServiceImpl implements MongoDBService {

	public void insert(DBCollection collection, BasicDBObject documents) {
		collection.insert(documents);
	}

	public MongoClient dbConnect(){
		MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017/globomart?maxPoolSize=10" ));
		return mongoClient;
	}

	public void closeDB(MongoClient mongoClient) {
		mongoClient.close();
	}

}
