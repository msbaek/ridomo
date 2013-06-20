package com.github.ridomo.usecase.support;

import com.github.ridomo.usecase.shared.ResponseModel;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AbstractPresenterTest {
	private AbstractPresenter<ResponseModel> presenter;
	@Mock
	private AbstractPresenter<ResponseModel> mock;
	@Mock
	private ResponseModel response;

	@Before
	public void setUp() throws Exception {
		presenter = new AbstractPresenter<ResponseModel>() {
			@Override
			protected void execute(ResponseModel response) {
				mock.execute(response);
			}
		};
	}

	@Test
	public void testInteract() throws Exception {
		presenter.accept(response);
		presenter.present();
		verify(mock, times(1)).execute(response);
	}

	@Test(expected = NullPointerException.class)
	public void testAcceptWhenResponseIsNull() throws Exception {
		presenter.accept(null);
		fail();
	}

	@Test(expected = NullPointerException.class)
	public void testInteractWhenAcceptIsNull() throws Exception {
		presenter.present();
		fail();
	}
}
