package org.hoteia.qalingo.core.web.mvc.controller.openid;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.web.mvc.controller.AbstractFrontofficeQalingoController;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.VelocityLayoutViewResolver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

/**
 * 
 */
@Controller("xrdsController")
public class XrdsController extends AbstractFrontofficeQalingoController {

	@Resource(name="viewResolver")
	protected VelocityLayoutViewResolver viewResolver;

	@RequestMapping(FoUrls.XRDS_URL)
	public ModelAndView displayXRDS(final HttpServletRequest request, final HttpServletResponse response, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.XRDS.getVelocityPage());
		String openIdCallBackURL = urlService.buildOpenIdCallBackUrl(requestUtil.getRequestData(request));
		String returnToURL = urlService.buildAbsoluteUrl(requestUtil.getRequestData(request), openIdCallBackURL);
		
		model.addAttribute("returnToURL", returnToURL);
		
		response.setContentType("application/xrds xml");
		
        return modelAndView;
	}
	
}