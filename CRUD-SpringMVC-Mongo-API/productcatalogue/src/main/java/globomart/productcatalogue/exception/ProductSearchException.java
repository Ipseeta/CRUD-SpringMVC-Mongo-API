package globomart.productcatalogue.exception;
/**
 * Caters product searching exceptions
 * @author ipseeta
 *
 */
@SuppressWarnings("serial")
public class ProductSearchException extends RuntimeException {
	private String message;
	public ProductSearchException(String typeInvalid) {
		super();
		this.message = typeInvalid;
    }
	@Override
	   public String getMessage(){
	       return message;
	   }

}
