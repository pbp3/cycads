package org.cycads.general;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.regex.Pattern;

public class ParametersDefault
{
	//	private static final String		BUNDLE_NAME		= "config";				//$NON-NLS-1$
	//
	//	private static final ResourceBundle	RESOURCE_BUNDLE	= ResourceBundle.getBundle(BUNDLE_NAME);

	private ParametersDefault() {
	}

	private static String getString(String key) {
		try {
			return Config.getString(key);
		}
		catch (MissingResourceException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static String getStringOptional(String key) {
		try {
			return Config.getString(key);
		}
		catch (MissingResourceException e) {
			return null;
		}
	}

	private static String getStringMandatory(String key) {
		try {
			return Config.getString(key);
		}
		catch (MissingResourceException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private static int getInt(String key) {
		return Integer.parseInt(getStringMandatory(key));
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

	private static String[] transforms(String key, List<Pattern> keyPatterns, List<String> values) {
		ArrayList<String> rets = new ArrayList<String>();
		for (int i = 0; i < keyPatterns.size(); i++) {
			if (keyPatterns.get(i).matcher(key).matches()) {
				if (values.size() > i) {
					rets.add(values.get(i));
				}
			}
		}
		return rets.toArray(new String[0]);
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

	// General

	public static int getNextCycId() {
		return getInt("general.organism.nextCycId");
	}

	public static String getSubseqAnnotationTypeName() {
		return getStringMandatory("general.type.subseqAnnotationTypeName");
	}

	public static String getDbxrefAnnotationTypeName() {
		return getStringMandatory("general.type.dbxrefAnnotationTypeName");
	}

	public static String getFunctionAnnotationTypeName() {
		return getStringMandatory("general.type.functionAnnotationTypeName");
	}

	public static String getDbxrefSourceAnnotationTypeName() {
		return getStringMandatory("general.type.dbxrefSourceAnnotationTypeName");
	}

	public static String getScoreAnnotationNoteTypeName() {
		return getStringMandatory("general.type.scoreAnnotationNoteTypeName");
	}

	public static String getCDSAnnotationTypeName() {
		return getStringMandatory("general.type.cdsAnnotationTypeName");
	}

	public static String getMRNAAnnotationTypeName() {
		return getStringMandatory("general.type.mrnaAnnotationTypeName");
	}

	public static String getGeneAnnotationTypeName() {
		return getStringMandatory("general.type.geneAnnotationTypeName");
	}

	public static String getDbxrefToStringSeparator() {
		return getStringMandatory("general.dbxref.toStringSeparator");
	}

	public static boolean isDebugging() {
		return new Boolean(getStringMandatory("general.debug"));
	}

	public static String getFunctionalAnnotationTypeName() {
		return getStringMandatory("general.functionalAnnotationTypeName");
	}

	// KOFile

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
		return getInt("KOFile.dataPos");
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
		return getString("KOFile.EC.separatorExpression");
	}

	public static String koFileDBLinkSubTagSeparator() {
		return getString("KOFile.DBLink.subTagSeparator");
	}

	public static String koFileDBLinkAccessionSeparator() {
		return getString("KOFile.DBLink.accessionSeparator");
	}

	public static String koFileDBLinkDbName(String DBLinkName) {
		String dbName = transform(DBLinkName, getKODBLinkPatterns(), getfKODBLinkNames());
		if (dbName == null) {
			return DBLinkName;
		}
		return dbName;
	}

	public static List<Pattern>	dbLinkPatterns;

	private static List<Pattern> getKODBLinkPatterns() {
		if (dbLinkPatterns == null) {
			dbLinkPatterns = getPatterns("KOFile.DBLink.names.regex");
		}
		return dbLinkPatterns;
	}

	public static List<String>	dbLinkNames;

	private static List<String> getfKODBLinkNames() {
		if (dbLinkNames == null) {
			dbLinkNames = getStrings("KOFile.DBLink.dbNames");
		}
		return dbLinkNames;
	}

	// PFFIle

	public static String getPFFileCommentStart() {
		return getStringMandatory("pf.file.comment.start");
	}

	public static String getPFFileFunctionCommentSeparator() {
		return getStringMandatory("pf.file.function.comment.separator");
	}

	public static String getPFFileGeneCommentSeparator() {
		return getStringMandatory("pf.file.gene.comment.separator");
	}

	public static String getPFFileCycIdNoteType() {
		return getStringMandatory("pf.file.id.noteType");
	}

	// CDS Fake

	public static String getSeqVersionFake() {
		return getStringMandatory("annot.fake.seqVersion");
	}

	public static int getSubseqStartFake() {
		return getInt("annot.fake.subseqStartPos");
	}

	public static int getSubseqEndFake() {
		return getInt("annot.fake.subseqEndPos");
	}

	public static String getAnnotationMethodFake() {
		return getStringMandatory("annot.fake.annotationMethod");
	}

	public static String getAnnotationFakeType() {
		return getStringMandatory("annot.fake.annotationType");
	}

	public static String getAnnotationFakeFeature() {
		return getStringMandatory("annot.fake.annotationFeature");
	}

	//annotatToDbxrefParser

	public static String annotToDbxrefFileComment() {
		return getStringMandatory("annotToDbxref.file.comment");
	}

	public static String annotToDbxrefFileSeparator() {
		return getStringMandatory("annotToDbxref.file.separator");
	}

	public static String annotToDbxrefFileTextDelimiter() {
		return getStringMandatory("annotToDbxref.file.textDelimiter");
	}

	public static String annotToDbxrefFileDbxrefsSeparator() {
		return getStringMandatory("annotToDbxref.file.dbxrefsSeparator");
	}

	public static String annotToDbxrefFileAnnotsSeparator() {
		return getStringMandatory("annotToDbxref.file.annotsSeparator");
	}

	public static String annotToDbxrefRemoveLineRegex() {
		return getStringMandatory("annotToDbxref.file.removeLineRegex");
	}

}
