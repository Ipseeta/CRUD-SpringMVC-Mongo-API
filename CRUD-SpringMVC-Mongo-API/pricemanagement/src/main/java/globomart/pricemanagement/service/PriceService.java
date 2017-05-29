package globomart.pricemanagement.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
/**
 * Price service for getting price of a product
 * @author ipseeta
 *
 */
@Service
public interface PriceService {
	double getPrice(String id) throws IOException;
}
