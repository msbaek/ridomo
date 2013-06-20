package com.github.ridomo.usecase.shared;

/**
 * Warning Interactor are not Thread Safe
 *
 * @param <T>
 */
public interface Interactor<T extends RequestModel> extends Boundary {
	void accept(T request);

	void interact();
}
