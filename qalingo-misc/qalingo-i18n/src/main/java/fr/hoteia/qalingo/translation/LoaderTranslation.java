/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.translation;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Denis Gosset (denis.gosset@hoteia.com)
 */
public class LoaderTranslation {

	private static final Logger LOG = LoggerFactory.getLogger(LoaderTranslationUtil.class);

	public static final String ENCODAGE =  Constants.UTF8;
	
    /**
	 *
	 */
    public LoaderTranslation(){
    }
    
    public void finalize(){
    }
    
	/**
	 *
	 */
	public static void main(String[] args) throws IOException {
		
		LOG.info("LoaderTranslation.main() : Start...");
		
		String currentPath = args[0];
		String folderWWW = args[1];
		String folderOutput = currentPath + "/target/output/";
		
		LoaderTranslation loaderTranslation = null;
		if( loaderTranslation == null ){
			loaderTranslation = new LoaderTranslation();
		}
		
		String folderOutputPath = LoaderTranslationUtil.getFolderPath(folderOutput);
		String folderWWWPath = LoaderTranslationUtil.getFolderPath(folderWWW);
		
		String project = new String(Constants.PROJECT_WWW);
		InputStream myxls = new FileInputStream(currentPath + "/src/main/resources/input/" + Constants.QALINGO_TRANSLATION_XLS_FILE);
		HSSFWorkbook wb = new HSSFWorkbook(myxls);
		
		LoaderTranslationUtil.buildMessagesCommonProperties(folderOutputPath, project, wb);
		LoaderTranslationUtil.buildMessagesFrontOfficeProperties(folderOutputPath, project, wb);
		LoaderTranslationUtil.buildMessagesBackOfficeProperties(folderOutputPath, project, wb);
		LoaderTranslationUtil.buildMessagesEmailProperties(folderOutputPath, project, wb);
		LoaderTranslationUtil.buildCountriesProperties(folderOutputPath, project, wb);
		LoaderTranslationUtil.buildLanguagesProperties(folderOutputPath, project, wb);
		LoaderTranslationUtil.buildTitlesProperties(folderOutputPath, project, wb);

		LoaderTranslationUtil.copyPropertiesFiles(folderOutputPath, folderWWWPath, project);

	}
	
}
