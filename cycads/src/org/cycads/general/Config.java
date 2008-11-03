package org.cycads.general;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class Config
{
	private static final String			BUNDLE_NAME		= "config";								//$NON-NLS-1$

	private static final ResourceBundle	RESOURCE_BUNDLE	= ResourceBundle.getBundle(BUNDLE_NAME);

	private static Transformer			BIOCYC_RECORD_TYPE;

	private Config() {
	}

	private static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		}
		catch (MissingResourceException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static String getStringOptional(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		}
		catch (MissingResourceException e) {
			return null;
		}
	}

	private static String getStringMandatory(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		}
		catch (MissingResourceException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private static Transformer createTransformer(String tag) {
		return new Transformer(tag);
	}

	//KOToECLoader

	public static String koToECLoaderSeparator() {
		return getString("KOToECLoader.separator");
	}

	public static String koToECLoaderComment() {
		return getString("KOToECLoader.comment");
	}

	public static String koToECLoaderDeleteExpression() {
		return getString("KOToECLoader.deleteExpression");
	}

	//KOToGOLoader

	public static String koToGOLoaderSeparator() {
		return getString("KOToGOLoader.separator");
	}

	public static String koToGOLoaderComment() {
		return getString("KOToGOLoader.comment");
	}

	public static String koToGOLoaderDeleteExpression() {
		return getString("KOToGOLoader.deleteExpression");
	}

	//GBKLoader

	public static String cdsToECMethodType() {
		return getString("CDSToEC.methodType");
	}

	public static String gBKLoaderMethodCDSToECDescription(String methodName) {
		Object[] a = {methodName};
		return MessageFormat.format(getString("GBKLoader.methodCDSToECDescription"), a);
	}

	//GBK file

	public static String gbkECTagExpression() {
		return getString("GBK.file.ECTagExpression");
	}

	public static String gbkGeneTagExpression() {
		return getString("GBK.file.GeneTagExpression");
	}

	public static String gbkRNATagExpression() {
		return getString("GBK.file.RNATagExpression");
	}

	public static String gbkCDSTagExpression() {
		return getString("GBK.file.CDSTagExpression");
	}

	//CDSToKOLoader

	public static String cdsToKOLoaderSeparator() {
		return getString("CDSToKOLoader.separator");
	}

	public static String cdsToKOLoaderComment() {
		return getString("CDSToKOLoader.comment");
	}

	public static String cdsToKOMethodType() {
		return getString("CDSToKO.methodType");
	}

	public static String cdsToKOMethodDescription(String methodName) {
		Object[] a = {methodName};
		return MessageFormat.format(getString("CDSToKOLoader.methodDescription"), a);
	}

	public static int cdsToKOLoaderPosCDSName() {
		return Integer.parseInt(getString("CDSToKOLoader.PosCDSName"));
	}

	public static String cdsToKOLoaderSeparatorCDSName() {
		return getString("CDSToKOLoader.SeparatorCDSName");
	}

	//KOFile

	public static String koFileEntryTag() {
		return getString("KOFile.entryTag");
	}

	public static String koFileDefinitionTag() {
		return getString("KOFile.definitionTag");
	}

	public static String koFileDBLinksTag() {
		return getString("KOFile.dbLinksTag");
	}

	public static String koFileECTag() {
		return getString("KOFile.ecTag");
	}

	public static int koFileDataPos() {
		return Integer.parseInt(getString("KOFile.dataPos"));
	}

	public static String koFileEndRecordStr() {
		return getString("KOFile.endRecord");
	}

	public static String koFileCommentStr() {
		return getString("KOFile.comment");
	}

	public static String koFileECDeleteExpression() {
		return getString("KOFile.EC.deleteExpression");
	}

	public static String koFileECSeparator() {
		return getString("KOFile.EC.SeparatorExpression");
	}

	public static String koFileDBLinkSubTagSeparator() {
		return getString("KOFile.DBLink.SubTagSeparator");
	}

	public static String koFileDBLinkGOTag() {
		return getString("KOFile.DBLink.GOTag");
	}

	public static String koFileDBLinkGOSeparator() {
		return getString("KOFile.DBLink.GOSeparator");
	}

	public static String koFileDBLinkCOGTag() {
		return getString("KOFile.DBLink.COGTag");
	}

	public static String koFileDBLinkCOGSeparator() {
		return getString("KOFile.DBLink.COGSeparator");
	}

	//PFFileGenerator

	public static String bioCycIdsSeparator() {
		return getString("BioCycIdsFileForPFFile.Separator");
	}

	public static String bioCycIdFormat() {
		return getString("BioCycId.Format");
	}

	//General

	public static String methodDescription() {
		return getString("General.Method.Description");
	}

	public static String bioCycRecordType(String featureTag) {
		if (BIOCYC_RECORD_TYPE == null) {
			BIOCYC_RECORD_TYPE = createTransformer("BioCycRecord.Type");
		}
		return BIOCYC_RECORD_TYPE.getValue(featureTag);
	}

	static class Transformer
	{
		ArrayList<Pattern>	keyPatterns	= new ArrayList<Pattern>();
		ArrayList<String>	values		= new ArrayList<String>();

		Transformer(String tag) {
			int i = 0;
			String typePatternStr;
			while ((typePatternStr = getStringOptional(tag + ".key." + i)) != null) {
				keyPatterns.add(Pattern.compile(typePatternStr));
				values.add(getStringMandatory(tag + ".value." + i));
				i++;
			}
			keyPatterns.trimToSize();
			values.trimToSize();
		}

		public String getValue(String key) {
			for (int i = 0; i < keyPatterns.size(); i++) {
				if (keyPatterns.get(i).matcher(key).matches()) {
					return values.get(i);
				}
			}
			return null;
		}
	}

}
