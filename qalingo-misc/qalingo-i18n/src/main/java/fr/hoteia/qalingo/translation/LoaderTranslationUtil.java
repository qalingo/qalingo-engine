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

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
*
* <p>
* <a href="LoaderTranslationUtil.java.html"><i>View Source</i></a>
* </p>
*
* @author Denis Gosset (http://www.hoteia.com)
*/
public class LoaderTranslationUtil {

	private static final Logger LOG = LoggerFactory.getLogger(LoaderTranslationUtil.class);

	private static Format formatterFolder = new SimpleDateFormat(Constants.FORMATTER_DATE_TO_FOLDER);

	/**
	 *
	 */
	public LoaderTranslationUtil() {
	}

	public void finalize() {
	}

	public static final void buildMessagesFrontOfficeProperties(String currentPath, String project, HSSFWorkbook wb) {
		try {
			String newPath = currentPath + project + Constants.PROPERTIES_PATH;
			File folderProject = new File(newPath);
			if (!folderProject.exists()){
				folderProject.mkdirs();
			}

			LOG.info(newPath + "/" + "messages.properties");
			String prefixFileName = "qalingo-fo-messages";
			File fileDefault = new File(newPath + "/" + prefixFileName + ".properties");
			File fileEN = new File(newPath + "/" + prefixFileName + "_en.properties");
			File fileFR = new File(newPath + "/" + prefixFileName + "_fr.properties");
			File fileDE = new File(newPath + "/" + prefixFileName + "_de.properties");
			File fileES = new File(newPath + "/" + prefixFileName + "_es.properties");
			File fileIT = new File(newPath + "/" + prefixFileName + "_it.properties");
			File fileNL = new File(newPath + "/" + prefixFileName + "_nl.properties");
			File filePT = new File(newPath + "/" + prefixFileName + "_pt.properties");

			DataOutputStream writerDefault = new DataOutputStream(new FileOutputStream(fileDefault));
			DataOutputStream writerEN = new DataOutputStream(new FileOutputStream(fileEN));
			DataOutputStream writerFR = new DataOutputStream(new FileOutputStream(fileFR));
			DataOutputStream writerDE = new DataOutputStream(new FileOutputStream(fileDE));
			DataOutputStream writerES = new DataOutputStream(new FileOutputStream(fileES));
			DataOutputStream writerIT = new DataOutputStream(new FileOutputStream(fileIT));
			DataOutputStream writerNL = new DataOutputStream(new FileOutputStream(fileNL));
			DataOutputStream writerPT = new DataOutputStream(new FileOutputStream(filePT));

			HSSFSheet sheetApplicationRessourcesFromCore = wb.getSheet(Constants.SHEET_FRONTOFFICE_MESSAGES_RESSOURCES);
			buildPropertiesWriters(sheetApplicationRessourcesFromCore, writerDefault, writerEN, writerFR, writerDE, writerES, writerIT, writerNL,	writerPT, "");

			writerDefault.close();
			writerEN.close();
			writerFR.close();
			writerDE.close();
			writerES.close();
			writerIT.close();
			writerNL.close();
			writerPT.close();

		} catch (Exception e) {
			LOG.info("Exception", e);
		}
	}
	
	public static final void buildMessagesBackOfficeProperties(String currentPath, String project, HSSFWorkbook wb) {
		try {
			String newPath = currentPath + project + Constants.PROPERTIES_PATH;
			File folderProject = new File(newPath);
			if (!folderProject.exists()){
				folderProject.mkdirs();
			}

			LOG.info(newPath + "/" + "messages.properties");
			String prefixFileName = "qalingo-bo-messages";
			File fileDefault = new File(newPath + "/" + prefixFileName + ".properties");
			File fileEN = new File(newPath + "/" + prefixFileName + "_en.properties");
			File fileFR = new File(newPath + "/" + prefixFileName + "_fr.properties");
			File fileDE = new File(newPath + "/" + prefixFileName + "_de.properties");
			File fileES = new File(newPath + "/" + prefixFileName + "_es.properties");
			File fileIT = new File(newPath + "/" + prefixFileName + "_it.properties");
			File fileNL = new File(newPath + "/" + prefixFileName + "_nl.properties");
			File filePT = new File(newPath + "/" + prefixFileName + "_pt.properties");

			DataOutputStream writerDefault = new DataOutputStream(new FileOutputStream(fileDefault));
			DataOutputStream writerEN = new DataOutputStream(new FileOutputStream(fileEN));
			DataOutputStream writerFR = new DataOutputStream(new FileOutputStream(fileFR));
			DataOutputStream writerDE = new DataOutputStream(new FileOutputStream(fileDE));
			DataOutputStream writerES = new DataOutputStream(new FileOutputStream(fileES));
			DataOutputStream writerIT = new DataOutputStream(new FileOutputStream(fileIT));
			DataOutputStream writerNL = new DataOutputStream(new FileOutputStream(fileNL));
			DataOutputStream writerPT = new DataOutputStream(new FileOutputStream(filePT));

			HSSFSheet sheetApplicationRessourcesFromCore = wb.getSheet(Constants.SHEET_BACKOFFICE_MESSAGES_RESSOURCES);
			buildPropertiesWriters(sheetApplicationRessourcesFromCore, writerDefault, writerEN, writerFR, writerDE, writerES, writerIT, writerNL,	writerPT, "");

			writerDefault.close();
			writerEN.close();
			writerFR.close();
			writerDE.close();
			writerES.close();
			writerIT.close();
			writerNL.close();
			writerPT.close();

		} catch (Exception e) {
			LOG.info("Exception", e);
		}
	}
	
	public static final void buildMessagesEmailProperties(String currentPath, String project, HSSFWorkbook wb) {
		try {
			String newPath = currentPath + project + Constants.PROPERTIES_PATH;
			File folderProject = new File(newPath);
			if (!folderProject.exists()){
				folderProject.mkdirs();
			}

			LOG.info(newPath + "/" + "messages.properties");
			String prefixFileName = "qalingo-email-messages";
			File fileDefault = new File(newPath + "/" + prefixFileName + ".properties");
			File fileEN = new File(newPath + "/" + prefixFileName + "_en.properties");
			File fileFR = new File(newPath + "/" + prefixFileName + "_fr.properties");
			File fileDE = new File(newPath + "/" + prefixFileName + "_de.properties");
			File fileES = new File(newPath + "/" + prefixFileName + "_es.properties");
			File fileIT = new File(newPath + "/" + prefixFileName + "_it.properties");
			File fileNL = new File(newPath + "/" + prefixFileName + "_nl.properties");
			File filePT = new File(newPath + "/" + prefixFileName + "_pt.properties");

			DataOutputStream writerDefault = new DataOutputStream(new FileOutputStream(fileDefault));
			DataOutputStream writerEN = new DataOutputStream(new FileOutputStream(fileEN));
			DataOutputStream writerFR = new DataOutputStream(new FileOutputStream(fileFR));
			DataOutputStream writerDE = new DataOutputStream(new FileOutputStream(fileDE));
			DataOutputStream writerES = new DataOutputStream(new FileOutputStream(fileES));
			DataOutputStream writerIT = new DataOutputStream(new FileOutputStream(fileIT));
			DataOutputStream writerNL = new DataOutputStream(new FileOutputStream(fileNL));
			DataOutputStream writerPT = new DataOutputStream(new FileOutputStream(filePT));

			HSSFSheet sheetApplicationRessourcesFromCore = wb.getSheet(Constants.SHEET_EMAIL_MESSAGES_RESSOURCES);
			buildPropertiesWriters(sheetApplicationRessourcesFromCore, writerDefault, writerEN, writerFR, writerDE, writerES, writerIT, writerNL,	writerPT, "");

			writerDefault.close();
			writerEN.close();
			writerFR.close();
			writerDE.close();
			writerES.close();
			writerIT.close();
			writerNL.close();
			writerPT.close();

		} catch (Exception e) {
			LOG.info("Exception", e);
		}
	}

	public static final void buildCountriesProperties(String currentPath, String project, HSSFWorkbook wb) {
		try {
			String newPath = currentPath + project + Constants.PROPERTIES_PATH;
			File folderProject = new File(newPath);
			if (!folderProject.exists()){
				folderProject.mkdirs();
			}

			String prefixFileName = "qalingo-countries";
			File fileDefault = new File(newPath + "/" + prefixFileName + ".properties");
			File fileEN = new File(newPath + "/" + prefixFileName + "_en.properties");
			File fileFR = new File(newPath + "/" + prefixFileName + "_fr.properties");
			File fileDE = new File(newPath + "/" + prefixFileName + "_de.properties");
			File fileES = new File(newPath + "/" + prefixFileName + "_es.properties");
			File fileIT = new File(newPath + "/" + prefixFileName + "_it.properties");
			File fileNL = new File(newPath + "/" + prefixFileName + "_nl.properties");
			File filePT = new File(newPath + "/" + prefixFileName + "_pt.properties");

			DataOutputStream writerDefault = new DataOutputStream(new FileOutputStream(fileDefault));
			DataOutputStream writerEN = new DataOutputStream(new FileOutputStream(fileEN));
			DataOutputStream writerFR = new DataOutputStream(new FileOutputStream(fileFR));
			DataOutputStream writerDE = new DataOutputStream(new FileOutputStream(fileDE));
			DataOutputStream writerES = new DataOutputStream(new FileOutputStream(fileES));
			DataOutputStream writerIT = new DataOutputStream(new FileOutputStream(fileIT));
			DataOutputStream writerNL = new DataOutputStream(new FileOutputStream(fileNL));
			DataOutputStream writerPT = new DataOutputStream(new FileOutputStream(filePT));

			HSSFSheet sheetApplicationRessourcesFromCore = wb.getSheet(Constants.SHEET_COUNTRIES_RESSOURCES);
			buildPropertiesWriters(sheetApplicationRessourcesFromCore, writerDefault, writerEN, writerFR, writerDE, writerES, writerIT, writerNL,
					writerPT, Constants.SHEET_COUNTRIES_RESSOURCES.toLowerCase() + ".");

			writerDefault.close();
			writerEN.close();
			writerFR.close();
			writerDE.close();
			writerES.close();
			writerIT.close();
			writerNL.close();
			writerPT.close();

		} catch (Exception e) {
			LOG.info("Exception", e);
		}
	}
	
	public static final void buildLanguagesProperties(String currentPath, String project, HSSFWorkbook wb) {
		try {
			String newPath = currentPath + project + Constants.PROPERTIES_PATH;
			File folderProject = new File(newPath);
			if (!folderProject.exists()){
				folderProject.mkdirs();
			}

			String prefixFileName = "qalingo-languages";
			File fileDefault = new File(newPath + "/" + prefixFileName + ".properties");
			File fileEN = new File(newPath + "/" + prefixFileName + "_en.properties");
			File fileFR = new File(newPath + "/" + prefixFileName + "_fr.properties");
			File fileDE = new File(newPath + "/" + prefixFileName + "_de.properties");
			File fileES = new File(newPath + "/" + prefixFileName + "_es.properties");
			File fileIT = new File(newPath + "/" + prefixFileName + "_it.properties");
			File fileNL = new File(newPath + "/" + prefixFileName + "_nl.properties");
			File filePT = new File(newPath + "/" + prefixFileName + "_pt.properties");

			DataOutputStream writerDefault = new DataOutputStream(new FileOutputStream(fileDefault));
			DataOutputStream writerEN = new DataOutputStream(new FileOutputStream(fileEN));
			DataOutputStream writerFR = new DataOutputStream(new FileOutputStream(fileFR));
			DataOutputStream writerDE = new DataOutputStream(new FileOutputStream(fileDE));
			DataOutputStream writerES = new DataOutputStream(new FileOutputStream(fileES));
			DataOutputStream writerIT = new DataOutputStream(new FileOutputStream(fileIT));
			DataOutputStream writerNL = new DataOutputStream(new FileOutputStream(fileNL));
			DataOutputStream writerPT = new DataOutputStream(new FileOutputStream(filePT));

			HSSFSheet sheetApplicationRessourcesFromCore = wb.getSheet(Constants.SHEET_LANGUAGES_RESSOURCES);
			buildPropertiesWriters(sheetApplicationRessourcesFromCore, writerDefault, writerEN, writerFR, writerDE, writerES, writerIT, writerNL,
					writerPT, Constants.SHEET_LANGUAGES_RESSOURCES.toLowerCase() + ".");

			writerDefault.close();
			writerEN.close();
			writerFR.close();
			writerDE.close();
			writerES.close();
			writerIT.close();
			writerNL.close();
			writerPT.close();

		} catch (Exception e) {
			LOG.info("Exception", e);
		}
	}
	
	public static final void buildTitlesProperties(String currentPath, String project, HSSFWorkbook wb) {
		try {
			String newPath = currentPath + project + Constants.PROPERTIES_PATH;
			File folderProject = new File(newPath);
			if (!folderProject.exists()){
				folderProject.mkdirs();
			}

			String prefixFileName = "qalingo-titles";
			File fileDefault = new File(newPath + "/" + prefixFileName + ".properties");
			File fileEN = new File(newPath + "/" + prefixFileName + "_en.properties");
			File fileFR = new File(newPath + "/" + prefixFileName + "_fr.properties");
			File fileDE = new File(newPath + "/" + prefixFileName + "_de.properties");
			File fileES = new File(newPath + "/" + prefixFileName + "_es.properties");
			File fileIT = new File(newPath + "/" + prefixFileName + "_it.properties");
			File fileNL = new File(newPath + "/" + prefixFileName + "_nl.properties");
			File filePT = new File(newPath + "/" + prefixFileName + "_pt.properties");

			DataOutputStream writerDefault = new DataOutputStream(new FileOutputStream(fileDefault));
			DataOutputStream writerEN = new DataOutputStream(new FileOutputStream(fileEN));
			DataOutputStream writerFR = new DataOutputStream(new FileOutputStream(fileFR));
			DataOutputStream writerDE = new DataOutputStream(new FileOutputStream(fileDE));
			DataOutputStream writerES = new DataOutputStream(new FileOutputStream(fileES));
			DataOutputStream writerIT = new DataOutputStream(new FileOutputStream(fileIT));
			DataOutputStream writerNL = new DataOutputStream(new FileOutputStream(fileNL));
			DataOutputStream writerPT = new DataOutputStream(new FileOutputStream(filePT));

			HSSFSheet sheetApplicationRessourcesFromCore = wb.getSheet(Constants.SHEET_TITLES_RESSOURCES);
			buildPropertiesWriters(sheetApplicationRessourcesFromCore, writerDefault, writerEN, writerFR, writerDE, writerES, writerIT, writerNL,
					writerPT, Constants.SHEET_TITLES_RESSOURCES.toLowerCase() + ".");

			writerDefault.close();
			writerEN.close();
			writerFR.close();
			writerDE.close();
			writerES.close();
			writerIT.close();
			writerNL.close();
			writerPT.close();

		} catch (Exception e) {
			LOG.info("Exception", e);
		}
	}
	
	private static final void buildPropertiesWriters(HSSFSheet sheetApplicationRessources, DataOutputStream writerDefault, DataOutputStream writerEN,
			DataOutputStream writerFR, DataOutputStream writerDE, DataOutputStream writerES, DataOutputStream writerIT,
			DataOutputStream writerNL, DataOutputStream writerPT, String prefix) throws UnsupportedEncodingException, IOException {
		int rowBeginning = 2;

		int cellPositionKey = 3;
		int cellPositionDefault = 4;
		int cellPositionEN = 4;
		int cellPositionFR = 5;
		int cellPositionDE = 6;
		int cellPositionES = 7;
		int cellPositionIT = 8;
		int cellPositionNL = 9;
		int cellPositionPT = 10;

		String encodage = LoaderTranslation.ENCODAGE;
		
		for (int i = sheetApplicationRessources.getFirstRowNum() + rowBeginning; i < sheetApplicationRessources.getLastRowNum(); i++) {
			HSSFRow row = sheetApplicationRessources.getRow(i);

			if (row != null) {
				// 0 : Property Key
				HSSFCell cellKey = row.getCell((short) cellPositionKey);
				String propertyKey = null;

				if (cellKey != null) {
					propertyKey = cellKey.getRichStringCellValue().getString();
					propertyKey = propertyKey.trim();
					if (propertyKey.contains("##") && getIfWritedRow(row)) {

						if (propertyKey.contains("XXXX file")) {
							writerDefault.write(((String) "## Internationalisation - Default file").getBytes(encodage));
							writerDefault.write(((String) "\n").getBytes(encodage));

							writerEN.write(((String) "## Internationalisation - English file").getBytes(encodage));
							writerEN.write(((String) "\n").getBytes(encodage));

							writerFR.write(((String) "## Internationalisation - French file").getBytes(encodage));
							writerFR.write(((String) "\n").getBytes(encodage));

							writerDE.write(((String) "## Internationalisation - German file").getBytes(encodage));
							writerDE.write(((String) "\n").getBytes(encodage));

							writerES.write(((String) "## Internationalisation - Spanish file").getBytes(encodage));
							writerES.write(((String) "\n").getBytes(encodage));

							writerIT.write(((String) "## Internationalisation - Italian file").getBytes(encodage));
							writerIT.write(((String) "\n").getBytes(encodage));

							writerNL.write(((String) "## Internationalisation - Netherlander file").getBytes(encodage));
							writerNL.write(((String) "\n").getBytes(encodage));

							writerPT.write(((String) "## Internationalisation - Portuguese file").getBytes(encodage));
							writerPT.write(((String) "\n").getBytes(encodage));
						} else {
							writerDefault.write(((String) propertyKey).getBytes(encodage));
							writerDefault.write(((String) "\n").getBytes(encodage));

							writerEN.write(((String) propertyKey).getBytes(encodage));
							writerEN.write(((String) "\n").getBytes(encodage));

							writerFR.write(((String) propertyKey).getBytes(encodage));
							writerFR.write(((String) "\n").getBytes(encodage));

							writerDE.write(((String) propertyKey).getBytes(encodage));
							writerDE.write(((String) "\n").getBytes(encodage));

							writerES.write(((String) propertyKey).getBytes(encodage));
							writerES.write(((String) "\n").getBytes(encodage));

							writerIT.write(((String) propertyKey).getBytes(encodage));
							writerIT.write(((String) "\n").getBytes(encodage));

							writerNL.write(((String) propertyKey).getBytes(encodage));
							writerNL.write(((String) "\n").getBytes(encodage));

							writerPT.write(((String) propertyKey).getBytes(encodage));
							writerPT.write(((String) "\n").getBytes(encodage));
						}

					} else if (getIfWritedRow(row)) {

						// 0 : Default Value
						HSSFCell cellValueToDefault = row.getCell((short) cellPositionDefault);
						String valueToDefault = handlePropertiesValue(cellValueToDefault);

						// 1 : EN Value
						HSSFCell cellValueToEN = row.getCell((short) cellPositionEN);
						String valueToEN = handlePropertiesValue(cellValueToEN);

						// 2 : FR Value
						HSSFCell cellValueToFR = row.getCell((short) cellPositionFR);
						String valueToFR = handlePropertiesValue(cellValueToFR);

						// 3 : DE Value
						HSSFCell cellValueToDE = row.getCell((short) cellPositionDE);
						String valueToDE = handlePropertiesValue(cellValueToDE);

						// 4 : ES Value
						HSSFCell cellValueToES = row.getCell((short) cellPositionES);
						String valueToES = handlePropertiesValue(cellValueToES);

						// 5 : IT Value
						HSSFCell cellValueToIT = row.getCell((short) cellPositionIT);
						String valueToIT = handlePropertiesValue(cellValueToIT);

						// 6 : NL Value
						HSSFCell cellValueToNL = row.getCell((short) cellPositionNL);
						String valueToNL = handlePropertiesValue(cellValueToNL);

						// 7 : PT Value
						HSSFCell cellValueToPT = row.getCell((short) cellPositionPT);
						String valueToPT = handlePropertiesValue(cellValueToPT);

						writerDefault.write(((String) prefix + propertyKey.trim() + "=" + valueToDefault.trim()).getBytes(encodage));
						writerDefault.write(((String) "\n").getBytes(encodage));

						writerEN.write(((String) prefix + propertyKey.trim() + "=" + valueToEN.trim()).getBytes(encodage));
						writerEN.write(((String) "\n").getBytes(encodage));

						writerFR.write(((String) prefix + propertyKey.trim() + "=" + valueToFR.trim()).getBytes(encodage));
						writerFR.write(((String) "\n").getBytes(encodage));

						writerDE.write(((String) prefix + propertyKey.trim() + "=" + valueToDE.trim()).getBytes(encodage));
						writerDE.write(((String) "\n").getBytes(encodage));

						writerES.write(((String) prefix + propertyKey.trim() + "=" + valueToES.trim()).getBytes(encodage));
						writerES.write(((String) "\n").getBytes(encodage));

						writerIT.write(((String) prefix + propertyKey.trim() + "=" + valueToIT.trim()).getBytes(encodage));
						writerIT.write(((String) "\n").getBytes(encodage));

						writerNL.write(((String) prefix + propertyKey.trim() + "=" + valueToNL.trim()).getBytes(encodage));
						writerNL.write(((String) "\n").getBytes(encodage));

						writerPT.write(((String) prefix + propertyKey.trim() + "=" + valueToPT.trim()).getBytes(encodage));
						writerPT.write(((String) "\n").getBytes(encodage));

					}
				} else {
					// NOTHING
				}
			}
		}

		writerDefault.flush();
		writerEN.flush();
		writerFR.flush();
		writerDE.flush();
		writerES.flush();
		writerIT.flush();
		writerNL.flush();
		writerPT.flush();
	}
	

	private static String handlePropertiesValue(HSSFCell cellValue){
		if (cellValue != null) {
			if (cellValue.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
				return new Double(cellValue.getNumericCellValue()).toString();
			}
			else {
				return cellValue.getRichStringCellValue().getString().trim().replace("\n", "/\n");
			}
		}
		return null;
	}
	
//	private static String handleXMLValue(HSSFCell cellValue){
//		if (cellValue != null) {
//			if (cellValue.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
//				return new Double(cellValue.getNumericCellValue()).toString();
//			}
//			else {
//				return cellValue.getRichStringCellValue().getString().trim().replace("\n", "/\n");
//			}
//		}
//		return null;
//	}
	
	private static final boolean getIfWritedRow(HSSFRow row) {
		int rowActived = 0;
		HSSFCell cell = row.getCell((short) rowActived);
		if (cell != null
				&& ((cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC 
						&& cell.getNumericCellValue() == 1) 
						|| (cell.getCellType() == HSSFCell.CELL_TYPE_STRING 
								&& cell.getRichStringCellValue().getString().equals("1")))){
			return true;
		} else {
			return false;
		}
	}

	public static final void copyPropertiesFiles(String folderSource, String folderTarget, String project) {

		LOG.info("folderTarget : " + folderTarget);

		// TODO : refactoring when scope will be clear!
		
		String prefixFileName = "qalingo-fo-messages";
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + ".properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_en.properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_fr.properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_de.properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_es.properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_it.properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_nl.properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_pt.properties");

		prefixFileName = "qalingo-bo-messages";
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + ".properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_en.properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_fr.properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_de.properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_es.properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_it.properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_nl.properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_pt.properties");
		
		prefixFileName = "qalingo-countries";
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + ".properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_en.properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_fr.properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_de.properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_es.properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_it.properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_nl.properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_pt.properties");

		prefixFileName = "qalingo-languages";
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + ".properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_en.properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_fr.properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_de.properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_es.properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_it.properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_nl.properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_pt.properties");

		prefixFileName = "qalingo-titles";
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + ".properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_en.properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_fr.properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_de.properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_es.properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_it.properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_nl.properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_pt.properties");

	}

	public static final void copyXmlFiles(String folderSource, String folderTarget, String project) {

		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + "/xml/values/", folderTarget + "/values/", "strings.xml");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + "/xml/values-en/", folderTarget + "/values-en/", "strings.xml");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + "/xml/values-fr/", folderTarget + "/values-fr/", "strings.xml");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + "/xml/values-de/", folderTarget + "/values-de/", "strings.xml");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + "/xml/values-es/", folderTarget + "/values-es/", "strings.xml");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + "/xml/values-it/", folderTarget + "/values-it/", "strings.xml");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + "/xml/values-nl/", folderTarget + "/values-nl/", "strings.xml");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + "/xml/values-nl/", folderTarget + "/values-nl/", "strings.xml");

	}

	public static final String getFolderPath(String path) {
		String date = formatterFolder.format(new Date());
		String currentPath = "";
		if (path != null && !path.equalsIgnoreCase("")) {
			currentPath = path;
		} else {
			currentPath = "output/" + date + "/";
		}
		File folderBuild = new File(currentPath);
		if (!folderBuild.exists()) {
			folderBuild.mkdirs();
		}

		return currentPath;
	}

	private static final void copyFile(String srcDir, String destDir, String name) {
		try {
			String newPath = destDir;
			File folderProject = new File(newPath);
			if (!folderProject.exists())
				folderProject.mkdirs();
			
			File f1 = new File(srcDir + name + "");
			File f2 = new File(destDir + name + "");
			InputStream in = new FileInputStream(f1);

			// For Overwrite the file.
			OutputStream out = new FileOutputStream(f2);

			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
		} catch (FileNotFoundException e) {
			LOG.error("FileNotFoundException", e);
		} catch (IOException e) {
			LOG.error("IOException", e);
		}
	}

}
