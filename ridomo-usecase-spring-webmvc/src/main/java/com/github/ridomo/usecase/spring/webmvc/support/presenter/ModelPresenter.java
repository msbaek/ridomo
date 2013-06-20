package com.github.ridomo.usecase.spring.webmvc.support.presenter;

import com.github.ridomo.usecase.shared.*;

import java.util.Map;

public interface ModelPresenter<T extends ResponseModel> extends Presenter<T> {
	void setModel(Map<String, Object> model);
}
