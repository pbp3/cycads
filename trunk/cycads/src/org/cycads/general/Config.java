package org.cycads.general;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class Config
{
	private static final String			BUNDLE_NAME						= "config";											//$NON-NLS-1$

	private static final ResourceBundle	RESOURCE_BUNDLE					= ResourceBundle.getBundle(BUNDLE_NAME);

	public static List<Pattern>			SEQUENCEFEATURE_TYPE_PATTERNS	= getPatterns("SequenceFeature.Tag.Type.regex");
	public static List<String>			BIOCYC_RECORD_TYPES				= getStrings("BioCycRecord.Type.value");
	public static List<String>			SEQUENCEFEATURE_TAG_PRODUCT_IDS	= getStrings("SequenceFeature.Tag.ProductId.value");

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

	public static String bioCycRecordType(String featureType) {
		return transform(featureType, SEQUENCEFEATURE_TYPE_PATTERNS, BIOCYC_RECORD_TYPES);
	}

	public static String sequenceFeatureTagProductId(String featureType) {
		return transform(featureType, SEQUENCEFEATURE_TYPE_PATTERNS, SEQUENCEFEATURE_TAG_PRODUCT_IDS);
	}

	private static String transform(String key, List<Pattern> keyPatterns, List<String> values) {
		for (int i = 0; i < keyPatterns.size(); i++) {
			if (keyPatterns.get(i).matcher(key).matches()) {
				if (values.size() > i) {
					return values.get(i);
				}
				else {
					return null;
				}
			}
		}
		return null;
	}

	private static List<Pattern> getPatterns(String tag) {
		List<String> patternsStr = getStrings(tag);
		List<Pattern> patterns = new ArrayList<Pattern>(patternsStr.size());
		for (String str : patternsStr) {
			patterns.add(Pattern.compile(str));
		}
		return patterns;
	}

	private static List<String> getStrings(String tag) {
		ArrayList<String> values = new ArrayList<String>(4);
		int i = 0;
		String str;
		while ((str = getStringOptional(tag + "." + i)) != null) {
			values.add(str);
			i++;
		}
		values.trimToSize();
		return values;
	}

	public static String sequenceFeatureNameTag() {
		return getString("SequenceFeature.Tag.Name");
	}

}
