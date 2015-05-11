package org.cycads.general;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.regex.Pattern;

import org.cycads.entities.annotation.Annotation;

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

	private static double getDbl(String key) {
		return Double.parseDouble(getStringMandatory(key));
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

	public static String getNoteToStringSeparator() {
		return getStringMandatory("general.note.toStringSeparator");
	}

	public static boolean isDebugging() {
		return new Boolean(getStringMandatory("general.debug"));
	}

	public static String getFunctionalAnnotationTypeName() {
		return getStringMandatory("general.functionalAnnotationTypeName");
	}

	/* adds pbp */
	// Annotation Generator
	public static String getMethodSeparator() {
		return getStringMandatory("AnnotationGenerator.methodSeparator");
	}

	public static String getMethodWaySeparator() {
		return getStringMandatory("AnnotationGenerator.methodPathSeparator");
	}

	public static String getAnnotationScoreDefault() {
		return getStringMandatory("AnnotationGenerator.annotationScoreDefault");
	}

	public static AnnotationFilterBytype	scoreAnnotFilterByType	= null;
	public static AnnotationFilterBytype	methodAnnotFilterByType	= null;

	public static boolean isValidAnnotForScore(Annotation annot) {
		if (scoreAnnotFilterByType == null) {
			scoreAnnotFilterByType = new AnnotationFilterBytype();
			for (String type : getStrings("AnnotationGenerator.scoreFilterByAnnotationType")) {
				scoreAnnotFilterByType.addType(annot.getNoteType(type));
			}
		}
		return scoreAnnotFilterByType.isValid(annot);
	}

	public static boolean isValidAnnotForMethods(Annotation annot) {
		if (methodAnnotFilterByType == null) {
			methodAnnotFilterByType = new AnnotationFilterBytype();
			for (String type : getStrings("AnnotationGenerator.methodFilterByAnnotationType")) {
				methodAnnotFilterByType.addType(annot.getNoteType(type));
			}
		}
		return methodAnnotFilterByType.isValid(annot);
	}

	// PfFile
	public static String getPFFileCycIdNoteType() {
		return getStringMandatory("AnnotationGenerator.idNoteType");
	}

	public static String getPFFileFunctionCommentSeparator() {
		return getStringMandatory("AnnotationGenerator.pf.functionComment.separator");
	}

	public static String getPFFileGeneCommentSeparator() {
		return getStringMandatory("AnnotationGenerator.pf.geneComment.separator");
	}
	
	public static String getPFFileGeneCommentsDivHtmlopen() {
		return getString("AnnotationGenerator.pf.geneComment.divhtmlopen");
	}
	public static String getPFFileGeneCommentsDivHtmlclose() {
		return getString("AnnotationGenerator.pf.geneComment.divhtmlclose");
	}

	/* end adds pbp*/

	// KOFile
	public static String koFileEntryTag() {
		return getString("KOLoader.file.entryTag");
	}

	public static String koFileDefinitionTag() {
		return getString("KOLoader.file.definitionTag");
	}

	public static String koFileDBLinksTag() {
		return getString("KOLoader.file.dbLinksTag");
	}

	public static String koFileECTag() {
		return getString("KOLoader.file.ecTag");
	}

	public static int koFileDataPos() {
		return getInt("KOLoader.file.dataPos");
	}

	public static String koFileEndRecordStr() {
		return getString("KOLoader.file.endRecord");
	}

	public static String koFileCommentStr() {
		return getString("KOLoader.file.comment");
	}

	public static String koFileECDeleteExpression() {
		return getString("KOLoader.file.EC.delete.regex");
	}

	public static String koFileECSeparator() {
		return getString("KOLoader.file.EC.separator.regex");
	}

	public static String koFileDBLinkSubTagSeparator() {
		return getString("KOLoader.file.DBLink.subTagSeparator");
	}

	public static String koFileDBLinkAccessionSeparator() {
		return getString("KOLoader.file.DBLink.accessionSeparator");
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
			dbLinkPatterns = getPatterns("KOLoader.file.DBLink.dbName.regex");
		}
		return dbLinkPatterns;
	}

	public static List<String>	dbLinkNames;

	private static List<String> getfKODBLinkNames() {
		if (dbLinkNames == null) {
			dbLinkNames = getStrings("KOLoader.file.DBLink.newDbName");
		}
		return dbLinkNames;
	}
}
