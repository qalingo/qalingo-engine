--
-- Most of the code in the Qalingo project is copyrighted Hoteia and licensed
-- under the Apache License Version 2.0 (release version ${license.version})
--         http://www.apache.org/licenses/LICENSE-2.0
--
--                   Copyright (c) Hoteia, 2012-2013
-- http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
--
--

CREATE DATABASE qalingo CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE USER 'qalingo' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON qalingo.* TO 'qalingo'@'%' IDENTIFIED BY 'password' WITH GRANT OPTION; 
GRANT ALL PRIVILEGES ON qalingo.* TO 'qalingo'@'localhost' IDENTIFIED BY 'password' WITH GRANT OPTION; 
FLUSH PRIVILEGES;
