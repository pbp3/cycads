package org.cycads.general;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.regex.Pattern;

import org.cycads.entities.annotation.Annotation;
import org.cycads.extract.cyc.CycDbxrefAnnotationPaths;

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

	//General

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
		return getStringOptional("gff3.loader.fileName");
	}

	public static int gff3LoaderOrganismNumber() {
		return getInt("gff3.loader.organismNumber");
	}

	public static String gff3LoaderSeqDBName() {
		return getStringOptional("gff3.loader.sequenceDbName");
	}

	public static String gff3LoaderSeqVersion() {
		return getStringOptional("gff3.loader.sequenceVersion");
	}

	public static String gff3LoaderOrganismName() {
		return getStringOptional("gff3.loader.organismName");
	}

	// PFFile Generator
	public static String pfGeneratorFileName() {
		return getStringOptional("pf.file.extract.fileName");
	}

	public static int pfGeneratorOrganismNumber() {
		return getInt("pf.file.extract.organismNumber");
	}

	public static String pfGeneratorSeqSynonym() {
		return getStringOptional("pf.file.extract.sequenceSynonym");
	}

	public static String pfGeneratorSeqVersion() {
		return getStringOptional("pf.file.extract.sequenceVersion");
	}

	public static double pfEcThreshold() {
		return getDouble("pf.file.ec.threshold");
	}

	public static double pfGoThreshold() {
		return getDouble("pf.file.go.threshold");
	}

	public static String pfGeneratorFileHeader() {
		return getStringOptional("pf.file.extract.fileHeader");
	}

	// CDS to KO Loader

	public static String cdsToKOFileComment() {
		return getStringMandatory("cdsToKO.file.comment");
	}

	public static String cdsToKOFileSeparator() {
		return getStringMandatory("cdsToKO.file.separator");
	}

	public static String cdsToKOLoaderFileName() {
		return getStringOptional("cdsToKO.loader.fileName");
	}

	public static int cdsToKOLoaderOrganismNumber() {
		return getInt("cdsToKO.loader.organismNumber");
	}

	public static String cdsToKOMethodName() {
		return getStringOptional("cdsToKO.loader.methodName");
	}

	public static String cdsToKOCDSDBName() {
		return getStringOptional("cdsToKO.loader.cdsDBName");
	}

	// subseqAnnotationLoader

	public static String subseqAnnotationLoaderFileName() {
		return getStringOptional("subseqAnnotation.loader.fileName");
	}

	public static int subseqAnnotationLoaderOrganismNumber() {
		return getInt("subseqAnnotation.loader.organismNumber");
	}

	public static String subseqAnnotationLoaderMethodName() {
		return getStringOptional("subseqAnnotation.loader.methodName");
	}

	public static int subseqAnnotationLoaderAnnotColumnIndex() {
		return getInt("subseqAnnotation.loader.annotColumnIndex");
	}

	public static String subseqAnnotationLoaderAnnotDBName() {
		return getStringOptional("subseqAnnotation.loader.annotDBName");
	}

	public static int subseqAnnotationLoaderDbxrefColumnIndex() {
		return getInt("subseqAnnotation.loader.dbxrefColumnIndex");
	}

	public static String subseqAnnotationLoaderDbxrefDBName() {
		return getStringOptional("subseqAnnotation.loader.dbxrefDBName");
	}

	public static int subseqAnnotationLoaderScoreColumnIndex() {
		return getInt("subseqAnnotation.loader.scoreColumnIndex");
	}

	// synonymLoader

	public static String synonymLoaderFileName() {
		return getStringOptional("synonym.loader.fileName");
	}

	public static int synonymLoaderOrganismNumber() {
		return getInt("synonym.loader.organismNumber");
	}

	public static int synonymLoaderAnnotColumnIndex() {
		return getInt("synonym.loader.annotColumnIndex");
	}

	public static String synonymLoaderAnnotDBName() {
		return getStringOptional("synonym.loader.annotDBName");
	}

	public static int synonymLoaderDbxrefColumnIndex() {
		return getInt("synonym.loader.dbxrefColumnIndex");
	}

	public static String synonymLoaderDbxrefDBName() {
		return getStringOptional("synonym.loader.dbxrefDBName");
	}

	//ecCDSFileGenerator

	public static String ecCDSFileGeneratorMethods(CycDbxrefAnnotationPaths ec) {
		StringBuffer buf = new StringBuffer();
		List<List<Annotation>> paths = ec.getAnnotationPaths();
		if (!paths.isEmpty()) {
			List<Annotation> path = paths.get(0);
			buf.append(path.get(0).getAnnotationMethod().getName());
			String methodSeparator = getStringMandatory("ecCDS.file.methodSeparator");
			for (int i = 1; i < path.size(); i++) {
				buf.append(methodSeparator);
				buf.append(path.get(i).getAnnotationMethod().getName());
			}
			String pathSeparator = getStringMandatory("ecCDS.file.pathSeparator");
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
		else {
			return ecCDSFileGeneratorAtributeNotPresentStr();
		}
		return buf.toString();
	}

	public static String ecCDSFileGeneratorAtributeNotPresentStr() {
		return getStringMandatory("ecCDS.file.atributeNotPresentStr");
	}

	public static char ecCDSFileGeneratorColumnSeparator() {
		return getStringMandatory("ecCDS.file.columnSeparator").charAt(0);
	}

	public static char ecCDSFileGeneratorDBLinkSeparator() {
		return getStringMandatory("ecCDS.file.dBLinkSeparator").charAt(0);
	}

	public static List<String> ecCDSFileGeneratorDBNames() {
		return getStrings("ecCDS.file.dBName");
	}

	public static String ecCDSFileGeneratorFileName() {
		return getStringMandatory("ecCDS.file.fileName");
	}

	public static int ecCDSFileGeneratorOrganismNumber() {
		return getInt("ecCDS.file.organismNumber");
	}

	public static String ecCDSFileGeneratorSeqVersion() {
		return getStringMandatory("ecCDS.file.seqVersion");
	}

	//dbxrefDbxrefAnnotationLoader

	public static String dbxrefDbxrefAnnotationLoaderFileName() {
		return getStringOptional("dbxrefDbxrefAnnotationLoader.fileName");
	}

	public static int dbxrefDbxrefAnnotationLoaderOrganismNumber() {
		return getInt("dbxrefDbxrefAnnotationLoader.organismNumber");
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

	public static String dbxrefDbxrefAnnotationLoaderSourceDelimiter() {
		return getStringMandatory("dbxrefDbxrefAnnotationLoader.sourceDelimiter");
	}

	public static String dbxrefDbxrefAnnotationLoaderTargetDelimiter() {
		return getStringMandatory("dbxrefDbxrefAnnotationLoader.targetDelimiter");
	}

	public static String dbxrefDbxrefAnnotationLoaderLineComment() {
		return getStringMandatory("dbxrefDbxrefAnnotationLoader.lineComment");
	}

	public static String dbxrefDbxrefAnnotationLoaderColumnSeparator() {
		return getStringMandatory("dbxrefDbxrefAnnotationLoader.columnSeparator");
	}

	public static String dbxrefDbxrefAnnotationLoaderSourceDbxrefsSeparator() {
		return getStringMandatory("dbxrefDbxrefAnnotationLoader.sourceDbxrefsSeparator");
	}

	public static String dbxrefDbxrefAnnotationLoaderTargetDbxrefsSeparator() {
		return getStringMandatory("dbxrefDbxrefAnnotationLoader.targetDbxrefsSeparator");
	}

}
