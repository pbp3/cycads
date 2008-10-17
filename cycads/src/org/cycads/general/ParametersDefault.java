package org.cycads.general;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ParametersDefault
{
	private static final String			BUNDLE_NAME		= "parametersDefault";						//$NON-NLS-1$

	private static final ResourceBundle	RESOURCE_BUNDLE	= ResourceBundle.getBundle(BUNDLE_NAME);

	private ParametersDefault() {
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

	//LoadTaxonomy

	public static String taxonomyLoaderNodesFileName() {
		return getString("TaxonomyLoader.nodesFileName");
	}

	public static String taxonomyLoaderNamesFileName() {
		return getString("TaxonomyLoader.namesFileName");
	}

	public static int taxonomyLoaderStepShowNodeInterval() {
		return Integer.parseInt(getString("TaxonomyLoader.stepShowNodeInterval"));
	}

	public static int taxonomyLoaderStepShowNameInterval() {
		return Integer.parseInt(getString("TaxonomyLoader.stepShowNameInterval"));
	}

	public static int taxonomyLoaderStepCache() {
		return Integer.parseInt(getString("TaxonomyLoader.stepCache"));
	}

	//KOToECLoader

	public static String koToECLoaderSeparator() {
		return getString("KOToECLoader.separator");
	}

	public static String koToECLoaderComment() {
		return getString("KOToECLoader.comment");
	}

	public static String koToECLoaderDeleteExpression() {
		return getString("KOToECLoader.deleteExpression");
	}

	public static int koToECLoaderStepCache() {
		return Integer.parseInt(getString("KOToECLoader.stepCache"));
	}

	public static int koToECLoaderStepShowInterval() {
		return Integer.parseInt(getString("KOToECLoader.stepShowInterval"));
	}

	public static String koToECLoaderFileName() {
		return getString("KOToECLoader.fileName");
	}

	//KOToGOLoader

	public static String koToGOLoaderSeparator() {
		return getString("KOToGOLoader.separator");
	}

	public static String koToGOLoaderComment() {
		return getString("KOToGOLoader.comment");
	}

	public static String koToGOLoaderDeleteExpression() {
		return getString("KOToGOLoader.deleteExpression");
	}

	public static int koToGOLoaderStepCache() {
		return Integer.parseInt(getString("KOToGOLoader.stepCache"));
	}

	public static int koToGOLoaderStepShowInterval() {
		return Integer.parseInt(getString("KOToGOLoader.stepShowInterval"));
	}

	public static String koToGOLoaderFileName() {
		return getString("KOToGOLoader.fileName");
	}

}
