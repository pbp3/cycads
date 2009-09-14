package org.cycads.parser.gbk;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.MissingResourceException;
import java.util.regex.Pattern;

import org.cycads.general.Config;
import org.cycads.general.ParametersDefault;

/*
 * #the gff3 parameters: gff3.file[.comment].<parameter name>[.<type>][.<source>][.regex][.<i>] #type =
 * gene|mrna|cds|exon
 * 
 */
public class GBKFileConfig
{
	//	private static final String			BUNDLE_NAME		= "config";								//$NON-NLS-1$
	//
	//	private static final ResourceBundle	RESOURCE_BUNDLE	= ResourceBundle.getBundle(BUNDLE_NAME);

	private GBKFileConfig() {
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
		return Integer.parseInt(getString(key));
	}

	public static ArrayList<String> getStrings(String fullParameterName) {
		ArrayList<String> values;
		values = new ArrayList<String>();
		String str;
		if ((str = getStringOptional(fullParameterName)) != null) {
			values.add(str);
		}
		int i = 0;
		while ((str = getStringOptional(fullParameterName + "." + i)) != null) {
			values.add(str);
			i++;
		}
		values.trimToSize();
		return values;
	}

	public static ArrayList<String> getStrings(String tag, ArrayList<String> values) {
		if (values == null) {
			values = new ArrayList<String>(4);
		}
		String str;
		if ((str = getStringOptional(tag)) != null) {
			values.add(str);
		}
		int i = 0;
		while ((str = getStringOptional(tag + "." + i)) != null) {
			values.add(str);
			i++;
		}
		values.trimToSize();
		return values;
	}

	public static ArrayList<Pattern> getPatterns(String fullParameterName) {
		fullParameterName += ".regex";
		ArrayList<Pattern> patterns;
		List<String> patternsStr = getStrings(fullParameterName, null);
		patterns = new ArrayList<Pattern>(patternsStr.size());
		for (String str : patternsStr) {
			patterns.add(Pattern.compile(str));
		}
		return patterns;
	}

	public static ArrayList<Pattern> getPatterns(String fullParameterName, ArrayList<Pattern> patterns) {
		fullParameterName += ".regex";
		List<String> patternsStr = getStrings(fullParameterName, null);
		if (patterns == null) {
			patterns = new ArrayList<Pattern>(patternsStr.size());
		}
		for (String str : patternsStr) {
			patterns.add(Pattern.compile(str));
		}
		return patterns;
	}

	public static ArrayList<Pattern> getPatternsByType(String parameterName, String type) {
		ArrayList<Pattern> patterns = getPatterns("gbk.file." + parameterName, null);
		patterns = getPatterns("gbk.file." + type + "." + parameterName, patterns);
		return patterns;
	}

	public static String getStringByType(String parameterName, String type) {
		ArrayList<String> values = getStringsByType(parameterName, type);
		if (values == null || values.isEmpty()) {
			return null;
		}
		return values.get(0);
	}

	public static ArrayList<String> getStringsByType(String parameterName, String type) {
		ArrayList<String> values = getStrings("gbk.file." + parameterName, null);
		values = getStrings("gbk.file." + type + "." + parameterName, values);
		return values;
	}

	public static int matches(String input, Collection<Pattern> patterns) {
		int i = 0;
		for (Pattern pattern : patterns) {
			if (pattern.matcher(input).matches()) {
				return i;
			}
			i++;
		}
		return -1;
	}

	static ArrayList<Pattern>	removeTypePatterns	= getPatterns("gbk.file.removeType");
	static ArrayList<Pattern>	changeTypePatterns	= getPatterns("gbk.file.changeType");
	static ArrayList<String>	newValueTypes		= getStrings("gbk.file.changeType.newValue");

	public static String getSeqCommentNoteType() {
		return getStringMandatory("gbk.file.sequence.comment.noteType");
	}

	public static String getSeqDescriptionNoteType() {
		return getStringMandatory("gbk.file.sequence.description.noteType");
	}

	private static Hashtable<String, String>	typeHash	= new Hashtable<String, String>();

