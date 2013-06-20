package com.github.ridomo.usecase.spring.webmvc.support.integration.configuration;

import com.github.ridomo.usecase.shared.*;
import com.github.ridomo.usecase.spring.webmvc.support.annotation.EnableUseCase;
import com.github.ridomo.usecase.spring.webmvc.support.presenter.*;
import com.github.ridomo.usecase.support.*;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@Configuration
@ComponentScan({"com.github.ridomo.usecase.spring.webmvc.support.integration.controller", "com.github.ridomo.usecase.spring.webmvc.support.integration.interactor", "com.github.ridomo.usecase.spring.webmvc.support.integration.presenter"})
@EnableUseCase
@EnableWebMvc
public class WebConfiguration {
	@SuppressWarnings("unchecked")
	@Autowired
	@Bean
	@Scope(BeanDefinition.SCOPE_PROTOTYPE)
	public Interactor<RequestModel> compositeInteractor(@Qualifier("deleteInteractor") Interactor<RequestModel> deleteInteractor, @Qualifier("updateInteractor") Interactor<RequestModel> updateInteractor) {
		List<Interactor<RequestModel>> interactors = Lists.newArrayList(deleteInteractor, updateInteractor);
		return new CompositeInteractor<RequestModel>(interactors);
	}

	@SuppressWarnings("unchecked")
	@Autowired
	@Bean
	@Scope(BeanDefinition.SCOPE_PROTOTYPE)
	public Interactor<RequestModel> compositePresentableInteractor(@Qualifier("listingInteractor") PresentableInteractor<RequestModel, ResponseModel> listingInteractor, @Qualifier("showInteractor") PresentableInteractor<RequestModel, ResponseModel> showInteractor) {
		List<PresentableInteractor<RequestModel, ResponseModel>> interactors = Lists.newArrayList(listingInteractor, showInteractor);
		return new CompositePresentableInteractor<RequestModel, ResponseModel>(interactors);
	}

	@SuppressWarnings("unchecked")
	@Autowired
	@Bean
	@Scope(BeanDefinition.SCOPE_PROTOTYPE)
	public Presenter<ResponseModel> compositeModelPresenter(@Qualifier("listingModelPresenter") ModelPresenter<ResponseModel> listingModelPresenter, @Qualifier("showModelPresenter") ModelPresenter<ResponseModel> showModelPresenter) {
		List<ModelPresenter<ResponseModel>> presenters = Lists.newArrayList(listingModelPresenter, showModelPresenter);
		return new CompositeModelPresenter<ResponseModel>(presenters);
	}
}