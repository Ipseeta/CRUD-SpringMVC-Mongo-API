package globomart.productcatalogue.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import globomart.productcatalogue.controller.ProductCatalogueController;
import globomart.productcatalogue.service.ProductCatalogueService;
/**
 * Test class for ProductCatalogue management
 * @author ipseeta
 *
 */
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(locations = "classpath:dispatcher-servlet.xml")
@EnableWebMvc
public class ProductCatalogueTest {

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(),
			Charset.forName("utf8"));

	private MockMvc mockMvc;
	@Mock
	ProductCatalogueService productCatalogueService;


	@Before
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(new ProductCatalogueController(productCatalogueService))
				.build();
	}
	@Test
	public void productCreation() throws Exception {
		String res = "{\"id\":\"8\",\"name\":\"iphone7+\",\"type\":\"mobile\",\"price\":80000}";
		this.mockMvc.perform(post("/product/create")
				.contentType(contentType)
				.content(res))
		.andExpect(status().isCreated());
	}

	@Test
	public void productCreationMissingAttr() throws Exception{
		String res = "{\"name\":\"iphone7+\",\"type\":\"mobile\",\"price\":80000}";
		this.mockMvc.perform(post("/product/create")
				.contentType(contentType)
				.content(res))
		.andExpect(status().isOk());
		
	}

	@Test
	public void productSearch() throws Exception{
		String type = "mobile";
		this.mockMvc.perform(get("/product/search")
				.param("type", type)
				).andDo(print()).andExpect(status().isOk());
		
	}

	@Test
	public void productWrongSearch() throws Exception{
		String type = "";
		this.mockMvc.perform(get("/product/search")
				.param("type", type)
				).andDo(print()).andExpect(status().isOk());

	}

	@Test
	public void productDeletion() throws Exception{
		String id = "8";
		this.mockMvc.perform(delete("/product/delete/{id}",id)
				).andDo(print()).andExpect(status().isNotFound());

	}


}
