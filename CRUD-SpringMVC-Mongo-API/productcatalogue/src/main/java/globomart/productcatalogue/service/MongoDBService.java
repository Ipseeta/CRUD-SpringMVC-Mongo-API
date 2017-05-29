package globomart.productcatalogue.service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
/**
 * MongoDB service for all connection, insertion functions
 * @author ipseeta
 *
 */
public interface MongoDBService {
	MongoClient dbConnect();
	void insert(DBCollection collection, BasicDBObject document);
	void closeDB(MongoClient mongClient);
}
