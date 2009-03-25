package org.cycads.general;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Config
{
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

	//GFF3 Loader

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

}
