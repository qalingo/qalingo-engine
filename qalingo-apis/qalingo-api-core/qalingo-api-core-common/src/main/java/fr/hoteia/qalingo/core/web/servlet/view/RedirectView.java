package fr.hoteia.qalingo.core.web.servlet.view;

public class RedirectView extends org.springframework.web.servlet.view.RedirectView {

	public RedirectView(String url) {
		super(url);
		setExposeModelAttributes(false);
    }
}
