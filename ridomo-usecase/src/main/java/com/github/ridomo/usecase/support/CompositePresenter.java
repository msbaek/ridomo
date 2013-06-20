package com.github.ridomo.usecase.support;

import com.github.ridomo.usecase.shared.*;
import com.google.common.collect.Lists;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class CompositePresenter<R extends ResponseModel> implements Presenter<R> {
	private final List<? extends Presenter<R>> presenters;

	public CompositePresenter(List<? extends Presenter<R>> presenters) {
		this.presenters = checkNotNull(presenters, "presenters must not be null");
	}

	public CompositePresenter(Presenter<R>... presenters) {
		this(Lists.newArrayList(presenters));
	}

	@Override
	public final void accept(R response) {
		for (Presenter<R> presenter : presenters) {
			presenter.accept(response);
		}
	}

	@Override
	public final void present() {
		for (Presenter<R> presenter : presenters) {
			presenter.present();
		}
	}
}
