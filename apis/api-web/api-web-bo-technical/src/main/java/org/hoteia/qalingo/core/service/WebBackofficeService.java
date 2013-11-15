/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service;

import org.hoteia.qalingo.core.domain.EngineSettingValue;
import org.hoteia.qalingo.core.domain.User;
import org.hoteia.qalingo.web.mvc.form.EngineSettingValueForm;
import org.hoteia.qalingo.web.mvc.form.UserForm;


public interface WebBackofficeService {

	void updateUser(User user, UserForm userForm);
	
	void updateEngineSettingValue(EngineSettingValue engineSettingValue, EngineSettingValueForm engineSettingValueForm);
}