	public static String getType(String type) {
		String newType = typeHash.get(type);
		if (newType == null) {
			String oldType = type;
			if (matches(type, removeTypePatterns) >= 0) {
				newType = "";
			}
			else {
				int i = matches(type, changeTypePatterns);
				if (i >= 0) {
					newType = newValueTypes.get(i);
					type = newType;
				}
				else {
					newType = type;
				}
				if (isTypeValue(type, ParametersDefault.getGeneAnnotationTypeName())) {
					newType = ParametersDefault.getGeneAnnotationTypeName();
				}
				else if (isTypeValue(type, ParametersDefault.getMRNAAnnotationTypeName())) {
					newType = ParametersDefault.getMRNAAnnotationTypeName();
				}
				else if (isTypeValue(type, ParametersDefault.getCDSAnnotationTypeName())) {
					newType = ParametersDefault.getCDSAnnotationTypeName();
				}
			}
			typeHash.put(oldType, newType);
		}
		return newType;
	}

	// cdsAnnotationType = factory.getAnnotationType(ParametersDefault.getCDSAnnotationTypeName());
	// mrnaAnnotationType = factory.getAnnotationType(ParametersDefault.getMRNAAnnotationTypeName());
	// geneAnnotationType = factory.getAnnotationType(ParametersDefault.getGeneAnnotationTypeName());

	public static boolean isTypeValue(String input, String type) {
		ArrayList<Pattern> patterns = getPatternsByType("typeValue", type);
		return matches(input, patterns) >= 0;
	}

	private static Hashtable<String, String>	annotationMethodNameHash	= new Hashtable<String, String>();

	public static String getAnnotationMethodName(String type) {
		String methodName = annotationMethodNameHash.get(type);
		if (methodName == null) {
			methodName = getStringByType("methodName", type);
			if (methodName == null || methodName.length() == 0) {
				methodName = getStringMandatory("gbk.file.methodNameDefault");
			}
			annotationMethodNameHash.put(type, methodName);
		}
		return methodName;
	}

	private static ArrayList<Pattern> getPatterns(String type, Hashtable<String, ArrayList<Pattern>> patternsHash,
			String parameterName) {
		ArrayList<Pattern> ret = patternsHash.get(type);
		if (ret == null) {
			ret = getPatternsByType(parameterName, type);
			patternsHash.put(type, ret);
		}
		return ret;
	}

	private static ArrayList<String> getStrings(String type, Hashtable<String, ArrayList<String>> stringsHash,
			String parameterName) {
		ArrayList<String> ret = stringsHash.get(type);
		if (ret == null) {
			ret = getStringsByType(parameterName, type);
			stringsHash.put(type, ret);
		}
		return ret;
	}

	static Hashtable<String, ArrayList<Pattern>>	removeTagPatterns	= new Hashtable<String, ArrayList<Pattern>>();
	static Hashtable<String, ArrayList<Pattern>>	changeTagPatterns	= new Hashtable<String, ArrayList<Pattern>>();
	static Hashtable<String, ArrayList<String>>		newValueTags		= new Hashtable<String, ArrayList<String>>();

	private static ArrayList<Pattern> getRemoveTagPatterns(String type) {
		return getPatterns(type, removeTagPatterns, "removeTag");
	}

	private static ArrayList<Pattern> getChangeTagPatterns(String type) {
		return getPatterns(type, changeTagPatterns, "changeTag");
	}

	private static ArrayList<String> getNewValueTags(String type) {
		return getStrings(type, newValueTags, "changeTag.newValue");
	}

	static Hashtable<String, Hashtable<String, String>>	tagHash	= new Hashtable<String, Hashtable<String, String>>();

	public static String getTag(String tag, String type) {
		Hashtable<String, String> tags = tagHash.get(type);
		if (tags == null) {
			tags = new Hashtable<String, String>();
			tagHash.put(type, tags);
		}
		String newValue = tags.get(tag);
		if (newValue == null) {
			String oldTag = tag;

			if (matches(tag, getRemoveTagPatterns(type)) >= 0) {
				newValue = "";
			}
			else {
				newValue = tag;
				int i = matches(tag, getChangeTagPatterns(type));
				if (i >= 0) {
					newValue = getNewValueTags(type).get(i);
					tag = newValue;
				}
			}

			tags.put(oldTag, newValue);
		}
		return newValue;
	}

