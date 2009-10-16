package org.cycads.extract.cyc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.MissingResourceException;
import java.util.regex.Pattern;

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.SubseqAnnotation;
import org.cycads.entities.note.Type;
import org.cycads.general.Config;

/*
 * #the gff3 parameters: gff3.file[.comment].<parameter name>[.<type>][.<source>][.regex][.<i>] #type =
 * gene|mrna|cds|exon
 * 
 */
public class PFFileConfig
{
	//	private static final String			BUNDLE_NAME		= "config";								//$NON-NLS-1$
	//
	//	private static final ResourceBundle	RESOURCE_BUNDLE	= ResourceBundle.getBundle(BUNDLE_NAME);

	private PFFileConfig() {
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

	public static List<String> getPFFileGeneCommentLocs() {
		return getStrings("geneComment.loc");
	}

	public static List<String> getPFFileGeneSynonymLocs() {
		return getStrings("synonym.loc");
	}

	public static List<String> getPFFileDblinkLocs() {
		return getStrings("dbLink.loc");
	}

	public static List<String> getPFFileECLocs() {
		return getLocs("ec");
	}

	public static List<String> getPFFileGOLocs() {
		return getLocs("go");
	}

	public static List<String> getPFFileGeneCommentAnnotationsLocs() {
		return getLocs("geneComment.annotations");
	}

	public static List<String> getLocs(String locName) {
		return getStrings(locName + ".loc");
	}

	//	public static List<String> getPFFileKOLocs() {
	//		return getStrings("ko.loc");
	//	}
	//
	//	public static String getECDbName() {
	//		return getString("pf.file.ec.dbName");
	//	}
	//
	//	public static String getGODbName() {
	//		return getString("pf.file.go.dbName");
	//	}

	//	public static String getKODbName() {
	//		return getString("pf.file.ko.dbName");
	//	}

	static ArrayList<Pattern>	dbLinkDbNameRemovePatterns;

	public static ArrayList<Pattern> getDbLinkDbNameRemovePatterns() {
		if (dbLinkDbNameRemovePatterns == null) {
			dbLinkDbNameRemovePatterns = getPatterns("pf.file.dbLink.dbName.remove", null);
		}
		return dbLinkDbNameRemovePatterns;
	}

	static ArrayList<Pattern>	dbLinkDbNameChangePatterns;

	public static ArrayList<Pattern> getDbLinkDbNameChangePatterns() {
		if (dbLinkDbNameChangePatterns == null) {
			dbLinkDbNameChangePatterns = getPatterns("pf.file.dbLink.dbName.change", null);
		}
		return dbLinkDbNameChangePatterns;
	}

	static ArrayList<String>	dbLinkDbNameChangeValues;

	public static ArrayList<String> getDbLinkDbNameChangeValues() {
		if (dbLinkDbNameChangeValues == null) {
			dbLinkDbNameChangeValues = getStrings("pf.file.dbLink.dbName.change.newValue", null);
		}
		return dbLinkDbNameChangeValues;
	}

	static ArrayList<Pattern>	dbLinkDbNameCopyPatterns;

	public static ArrayList<Pattern> getDbLinkDbNameCopyPatterns() {
		if (dbLinkDbNameCopyPatterns == null) {
			dbLinkDbNameCopyPatterns = getPatterns("pf.file.dbLink.dbName.copy", null);
		}
		return dbLinkDbNameCopyPatterns;
	}

	static ArrayList<String>	dbLinkDbNameCopyValues;

	public static ArrayList<String> getDbLinkDbNameCopyValues() {
		if (dbLinkDbNameCopyValues == null) {
			dbLinkDbNameCopyValues = getStrings("pf.file.dbLink.dbName.copy.newValue", null);
		}
		return dbLinkDbNameCopyValues;
	}

	public static String getAnnotationComment(CycDbxrefAnnotationPaths annotation) {
		StringBuffer buf = new StringBuffer();
		Object[] a = {annotation.getDbName(), annotation.getAccession(), annotation.getScore()};
		buf.append(MessageFormat.format(getStringMandatory("pf.file.geneComment.Score"), a));
		List<List<Annotation>> paths = annotation.getAnnotationPaths();
		if (!paths.isEmpty()) {
			buf.append(getStringMandatory("pf.file.geneComment.Method"));
			List<Annotation> path = paths.get(0);
			buf.append(path.get(0).getAnnotationMethod().getName());
			String methodSeparator = getStringMandatory("pf.file.geneComment.MethodSeparator");
			for (int i = 1; i < path.size(); i++) {
				buf.append(methodSeparator);
				buf.append(path.get(i).getAnnotationMethod().getName());
			}
			String pathSeparator = getStringMandatory("pf.file.geneComment.PathSeparator");
			for (int i = 1; i < paths.size(); i++) {
				buf.append(pathSeparator);
				path = paths.get(i);
				buf.append(path.get(0).getAnnotationMethod().getName());
				for (int j = 1; j < path.size(); j++) {
					buf.append(methodSeparator);
					buf.append(path.get(j).getAnnotationMethod().getName());
				}
			}
		}
		return buf.toString();
	}

	public static List<String> getPFFileFunctionsLocs() {
		return getStrings("function.loc");
	}

	public static List<String> getPFFileFunctionSynonymLocs() {
		return getStrings("functionSynonym.loc");
	}

	public static List<String> getPFFileFunctionCommentLocs() {
		return getStrings("functionComment.loc");
	}

	public static ScoreSystemCollection getEcScoreSystems() {
		return getScoreSystems("ec");
	}

	public static ScoreSystemCollection getGoScoreSystems() {
		return getScoreSystems("go");
	}

	public static ScoreSystemCollection getScoreSystems(String scoreName) {
		SimpleScoreSystemCollection scoreSystemCollection = new SimpleScoreSystemCollection();
		ArrayList<Pattern> patterns = PFFileConfig.getPatterns("pf.file.scoreAnnotation.methodName." + scoreName, null);
		ArrayList<String> values = PFFileConfig.getStrings("scoreAnnotation.value." + scoreName);
		ArrayList<String> fileNames = PFFileConfig.getStrings("scoreAnnotation.scoreNote.file." + scoreName);
		FileScoreSystem fileScoreSystem;
		String fileName;
		for (int i = 0; i < patterns.size(); i++) {
			if (fileNames.size() > i) {
				fileName = fileNames.get(i);
			}
			else {
				fileName = null;
			}
			if (fileName != null && fileName.length() > 0) {
				try {
					fileScoreSystem = new FileScoreSystem(fileName);
				}
				catch (FileNotFoundException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
				catch (IOException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
			else {
				fileScoreSystem = null;
			}
			scoreSystemCollection.addScoreSystem(patterns.get(i), new FixAndFileScoreSystem(
				Double.parseDouble(values.get(i)), fileScoreSystem));
		}
		return scoreSystemCollection;
	}

	public static List<String> getTypes() {
		return getStrings("annotationType");
	}

}
