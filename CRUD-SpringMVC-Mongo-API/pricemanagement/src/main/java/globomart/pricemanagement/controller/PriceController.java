package globomart.pricemanagement.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import globomart.pricemanagement.exception.ProductIDInvalidException;
import globomart.pricemanagement.service.PriceService;
/**
 * This controller provides the public API that is used to get the price for a given product
 * @author ipseeta
 *
 */
@RestController
@RequestMapping("/price")
public class PriceController {
	@Autowired
	PriceService priceService;
	@Autowired
	public PriceController(PriceService service) {
		this.priceService = service;
	}
	private static final Logger LOGGER = LoggerFactory.getLogger(PriceController.class);
	/**
	 * This method is called when we try to get product price by passing its id, 
	 * it internally calls product catalogue microservice's getProductDetails
	 * @param id Product ID
	 * @return price
	 * @throws IOException
	 */
	@RequestMapping(value="/getProductPrice",method=RequestMethod.GET)
	public  double getPrice(@RequestParam(value="id")String id) throws IOException {
		return priceService.getPrice(id);
	}
	/**
	 * Exceptional handler for wrong product id
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(ProductIDInvalidException.class)
	public ResponseEntity<String> handlePriceServiceException(ProductIDInvalidException ex) {
		LOGGER.error("Handling error with message: {}", ex.getMessage());
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(ex.getMessage());
	}
	/**
	 * Exceptional handler for any connection issue during product catalogue microservice call
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(IOException.class)
	public ResponseEntity<String> handleIOException(IOException ex) {
		LOGGER.error("Handling error with message: {}", ex.getMessage());
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body("Something went wrong while retrieving price");
	}

}
