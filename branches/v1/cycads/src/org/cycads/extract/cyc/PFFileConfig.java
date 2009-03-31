package org.cycads.extract.cyc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import org.cycads.parser.gff3.GFF3Record;

/*
 #the gff3 parameters: gff3.file[.comment].<parameter name>[.<type>][.<source>][.regex][.<i>]
 #type = gene|mrna|cds|exon

 */
public class PFFileConfig
{
	private static final String			BUNDLE_NAME		= "GFF3FileConfig";						//$NON-NLS-1$

	private static final ResourceBundle	RESOURCE_BUNDLE	= ResourceBundle.getBundle(BUNDLE_NAME);

	private PFFileConfig() {
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

	private static int getInt(String key) {
		return Integer.parseInt(getString(key));
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

	public static ArrayList<Pattern> getPatterns(String parameterName, boolean comment, String type, String source) {
		if (comment) {
			parameterName = "gff3.file.comment." + parameterName;
		}
		else {
			parameterName = "gff3.file." + parameterName;
		}
		ArrayList<Pattern> patterns = getPatterns(parameterName, null);
		if (type != null) {
			patterns = getPatterns(parameterName + "." + type, patterns);
			if (source != null) {
				patterns = getPatterns(parameterName + "." + type + "." + source, patterns);
			}
		}
		if (source != null) {
			patterns = getPatterns(parameterName + "." + source, patterns);
		}
		return patterns;
	}

	public static String getString(String parameterName, boolean comment, String type, String source) {
		ArrayList<String> values = getStrings(parameterName, comment, type, source);
		if (values == null || values.isEmpty()) {
			return null;
		}
		return values.get(0);
	}

	public static ArrayList<String> getStrings(String parameterName, boolean comment, String type, String source) {
		if (comment) {
			parameterName = "gff3.file.comment." + parameterName;
		}
		else {
			parameterName = "gff3.file." + parameterName;
		}
		ArrayList<String> values = getStrings(parameterName, null);
		if (type != null && type.length() > 0) {
			values = getStrings(parameterName + "." + type, values);
			if (source != null && source.length() > 0) {
				values = getStrings(parameterName + "." + type + "." + source, values);
			}
		}
		if (source != null && source.length() > 0) {
			values = getStrings(parameterName + "." + source, values);
		}
		return values;
	}

	public static ArrayList<Pattern> getPatternsTypeSource(String parameterName, boolean comment, String type,
			String source, Hashtable<String, Hashtable<String, ArrayList<Pattern>>> patternsTypeSource) {
		Hashtable<String, ArrayList<Pattern>> patternsType = patternsTypeSource.get(type);
		if (patternsType == null) {
			patternsType = new Hashtable<String, ArrayList<Pattern>>(2);
			patternsTypeSource.put(type, patternsType);
		}
		ArrayList<Pattern> patterns = patternsType.get(source);
		if (patterns == null) {
			patterns = getPatterns(parameterName, comment, type, source);
			patternsType.put(source, patterns);
		}
		return patterns;
	}

	public static ArrayList<String> getStringsTypeSource(String parameterName, boolean comment, String type,
			String source, Hashtable<String, Hashtable<String, ArrayList<String>>> strsTypeSource) {
		Hashtable<String, ArrayList<String>> strsType = strsTypeSource.get(type);
		if (strsType == null) {
			strsType = new Hashtable<String, ArrayList<String>>(2);
			strsTypeSource.put(type, strsType);
		}
		ArrayList<String> strs = strsType.get(source);
		if (strs == null) {
			strs = getStrings(parameterName, comment, type, source);
			strsType.put(source, strs);
		}
		return strs;
	}

	public static String getStringTypeSource(String parameterName, boolean comment, String type, String source,
			Hashtable<String, Hashtable<String, ArrayList<String>>> strsTypeSource) {
		ArrayList<String> values = getStringsTypeSource(parameterName, comment, type, source, strsTypeSource);
		if (values == null || values.isEmpty()) {
			return null;
		}
		return values.get(0);
	}

	public static boolean matches(String input, Collection<Pattern> patterns) {
		for (Pattern pattern : patterns) {
			if (pattern.matcher(input).matches()) {
				return true;
			}
		}
		return false;
	}

	public static final String										geneType			= getStringMandatory("gff3.file.typeValue.gene");
	public static final String										cdsType				= getStringMandatory("gff3.file.typeValue.cds");
	public static final String										mrnaType			= getStringMandatory("gff3.file.typeValue.mrna");
	public static final String										exonType			= getStringMandatory("gff3.file.typeValue.exon");

	static Hashtable<String, Hashtable<String, ArrayList<Pattern>>>	typeValuePatterns	= new Hashtable<String, Hashtable<String, ArrayList<Pattern>>>(
																							4);

	public static boolean isTypeValue(String input, String type, String source) {
		ArrayList<Pattern> patterns = getPatternsTypeSource("typeValue", false, type, source, typeValuePatterns);
		return matches(input, patterns);
	}

	public static boolean isGene(String input, String source) {
		return isTypeValue(input, geneType, source);
	}

	public static boolean isMRNA(String input, String source) {
		return isTypeValue(input, mrnaType, source);
	}

	public static boolean isCDS(String input, String source) {
		return isTypeValue(input, cdsType, source);
	}

	public static boolean isExon(String input, String source) {
		return isTypeValue(input, exonType, source);
	}

	static Hashtable<String, Hashtable<String, ArrayList<String>>>	methodNames	= new Hashtable<String, Hashtable<String, ArrayList<String>>>(
																					4);

	public static String getAnnotationMethod(String type, String source) {
		String methodName = getStringTypeSource("methodName", false, type, source, methodNames);
		if (methodName == null || methodName.length() == 0) {
			methodName = source;
		}
		return methodName;
	}

	public static String getAnnotationMethod(GFF3Record record) {
		return getAnnotationMethod(record.getType(), record.getSource());
	}

	static Hashtable<String, Hashtable<String, ArrayList<Pattern>>>	parentAccessionTagPatterns	= new Hashtable<String, Hashtable<String, ArrayList<Pattern>>>(
																									4);

	public static ArrayList<Pattern> getParentAccessionTagPatterns(String type, String source) {
		return getPatternsTypeSource("parentAccessionTag", true, type, source, parentAccessionTagPatterns);
	}

	static Hashtable<String, Hashtable<String, ArrayList<String>>>	parentDbNames	= new Hashtable<String, Hashtable<String, ArrayList<String>>>(
																						4);

	public static ArrayList<String> getParentDbNames(String type, String source) {
		return getStringsTypeSource("parentDbName", true, type, source, parentDbNames);
	}

	static Hashtable<String, Hashtable<String, ArrayList<Pattern>>>	removeTagPatterns	= new Hashtable<String, Hashtable<String, ArrayList<Pattern>>>(
																							4);

	public static ArrayList<Pattern> getRemoveTagPatterns(String type, String source) {
		return getPatternsTypeSource("removeTag", true, type, source, removeTagPatterns);
	}

	static Hashtable<String, Hashtable<String, ArrayList<Pattern>>>	replaceTagPatterns	= new Hashtable<String, Hashtable<String, ArrayList<Pattern>>>(
																							4);

	public static ArrayList<Pattern> getReplaceTagPatterns(String type, String source) {
		return getPatternsTypeSource("replaceTag", true, type, source, replaceTagPatterns);
	}

	static Hashtable<String, Hashtable<String, ArrayList<String>>>	replaceNewValues	= new Hashtable<String, Hashtable<String, ArrayList<String>>>(
																							4);

	public static ArrayList<String> getReplaceNewValues(String type, String source) {
		return getStringsTypeSource("replaceNewValue", true, type, source, replaceNewValues);
	}

	static Hashtable<String, Hashtable<String, ArrayList<Pattern>>>	completSynonymTagPatterns	= new Hashtable<String, Hashtable<String, ArrayList<Pattern>>>(
																									4);

	public static ArrayList<Pattern> getCompletSynonymTagPatterns(String type, String source) {
		return getPatternsTypeSource("completSynonymTag", true, type, source, completSynonymTagPatterns);
	}

	static Hashtable<String, Hashtable<String, ArrayList<String>>>	completSynonymSeparators	= new Hashtable<String, Hashtable<String, ArrayList<String>>>(
																									4);

	public static ArrayList<String> getCompletSynonymSeparators(String type, String source) {
		return getStringsTypeSource("completSynonymSeparator", true, type, source, completSynonymSeparators);
	}

	static Hashtable<String, Hashtable<String, ArrayList<Pattern>>>	annotationSynonymAccessionTagPatterns	= new Hashtable<String, Hashtable<String, ArrayList<Pattern>>>(
																												4);

	public static ArrayList<Pattern> getAnnotationSynonymAccessionTagPatterns(String type, String source) {
		return getPatternsTypeSource("annotationSynonymAccessionTag", true, type, source,
			annotationSynonymAccessionTagPatterns);
	}

	static Hashtable<String, Hashtable<String, ArrayList<String>>>	annotationSynonymDbNames	= new Hashtable<String, Hashtable<String, ArrayList<String>>>(
																									4);

	public static ArrayList<String> getAnnotationSynonymDbNames(String type, String source) {
		return getStringsTypeSource("annotationSynonymDbName", true, type, source, annotationSynonymDbNames);
	}

	static Hashtable<String, Hashtable<String, ArrayList<Pattern>>>	subsequenceSynonymAccessionTagPatterns	= new Hashtable<String, Hashtable<String, ArrayList<Pattern>>>(
																												4);

	public static ArrayList<Pattern> getSubsequenceSynonymAccessionTagPatterns(String type, String source) {
		return getPatternsTypeSource("subsequenceSynonymAccessionTag", true, type, source,
			subsequenceSynonymAccessionTagPatterns);
	}

	static Hashtable<String, Hashtable<String, ArrayList<String>>>	subsequenceSynonymDbNames	= new Hashtable<String, Hashtable<String, ArrayList<String>>>(
																									4);

	public static ArrayList<String> getSubsequenceSynonymDbNames(String type, String source) {
		return getStringsTypeSource("subsequenceSynonymDbName", true, type, source, subsequenceSynonymDbNames);
	}

	static Hashtable<String, Hashtable<String, ArrayList<Pattern>>>	noteTypeTagPatterns	= new Hashtable<String, Hashtable<String, ArrayList<Pattern>>>(
																							4);

	public static ArrayList<Pattern> getNoteTypeTagPatterns(String type, String source) {
		return getPatternsTypeSource("noteTypeTag", true, type, source, noteTypeTagPatterns);
	}

	static Hashtable<String, Hashtable<String, ArrayList<String>>>	noteTypeValues	= new Hashtable<String, Hashtable<String, ArrayList<String>>>(
																						4);

	public static ArrayList<String> getNoteTypeValues(String type, String source) {
		return getStringsTypeSource("noteTypeValue", true, type, source, noteTypeValues);
	}

	static Hashtable<String, Hashtable<String, ArrayList<Pattern>>>	functionTagPatterns	= new Hashtable<String, Hashtable<String, ArrayList<Pattern>>>(
																							4);

	public static ArrayList<Pattern> getFunctionTagPatterns(String type, String source) {
		return getPatternsTypeSource("functionTag", true, type, source, functionTagPatterns);
	}

	static Hashtable<String, Hashtable<String, ArrayList<String>>>	functionMethodNames	= new Hashtable<String, Hashtable<String, ArrayList<String>>>(
																							4);

	public static ArrayList<String> getFunctionMethodNames(String type, String source) {
		return getStringsTypeSource("functionMethodName", true, type, source, functionMethodNames);
	}

}
