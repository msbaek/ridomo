package com.github.ridomo.usecase.spring.webmvc.support.presenter;

import com.github.ridomo.usecase.shared.ResponseModel;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CompositeModelPresenterTest {
	private CompositeModelPresenter<ResponseModel> presenter;
	@Mock
	private ModelPresenter<ResponseModel> mock;
	@Mock
	private Map<String, Object> model;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		presenter = new CompositeModelPresenter<ResponseModel>(mock, mock);
	}

	@Test
	public void testSetModel() throws Exception {
		presenter.setModel(model);
		verify(mock, times(2)).setModel(model);
	}
}
