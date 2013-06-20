package com.github.ridomo.usecase.support;

import com.github.ridomo.usecase.shared.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CompositePresentableInteractorTest {
	private CompositePresentableInteractor<RequestModel, ResponseModel> interactor;
	@Mock
	private PresentableInteractor<RequestModel, ResponseModel> mock;
	@Mock
	private Presenter<ResponseModel> presenter;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		interactor = new CompositePresentableInteractor<RequestModel, ResponseModel>(mock, mock);
	}

	@Test
	public void testSetPresenter() throws Exception {
		interactor.setPresenter(presenter);
		assertThat(interactor.getPresenter(), is(presenter));
		verify(mock, times(2)).setPresenter(presenter);
	}

	@Test(expected = NullPointerException.class)
	public void testSetPresenterWhenSetNull() throws Exception {
		interactor.setPresenter(null);
		fail();
	}
}
