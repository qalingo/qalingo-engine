qalingo-b2c-engine
==================

Qalingo : B2C Engine (opensource Java eCommerce)

To start to use Qalingo, contribut, or use it :

0) over windows, you will find a quick start here :
https://github.com/qalingo/qalingo-b2c-windows-quick-start-env

0) over Linux, you will find a quick start here :
https://github.com/qalingo/qalingo-b2c-linux-quick-start-env

1) Check your Maven configuration to add nexus.hoteia.com
<QUICK_START>/qalingo/tools/Maven_m2_conf/settings.xml
Maven profile "dev-qalingo" is in here, with all the specific configuration like JDBC, SMTP etc

2) Clone git projects like :
* qalingo-b2c-engine : the core
* qalingo-b2c-i18n : wordins with *.properties
* qalingo-b2c-shared : resources like jdbc and email configuration, etc etc

* qalingo-b2c-web-classic-fo-bo : the basic webapps : backoffice and frontoffice

3) change your hosts :
127.0.0.1  fo-mcommerce.dev.qalingo.com fo-prehome.dev.qalingo.com rest-remote.dev.qalingo.com
127.0.0.1  bo-business.dev.qalingo.com bo-reporting.dev.qalingo.com bo-technical.dev.qalingo.com

4) build a "qalingo" database from files or use Maven goal : 
qalingo-b2c-engine\qalingo-misc\qalingo-sql\src\main\resources\sql\mysql

5) configure your Apache/Tomcat servers, with configuration from step 0

6) Use the pom.xml from <QUICK_START>/qalingo/workspace/pom.xml to plug module you need.
You wil be able to run a maven clean install -P dev-qalingo and build all the big projects

7) Use SH or BAT to start your servers

When you have start with Qalingo, you can download zip to start your own eCommerce project, which will will build on and extend Qalingo.
You create your own Maven project and :
* put a zip/copy/past qalingo-b2c-shared : don't clone, you don't need to sync Qalingo
* put a zip/copy/past qalingo-b2c-i18n : don't clone, you don't need to sync Qalingo
* put a zip/copy/past assets from qalingo-b2c-web-classic-fo-bo : don't clone, you don't need to sync Qalingo
* put a zip/copy/past backoffice/frontoffice webapps from qalingo-b2c-web-classic-fo-bo : don't clone, you don't need to sync Qalingo
* put a zip/copy/past qalingo-b2c-web-batch : don't clone, you don't need to sync Qalingo

After that, in your own project you will write and build your own Jars and add it with Maven Qalingo dependencies.
Example in your root pom : 

	<dependencyManagement>
		<dependencies>
			<!-- Qalingo libs -->
			<dependency>
				<groupId>fr.hoteia.qalingo</groupId>
				<artifactId>qalingo-api-core-common</artifactId>
				<version>${qalingo.engine.version}</version>
			</dependency>
			<dependency>
				<groupId>fr.hoteia.qalingo</groupId>
				<artifactId>qalingo-i18n-properties</artifactId>
				<version>${qalingo.engine.version}</version>
			</dependency>
      ...
      
			<!-- Your libs -->
			<dependency>
				<groupId>com.MyCompany.MyProject</groupId>
				<artifactId>MyProject-common-ext</artifactId><!-- common-ext is an example, name it as you which-->
				<version>${MyProject.engine.version}</version>
			</dependency>
			<dependency>
				<groupId>com.MyCompany.MyProject</groupId>
				<artifactId>MyProject-i18n-properties</artifactId>
				<version>${MyProject.engine.version}</version>
			</dependency>

      ...
      
		</dependencies>
	</dependencyManagement>


* If you need to override a class, create a new bean like ExtViewBeanFactoryImpl which extends ViewBeanFactoryImpl and add a bean in your Spring configuration context.
	<bean id="viewBeanFactory" 
		  class="fr.hoteia.opentailor.web.mvc.factory.impl.ExtViewBeanFactoryImpl" />
		  
* If you can't -> create an issue, or make a pull request on Qalingo :D

* If you need something more -> create an issue, or make a pull request on Qalingo :D

* If you can do more -> create an issue, or make a pull request on Qalingo :D

