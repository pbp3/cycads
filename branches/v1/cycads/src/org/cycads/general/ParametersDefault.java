package org.cycads.general;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ParametersDefault {
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

	// General

	public static int getNoteTypeId() {
		return getInt("general.type.noteTypeId");
	}

	public static int getAnnotationTypeId() {
		return getInt("general.type.annotationTypeId");
	}
}
