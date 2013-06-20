package com.github.ridomo.usecase.support;

import com.github.ridomo.usecase.shared.*;

public interface PresentableInteractor<T extends RequestModel, S extends ResponseModel> extends Interactor<T> {
	Presenter<S> getPresenter();

	void setPresenter(Presenter<S> presenter);
}
