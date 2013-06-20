package com.github.ridomo.usecase.support;

import com.github.ridomo.usecase.shared.*;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class AbstractPresentableInteractor<T extends RequestModel, S extends ResponseModel> extends AbstractInteractor<T> implements PresentableInteractor<T, S> {
	private Presenter<S> presenter;

	protected abstract void perform(T request);

	protected abstract S createResponseModel();

	@Override
	protected final void execute(T request) {
		perform(request);
		present();
	}

	private void present() {
		presenter.accept(createResponseModel());
		presenter.present();
	}

	@Override
	public final Presenter<S> getPresenter() {
		return presenter;
	}

	@Override
	public final void setPresenter(Presenter<S> presenter) {
		this.presenter = checkNotNull(presenter, "presenter must not be null");
	}
}
