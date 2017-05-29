package globomart.productcatalogue.exception;

import java.util.ArrayList;
/**
 * Caters all the product creation exceptions
 * @author ipseeta
 *
 */
public class ProductCreationException extends RuntimeException{
	private String message;
	private static final long serialVersionUID = 1L;
	public ProductCreationException(ArrayList<String> missingAttrs) {
		super();
		String missingProperties = "";
		for(String productAttr : missingAttrs){
			missingProperties = missingProperties + ' ' + productAttr + ' ';
		}
		missingProperties = missingProperties.substring(0, missingProperties.lastIndexOf(" "));
		this.message = String.format("Please check the missing product details: <%s>", missingProperties);
    }
	public ProductCreationException(String duplicate) {
		super();
		this.message = String.format("%s already exists",duplicate);
    }
	@Override
	   public String getMessage(){
	       return message;
	   }
}
