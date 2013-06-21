# ridomo-usecase-spring-webmvc
> Rich Domain Model Support For Uncle Bob's Clean Code Cast Episode 7 Architecture, Use Cases, and High Level Design with Spring Web MVC

## Warning
> Interactor & Presenter are not Thread Safe

## Example

### Spring Configuration(3.2+)

#### Web Configuration(Use ArgumentResolvers)

##### Xml Configuration Example

```xml
<mvc:annotation-driven>
	<mvc:return-value-handlers>
		<bean class="InteractorMethodProcessor"
		      c:messageConverters-ref="messageConverters" c:manager-ref="mvcContentNegotiationManager"/>
	</mvc:return-value-handlers>
	<mvc:argument-resolvers>
		<bean class="InteractorMethodProcessor"
		      c:messageConverters-ref="messageConverters" c:manager-ref="mvcContentNegotiationManager"/>
	</mvc:argument-resolvers>
</mvc:annotation-driven>

<util:list id="messageConverters">
	<bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter"/>
</util:list>

<!-- this bean processor check spring scope beans(interactor, presenter class) -->
<bean class="BoundaryScopeFactoryPostProcessor"/>
```

##### Java Configuration(Use @EnableUseCase Annotation) Example

```java
@Configuration
@EnableUseCase
@EnableWebMvc
public class WebConfiguration {
}
...
```

#### Boundary(Interactor, Presenter) must not singleton scope

```xml
<!-- Interactors -->
<bean id="listingInteractor" scope="prototype"
      class="ListingInteractor"/>

<!-- Presenters -->
<bean id="listingPresenter" scope="request"
      class="ListingPresenter"/>
<bean id="listingModelPresenter" scope="request"
      class="ListingModelPresenter"/>

<!-- Composite Boundaries -->
<bean id="compositeInteractor" class="CompositeInteractor"
      c:interactors-ref="interactors" scope="prototype"/>
<util:list id="interactors" scope="prototype">
	<ref bean="deleteInteractor"/>
	<ref bean="updateInteractor"/>
</util:list>

<bean id="compositePresentableInteractor" class="CompositePresentableInteractor"
      c:interactors-ref="presentableInteractors" scope="prototype"/>
<util:list id="presentableInteractors" scope="prototype">
	<ref bean="listingInteractor"/>
	<ref bean="showInteractor"/>
</util:list>

<bean id="compositeModelPresenter" class="CompositeModelPresenter"
      c:presenters-ref="presenters" scope="request"/>
<util:list id="presenters" scope="request">
	<ref bean="listingModelPresenter"/>
	<ref bean="showModelPresenter"/>
</util:list>
...
```

#### Presenter Example

```java
// spring ModelMap(ModelAndView)
public class ListingModelPresenter extends AbstractModelPresenter<ResponseModel> {
	@Override
	protected void execute(ResponseModel response) {
		// put attributes
		put("accounts", accounts);
		put("values", values);
	}
}

// spring @ResponseBody, implements Targetable
public class ListingPresenter extends AbstractPresenter<ResponseModel> implements Presenter<ResponseModel>, Targetable<List<Integer>> {
	@Override
	protected void execute(ResponseModel response) {
		// create target
	}

	@Override
	public List<Integer> getTarget() {
		// like @ResponseBody
		return target;
	}
}

// composite model presenter
Presenter<ResponseModel> presenter = new CompositeModelPresenter<ResponseModel>(listModelPresenter, showModelPresenter);
...
```

#### Controller Example

```java
// only use interactor
@Interaction(interactor = "updateInteractor")
@RequestMapping("update")
public void update(Interactor<RequestModel> interactor, @ModelAttribute TestRequestModel request) {
	interactor.accept(request);
	interactor.interact();
}

// like spring ModelMap(ModelAndView)
@Interaction(interactor = "listingInteractor", presenter = "listingModelPresenter")
@RequestMapping("index")
public void index(Interactor<RequestModel> interactor, @ModelAttribute TestRequestModel request) {
	interactor.accept(request);
	interactor.interact();
}

// like spring @ResponseBody
@Interaction(interactor = "listingInteractor", presenter = "listingPresenter")
@RequestMapping(value = "list", produces = {MediaType.APPLICATION_JSON_VALUE})
public Interactor<RequestModel> list(Interactor<RequestModel> interactor, @ModelAttribute TestRequestModel request) {
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
...
```