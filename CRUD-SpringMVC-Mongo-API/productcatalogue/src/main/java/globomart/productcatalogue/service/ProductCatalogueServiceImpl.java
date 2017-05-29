package globomart.productcatalogue.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import globomart.productcatalogue.exception.ProductCreationException;
import globomart.productcatalogue.exception.ProductDeletionException;
import globomart.productcatalogue.exception.ProductSearchException;
import globomart.productcatalogue.model.Product;
/**
 * Product catalogue service implementation
 * @author ipseeta
 *
 */
@Component
public class ProductCatalogueServiceImpl implements ProductCatalogueService{
	@Autowired
	MongoDBService mongodbService;
	private static String DB = "globomart";
	private static String COLLECTION = "products";

	/*
	 * (non-Javadoc)
	 * @see globomart.productcatalogue.service.ProductCatalogueService#addProduct(globomart.productcatalogue.model.Product)
	 * This method searches for not null params for id,name and type, checks for already exists on basis of product id
	 * then creates a product in database globomart, collection products
	 */

	public Product addProduct(Product product){
		MongoClient mongoClient = mongodbService.dbConnect();
		@SuppressWarnings("deprecation")
		DB db = mongoClient.getDB(DB);
		DBCollection collection = db.getCollection(COLLECTION);
		BasicDBObject document = new BasicDBObject();
		List<Product> res = null;
		ArrayList<String> missingAttr = new ArrayList<String>();

		if( StringUtils.isEmpty(product.getId()) || StringUtils.isEmpty(product.getName()) || StringUtils.isEmpty(product.getType())){
			if(StringUtils.isEmpty(product.getId())){
				missingAttr.add("id");
			}
			if(StringUtils.isEmpty(product.getName())){
				missingAttr.add("name");
			}
			if(StringUtils.isEmpty(product.getType())){
				missingAttr.add("type");
			}
			throw new ProductCreationException(missingAttr);
		}
		String id = product.getId();
		Product alreadyExists = getProductById(id);
		if(null!=alreadyExists && !StringUtils.isEmpty(alreadyExists.getId())){
			if(alreadyExists.getId().equalsIgnoreCase(id)){
				throw new ProductCreationException(id);
			}
		}

		document.put("id", id);
		document.put("name", product.getName());
		document.put("type", product.getType());
		document.put("price", product.getPrice());

		collection.insert(document);
		DBCursor newProductCursor = collection.find(new BasicDBObject().append("id", id));
		res = getProducts(newProductCursor);
		mongodbService.closeDB(mongoClient);
		return res.get(0);
	}
	/*
	 * (non-Javadoc)
	 * @see globomart.productcatalogue.service.ProductCatalogueService#getProductById(java.lang.String)
	 * Searches for valid ID and returns a product
	 */
	public Product getProductById(String id) {
		MongoClient mongoClient = mongodbService.dbConnect();
		@SuppressWarnings("deprecation")
		DB db = mongoClient.getDB(DB);
		if(StringUtils.isEmpty(id)){
			throw new ProductSearchException("Product ID is invalid");
		}
		BasicDBObject whereQuery = new BasicDBObject()
				.append("id", id);
		DBCursor cursor = db.getCollection(COLLECTION).find(whereQuery);
		Product product = new Product();
		while (cursor.hasNext()) {
			DBObject obj = new BasicDBObject();
			obj = cursor.next();
			product.setName(obj.get("name").toString());
			product.setId(obj.get("id").toString());
			product.setType(obj.get("type").toString());
			product.setPrice(Double.parseDouble(obj.get("price").toString()));
		}
		mongodbService.closeDB(mongoClient);
		return product;
	}
	/*
	 * (non-Javadoc)
	 * @see globomart.productcatalogue.service.ProductCatalogueService#getProductsByType(java.lang.String)
	 * Searches for valid type and returns products
	 */
	public List<Product> getProductsByType(String productType) {
		MongoClient mongoClient = mongodbService.dbConnect();
		@SuppressWarnings("deprecation")
		DB db = mongoClient.getDB(DB);
		List<Product> products = new ArrayList<Product>();
		if(StringUtils.isEmpty(productType)){
			throw new ProductSearchException("Product type is invalid");
		}
		BasicDBObject whereQuery = new BasicDBObject()
				.append("type", Pattern.compile(".*"+productType+".*" , Pattern.CASE_INSENSITIVE));
		DBCursor cursor = db.getCollection(COLLECTION).find(whereQuery);
		while (cursor.hasNext()) {
			Product product = new Product();
			DBObject obj = new BasicDBObject();
			obj = cursor.next();
			product.setName(obj.get("name").toString());
			product.setId(obj.get("id").toString());
			product.setType(obj.get("type").toString());
			product.setPrice(Double.parseDouble(obj.get("price").toString()));
			products.add(product);
		}
		mongodbService.closeDB(mongoClient);
		return products;
	}
	/*
	 * (non-Javadoc)
	 * @see globomart.productcatalogue.service.ProductCatalogueService#deleteProduct(java.lang.String)
	 * Deletes a product by its ID, if found
	 */
	public void deleteProduct(String id) {
		MongoClient mongoClient = mongodbService.dbConnect();
		@SuppressWarnings("deprecation")
		DB db = mongoClient.getDB(DB);
		BasicDBObject whereQuery = new BasicDBObject()
				.append("id", id);
		DBObject dbObj = db.getCollection(COLLECTION).findAndRemove(whereQuery);
		if(null == dbObj){
			throw new ProductDeletionException("No such product exists");
		}
		mongodbService.closeDB(mongoClient);
	}

	private List<Product> getProducts(DBCursor cursor){
		List<Product> result = new ArrayList<Product>();
		while (cursor.hasNext()) {
			Product product = new Product();
			DBObject obj = new BasicDBObject();
			obj = cursor.next();
			product.setName(obj.get("name") == null ? "" : obj.get("name").toString());
			product.setId(obj.get("id") == null ? "" : obj.get("id").toString());
			product.setType(obj.get("type") == null ? "" : obj.get("type").toString());
			product.setPrice(Double.parseDouble(obj.get("price") == null ? "0" : obj.get("price").toString()));
			result.add(product);
		}
		return result;
	}

}
