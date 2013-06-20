package com.github.ridomo.usecase.support;

import com.github.ridomo.usecase.shared.RequestModel;
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
public class AbstractValidateInteractorTest {
	private AbstractValidateInteractor<RequestModel> interactor;
	@Mock
	private AbstractValidateInteractor<RequestModel> mock;
	@Mock
	private RequestModel request;
	@Mock
	private Validator validator;
	@Mock
	private ConstraintViolation<RequestModel> violation;

	@Before
	public void setUp() throws Exception {
		interactor = new AbstractValidateInteractor<RequestModel>() {
			@Override
			protected void execute(RequestModel request) {
				mock.execute(request);
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
	public void testInteract() throws Exception {
		interactor.accept(request);
		interactor.interact();
		verify(mock, times(1)).execute(request);
	}

	@Test(expected = NullPointerException.class)
	public void testAcceptWhenRequestIsNull() throws Exception {
		interactor.accept(null);
		fail();
	}

	@Test(expected = NullPointerException.class)
	public void testInteractWhenAcceptIsNull() throws Exception {
		interactor.interact();
		fail();
	}
}
