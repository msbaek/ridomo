package com.github.ridomo.usecase.support;

import com.github.ridomo.usecase.shared.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AbstractPresentableInteractorTest {
	private AbstractPresentableInteractor<RequestModel, ResponseModel> interactor;
	@Mock
	private AbstractPresentableInteractor<RequestModel, ResponseModel> mock;
	@Mock
	private RequestModel request;
	@Mock
	private ResponseModel response;
	@Mock
	private Presenter<ResponseModel> presenter;

	@Before
	public void setUp() throws Exception {
		interactor = new AbstractPresentableInteractor<RequestModel, ResponseModel>() {
			@Override
			protected void perform(RequestModel request) {
				mock.perform(request);
			}

			@Override
			protected ResponseModel createResponseModel() {
				return mock.createResponseModel();
			}
		};
		interactor.setPresenter(presenter);
	}

	@Test
	public void testInteract() throws Exception {
		when(mock.createResponseModel()).thenReturn(response);
		interactor.accept(request);
		interactor.interact();
		verify(mock, times(1)).createResponseModel();
		verify(mock, times(1)).perform(request);
		verify(presenter, times(1)).accept(response);
		verify(presenter, times(1)).present();
	}

	@Test(expected = NullPointerException.class)
	public void testInteractWhenAcceptIsNull() throws Exception {
		interactor.interact();
		fail();
	}
}
