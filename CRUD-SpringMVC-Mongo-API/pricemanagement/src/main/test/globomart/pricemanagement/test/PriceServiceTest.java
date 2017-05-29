package globomart.pricemanagement.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import globomart.pricemanagement.controller.PriceController;
import globomart.pricemanagement.service.PriceService;
/**
 * Test class for price management
 * @author ipseeta
 *
 */
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(locations = "classpath:dispatcher-servlet.xml")
@EnableWebMvc
public class PriceServiceTest {

	private MockMvc mockMvc;
	@Mock
	PriceService priceService;

	@Before
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(new PriceController(priceService))
				.build();
	}
	@Test
	public void retrieveProductPrice() throws Exception{
		String id = "1";
		this.mockMvc.perform(get("/price/getProductPrice")
				.param("id", id)
				).andDo(print()).andExpect(status().isOk());
	}
}
