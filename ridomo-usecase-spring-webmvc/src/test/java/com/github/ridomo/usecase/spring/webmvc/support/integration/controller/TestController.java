package com.github.ridomo.usecase.spring.webmvc.support.integration.controller;

import com.github.ridomo.usecase.shared.*;
import com.github.ridomo.usecase.spring.webmvc.support.annotation.Interaction;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("test")
public class TestController {
	@Interaction(interactor = "listingInteractor", presenter = "listingModelPresenter")
	@RequestMapping("interactor")
	public void interactor(Interactor<RequestModel> interactor, @ModelAttribute TestRequestModel request) {
		interactor.accept(request);
		interactor.interact();
	}

	@Interaction(interactor = "listingInteractor", presenter = "listingPresenter")
	@RequestMapping(value = "interactor", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Interactor<RequestModel> interactorJson(Interactor<RequestModel> interactor, @ModelAttribute TestRequestModel request) {
		interactor.accept(request);
		interactor.interact();
		return interactor;
	}

	@Interaction(interactor = "compositeInteractor")
	@RequestMapping("compositeInteractor")
	public void compositeInteractor(Interactor<RequestModel> interactor, @ModelAttribute TestRequestModel request) {
		interactor.accept(request);
		interactor.interact();
	}

	@Interaction(interactor = "compositePresentableInteractor", presenter = "showModelPresenter")
	@RequestMapping("compositePresentableInteractor")
	public void compositePresentableInteractor(Interactor<RequestModel> interactor, @ModelAttribute TestRequestModel request) {
		interactor.accept(request);
		interactor.interact();
	}

	@Interaction(interactor = "listingInteractor", presenter = "compositeModelPresenter")
	@RequestMapping("compositeModelPresenter")
	public void compositeModelPresenter(Interactor<RequestModel> interactor, @ModelAttribute TestRequestModel request) {
		interactor.accept(request);
		interactor.interact();
	}
}
