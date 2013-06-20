package com.github.ridomo.usecase.spring.webmvc.support.processor;

import com.github.ridomo.usecase.shared.*;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.*;

import static org.apache.commons.lang3.ArrayUtils.isNotEmpty;

public class BoundaryScopeFactoryPostProcessor implements BeanFactoryPostProcessor {
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		checkNotSingletonBeans(beanFactory, Interactor.class);
		checkNotSingletonBeans(beanFactory, Presenter.class);
	}

	private void checkNotSingletonBeans(ConfigurableListableBeanFactory beanFactory, Class<? extends Boundary> clazz) {
		String[] names = beanFactory.getBeanNamesForType(clazz, false, true);
		if (isNotEmpty(names)) {
			throw new NotSingletonScopeBeanException(clazz, names);
		}
	}
}
