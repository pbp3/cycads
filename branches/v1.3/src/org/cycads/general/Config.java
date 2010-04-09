package org.cycads.general;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.regex.Pattern;

import org.cycads.extract.score.TransformScore;

public class Config
{
	private static final String		BUNDLE_NAME		= "config.properties";	//$NON-NLS-1$

	public static final Properties	RESOURCE_BUNDLE	= new Properties();

	static {
		try {
			RESOURCE_BUNDLE.load(new FileInputStream(BUNDLE_NAME));
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

	//	ResourceBundle.getBundle(BUNDLE_NAME);

	private Config() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getProperty(key);
		}
		catch (MissingResourceException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getStringOptional(String key) {
		try {
			return RESOURCE_BUNDLE.getProperty(key);
		}
		catch (MissingResourceException e) {
			return null;
		}
	}

	public static String getStringMandatory(String key) {
		try {
			String ret = RESOURCE_BUNDLE.getProperty(key);
			if (ret == null) {
				throw new MissingResourceException("", BUNDLE_NAME, key);
			}
			return ret;
		}
		catch (MissingResourceException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private static int getInt(String key) {
		return Integer.parseInt(getStringMandatory(key));
	}

	private static double getDouble(String key) {
		return Double.parseDouble(getStringMandatory(key));
	}

	public static String transform(String key, List<Pattern> keyPatterns, List<String> values) {
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
		tag += ".regex";
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
		if ((str = getStringOptional(tag)) != null) {
			values.add(str);
		}
		while ((str = getStringOptional(tag + "." + i)) != null) {
			values.add(str);
			i++;
		}
		values.trimToSize();
		return values;
	}

	private static List<TransformScore> getTransformScores(String tag) {
		String tagWeight = tag + ".weight";
		String tagScaleFile = tag + ".file";

		// todo List<TransformScore> 
	}

	//General
	public static String getSQLDriverName() {
		return getStringMandatory("general.sql.driverName");
	}

	public static String getSQLConnectionUrl() {
		return getStringMandatory("general.sql.connectionUrl");
	}

	public static String getSQLUser() {
		return getStringMandatory("general.sql.usr");
	}

	public static String getSQLPassword() {
		return getStringMandatory("general.sql.pass");
	}

	// KOLoader

	public static String koLoaderFileName() {
		return getStringOptional("KOLoader.fileName");
	}

	public static String getKOLoaderMethodDBLinkName() {
		return getStringOptional("KOLoader.DBLinkMethodName");
	}

	public static String getKOLoaderECAnnotationMethodName() {
		return getStringOptional("KOLoader.ECAnnotationMethodName");
	}

	// GFF3 Loader

	public static String gff3LoaderFileName() {
		return getStringOptional("GFF3Loader.loader.fileName");
	}

	public static int gff3LoaderOrganismNumber() {
		return getInt("GFF3Loader.loader.organismNumber");
	}

	public static String gff3LoaderSeqDBName() {
		return getStringOptional("GFF3Loader.loader.sequenceDbName");
	}

	public static String gff3LoaderSeqVersion() {
		return getStringOptional("GFF3Loader.loader.sequenceVersion");
	}

	public static String gff3LoaderOrganismName() {
		return getStringOptional("GFF3Loader.loader.organismName");
	}

	// Annotation Generator
	public static String annotationGeneratorFileName() {
		return getStringOptional("AnnotationGenerator.fileName");
	}

	public static int annotationGeneratorOrganismNumber() {
		return getInt("AnnotationGenerator.organismNumber");
	}

	public static String annotationGeneratorSeqSynonym() {
		return getStringOptional("AnnotationGenerator.sequenceSynonym");
	}

	public static String annotationGeneratorSeqVersion() {
		return getStringOptional("AnnotationGenerator.sequenceVersion");
	}

	public static double annotationGeneratorEcThreshold() {
		return getDouble("AnnotationGenerator.ec.threshold");
	}

	public static double annotationGeneratorGoThreshold() {
		return getDouble("AnnotationGenerator.go.threshold");
	}

	// for further datas export formats
	public static String annotationGeneratorOutFormat() {
		return getStringOptional("AnnotationGenerator.outFormat");
	}

	// PFFile
	
	public static String annotationGeneratorPfFileHeader() {
		return getStringOptional("AnnotationGenerator.pf.header");
	}
	
	public static List<String> getAnnotationClusterLocs(String clusterName) {
		return getStrings("AnnotationGenerator." + clusterName + ".loc");
	}

	public static List<String> getClusterReplaceRegex(String clusterName) {
		return getStrings("AnnotationGenerator." + clusterName + ".replace.regex");
	}

	public static List<String> getClusterReplaceReplacement(String clusterName) {
		return getStrings("AnnotationGenerator." + clusterName + ".replace.replacement");
	}

	public static String getClusterMsgChangeTarget(String clusterName) {
		return getStringOptional("AnnotationGenerator." + clusterName + ".msgChange");
	}

	public static List<Pattern> getScoreMethodPatterns(String clusterName) {
		return getPatterns("AnnotationGenerator." + clusterName + ".score.method");
	}

	public static List<TransformScore> getScoreMethodTransforms(String clusterName) {
		return getTransformScores("AnnotationGenerator." + clusterName + ".scoretransform");
		// .file .weight
		// weight : multiplydbl
		// file : scale
	}

	//	// CDS to KO Loader
	//
	//	public static String cdsToKOFileComment() {
	//		return getStringMandatory("cdsToKO.file.comment");
	//	}
	//
	//	public static String cdsToKOFileSeparator() {
	//		return getStringMandatory("cdsToKO.file.separator");
	//	}
	//
	//	public static String cdsToKOLoaderFileName() {
	//		return getStringOptional("cdsToKO.loader.fileName");
	//	}
	//
	//	public static int cdsToKOLoaderOrganismNumber() {
	//		return getInt("cdsToKO.loader.organismNumber");
	//	}
	//
	//	public static String cdsToKOMethodName() {
	//		return getStringOptional("cdsToKO.loader.methodName");
	//	}
	//
	//	public static String cdsToKOCDSDBName() {
	//		return getStringOptional("cdsToKO.loader.cdsDBName");
	//	}
	//
	// subseqDbxrefAnnotationLoader

	public static String subseqDbxrefAnnotationLoaderFileName() {
		return getStringOptional("subseqDbxrefAnnotationLoader.fileName");
	}

	public static int subseqDbxrefAnnotationLoaderOrganismNumber() {
		return getInt("subseqDbxrefAnnotationLoader.organismNumber");
	}

	public static String subseqDbxrefAnnotationLoaderMethodName() {
		return getStringOptional("subseqDbxrefAnnotationLoader.methodName");
	}

	public static int subseqDbxrefAnnotationLoaderAnnotColumnIndex() {
		return getInt("subseqDbxrefAnnotationLoader.subseqColumnIndex");
	}

	public static String subseqDbxrefAnnotationLoaderAnnotDBName() {
		return getStringOptional("subseqDbxrefAnnotationLoader.subseqDBName");
	}

	public static int subseqDbxrefAnnotationLoaderDbxrefColumnIndex() {
		return getInt("subseqDbxrefAnnotationLoader.dbxrefColumnIndex");
	}

	public static String subseqDbxrefAnnotationLoaderDbxrefDBName() {
		return getStringOptional("subseqDbxrefAnnotationLoader.dbxrefDBName");
	}

	public static int subseqDbxrefAnnotationLoaderScoreColumnIndex() {
		return getInt("subseqDbxrefAnnotationLoader.scoreColumnIndex");
	}

	public static int subseqDbxrefAnnotationLoaderMethodColumnIndex() {
		return getInt("subseqDbxrefAnnotationLoader.methodColumnIndex");
	}

	public static Collection<String> subseqDbxrefAnnotationLoaderAssocTypeNames() {
		Collection<String> ret = getStrings("subseqDbxrefAnnotationLoader.assocTypeName");
		ArrayList<String> empty = new ArrayList<String>(1);
		empty.add("");
		ret.removeAll(empty);
		return ret;
	}

	public static String subseqDbxrefAnnotationLoaderSourcesDelimiter() {
		return getStringMandatory("subseqDbxrefAnnotationLoader.file.sourcesDelimiter");
	}

	public static String subseqDbxrefAnnotationLoaderTargetsDelimiter() {
		return getStringMandatory("subseqDbxrefAnnotationLoader.file.targetsDelimiter");
	}

	public static String subseqDbxrefAnnotationLoaderSourceColumnDelimiter() {
		return getStringMandatory("subseqDbxrefAnnotationLoader.file.sourceColumnDelimiter");
	}

	public static String subseqDbxrefAnnotationLoaderTargetColumnDelimiter() {
		return getStringMandatory("subseqDbxrefAnnotationLoader.file.targetColumnDelimiter");
	}

	public static String subseqDbxrefAnnotationLoaderScoreDelimiter() {
		return getStringMandatory("subseqDbxrefAnnotationLoader.file.scoreDelimiter");
	}

	public static String subseqDbxrefAnnotationLoaderMethodDelimiter() {
		return getStringMandatory("subseqDbxrefAnnotationLoader.file.methodDelimiter");
	}

	public static String subseqDbxrefAnnotationLoaderLineComment() {
		return getStringMandatory("subseqDbxrefAnnotationLoader.file.lineComment");
	}

	public static String subseqDbxrefAnnotationLoaderColumnSeparator() {
		return getStringMandatory("subseqDbxrefAnnotationLoader.file.columnSeparator");
	}

	public static String subseqDbxrefAnnotationLoaderSourcesSeparator() {
		return getStringMandatory("subseqDbxrefAnnotationLoader.file.sourcesSeparator");
	}

	public static String subseqDbxrefAnnotationLoaderTargetsSeparator() {
		return getStringMandatory("subseqDbxrefAnnotationLoader.file.targetsSeparator");
	}

	public static Pattern subseqDbxrefAnnotationLoaderRemoveLineRegex() {
		return Pattern.compile(getStringMandatory("subseqDbxrefAnnotationLoader.file.removeLineRegex"));
	}

	public static List<Pattern> subseqDbxrefAnnotationLoaderMethodPatterns() {
		return getPatterns("subseqDbxrefAnnotationLoader.file.method");
	}

	public static List<String> subseqDbxrefAnnotationLoaderMethodNames() {
		return getStrings("subseqDbxrefAnnotationLoader.file.methodName");
	}

	//dbxrefDbxrefAnnotationLoader

	public static String dbxrefDbxrefAnnotationLoaderFileName() {
		return getStringOptional("dbxrefDbxrefAnnotationLoader.fileName");
	}

	public static String dbxrefDbxrefAnnotationLoaderMethodName() {
		return getStringOptional("dbxrefDbxrefAnnotationLoader.methodName");
	}

	public static int dbxrefDbxrefAnnotationLoaderSourceColumnIndex() {
		return getInt("dbxrefDbxrefAnnotationLoader.sourceColumnIndex");
	}

	public static String dbxrefDbxrefAnnotationLoaderSourceDBName() {
		return getStringOptional("dbxrefDbxrefAnnotationLoader.sourceDBName");
	}

	public static int dbxrefDbxrefAnnotationLoaderTargetColumnIndex() {
		return getInt("dbxrefDbxrefAnnotationLoader.targetColumnIndex");
	}

	public static String dbxrefDbxrefAnnotationLoaderTargetDBName() {
		return getStringOptional("dbxrefDbxrefAnnotationLoader.targetDBName");
	}

	public static int dbxrefDbxrefAnnotationLoaderScoreColumnIndex() {
		return getInt("dbxrefDbxrefAnnotationLoader.scoreColumnIndex");
	}

	public static int dbxrefDbxrefAnnotationLoaderMethodColumnIndex() {
		return getInt("dbxrefDbxrefAnnotationLoader.methodColumnIndex");
	}

	public static Collection<String> dbxrefDbxrefAnnotationLoaderAssocTypeNames() {
		Collection<String> ret = getStrings("dbxrefDbxrefAnnotationLoader.assocTypeName");
		ArrayList<String> empty = new ArrayList<String>(1);
		empty.add("");
		ret.removeAll(empty);
		return ret;
	}

	public static String dbxrefDbxrefAnnotationLoaderSourcesDelimiter() {
		return getStringMandatory("dbxrefDbxrefAnnotationLoader.file.sourcesDelimiter");
	}

	public static String dbxrefDbxrefAnnotationLoaderTargetsDelimiter() {
		return getStringMandatory("dbxrefDbxrefAnnotationLoader.file.targetsDelimiter");
	}

	public static String dbxrefDbxrefAnnotationLoaderSourceColumnDelimiter() {
		return getStringMandatory("dbxrefDbxrefAnnotationLoader.file.sourceColumnDelimiter");
	}

	public static String dbxrefDbxrefAnnotationLoaderTargetColumnDelimiter() {
		return getStringMandatory("dbxrefDbxrefAnnotationLoader.file.targetColumnDelimiter");
	}

	public static String dbxrefDbxrefAnnotationLoaderScoreDelimiter() {
		return getStringMandatory("dbxrefDbxrefAnnotationLoader.file.scoreDelimiter");
	}

	public static String dbxrefDbxrefAnnotationLoaderMethodDelimiter() {
		return getStringMandatory("dbxrefDbxrefAnnotationLoader.file.methodDelimiter");
	}

	public static String dbxrefDbxrefAnnotationLoaderLineComment() {
		return getStringMandatory("dbxrefDbxrefAnnotationLoader.file.lineComment");
	}

	public static String dbxrefDbxrefAnnotationLoaderColumnSeparator() {
		return getStringMandatory("dbxrefDbxrefAnnotationLoader.file.columnSeparator");
	}

	public static String dbxrefDbxrefAnnotationLoaderSourcesSeparator() {
		return getStringMandatory("dbxrefDbxrefAnnotationLoader.file.sourcesSeparator");
	}

	public static String dbxrefDbxrefAnnotationLoaderTargetsSeparator() {
		return getStringMandatory("dbxrefDbxrefAnnotationLoader.file.targetsSeparator");
	}

	public static Pattern dbxrefDbxrefAnnotationLoaderRemoveLineRegex() {
		return Pattern.compile(getStringMandatory("dbxrefDbxrefAnnotationLoader.file.removeLineRegex"));
	}

	public static List<Pattern> dbxrefDbxrefAnnotationLoaderMethodPatterns() {
		return getPatterns("dbxrefDbxrefAnnotationLoader.file.method");
	}

	public static List<String> dbxrefDbxrefAnnotationLoaderMethodNames() {
		return getStrings("dbxrefDbxrefAnnotationLoader.file.methodName");
	}

	//entityDbxrefAnnotationLoader

	public static String entityDbxrefAnnotationLoaderFileName() {
		return getStringOptional("entityDbxrefAnnotationLoader.fileName");
	}

	public static String entityDbxrefAnnotationLoaderMethodName() {
		return getStringOptional("entityDbxrefAnnotationLoader.methodName");
	}

	public static int entityDbxrefAnnotationLoaderSourceColumnIndex() {
		return getInt("entityDbxrefAnnotationLoader.sourceColumnIndex");
	}

	public static String entityDbxrefAnnotationLoaderSourceDBName() {
		return getStringOptional("entityDbxrefAnnotationLoader.sourceDBName");
	}

	public static int entityDbxrefAnnotationLoaderTargetColumnIndex() {
		return getInt("entityDbxrefAnnotationLoader.targetColumnIndex");
	}

	public static String entityDbxrefAnnotationLoaderTargetDBName() {
		return getStringOptional("entityDbxrefAnnotationLoader.targetDBName");
	}

	public static int entityDbxrefAnnotationLoaderScoreColumnIndex() {
		return getInt("entityDbxrefAnnotationLoader.scoreColumnIndex");
	}

	public static int entityDbxrefAnnotationLoaderMethodColumnIndex() {
		return getInt("entityDbxrefAnnotationLoader.methodColumnIndex");
	}

	public static Collection<String> entityDbxrefAnnotationLoaderAssocTypeNames() {
		Collection<String> ret = getStrings("entityDbxrefAnnotationLoader.assocTypeName");
		ArrayList<String> empty = new ArrayList<String>(1);
		empty.add("");
		ret.removeAll(empty);
		return ret;
	}

	public static String entityDbxrefAnnotationLoaderSourcesDelimiter() {
		return getStringMandatory("entityDbxrefAnnotationLoader.file.sourcesDelimiter");
	}

	public static String entityDbxrefAnnotationLoaderTargetsDelimiter() {
		return getStringMandatory("entityDbxrefAnnotationLoader.file.targetsDelimiter");
	}

	public static String entityDbxrefAnnotationLoaderSourceColumnDelimiter() {
		return getStringMandatory("entityDbxrefAnnotationLoader.file.sourceColumnDelimiter");
	}

	public static String entityDbxrefAnnotationLoaderTargetColumnDelimiter() {
		return getStringMandatory("entityDbxrefAnnotationLoader.file.targetColumnDelimiter");
	}

	public static String entityDbxrefAnnotationLoaderScoreDelimiter() {
		return getStringMandatory("entityDbxrefAnnotationLoader.file.scoreDelimiter");
	}

	public static String entityDbxrefAnnotationLoaderMethodDelimiter() {
		return getStringMandatory("entityDbxrefAnnotationLoader.file.methodDelimiter");
	}

	public static String entityDbxrefAnnotationLoaderLineComment() {
		return getStringMandatory("entityDbxrefAnnotationLoader.file.lineComment");
	}

	public static String entityDbxrefAnnotationLoaderColumnSeparator() {
		return getStringMandatory("entityDbxrefAnnotationLoader.file.columnSeparator");
	}

	public static String entityDbxrefAnnotationLoaderSourcesSeparator() {
		return getStringMandatory("entityDbxrefAnnotationLoader.file.sourcesSeparator");
	}

	public static String entityDbxrefAnnotationLoaderTargetsSeparator() {
		return getStringMandatory("entityDbxrefAnnotationLoader.file.targetsSeparator");
	}

	public static Pattern entityDbxrefAnnotationLoaderRemoveLineRegex() {
		return Pattern.compile(getStringMandatory("entityDbxrefAnnotationLoader.file.removeLineRegex"));
	}

	public static List<Pattern> entityDbxrefAnnotationLoaderMethodPatterns() {
		return getPatterns("entityDbxrefAnnotationLoader.file.method");
	}

	public static List<String> entityDbxrefAnnotationLoaderMethodNames() {
		return getStrings("entityDbxrefAnnotationLoader.file.methodName");
	}

	// entitySynonymLoader

	public static String entitySynonymLoaderFileName() {
		return getStringOptional("entitySynonymLoader.fileName");
	}

	public static int entitySynonymLoaderOrganismNumber() {
		return getInt("entitySynonymLoader.organismNumber");
	}

	public static int entitySynonymLoaderSourceColumnIndex() {
		return getInt("entitySynonymLoader.annotColumnIndex");
	}

	public static String entitySynonymLoaderSourceDBName() {
		return getStringOptional("entitySynonymLoader.annotDBName");
	}

	public static int entitySynonymLoaderSynonymColumnIndex() {
		return getInt("entitySynonymLoader.dbxrefColumnIndex");
	}

	public static String entitySynonymLoaderSynonymDBName() {
		return getStringOptional("entitySynonymLoader.dbxrefDBName");
	}

	public static String entitySynonymLoaderSourcesDelimiter() {
		return getStringMandatory("entitySynonymLoader.file.sourcesDelimiter");
	}

	public static String entitySynonymLoaderSynonymsDelimiter() {
		return getStringMandatory("entitySynonymLoader.file.synonymsDelimiter");
	}

	public static String entitySynonymLoaderSourceColumnDelimiter() {
		return getStringMandatory("entitySynonymLoader.file.sourceColumnDelimiter");
	}

	public static String entitySynonymLoaderSynonymColumnDelimiter() {
		return getStringMandatory("entitySynonymLoader.file.synonymColumnDelimiter");
	}

	public static String entitySynonymLoaderLineComment() {
		return getStringMandatory("entitySynonymLoader.file.lineComment");
	}

	public static String entitySynonymLoaderColumnSeparator() {
		return getStringMandatory("entitySynonymLoader.file.columnSeparator");
	}

	public static String entitySynonymLoaderSourcesSeparator() {
		return getStringMandatory("entitySynonymLoader.file.sourcesSeparator");
	}

	public static String entitySynonymLoaderSynonymsSeparator() {
		return getStringMandatory("entitySynonymLoader.file.synonymsSeparator");
	}

	public static Pattern entitySynonymLoaderRemoveLineRegex() {
		return Pattern.compile(getStringMandatory("entitySynonymLoader.file.removeLineRegex"));
	}

	// dbxrefSynonymLoader

	public static String dbxrefSynonymLoaderFileName() {
		return getStringOptional("dbxrefSynonymLoader.fileName");
	}

	public static int dbxrefSynonymLoaderOrganismNumber() {
		return getInt("dbxrefSynonymLoader.organismNumber");
	}

	public static int dbxrefSynonymLoaderSourceColumnIndex() {
		return getInt("dbxrefSynonymLoader.annotColumnIndex");
	}

	public static String dbxrefSynonymLoaderSourceDBName() {
		return getStringOptional("dbxrefSynonymLoader.annotDBName");
	}

	public static int dbxrefSynonymLoaderSynonymColumnIndex() {
		return getInt("dbxrefSynonymLoader.dbxrefColumnIndex");
	}

	public static String dbxrefSynonymLoaderSynonymDBName() {
		return getStringOptional("dbxrefSynonymLoader.dbxrefDBName");
	}

	public static String dbxrefSynonymLoaderSourcesDelimiter() {
		return getStringMandatory("dbxrefSynonymLoader.file.sourcesDelimiter");
	}

	public static String dbxrefSynonymLoaderSynonymsDelimiter() {
		return getStringMandatory("dbxrefSynonymLoader.file.synonymsDelimiter");
	}

	public static String dbxrefSynonymLoaderSourceColumnDelimiter() {
		return getStringMandatory("dbxrefSynonymLoader.file.sourceColumnDelimiter");
	}

	public static String dbxrefSynonymLoaderSynonymColumnDelimiter() {
		return getStringMandatory("dbxrefSynonymLoader.file.synonymColumnDelimiter");
	}

	public static String dbxrefSynonymLoaderLineComment() {
		return getStringMandatory("dbxrefSynonymLoader.file.lineComment");
	}

	public static String dbxrefSynonymLoaderColumnSeparator() {
		return getStringMandatory("dbxrefSynonymLoader.file.columnSeparator");
	}

	public static String dbxrefSynonymLoaderSourcesSeparator() {
		return getStringMandatory("dbxrefSynonymLoader.file.sourcesSeparator");
	}

	public static String dbxrefSynonymLoaderSynonymsSeparator() {
		return getStringMandatory("dbxrefSynonymLoader.file.synonymsSeparator");
	}

	public static Pattern dbxrefSynonymLoaderRemoveLineRegex() {
		return Pattern.compile(getStringMandatory("dbxrefSynonymLoader.file.removeLineRegex"));
	}

	//	//ecCDSFileGenerator
	//
	//	public static String ecCDSFileGeneratorMethods(CycDbxrefAnnotationPaths ec) {
	//		StringBuffer buf = new StringBuffer();
	//		List<List<Annotation>> paths = ec.getAnnotationPaths();
	//		if (!paths.isEmpty()) {
	//			List<Annotation> path = paths.get(0);
	//			buf.append(path.get(0).getAnnotationMethod().getName());
	//			String methodSeparator = getStringMandatory("ecCDS.file.methodSeparator");
	//			for (int i = 1; i < path.size(); i++) {
	//				buf.append(methodSeparator);
	//				buf.append(path.get(i).getAnnotationMethod().getName());
	//			}
	//			String pathSeparator = getStringMandatory("ecCDS.file.pathSeparator");
	//			for (int i = 1; i < paths.size(); i++) {
	//				buf.append(pathSeparator);
	//				path = paths.get(i);
	//				buf.append(path.get(0).getAnnotationMethod().getName());
	//				for (int j = 1; j < path.size(); j++) {
	//					buf.append(methodSeparator);
	//					buf.append(path.get(j).getAnnotationMethod().getName());
	//				}
	//			}
	//		}
	//		else {
	//			return ecCDSFileGeneratorAtributeNotPresentStr();
	//		}
	//		return buf.toString();
	//	}
	//
	//	public static String ecCDSFileGeneratorAtributeNotPresentStr() {
	//		return getStringMandatory("ecCDS.file.atributeNotPresentStr");
	//	}
	//
	//	public static char ecCDSFileGeneratorColumnSeparator() {
	//		return getStringMandatory("ecCDS.file.columnSeparator").charAt(0);
	//	}
	//
	//	public static char ecCDSFileGeneratorDBLinkSeparator() {
	//		return getStringMandatory("ecCDS.file.dBLinkSeparator").charAt(0);
	//	}
	//
	//	public static List<String> ecCDSFileGeneratorDBNames() {
	//		return getStrings("ecCDS.file.dBName");
	//	}
	//
	//	public static String ecCDSFileGeneratorFileName() {
	//		return getStringMandatory("ecCDS.file.fileName");
	//	}
	//
	//	public static int ecCDSFileGeneratorOrganismNumber() {
	//		return getInt("ecCDS.file.organismNumber");
	//	}
	//
	//	public static String ecCDSFileGeneratorSeqVersion() {
	//		return getStringMandatory("ecCDS.file.seqVersion");
	//	}
	//
}
