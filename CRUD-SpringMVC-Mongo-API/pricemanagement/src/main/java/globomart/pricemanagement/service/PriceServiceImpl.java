package globomart.pricemanagement.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import globomart.pricemanagement.exception.ProductIDInvalidException;
/**
 * Service implementation class for price management microservice
 * @author ipseeta
 *
 */
@Component
public class PriceServiceImpl implements PriceService{
	/**
	 * This method calls product catalogue microservice for getting all product details and retrieving price from it
	 * on the basis of productID
	 */
	public double getPrice(String id) throws IOException{
		if(StringUtils.isEmpty(id)){
			throw new ProductIDInvalidException("Product ID is invalid");
		}
		URL url;
		double price = 0;
		HttpURLConnection request;
		url = new URL("http://localhost:8080/productcatalogue/product/?id=" + id);
		request = (HttpURLConnection) url.openConnection();
		request.connect();
		JsonParser jp = new JsonParser(); 
		JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
		JsonObject rootobj = root.getAsJsonObject(); 
		price = rootobj.get("price").getAsDouble(); 
		return price;
	}

}
