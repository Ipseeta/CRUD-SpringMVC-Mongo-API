package globomart.productcatalogue.service;

import java.util.List;

import org.springframework.stereotype.Service;

import globomart.productcatalogue.model.Product;
/**
 * Product Catalogue services
 * @author ipseeta
 *
 */
@Service
public interface ProductCatalogueService {
	Product addProduct(Product product);
	Product getProductById(String id);
	List<Product> getProductsByType(String productType);
	void deleteProduct(String id);
}
