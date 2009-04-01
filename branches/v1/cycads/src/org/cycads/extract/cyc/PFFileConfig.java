package org.cycads.extract.cyc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import org.cycads.entities.annotation.SubseqAnnotation;
import org.cycads.entities.note.Type;

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
			values = new ArrayList<String>();
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

	public static String getFirstString(String parameterName) {
		ArrayList<String> values = getStrings(parameterName);
		if (values == null || values.isEmpty()) {
			return null;
		}
		return values.get(0);
	}

	public static ArrayList<String> getStrings(String parameterName) {
		parameterName = "pf.file." + parameterName;
		ArrayList<String> values = getStrings(parameterName, null);
		return values;
	}

	public static boolean matches(String input, Collection<Pattern> patterns) {
		for (Pattern pattern : patterns) {
			if (pattern.matcher(input).matches()) {
				return true;
			}
		}
		return false;
	}

	public static String getProductType(String type) {
		return getStringMandatory("pf.file.productType." + type);
	}

	public static String getProductType(SubseqAnnotation< ? , ? , ? , ? , ? > annot) {
		for (Type type : annot.getTypes()) {
			String typeStr = getProductType(type.getName());
			if (typeStr != null && typeStr.length() != 0) {
				return typeStr;
			}
		}
		return null;
	}

	public static List<String> getPFFileNamesLocs() {
		return getStrings("name.loc");
	}

}
