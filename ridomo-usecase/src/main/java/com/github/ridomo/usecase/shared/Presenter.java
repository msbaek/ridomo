package com.github.ridomo.usecase.shared;

/**
 * Warning Presenter are not Thread Safe
 *
 * @param <T>
 */
public interface Presenter<T extends ResponseModel> extends Boundary {
	void accept(T response);

	void present();
}
