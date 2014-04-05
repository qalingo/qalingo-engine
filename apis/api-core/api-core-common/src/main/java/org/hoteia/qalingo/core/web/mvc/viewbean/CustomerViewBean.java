/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.viewbean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CustomerViewBean extends AbstractViewBean implements Serializable {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 6264101125517957897L;

    public static String SCREEN_NAME = "screenName";

    private Long id;
    private int version;
    private String code;
    private String login;
    private String title;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String defaultLocale;
    private boolean active;

    private String avatarImg;

    private String lastConnectionDate;

    private Map<String, ValueBean> customerAttributes = new HashMap<String, ValueBean>();

    private String detailsUrl;
    private String editUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDefaultLocale() {
        return defaultLocale;
    }

    public void setDefaultLocale(String defaultLocale) {
        this.defaultLocale = defaultLocale;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getAvatarImg() {
        return avatarImg;
    }

    public void setAvatarImg(String avatarImg) {
        this.avatarImg = avatarImg;
    }

    public String getLastConnectionDate() {
        return lastConnectionDate;
    }

    public void setLastConnectionDate(String lastConnectionDate) {
        this.lastConnectionDate = lastConnectionDate;
    }

    public Map<String, ValueBean> getCustomerAttributes() {
        return customerAttributes;
    }

    public void setCustomerAttributes(Map<String, ValueBean> customerAttributes) {
        this.customerAttributes = customerAttributes;
    }

    public String getScreenName() {
        String screenNameValue = null;
        if (customerAttributes != null && customerAttributes.size() > 0) {
            ValueBean screenName = customerAttributes.get(SCREEN_NAME);
            if (screenName != null) {
                screenNameValue = screenName.getValue();
            }
        }
        if (screenNameValue == null) {
            screenNameValue = getLastname() + " " + getFirstname();
        }
        return screenNameValue;
    }

    public String getDetailsUrl() {
        return detailsUrl;
    }

    public void setDetailsUrl(String detailsUrl) {
        this.detailsUrl = detailsUrl;
    }

    public String getEditUrl() {
        return editUrl;
    }

    public void setEditUrl(String editUrl) {
        this.editUrl = editUrl;
    }
    
}