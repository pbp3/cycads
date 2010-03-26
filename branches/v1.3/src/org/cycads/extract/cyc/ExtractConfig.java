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
import org.cycads.extract.score.FileScoreSystem;
import org.cycads.extract.score.FixAndFileScoreSystem;
import org.cycads.extract.score.AnnotationScoreSystem;
import org.cycads.extract.score.SimpleAnnotationScoreSystem;
import org.cycads.extract.score.TransformScore;
import org.cycads.general.Config;
import org.cycads.extract.cyc.ExtractConfig;

/*
 * #the AnnotationGenerator parameters: AnnotationGenerator.<parameter name>[.<type>][.<source>][.regex][.<i>] #type =
 * gene|mrna|cds|exon
 */
public class ExtractConfig
{
	//	private static final String			BUNDLE_NAME		= "config";								//$NON-NLS-1$
	//
	//	private static final ResourceBundle	RESOURCE_BUNDLE	= ResourceBundle.getBundle(BUNDLE_NAME);

	private ExtractConfig() {
	}

	private static String getString(String parameterName) {
		try {
			return Config.getString("AnnotationGenerator." + parameterName);
		}
		catch (MissingResourceException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static String getStringOptional(String parameterName) {
		try {
			return Config.getString("AnnotationGenerator." + parameterName);
		}
		catch (MissingResourceException e) {
			return null;
		}
	}

	private static String getStringMandatory(String parameterName) {
		try {
			return Config.getString("AnnotationGenerator." + parameterName);
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
		return getStrings(parameterName, null);
	}

	public static boolean matches(String input, Collection<Pattern> patterns) {
		for (Pattern pattern : patterns) {
			if (pattern.matcher(input).matches()) {
				return true;
			}
		}
		return false;
	}

	public static AnnotationScoreSystem getScoreSystems(String scoreName) {
		SimpleAnnotationScoreSystem scoreSystemCollection = new SimpleAnnotationScoreSystem();
		ArrayList<Pattern> patterns = getPatterns("scoreAnnotation.methodName." + scoreName, null);
		ArrayList<String> values = getStrings("scoreAnnotation.value." + scoreName);
		ArrayList<String> fileNames = getStrings("scoreAnnotation.scoreNote.file." + scoreName);
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
			scoreSystemCollection.addTransformScore(patterns.get(i), new FixAndFileScoreSystem(
				Double.parseDouble(values.get(i)), fileScoreSystem));
		}
		return scoreSystemCollection;
	}
	
	// AnnotationClustersGetter
	public static List<String> getAnnotationClusterLocs(String clusterName) {
		return getStrings(clusterName);
	}
	
	// AnnotationWayListScoreSystem
	public static ArrayList<Pattern> getScoreMethodPatterns(String clusterName) {
		return getPatterns(clusterName, null);
	}

	public static List<TransformScore> getScoreMethodTransforms(String clusterName) {
		
	}
	
	// File
	public static String AnnotationGeneratorFileName() {
		return getStringMandatory("AnnotationGenerator.file.name");
	}
	public static String defaultFileFormat() {
		return getStringMandatory("AnnotationGenerator.file.format");
	}
	
	/* PFFile
	public static String getProductType(String type) {
		return getStringMandatory("productType." + type);
	}

	public static List<String> getPFFileNamesLocs() {
		return getLocs("name");
	}

	public static List<String> getPFFileGeneCommentLocs() {
		return getLocs("geneComment");
	}

	public static List<String> getPFFileGeneSynonymLocs() {
		return getLocs("synonym");
	}

	public static List<String> getPFFileDblinkLocs() {
		return getLocs("dbLink");
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

	public static String getPFFileFunctionCommentSeparator() {
		return getStringMandatory("function.comment.separator");
	}

	public static String getPFFileGeneCommentSeparator() {
		return getStringMandatory("geneComment.separator");
	}

	public static String getPFFileCycIdNoteType() {
		return getStringMandatory("id.noteType");
	}

	public static String pfGeneratorFileHeader() {
		return getStringOptional("header");
	}

	static ArrayList<Pattern>	dbLinkDbNameRemovePatterns;

	public static ArrayList<Pattern> getDbLinkDbNameRemovePatterns() {
		if (dbLinkDbNameRemovePatterns == null) {
			dbLinkDbNameRemovePatterns = getPatterns("dbLink.dbName.remove", null);
		}
		return dbLinkDbNameRemovePatterns;
	}

	static ArrayList<Pattern>	dbLinkDbNameChangePatterns;

	public static ArrayList<Pattern> getDbLinkDbNameChangePatterns() {
		if (dbLinkDbNameChangePatterns == null) {
			dbLinkDbNameChangePatterns = getPatterns("dbLink.dbName.change", null);
		}
		return dbLinkDbNameChangePatterns;
	}

	static ArrayList<String>	dbLinkDbNameChangeValues;

	public static ArrayList<String> getDbLinkDbNameChangeValues() {
		if (dbLinkDbNameChangeValues == null) {
			dbLinkDbNameChangeValues = getStrings("dbLink.dbName.change.newValue", null);
		}
		return dbLinkDbNameChangeValues;
	}

	static ArrayList<Pattern>	dbLinkDbNameCopyPatterns;

	public static ArrayList<Pattern> getDbLinkDbNameCopyPatterns() {
		if (dbLinkDbNameCopyPatterns == null) {
			dbLinkDbNameCopyPatterns = getPatterns("dbLink.dbName.copy", null);
		}
		return dbLinkDbNameCopyPatterns;
	}

	static ArrayList<String>	dbLinkDbNameCopyValues;

	public static ArrayList<String> getDbLinkDbNameCopyValues() {
		if (dbLinkDbNameCopyValues == null) {
			dbLinkDbNameCopyValues = getStrings("dbLink.dbName.copy.newValue", null);
		}
		return dbLinkDbNameCopyValues;
	}

	public static String getAnnotationComment(CycDbxrefAnnotationPaths annotation) {
		StringBuffer buf = new StringBuffer();
		Object[] a = {annotation.getDbName(), annotation.getAccession(), annotation.getScore()};
		buf.append(MessageFormat.format(getStringMandatory("geneComment.Score"), a));
		List<List<Annotation>> paths = annotation.getAnnotationPaths();
		if (!paths.isEmpty()) {
			buf.append(getStringMandatory("geneComment.Method"));
			List<Annotation> path = paths.get(0);
			buf.append(path.get(0).getAnnotationMethod().getName());
			String methodSeparator = getStringMandatory("geneComment.MethodSeparator");
			for (int i = 1; i < path.size(); i++) {
				buf.append(methodSeparator);
				buf.append(path.get(i).getAnnotationMethod().getName());
			}
			String pathSeparator = getStringMandatory("geneComment.PathSeparator");
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

	public static AnnotationScoreSystem getEcScoreSystems() {
		return getScoreSystems("ec");
	}

	public static AnnotationScoreSystem getGoScoreSystems() {
		return getScoreSystems("go");
	}

	public static List<String> getTypes() {
		return getStrings("annotationType");
	}
	*/

}
