package com.github.ridomo.usecase.support;

import com.github.ridomo.usecase.shared.*;
import com.google.common.collect.Sets;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.validation.*;
import java.util.Set;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AbstractValidatePresentableInteractorTest {
	private AbstractValidatePresentableInteractor<RequestModel, ResponseModel> interactor;
	@Mock
	private AbstractValidatePresentableInteractor<RequestModel, ResponseModel> mock;
	@Mock
	private RequestModel request;
	@Mock
	private ResponseModel response;
	@Mock
	private Presenter<ResponseModel> presenter;
	@Mock
	private Validator validator;
	@Mock
	private ConstraintViolation<RequestModel> violation;

	@Before
	public void setUp() throws Exception {
		interactor = new AbstractValidatePresentableInteractor<RequestModel, ResponseModel>() {
			@Override
			protected void perform(RequestModel request) {
				mock.perform(request);
			}

			@Override
			protected ResponseModel createResponseModel() {
				return mock.createResponseModel();
			}
		};
		interactor.setValidator(validator);
	}

	@SuppressWarnings("unchecked")
	@Test(expected = ValidationException.class)
	public void testAcceptWhenNotValidate() throws Exception {
		Set<ConstraintViolation<RequestModel>> violations = Sets.newHashSet(violation);
		when(validator.validate(request)).thenReturn(violations);
		interactor.accept(request);
		fail();
	}

	@Test
	public void testAcceptWhenValidate() throws Exception {
		Set<ConstraintViolation<RequestModel>> violations = Sets.newHashSet();
		when(validator.validate(request)).thenReturn(violations);
		interactor.accept(request);
		verify(validator, times(1)).validate(request);
	}

	@Test
	public void testExecute() throws Exception {
		when(mock.createResponseModel()).thenReturn(response);
		interactor.setPresenter(presenter);
		interactor.execute(request);
		verify(mock, times(1)).perform(request);
		verify(mock, times(1)).createResponseModel();
		verify(presenter, times(1)).accept(response);
		verify(presenter, times(1)).present();
	}
}
