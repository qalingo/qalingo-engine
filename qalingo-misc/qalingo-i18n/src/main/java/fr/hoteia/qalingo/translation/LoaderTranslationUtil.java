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

import org.apache.commons.lang.StringUtils;
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

	public static final void buildMessagesCommonProperties(String currentPath, String project, HSSFWorkbook wb) {
		try {
			String newPath = currentPath + project + Constants.PROPERTIES_PATH;
			File folderProject = new File(newPath);
			if (!folderProject.exists()){
				folderProject.mkdirs();
			}

			LOG.info(newPath + "/" + "wording.properties");
			String prefixFileName = "qalingo-common-wording";
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

			HSSFSheet sheetApplicationRessourcesFromCore = wb.getSheet(Constants.SHEET_COMMON_MESSAGES_RESSOURCES);
			buildPropertiesWriters(sheetApplicationRessourcesFromCore, writerDefault, writerEN, writerFR, writerDE, writerES, writerIT, writerNL,	writerPT);

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
	
	public static final void buildMessagesFrontOfficeProperties(String currentPath, String project, HSSFWorkbook wb) {
		try {
			String newPath = currentPath + project + Constants.PROPERTIES_PATH;
			File folderProject = new File(newPath);
			if (!folderProject.exists()){
				folderProject.mkdirs();
			}

			LOG.info(newPath + "/" + "wording.properties");
			String prefixFileName = "qalingo-fo-wording";
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
			buildPropertiesWriters(sheetApplicationRessourcesFromCore, writerDefault, writerEN, writerFR, writerDE, writerES, writerIT, writerNL,	writerPT);

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

			LOG.info(newPath + "/" + "wording.properties");
			String prefixFileName = "qalingo-bo-wording";
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
			buildPropertiesWriters(sheetApplicationRessourcesFromCore, writerDefault, writerEN, writerFR, writerDE, writerES, writerIT, writerNL,	writerPT);

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

			LOG.info(newPath + "/" + "wording.properties");
			String prefixFileName = "qalingo-email-wording";
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
			buildPropertiesWriters(sheetApplicationRessourcesFromCore, writerDefault, writerEN, writerFR, writerDE, writerES, writerIT, writerNL,	writerPT);

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

			HSSFSheet sheetApplicationRessourcesFromCore = wb.getSheet(Constants.SHEET_COUNTRY_RESSOURCES);
			buildPropertiesWriters(sheetApplicationRessourcesFromCore, writerDefault, writerEN, writerFR, writerDE, writerES, writerIT, writerNL,
					writerPT);

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

			HSSFSheet sheetApplicationRessourcesFromCore = wb.getSheet(Constants.SHEET_LANGUAGE_RESSOURCES);
			buildPropertiesWriters(sheetApplicationRessourcesFromCore, writerDefault, writerEN, writerFR, writerDE, writerES, writerIT, writerNL,
					writerPT);

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

			HSSFSheet sheetApplicationRessourcesFromCore = wb.getSheet(Constants.SHEET_TITLE_RESSOURCES);
			buildPropertiesWriters(sheetApplicationRessourcesFromCore, writerDefault, writerEN, writerFR, writerDE, writerES, writerIT, writerNL,
					writerPT);

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
			DataOutputStream writerNL, DataOutputStream writerPT) throws UnsupportedEncodingException, IOException {
		int rowBeginning = 2;

		int cellPositionPrefix = 3;
		int cellPositionKey = 4;
		int cellPositionDefault = 5;
		int cellPositionEN = 5;
		int cellPositionFR = 6;
		int cellPositionDE = 7;
		int cellPositionES = 8;
		int cellPositionIT = 9;
		int cellPositionNL = 10;
		int cellPositionPT = 11;

		String sheetName = sheetApplicationRessources.getSheetName();
		for (int i = sheetApplicationRessources.getFirstRowNum() + rowBeginning; i < sheetApplicationRessources.getLastRowNum(); i++) {
			HSSFRow row = sheetApplicationRessources.getRow(i);

			if (row != null) {
				// 0 : Property Key
				HSSFCell cellKey = row.getCell(cellPositionKey);
				String propertyKey = null;

				if (cellKey != null) {
					propertyKey = cellKey.getRichStringCellValue().getString();
					if (propertyKey.contains("##") 
							&& getIfWritedRow(row)) {
						if (propertyKey.contains("XXXX file")) {
							writerDefault.write(((String) "## Internationalisation - Default file").getBytes(LoaderTranslation.ENCODAGE));
							writerDefault.write(buildCarriageReturn());

							writerEN.write(((String) "## Internationalisation - English file").getBytes(LoaderTranslation.ENCODAGE));
							writerEN.write(buildCarriageReturn());

							writerFR.write(((String) "## Internationalisation - French file").getBytes(LoaderTranslation.ENCODAGE));
							writerFR.write(buildCarriageReturn());

							writerDE.write(((String) "## Internationalisation - German file").getBytes(LoaderTranslation.ENCODAGE));
							writerDE.write(buildCarriageReturn());

							writerES.write(((String) "## Internationalisation - Spanish file").getBytes(LoaderTranslation.ENCODAGE));
							writerES.write(buildCarriageReturn());

							writerIT.write(((String) "## Internationalisation - Italian file").getBytes(LoaderTranslation.ENCODAGE));
							writerIT.write(buildCarriageReturn());

							writerNL.write(((String) "## Internationalisation - Netherlander file").getBytes(LoaderTranslation.ENCODAGE));
							writerNL.write(buildCarriageReturn());

							writerPT.write(((String) "## Internationalisation - Portuguese file").getBytes(LoaderTranslation.ENCODAGE));
							writerPT.write(buildCarriageReturn());
							
						} else {
							writerDefault.write(((String) propertyKey).getBytes(LoaderTranslation.ENCODAGE));
							writerDefault.write(buildCarriageReturn());

							writerEN.write(((String) propertyKey).getBytes(LoaderTranslation.ENCODAGE));
							writerEN.write(buildCarriageReturn());

							writerFR.write(((String) propertyKey).getBytes(LoaderTranslation.ENCODAGE));
							writerFR.write(buildCarriageReturn());

							writerDE.write(((String) propertyKey).getBytes(LoaderTranslation.ENCODAGE));
							writerDE.write(buildCarriageReturn());

							writerES.write(((String) propertyKey).getBytes(LoaderTranslation.ENCODAGE));
							writerES.write(buildCarriageReturn());

							writerIT.write(((String) propertyKey).getBytes(LoaderTranslation.ENCODAGE));
							writerIT.write(buildCarriageReturn());

							writerNL.write(((String) propertyKey).getBytes(LoaderTranslation.ENCODAGE));
							writerNL.write(buildCarriageReturn());

							writerPT.write(((String) propertyKey).getBytes(LoaderTranslation.ENCODAGE));
							writerPT.write(buildCarriageReturn());
						}

					} else if (getIfWritedRow(row)) {
						// PREFIX
						HSSFCell cellPrefix = row.getCell(cellPositionPrefix);
						String prefix = cellPrefix.getRichStringCellValue().getString();
						
						// 0 : Default Value
						HSSFCell cellValueToDefault = row.getCell(cellPositionDefault);
						String valueToDefault = handlePropertiesValue(cellValueToDefault);

						// 1 : EN Value
						HSSFCell cellValueToEN = row.getCell(cellPositionEN);
						String valueToEN = handlePropertiesValue(cellValueToEN);

						// 2 : FR Value
						HSSFCell cellValueToFR = row.getCell(cellPositionFR);
						String valueToFR = handlePropertiesValue(cellValueToFR);

						// 3 : DE Value
						HSSFCell cellValueToDE = row.getCell(cellPositionDE);
						String valueToDE = handlePropertiesValue(cellValueToDE);

						// 4 : ES Value
						HSSFCell cellValueToES = row.getCell(cellPositionES);
						String valueToES = handlePropertiesValue(cellValueToES);

						// 5 : IT Value
						HSSFCell cellValueToIT = row.getCell(cellPositionIT);
						String valueToIT = handlePropertiesValue(cellValueToIT);

						// 6 : NL Value
						HSSFCell cellValueToNL = row.getCell(cellPositionNL);
						String valueToNL = handlePropertiesValue(cellValueToNL);

						// 7 : PT Value
						HSSFCell cellValueToPT = row.getCell(cellPositionPT);
						String valueToPT = handlePropertiesValue(cellValueToPT);

						writerDefault.write(buildStringToWrite(sheetName, prefix, propertyKey, valueToDefault));
						writerDefault.write(buildCarriageReturn());

						writerEN.write(buildStringToWrite(sheetName, prefix, propertyKey, valueToEN));
						writerEN.write(buildCarriageReturn());

						writerFR.write(buildStringToWrite(sheetName, prefix, propertyKey, valueToFR));
						writerFR.write(buildCarriageReturn());

						writerDE.write(buildStringToWrite(sheetName, prefix, propertyKey, valueToDE));
						writerDE.write(buildCarriageReturn());

						writerES.write(buildStringToWrite(sheetName, prefix, propertyKey, valueToES));
						writerES.write(buildCarriageReturn());

						writerIT.write(buildStringToWrite(sheetName, prefix, propertyKey, valueToIT));
						writerIT.write(buildCarriageReturn());

						writerNL.write(buildStringToWrite(sheetName, prefix, propertyKey, valueToNL));
						writerNL.write(buildCarriageReturn());

						writerPT.write(buildStringToWrite(sheetName, prefix, propertyKey, valueToPT));
						writerPT.write(buildCarriageReturn());

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
	
	private static byte[] buildStringToWrite(String sheetName, String prefix, String propertyKey, String value) throws UnsupportedEncodingException {
		if(StringUtils.isNotEmpty(propertyKey)){
			StringBuffer finalValue = new StringBuffer();
			if(StringUtils.isNotEmpty(sheetName)){
				finalValue.append(buildUniversePrefix(sheetName) + ".");
			}
			if(StringUtils.isNotEmpty(prefix)){
				finalValue.append(prefix.replaceAll("\\.", "_").trim() + ".");
			}
			finalValue.append(propertyKey.replaceAll("\\.", "_").trim());
			finalValue.append("=" + value.trim());
			return ((String) finalValue.toString() ).getBytes(LoaderTranslation.ENCODAGE);
		}
		return buildCarriageReturn();
	}
	
	private static byte[] buildCarriageReturn() throws UnsupportedEncodingException{
		return ((String) "\n").getBytes(LoaderTranslation.ENCODAGE);
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
	
	private static final boolean getIfWritedRow(HSSFRow row) {
		int rowActived = 0;
		HSSFCell cell = row.getCell(rowActived);
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
		
		String prefixFileName = "qalingo-common-wording";
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + ".properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_en.properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_fr.properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_de.properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_es.properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_it.properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_nl.properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_pt.properties");
		
		prefixFileName = "qalingo-fo-wording";
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + ".properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_en.properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_fr.properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_de.properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_es.properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_it.properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_nl.properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_pt.properties");


		prefixFileName = "qalingo-bo-wording";
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + ".properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_en.properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_fr.properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_de.properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_es.properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_it.properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_nl.properties");
		LoaderTranslationUtil.copyFile(folderSource + "/" + project  + Constants.PROPERTIES_PATH, folderTarget, prefixFileName + "_pt.properties");
		
		prefixFileName = "qalingo-email-wording";
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
	
	private static String buildUniversePrefix(String sheetName){
		if(sheetName.contains("-Messages")){
			return sheetName.replace("-Messages", "").toLowerCase();
		} else {
			return sheetName.toLowerCase();
		}
	}

}