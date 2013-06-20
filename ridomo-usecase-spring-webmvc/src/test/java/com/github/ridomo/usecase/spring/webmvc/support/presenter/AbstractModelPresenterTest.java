package com.github.ridomo.usecase.spring.webmvc.support.presenter;

import com.github.ridomo.usecase.shared.ResponseModel;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AbstractModelPresenterTest {
	private AbstractModelPresenter<ResponseModel> presenter;
	@Mock
	private Map<String, Object> model;

	@Before
	public void setUp() throws Exception {
		presenter = new AbstractModelPresenter<ResponseModel>() {
			@Override
			protected void execute(ResponseModel response) {
			}
		};
		presenter.setModel(model);
	}

	@Test
	public void testPut() throws Exception {
		String key = "kew";
		String value = "value";
		presenter.put(key, value);
		verify(model, times(1)).put(key, value);
	}
}
