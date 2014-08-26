/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service;

import org.hoteia.qalingo.core.dao.GroupRoleDao;
import org.hoteia.qalingo.core.domain.CustomerGroup;
import org.hoteia.qalingo.core.domain.UserGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("groupRoleService")
@Transactional
public class GroupRoleService {

    @Autowired
    private GroupRoleDao groupRoleDao;

    // CUSTOMER GROUP
    
    public CustomerGroup getCustomerGroupById(final Long customerGroupId, Object... params) {
        return groupRoleDao.getCustomerGroupById(customerGroupId, params);
    }

    public CustomerGroup getCustomerGroupById(final String rawCustomerGroupId, Object... params) {
        long customerGroupId = -1;
        try {
            customerGroupId = Long.parseLong(rawCustomerGroupId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getCustomerGroupById(customerGroupId, params);
    }

    public CustomerGroup getCustomerGroupByCode(final String code, Object... params) {
        return groupRoleDao.getCustomerGroupByCode(code, params);
    }

    public void saveOrUpdateCustomerGroup(final CustomerGroup customerGroup) {
        groupRoleDao.saveOrUpdateCustomerGroup(customerGroup);
    }

    public void deleteCustomerGroup(final CustomerGroup customerGroup) {
        groupRoleDao.deleteCustomerGroup(customerGroup);
    }

    // USER GROUP
    
    public UserGroup getUserGroupById(final Long userGroupId, Object... params) {
        return groupRoleDao.getUserGroupById(userGroupId, params);
    }

    public UserGroup getUserGroupById(final String rawUserGroupId, Object... params) {
        long userGroupId = -1;
        try {
            userGroupId = Long.parseLong(rawUserGroupId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getUserGroupById(userGroupId, params);
    }

    public UserGroup getUserGroupByCode(final String code, Object... params) {
        return groupRoleDao.getUserGroupByCode(code, params);
    }

    public void saveOrUpdateUserGroup(final UserGroup userGroup) {
        groupRoleDao.saveOrUpdateUserGroup(userGroup);
    }

    public void deleteUserGroup(final UserGroup userGroup) {
        groupRoleDao.deleteUserGroup(userGroup);
    }
    
}