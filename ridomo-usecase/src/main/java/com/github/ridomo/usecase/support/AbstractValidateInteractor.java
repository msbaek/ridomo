package com.github.ridomo.usecase.support;

import com.github.ridomo.usecase.shared.*;

import javax.validation.*;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

public abstract class AbstractValidateInteractor<T extends RequestModel> implements Interactor<T> {
	private T request;
	private Validator validator;

	protected abstract void execute(T request);

	@Override
	public final void accept(T request) {
		this.request = checkNotNull(request, "request must not be null");
		validate(request);
	}

	private void validate(T request) {
		Set<ConstraintViolation<T>> violations = checkNotNull(validator, "validator must not be null").validate(request);
		if (isNotEmpty(violations)) {
			throw new ConstraintViolationException(violations);
		}
	}

	@Override
	public final void interact() {
		execute(checkNotNull(request, "request must not be null"));
	}

	public final void setValidator(Validator validator) {
		this.validator = checkNotNull(validator, "validator must not be null");
	}
}
