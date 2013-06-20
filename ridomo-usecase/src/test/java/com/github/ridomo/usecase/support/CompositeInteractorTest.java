package com.github.ridomo.usecase.support;

import com.github.ridomo.usecase.shared.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CompositeInteractorTest {
	private CompositeInteractor<RequestModel> interactor;
	@Mock
	private Interactor<RequestModel> mock;
	@Mock
	private RequestModel request;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		interactor = new CompositeInteractor<RequestModel>(mock, mock);
	}

	@Test
	public void testAccept() throws Exception {
		interactor.accept(request);
		verify(mock, times(2)).accept(request);
	}

	@Test
	public void testInteract() throws Exception {
		interactor.interact();
		verify(mock, times(2)).interact();
	}
}
