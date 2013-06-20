package com.github.ridomo.usecase.spring.webmvc.support.integration;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("src/test/resources")
public abstract class AbstractConfig {
	protected MockMvc mockMvc;
	@Autowired
	private WebApplicationContext context;

	@Before
	public void setUp() throws Exception {
		mockMvc = webAppContextSetup(context).build();
	}

	@Test
	public void testChangeInteractorScope() {
		assertThat(context.isPrototype("listingInteractor"), is(true));
	}

	@Test
	@Repeat(2)
	public void testInteractor() throws Exception {
		mockMvc.perform(get("/test/interactor"))
				.andExpect(status().isOk())
				.andExpect(model().attribute("list", 1));
	}

	@Test
	@Repeat(2)
	public void testInteractorJson() throws Exception {
		mockMvc.perform(get("/test/interactor.json"))
//				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$[0]").value(1))
				.andExpect(jsonPath("$[1]").value(2));
	}

	@Test
	@Repeat(2)
	public void testCompositeInteractor() throws Exception {
		mockMvc.perform(get("/test/compositeInteractor"))
				.andExpect(status().isOk());
	}

	@Test
	@Repeat(2)
	public void testCompositePresentableInteractor() throws Exception {
		mockMvc.perform(get("/test/compositePresentableInteractor"))
				.andExpect(status().isOk())
				.andExpect(model().attribute("show", 1));
	}

	@Test
	@Repeat(2)
	public void testCompositeModelInteractor() throws Exception {
		mockMvc.perform(get("/test/compositeModelPresenter"))
				.andExpect(status().isOk())
				.andExpect(model().attribute("list", 1))
				.andExpect(model().attribute("show", 1));
	}
}
