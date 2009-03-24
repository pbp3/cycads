package org.cycads.parser.gff3;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import org.cycads.entities.EntityFactory;
import org.cycads.entities.annotation.AnnotationMethod;

/*
 #the gff3 parameters: gff3.file[.comment].<parameter name>[.<type>][.<source>][.regex][.<i>]
 #type = gene|mrna|cds|exon

 */
public class GFF3FileConfig
{
	private static final String			BUNDLE_NAME		= "GFF3FileConfig";						//$NON-NLS-1$

	private static final ResourceBundle	RESOURCE_BUNDLE	= ResourceBundle.getBundle(BUNDLE_NAME);

	private GFF3FileConfig() {
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

	public static final String	geneType	= getStringMandatory("gff3.file.typeValue.gene");
	public static final String	cdsType		= getStringMandatory("gff3.file.typeValue.cds");
	public static final String	mrnaType	= getStringMandatory("gff3.file.typeValue.mrna");
	public static final String	exonType	= getStringMandatory("gff3.file.typeValue.exon");

	public static boolean isTypeValue(String input, String type, String source) {
		ArrayList<Pattern> patterns = getPatterns("typeValue", false, type, source);
		for (Pattern pattern : patterns) {
			if (pattern.matcher(input).matches()) {
				return true;
			}
		}
		return false;
	}

	public static boolean isGene(String type, String source) {
		return isTypeValue(type, geneType, source);
	}

	public static boolean isMRNA(String type, String source) {
		return isTypeValue(type, mrnaType, source);
	}

	public static boolean isCDS(String type, String source) {
		return isTypeValue(type, cdsType, source);
	}

	public static boolean isExon(String type, String source) {
		return isTypeValue(type, exonType, source);
	}

	public static AnnotationMethod getAnnotationMethod(EntityFactory< ? , ? , ? , ? > factory, String type,
			String source) {
		String methodName = getString("methodName", false, type, source);
		if (methodName == null || methodName.length() == 0) {
			methodName = source;
		}
		return factory.getAnnotationMethod(methodName);
	}

	public static AnnotationMethod getAnnotationMethod(EntityFactory< ? , ? , ? , ? > factory, GFF3Record record) {
		return getAnnotationMethod(factory, record.getType(), record.getSource());
	}

	public static ArrayList<Pattern> getParentAccessionTagPatterns(String type, String source) {
		return getPatterns("parentAccessionTag", true, type, source);
	}

	public static ArrayList<String> getParentDbNames(String type, String source) {
		return getStrings("parentDbName", true, type, source);
	}
}
