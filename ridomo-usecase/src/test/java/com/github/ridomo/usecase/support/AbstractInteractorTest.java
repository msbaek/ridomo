package com.github.ridomo.usecase.support;

import com.github.ridomo.usecase.shared.RequestModel;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AbstractInteractorTest {
	private AbstractInteractor<RequestModel> interactor;
	@Mock
	private AbstractInteractor<RequestModel> mock;
	@Mock
	private RequestModel request;

	@Before
	public void setUp() throws Exception {
		interactor = new AbstractInteractor<RequestModel>() {
			@Override
			protected void execute(RequestModel request) {
				mock.execute(request);
			}
		};
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