	private static ArrayList<String> filter(ArrayList<String> results, ArrayList<Pattern> patterns, String expression) {
		ArrayList<String> ret = new ArrayList<String>();
		for (int i = 0; i < patterns.size(); i++) {
			if (patterns.get(i).matcher(expression).matches()) {
				ret.add(results.get(i));
			}
		}
		return ret;
	}

	static Hashtable<String, ArrayList<Pattern>>	parentAccessionTagPatterns	= new Hashtable<String, ArrayList<Pattern>>();
	static Hashtable<String, ArrayList<String>>		parentDbNames				= new Hashtable<String, ArrayList<String>>();

	private static ArrayList<Pattern> getParentAccessionTagPatterns(String type) {
		return getPatterns(type, parentAccessionTagPatterns, "parentAccessionTag");
	}

	private static ArrayList<String> getParentDBNames(String type) {
		return getStrings(type, parentDbNames, "parentDbName");
	}

	public static ArrayList<String> getParentDBNames(String tag, String type) {
		return filter(getParentDBNames(type), getParentAccessionTagPatterns(type), tag);
	}

	static Hashtable<String, ArrayList<Pattern>>	annotationSynonymAccessionTagPatterns	= new Hashtable<String, ArrayList<Pattern>>();
	static Hashtable<String, ArrayList<String>>		annotationSynonymDbNames				= new Hashtable<String, ArrayList<String>>();

	private static ArrayList<Pattern> getAnnotationSynonymAccessionTagPatterns(String type) {
		return getPatterns(type, annotationSynonymAccessionTagPatterns, "annotationSynonymAccessionTag");
	}

	private static ArrayList<String> getAnnotationSynonymDbNames(String type) {
		return getStrings(type, annotationSynonymDbNames, "annotationSynonymDbName");
	}

	public static ArrayList<String> getSynonymDBNames(String tag, String type) {
		return filter(getAnnotationSynonymDbNames(type), getAnnotationSynonymAccessionTagPatterns(type), tag);
	}

	static Hashtable<String, ArrayList<Pattern>>	ecPatterns		= new Hashtable<String, ArrayList<Pattern>>();
	static Hashtable<String, ArrayList<String>>		ecMethodNames	= new Hashtable<String, ArrayList<String>>();

	private static ArrayList<Pattern> getEcPatterns(String type) {
		return getPatterns(type, ecPatterns, "ecTag");
	}

	private static ArrayList<String> getEcMethodNames(String type) {
		return getStrings(type, ecMethodNames, "ecMethodName");
	}

	public static ArrayList<String> getECMethodNames(String tag, String type) {
		return filter(getEcMethodNames(type), getEcPatterns(type), tag);
	}

	static Hashtable<String, ArrayList<Pattern>>	functionTagPatterns	= new Hashtable<String, ArrayList<Pattern>>();
	static Hashtable<String, ArrayList<String>>		functionMethodNames	= new Hashtable<String, ArrayList<String>>();

	private static ArrayList<Pattern> getFunctionTagPatterns(String type) {
		return getPatterns(type, functionTagPatterns, "functionTag");
	}

	private static ArrayList<String> getFunctionMethodNames(String type) {
		return getStrings(type, functionMethodNames, "functionMethodName");
	}

	public static ArrayList<String> getFunctionMethodNames(String tag, String type) {
		return filter(getFunctionMethodNames(type), getFunctionTagPatterns(type), tag);
	}

	public static String gbkLoaderSeqDBName() {
		return getStringOptional("gbk.file.sequence.dbName");
	}

	public static String gbkLoaderFileName() {
		return getStringOptional("gbk.file.fileName");
	}

	public static String getOutputFile() {
		return getStringOptional("gbk.file.debug.outputGbkFile");
	}

}
