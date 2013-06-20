package com.github.ridomo.usecase.spring.webmvc.support.processor;

import com.github.ridomo.usecase.spring.webmvc.support.annotation.Interaction;
import com.google.common.base.Objects;
import org.springframework.core.MethodParameter;
import org.springframework.ui.ModelMap;
import org.springframework.web.method.support.ModelAndViewContainer;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.lang3.StringUtils.*;

class ResolveParameter {
	private final MethodParameter parameter;
	private final ModelAndViewContainer mavContainer;

	private ResolveParameter(MethodParameter parameter, ModelAndViewContainer mavContainer) {
		this.parameter = checkNotNull(parameter, "parameter must not be null");
		this.mavContainer = checkNotNull(mavContainer, "mavContainer must not be null");
	}

	static ResolveParameter of(MethodParameter parameter, ModelAndViewContainer mavContainer) {
		return new ResolveParameter(parameter, mavContainer);
	}

	protected String getInteractor() {
		return hasInteraction() ? defaultIfBlank(getInteraction().interactor(), getParameterName()) : getParameterName();
	}

	protected String getPresenter() {
		return getInteraction().presenter();
	}

	protected Boolean hasPresenter() {
		return hasInteraction() && isNotBlank(getPresenter());
	}

	private Boolean hasInteraction() {
		return null != getInteraction();
	}

	private Interaction getInteraction() {
		return parameter.getMethodAnnotation(Interaction.class);
	}

	private String getParameterName() {
		return parameter.getParameterName();
	}

	protected ModelMap getModel() {
		return mavContainer.getModel();
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (this == o) {
			return true;
		}
		if (getClass() != o.getClass()) {
			return false;
		}
		ResolveParameter that = (ResolveParameter) o;
		return Objects.equal(parameter, that.parameter) && Objects.equal(mavContainer, that.mavContainer);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(parameter, mavContainer);
	}
}
