package com.github.ridomo.usecase.spring.webmvc.support.processor;

import org.springframework.beans.BeanInstantiationException;

import static org.apache.commons.lang3.StringUtils.join;

public class NotSingletonScopeBeanException extends BeanInstantiationException {
	private static final long serialVersionUID = -8138923479093196121L;

	public NotSingletonScopeBeanException(Class<?> beanClass, String[] beanNames) {
		super(beanClass, join(beanNames, ","));
	}
}
