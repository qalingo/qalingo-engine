/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.hoteia.qalingo.core.dao.GroupRoleDao;
import org.hoteia.qalingo.core.domain.CustomerGroup;
import org.hoteia.qalingo.core.service.GroupRoleService;

@Service("groupRoleService")
@Transactional
public class GroupRoleServiceImpl implements GroupRoleService {

    @Autowired
    private GroupRoleDao groupRoleDao;

    public CustomerGroup getCustomerGroupById(final Long customerGroupId) {
        return groupRoleDao.getCustomerGroupById(customerGroupId);
    }

    public CustomerGroup getCustomerGroupById(final String rawCustomerGroupId) {
        long customerGroupId = -1;
        try {
            customerGroupId = Long.parseLong(rawCustomerGroupId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getCustomerGroupById(customerGroupId);
    }

    public CustomerGroup getCustomerGroupByCode(final String code) {
        return groupRoleDao.getCustomerGroupByCode(code);
    }

    public void saveOrUpdateCustomerGroup(final CustomerGroup customerGroup) {
        groupRoleDao.saveOrUpdateCustomerGroup(customerGroup);
    }

    public void deleteCustomerGroup(final CustomerGroup customerGroup) {
        groupRoleDao.deleteCustomerGroup(customerGroup);
    }

}