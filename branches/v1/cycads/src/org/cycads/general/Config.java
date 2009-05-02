package org.cycads.general;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Config {
	private static final String			BUNDLE_NAME		= "config";								//$NON-NLS-1$

	private static final ResourceBundle	RESOURCE_BUNDLE	= ResourceBundle.getBundle(BUNDLE_NAME);

	private Config() {
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

	private static double getDouble(String key) {
		return Double.parseDouble(getString(key));
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
		return getStringOptional("pffile.extract.fileName");
	}

	public static int pfGeneratorOrganismNumber() {
		return getInt("pffile.extract.organismNumber");
	}

	public static String pfGeneratorSeqVersion() {
		return getStringOptional("pffile.extract.sequenceVersion");
	}

	public static double pfGeneratorThreshold() {
		return getDouble("pffile.extract.threshold");
	}

	public static String pfGeneratorFileHeader() {
		return getStringOptional("pffile.extract.fileHeader");
	}

	public static String pfGeneratorOrganismName() {
		return getStringOptional("pffile.extract.organismName");
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

	// cdsToDbxrefLoader

	public static String cdsToDbxrefLoaderFileName() {
		return getStringOptional("cdsToDbxref.loader.fileName");
	}

	public static int cdsToDbxrefLoaderOrganismNumber() {
		return getInt("cdsToDbxref.loader.organismNumber");
	}

	public static String cdsToDbxrefLoaderMethodName() {
		return getStringOptional("cdsToDbxref.loader.methodName");
	}

	public static int cdsToDbxrefLoaderCDSColumnIndex() {
		return getInt("cdsToDbxref.loader.cdsColumnIndex");
	}

	public static String cdsToDbxrefCDSDBName() {
		return getStringOptional("cdsToDbxref.loader.cdsDBName");
	}

	public static int cdsToDbxrefLoaderDbxrefColumnIndex() {
		return getInt("cdsToDbxref.loader.dbxrefColumnIndex");
	}

	public static String cdsToDbxrefLoaderDbxrefDBName() {
		return getStringOptional("cdsToDbxref.loader.dbxrefDBName");
	}

	public static int cdsToDbxrefLoaderScoreColumnIndex() {
		return getInt("cdsToDbxref.loader.scoreColumnIndex");
	}

	public static String cdsToDbxrefFileComment() {
		return getStringMandatory("cdsToDbxref.file.comment");
	}

	public static String cdsToDbxrefFileSeparator() {
		return getStringMandatory("cdsToDbxref.file.separator");
	}

	public static String cdsToDbxrefTextDelimiter() {
		return getStringMandatory("cdsToDbxref.file.textDelimiter");
	}

}
