package org.hoteia.qalingo.web.mvc.controller.user;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.User;
import org.hoteia.qalingo.core.domain.enumtype.BoUrls;
import org.hoteia.qalingo.core.i18n.BoMessageKey;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.web.mvc.controller.AbstractBackofficeQalingoController;
import org.hoteia.qalingo.core.web.mvc.form.UserForm;
import org.hoteia.qalingo.core.web.mvc.viewbean.UserViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.springframework.beans.support.PagedListHolder;
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

    public static final String SESSION_KEY = "PagedListHolder_Users";
    
    @RequestMapping(value = BoUrls.USER_LIST_URL, method = RequestMethod.GET)
    public ModelAndView userList(final HttpServletRequest request, final Model model) throws Exception {
        ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.USER_LIST.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        final MarketArea marketArea = requestData.getMarketArea();
        final Locale locale = requestData.getLocale();
        
        final String contentText = getSpecificMessage(ScopeWebMessage.USER, BoMessageKey.MAIN_CONTENT_TEXT, locale);
        modelAndView.addObject(ModelConstants.CONTENT_TEXT, contentText);
        
        displayList(request, model, requestData);
        
        Object[] params = {marketArea.getName() + " (" + marketArea.getCode() + ")"};
        initPageTitleAndMainContentTitle(request, modelAndView,  BoUrls.USER_LIST.getKey() + "", params);

        return modelAndView;
    }
    
    @RequestMapping(value = BoUrls.USER_DETAILS_URL, method = RequestMethod.GET)
    public ModelAndView userDetails(final HttpServletRequest request, final Model model) throws Exception {
        ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.USER_DETAILS.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        
        final String userCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_USER_CODE);
        final User user = userService.getUserByCode(userCode);
        
        UserViewBean userViewBean = backofficeViewBeanFactory.buildViewBeanUser(requestData, user);

        if(userViewBean == null){
            final String url = requestUtil.getLastRequestUrl(request);
            return new ModelAndView(new RedirectView(url));
        }
        
        model.addAttribute(ModelConstants.URL_BACK, backofficeUrlService.generateUrl(BoUrls.USER_LIST, requestData));
        
        request.setAttribute(ModelConstants.USER_VIEW_BEAN, userViewBean);
        
        Object[] params = {user.getLastname() + " " + user.getFirstname() + " (" + user.getLogin() + ")"};
        initPageTitleAndMainContentTitle(request, modelAndView,  BoUrls.USER_DETAILS.getKey(), params);

        return modelAndView;
    }
    
    @RequestMapping(value = BoUrls.USER_EDIT_URL, method = RequestMethod.GET)
    public ModelAndView userEdit(final HttpServletRequest request, final Model model, @ModelAttribute(ModelConstants.USER_FORM) UserForm userForm) throws Exception {
        ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.USER_EDIT.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        
        final String userCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_USER_CODE);
        final User user = userService.getUserByCode(userCode);
        
        UserViewBean userViewBean = backofficeViewBeanFactory.buildViewBeanUser(requestData, user);
        
        if(userViewBean == null){
            final String url = requestUtil.getLastRequestUrl(request);
            return new ModelAndView(new RedirectView(url));
        }
        
        model.addAttribute(ModelConstants.URL_BACK, backofficeUrlService.generateUrl(BoUrls.USER_DETAILS, requestData, user));
        
        request.setAttribute(ModelConstants.USER_VIEW_BEAN, userViewBean);
        
        Object[] params = {user.getLastname() + " " + user.getFirstname() + " (" + user.getLogin() + ")"};
        initPageTitleAndMainContentTitle(request, modelAndView,  BoUrls.USER_DETAILS.getKey(), params);

        return modelAndView;
    }
    
    @RequestMapping(value = BoUrls.USER_EDIT_URL, method = RequestMethod.POST)
    public ModelAndView submitUserEdit(final HttpServletRequest request, @Valid @ModelAttribute(ModelConstants.USER_FORM) UserForm userForm,
                                BindingResult result, final Model model) throws Exception {
        
        if (result.hasErrors()) {
            return userEdit(request, model, userForm);
        }
        
        final String userCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_USER_CODE);
        final User user = userService.getUserByCode(userCode);

        // Update the user
        webBackofficeService.createOrUpdateUser(user, userForm);
        
        final String urlRedirect = backofficeUrlService.generateUrl(BoUrls.USER_DETAILS, requestUtil.getRequestData(request), user);
        return new ModelAndView(new RedirectView(urlRedirect));
    }
    
    /**
     * 
     */
    @ModelAttribute(ModelConstants.USER_FORM)
    protected UserForm initUserForm(final HttpServletRequest request, final Model model) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        
        final String userCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_USER_CODE);
        if(StringUtils.isNotEmpty(userCode)){
            final User userEdit = userService.getUserByCode(userCode);
            return backofficeFormFactory.buildUserForm(requestData, userEdit);
        }

        return backofficeFormFactory.buildUserForm(requestData, null);
    }
    
    protected void displayList(final HttpServletRequest request, final Model model, final RequestData requestData) throws Exception {
        String url = request.getRequestURI();
        String page = request.getParameter(Constants.PAGINATION_PAGE_PARAMETER);
        
        PagedListHolder<UserViewBean> userViewBeanPagedListHolder = new PagedListHolder<UserViewBean>();

        if(StringUtils.isEmpty(page)){
            userViewBeanPagedListHolder = initList(request, SESSION_KEY, requestData);
            
        } else {
            userViewBeanPagedListHolder = (PagedListHolder) request.getSession().getAttribute(SESSION_KEY); 
            if (userViewBeanPagedListHolder == null) { 
                userViewBeanPagedListHolder = initList(request, SESSION_KEY, requestData);
            }
            int pageTarget = new Integer(page).intValue() - 1;
            int pageCurrent = userViewBeanPagedListHolder.getPage();
            if (pageCurrent < pageTarget) { 
                for (int i = pageCurrent; i < pageTarget; i++) {
                    userViewBeanPagedListHolder.nextPage(); 
                }
            } else if (pageCurrent > pageTarget) { 
                for (int i = pageTarget; i < pageCurrent; i++) {
                    userViewBeanPagedListHolder.previousPage(); 
                }
            } 
        }
        model.addAttribute(Constants.PAGINATION_PAGE_URL, url);
        model.addAttribute(Constants.PAGINATION_PAGE_PAGED_LIST_HOLDER, userViewBeanPagedListHolder);
    }
    
    protected PagedListHolder<UserViewBean> initList(final HttpServletRequest request, String sessionKey, final RequestData requestData) throws Exception {
        final MarketArea marketArea = requestData.getMarketArea();
        List<User> users = userService.findUsers();

        PagedListHolder<UserViewBean> userViewBeanPagedListHolder = new PagedListHolder<UserViewBean>();
        
        final List<UserViewBean> userViewBeans = new ArrayList<UserViewBean>();
        for (Iterator<User> iterator = users.iterator(); iterator.hasNext();) {
            User userIt = (User) iterator.next();
            userViewBeans.add(backofficeViewBeanFactory.buildViewBeanUser(requestData, userIt));
        }
        userViewBeanPagedListHolder = new PagedListHolder<UserViewBean>(userViewBeans);
        userViewBeanPagedListHolder.setPageSize(Constants.PAGE_SIZE);
        request.getSession().setAttribute(sessionKey, userViewBeanPagedListHolder); 
        
        return userViewBeanPagedListHolder;
    }
    
}