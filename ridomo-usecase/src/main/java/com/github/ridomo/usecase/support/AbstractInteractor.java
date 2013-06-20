package com.github.ridomo.usecase.support;

import com.github.ridomo.usecase.shared.*;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class AbstractInteractor<T extends RequestModel> implements Interactor<T> {
	private T request;

	protected abstract void execute(T request);

	@Override
	public final void accept(T request) {
		this.request = checkNotNull(request, "request must not be null");
	}

	@Override
	public final void interact() {
		execute(checkNotNull(request, "request must not be null"));
	}
}
