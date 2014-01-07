/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.dao;

import java.util.List;

import org.hoteia.qalingo.core.domain.EngineSetting;
import org.hoteia.qalingo.core.domain.EngineSettingValue;

public interface EngineSettingDao {

    // Engine Setting
    EngineSetting getEngineSettingById(Long id);

    EngineSetting getEngineSettingByCode(String code);

    List<EngineSetting> findEngineSettings();

    EngineSetting saveEngineSetting(EngineSetting engineSetting);

    void deleteEngineSetting(EngineSetting engineSetting);

    // Engine Setting Value
    EngineSettingValue getEngineSettingValueById(Long id);

    EngineSettingValue saveOrUpdateEngineSettingValue(EngineSettingValue engineSettingValue);

    void deleteEngineSettingValue(EngineSettingValue engineSettingValue);
}
