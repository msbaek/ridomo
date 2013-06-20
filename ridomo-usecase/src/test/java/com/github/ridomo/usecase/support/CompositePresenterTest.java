package com.github.ridomo.usecase.support;

import com.github.ridomo.usecase.shared.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CompositePresenterTest {
	private CompositePresenter<ResponseModel> presenter;
	@Mock
	private Presenter<ResponseModel> mock;
	@Mock
	private ResponseModel response;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		presenter = new CompositePresenter<ResponseModel>(mock, mock);
	}

	@Test
	public void testAccept() throws Exception {
		presenter.accept(response);
		verify(mock, times(2)).accept(response);
	}

	@Test
	public void testPresent() throws Exception {
		presenter.present();
		verify(mock, times(2)).present();
	}
}
