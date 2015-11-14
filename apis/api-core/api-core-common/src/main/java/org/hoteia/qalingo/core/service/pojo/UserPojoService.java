/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service.pojo;

import java.util.List;

import org.dozer.Mapper;
import org.hoteia.qalingo.core.domain.User;
import org.hoteia.qalingo.core.pojo.user.UserPojo;
import org.hoteia.qalingo.core.pojo.util.mapper.PojoUtil;
import org.hoteia.qalingo.core.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userPojoService")
@Transactional(readOnly = true)
public class UserPojoService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected Mapper dozerBeanMapper;
    
    @Autowired
    protected UserService userService;

    public List<UserPojo> getAllUsers() {
        List<User> users = userService.findUsers();
        logger.debug("Found {} users", users.size());
        return PojoUtil.mapAll(dozerBeanMapper, users, UserPojo.class);
    }

    public UserPojo getUserById(final String id) {
        User user = userService.getUserById(id);
        logger.debug("Found user {} for id {}", user, id);
        return user == null ? null : dozerBeanMapper.map(user, UserPojo.class);
    }

    @Transactional
    public void saveOrUpdate(final UserPojo userJsonPojo) throws Exception {
        User user = dozerBeanMapper.map(userJsonPojo, User.class);
        logger.info("Saving user {}", user);
        userService.saveOrUpdateUser(user);
    }

}