package globomart.pricemanagement.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;
/**
 * Test class for all the interactions with productCatalogue management services
 * @author ipseeta
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsTest {
	@Test
	public void testPriceController(){
		RestTemplate restTemplate = new RestTemplate();
		  Object product = restTemplate.getForObject(
		       "http://localhost:8080/productcatalogue/product/?id=1", Object.class);
		  System.out.println(product.toString());
	}

}
