package com.github.ridomo.usecase.support;

import com.github.ridomo.usecase.shared.*;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class AbstractPresenter<T extends ResponseModel> implements Presenter<T> {
	private T response;

	protected abstract void execute(T response);

	@Override
	public final void accept(T response) {
		this.response = checkNotNull(response, "response must not be null");
	}

	@Override
	public final void present() {
		execute(checkNotNull(response, "response must not be null"));
	}
}
