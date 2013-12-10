package org.hoteia.qalingo.core.web.mvc.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.domain.User;
import org.hoteia.qalingo.core.domain.enumtype.BoUrls;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.web.mvc.controller.AbstractBackofficeQalingoController;
import org.hoteia.qalingo.core.web.mvc.form.UserForm;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * User details
 */
@Controller("userController")
public class UserController extends AbstractBackofficeQalingoController {

    @RequestMapping(value = BoUrls.PERSONAL_DETAILS_URL, method = RequestMethod.GET)
    public ModelAndView personalDetails(final HttpServletRequest request, final Model model) throws Exception {
        ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.PERSONAL_DETAILS.getVelocityPage());
        
        // User is already set by the abstract

        return modelAndView;
    }
    
    @RequestMapping(value = BoUrls.PERSONAL_EDIT_URL, method = RequestMethod.GET)
    public ModelAndView displayPersonalEdit(final HttpServletRequest request, final Model model, @ModelAttribute(ModelConstants.USER_FORM) UserForm userForm) throws Exception {
        ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.PERSONAL_EDIT.getVelocityPage());
//        final RequestData requestData = requestUtil.getRequestData(request);
//        final User currentUser = requestData.getUser();
        
//        if(userForm == null 
//                || userForm.equals(new UserForm())){
//            userForm = backofficeFormFactory.buildUserForm(requestData, currentUser);
//            model.addAttribute(ModelConstants.USER_FORM, userForm);
//        }
        
        return modelAndView;
    }
    
    @RequestMapping(value = BoUrls.PERSONAL_EDIT_URL, method = RequestMethod.POST)
    public ModelAndView submitPersonalEdit(final HttpServletRequest request, @Valid @ModelAttribute(ModelConstants.USER_FORM) UserForm userForm,
                                BindingResult result, final Model model) throws Exception {
        
        if (result.hasErrors()) {
            return displayPersonalEdit(request, model, userForm);
        }
        
        final String newEmail = userForm.getEmail();
        final User currentUser = requestUtil.getCurrentUser(request);
        final User checkUser = userService.getUserByLoginOrEmail(newEmail);
        if(checkUser != null
                && !currentUser.getEmail().equalsIgnoreCase(newEmail)) {
            final String forgottenPasswordUrl = backofficeUrlService.generateUrl(BoUrls.FORGOTTEN_PASSWORD, requestUtil.getRequestData(request));
            final Object[] objects = {forgottenPasswordUrl};
            result.rejectValue("email", "fo.user.error_form_create_account_account_already_exist", objects,"This email user account already exist! Go on this <a href=\"${0}\" alt=\"\">page</a> to get a new password.");
        }

        // Update the user
//        webBackofficeService.updateCurrentUser(request, requestUtil.getRequestData(request), currentMarket, currentMarketArea, userForm);
        webBackofficeService.updateUser(checkUser, userForm);
        
        final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.PERSONAL_DETAILS, requestUtil.getRequestData(request));
        return new ModelAndView(new RedirectView(urlRedirect));
    }
    
    /**
     * 
     */
    @ModelAttribute(ModelConstants.USER_FORM)
    protected UserForm initRetailerForm(final HttpServletRequest request, final Model model) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final User currentUser = requestData.getUser();
        return backofficeFormFactory.buildUserForm(requestData, currentUser);
    }

}