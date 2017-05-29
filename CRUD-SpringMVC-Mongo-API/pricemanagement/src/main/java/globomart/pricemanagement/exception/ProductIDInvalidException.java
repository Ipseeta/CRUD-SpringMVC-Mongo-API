package globomart.pricemanagement.exception;
/**
 * Custom exception for invalid product ID
 * @author ipseeta
 *
 */
public class ProductIDInvalidException extends RuntimeException{
	private String message;
	private static final long serialVersionUID = 1L;
	public ProductIDInvalidException(String message) {
		super();
		this.message = message;
    }
	@Override
	   public String getMessage(){
	       return message;
	   }

}
