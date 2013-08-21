package fr.hoteia.qalingo.web.mvc.controller.openid;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.core.web.servlet.VelocityLayoutViewResolver;
import fr.hoteia.qalingo.web.mvc.controller.AbstractFrontofficeQalingoController;

/**
 * 
 */
@Controller("xrdsController")
public class XrdsController extends AbstractFrontofficeQalingoController {

	@Resource(name="viewResolver")
	protected VelocityLayoutViewResolver viewResolver;

	@RequestMapping("/xrds.html*")
	public ModelAndView displayXRDS(final HttpServletRequest request, final HttpServletResponse response, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "");
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		
		String openIdCallBackURL = urlService.buildOpenIdCallBackUrl( currentMarketArea);
		String contextValue = requestUtil.getCurrentContextNameValue(request);
		String returnToURL = urlService.buildAbsoluteUrl( currentMarketArea, contextValue, openIdCallBackURL);
		
		model.addAttribute("returnToURL", returnToURL);
		
		response.setContentType("application/xrds xml");
		
        return modelAndView;
	}
	
}