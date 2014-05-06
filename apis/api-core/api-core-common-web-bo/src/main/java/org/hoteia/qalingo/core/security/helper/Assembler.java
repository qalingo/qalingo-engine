/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.security.helper;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.CustomerRole;
import org.hoteia.qalingo.core.domain.User;
import org.hoteia.qalingo.core.domain.UserRole;

@Service("assembler")
public class Assembler {
	
	@Transactional(readOnly = true)
	public org.springframework.security.core.userdetails.User buildUserFromUserEntity(User user) {
		
		String username = user.getLogin();
		String password = user.getPassword();
		boolean enabled = user.isActive();
		boolean accountNonExpired = user.isActive();
		boolean credentialsNonExpired = user.isActive();
		boolean accountNonLocked = user.isActive();
		
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (UserRole role : user.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		org.springframework.security.core.userdetails.User userSecurity = new org.springframework.security.core.userdetails.User(username, password, enabled, 
												accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		return userSecurity;
	}
	
	@Transactional(readOnly = true)
	public org.springframework.security.core.userdetails.User buildUserFromCustomerEntity(Customer customer) {
		
		String username = customer.getLogin();
		String password = customer.getPassword();
		boolean enabled = customer.isActive();
		boolean accountNonExpired = customer.isActive();
		boolean credentialsNonExpired = customer.isActive();
		boolean accountNonLocked = customer.isActive();
		
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (CustomerRole role : customer.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		org.springframework.security.core.userdetails.User userSecurity = new org.springframework.security.core.userdetails.User(username, password, enabled, 
												accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		return userSecurity;
	}
}
