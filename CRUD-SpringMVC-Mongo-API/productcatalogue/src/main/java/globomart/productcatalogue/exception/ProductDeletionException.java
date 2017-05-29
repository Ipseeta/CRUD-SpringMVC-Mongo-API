package globomart.productcatalogue.exception;
/**
 * Caters product deletion runtime exception
 * @author ipseeta
 *
 */
@SuppressWarnings("serial")
public class ProductDeletionException extends RuntimeException {
	private String message;
	public ProductDeletionException(String typeInvalid) {
		super();
		this.message = typeInvalid;
    }
	@Override
	   public String getMessage(){
	       return message;
	   }
}
