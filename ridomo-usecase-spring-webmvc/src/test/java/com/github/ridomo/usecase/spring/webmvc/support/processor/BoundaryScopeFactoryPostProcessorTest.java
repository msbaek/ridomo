package com.github.ridomo.usecase.spring.webmvc.support.processor;

import com.github.ridomo.usecase.shared.*;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BoundaryScopeFactoryPostProcessorTest {
	private BoundaryScopeFactoryPostProcessor processor;
	@Mock
	private ConfigurableListableBeanFactory beanFactory;

	@Before
	public void setUp() throws Exception {
		processor = new BoundaryScopeFactoryPostProcessor();
	}

	@Test
	public void testPostProcessBeanFactory() throws Exception {
		String[] empty = new String[0];
		when(beanFactory.getBeanNamesForType(Interactor.class, false, true)).thenReturn(empty);
		when(beanFactory.getBeanNamesForType(Presenter.class, false, true)).thenReturn(empty);
		processor.postProcessBeanFactory(beanFactory);
		verify(beanFactory, times(1)).getBeanNamesForType(Interactor.class, false, true);
		verify(beanFactory, times(1)).getBeanNamesForType(Presenter.class, false, true);
	}

	@Test(expected = NotSingletonScopeBeanException.class)
	public void testPostProcessBeanFactoryWhenHasInteractors() throws Exception {
		String[] interactors = ArrayUtils.toArray("name");
		when(beanFactory.getBeanNamesForType(Interactor.class, false, true)).thenReturn(interactors);
		processor.postProcessBeanFactory(beanFactory);
		fail();
	}

	@Test(expected = NotSingletonScopeBeanException.class)
	public void testPostProcessBeanFactoryWhenHasPresenters() throws Exception {
		String[] empty = new String[0];
		String[] presenters = ArrayUtils.toArray("name");
		when(beanFactory.getBeanNamesForType(Interactor.class, false, true)).thenReturn(empty);
		when(beanFactory.getBeanNamesForType(Presenter.class, false, true)).thenReturn(presenters);
		processor.postProcessBeanFactory(beanFactory);
		fail();
	}
}
