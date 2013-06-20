package com.github.ridomo.usecase.spring.webmvc.support.processor;

import com.github.ridomo.usecase.spring.webmvc.support.annotation.Interaction;
import com.google.common.base.Objects;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.MethodParameter;
import org.springframework.ui.ModelMap;
import org.springframework.web.method.support.ModelAndViewContainer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ResolveParameterTest {
	private ResolveParameter resolveParameter;
	@Mock
	private MethodParameter parameter;
	@Mock
	private ModelAndViewContainer mavContainer;
	@Mock
	private ModelMap modelMap;
	@Mock
	private Interaction interaction;

	@Before
	public void setUp() throws Exception {
		resolveParameter = ResolveParameter.of(parameter, mavContainer);
	}

	@Test
	public void testInteractor() throws Exception {
		String name = "name";
		when(parameter.getMethodAnnotation(Interaction.class)).thenReturn(interaction);
		when(interaction.interactor()).thenReturn(name);
		assertThat(resolveParameter.getInteractor(), is(name));
		verify(parameter, times(2)).getMethodAnnotation(Interaction.class);
		verify(interaction, times(1)).interactor();
	}

	@Test
	public void testInteractorWhenInteractionInteractorIsEmpty() throws Exception {
		String name = "name";
		when(parameter.getMethodAnnotation(Interaction.class)).thenReturn(interaction);
		when(interaction.interactor()).thenReturn("  ");
		when(parameter.getParameterName()).thenReturn(name);
		assertThat(resolveParameter.getInteractor(), is(name));
		verify(parameter, times(2)).getMethodAnnotation(Interaction.class);
		verify(interaction, times(1)).interactor();
		verify(parameter, times(1)).getParameterName();
	}

	@Test
	public void testInteractorWhenHasNotInteraction() throws Exception {
		String name = "name";
		when(parameter.getMethodAnnotation(Interaction.class)).thenReturn(null);
		when(parameter.getParameterName()).thenReturn(name);
		assertThat(resolveParameter.getInteractor(), is(name));
		verify(parameter, times(1)).getMethodAnnotation(Interaction.class);
		verify(parameter, times(1)).getParameterName();
	}

	@Test
	public void testHasPresenter() throws Exception {
		String name = "name";
		when(parameter.getMethodAnnotation(Interaction.class)).thenReturn(interaction);
		when(interaction.presenter()).thenReturn(name);
		assertThat(resolveParameter.hasPresenter(), is(true));
		verify(parameter, times(2)).getMethodAnnotation(Interaction.class);
		verify(interaction, times(1)).presenter();
	}

	@Test
	public void testHasPresenterWhenHasNotInteraction() throws Exception {
		when(parameter.getMethodAnnotation(Interaction.class)).thenReturn(null);
		assertThat(resolveParameter.hasPresenter(), is(false));
		verify(parameter, times(1)).getMethodAnnotation(Interaction.class);
		verify(interaction, never()).presenter();
	}

	@Test
	public void testHasPresenterWhenInteractionPresenterIsEmpty() throws Exception {
		when(parameter.getMethodAnnotation(Interaction.class)).thenReturn(interaction);
		when(interaction.presenter()).thenReturn(" ");
		assertThat(resolveParameter.hasPresenter(), is(false));
		verify(parameter, times(2)).getMethodAnnotation(Interaction.class);
		verify(interaction, times(1)).presenter();
	}

	@Test
	public void testPresenter() throws Exception {
		String name = "name";
		when(parameter.getMethodAnnotation(Interaction.class)).thenReturn(interaction);
		when(interaction.presenter()).thenReturn(name);
		assertThat(resolveParameter.getPresenter(), is(name));
		verify(parameter, times(1)).getMethodAnnotation(Interaction.class);
		verify(interaction, times(1)).presenter();
	}

	@Test
	public void testGetModel() throws Exception {
		when(mavContainer.getModel()).thenReturn(modelMap);
		assertThat(resolveParameter.getModel(), is(modelMap));
		verify(mavContainer, times(1)).getModel();
	}

	@Test
	public void testEquals() throws Exception {
		assertThat(resolveParameter.equals(null), is(false));
		assertThat(resolveParameter.equals(resolveParameter), is(true));
		assertThat(resolveParameter.equals(parameter), is(false));
		assertThat(resolveParameter.equals(ResolveParameter.of(parameter, mock(ModelAndViewContainer.class))), is(false));
		assertThat(resolveParameter.equals(ResolveParameter.of(mock(MethodParameter.class), mock(ModelAndViewContainer.class))), is(false));
		assertThat(resolveParameter.equals(ResolveParameter.of(parameter, mavContainer)), is(true));
	}

	@Test
	public void testHasCode() throws Exception {
		assertThat(resolveParameter.hashCode(), is(Objects.hashCode(parameter, mavContainer)));
	}
}

