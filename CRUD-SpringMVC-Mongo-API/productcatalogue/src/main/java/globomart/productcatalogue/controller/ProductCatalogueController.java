package globomart.productcatalogue.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.MongoTimeoutException;

import globomart.productcatalogue.exception.ProductCreationException;
import globomart.productcatalogue.exception.ProductDeletionException;
import globomart.productcatalogue.exception.ProductSearchException;
import globomart.productcatalogue.model.Product;
import globomart.productcatalogue.service.ProductCatalogueService;
/**
 * RestController for Product Catalogue where we can do CRUD operations on product
 * @author ipseeta
 *
 */
@RestController
@RequestMapping("/product")
public class ProductCatalogueController {
	@Autowired
	ProductCatalogueService productCatalogueService;
	@Autowired
	public ProductCatalogueController(ProductCatalogueService service) {
		this.productCatalogueService = service;
	}
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductCatalogueController.class);
	/**
	 * Creation of a product with product as responsebody
	 * @param product RequestBody
	 * @return Product with 201 http status code
	 */
	@RequestMapping(value="/create",method=RequestMethod.POST)
	public  ResponseEntity<Product> createProduct(@RequestBody Product product) {
		Product result = productCatalogueService.addProduct(product);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(result);
	}
	/**
	 * Searching list of products by their type.
	 * @param type Request param
	 * @return list of products
	 */
	@RequestMapping(value="/search",method=RequestMethod.GET)
	public  List<Product> searchProduct(@RequestParam(value="type")String type) {
		return productCatalogueService.getProductsByType(type);
	}
	/**
	 * Deleting a product by its id
	 * @param id product id
	 */
	@RequestMapping(value="/delete/{id}",method=RequestMethod.DELETE)
	public  void deleteProduct(@PathVariable("id")String id) {
		productCatalogueService.deleteProduct(id);
	}
	/**
	 * Retrieving product details from its id
	 * @param id
	 * @return product
	 */
	@RequestMapping(value="/",method=RequestMethod.GET)
	public  Product getProductDetails(@RequestParam(value="id")String id) {
		return productCatalogueService.getProductById(id);
	}
	/**
	 * Exceptional handler for product creation
	 * @param ex ProductCreationException
	 * @return message
	 */
	@ExceptionHandler(ProductCreationException.class)
	public ResponseEntity<String> handleProductCreationException(ProductCreationException ex) {
		LOGGER.error("Handling error with message: {}", ex.getMessage());
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(ex.getMessage());
	}
	/**
	 * Exceptional handler for product search
	 * @param ex ProductSearchException
	 * @return message
	 */
	@ExceptionHandler(ProductSearchException.class)
	public ResponseEntity<String> handleProductSearchException(ProductSearchException ex) {
		LOGGER.error("Handling error with message: {}", ex.getMessage());
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(ex.getMessage());
	}
	/**
	 * Exceptional handler for mongodb connection failure
	 * @param ex MongoTimeoutException
	 * @return message
	 */
	@ExceptionHandler(MongoTimeoutException.class)
	public ResponseEntity<String> handleMongoConnectionException(MongoTimeoutException ex) {
		LOGGER.error("Handling error with message: {}", ex.getMessage());
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body("Sorry! could not connect to mongodb");
	}
	/**
	 * Exceptional handler for product deletion
	 * @param ex ProductDeletionException
	 * @return message
	 */
	@ExceptionHandler(ProductDeletionException.class)
	public ResponseEntity<String> handleDeletionException(ProductDeletionException ex) {
		LOGGER.error("Handling error with message: {}", ex.getMessage());
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(ex.getMessage());
	}
}
