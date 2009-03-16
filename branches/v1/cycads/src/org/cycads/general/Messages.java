package org.cycads.general;

import java.text.MessageFormat;
import java.util.Date;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
	private static final String			BUNDLE_NAME		= "messages";								//$NON-NLS-1$

	private static final ResourceBundle	RESOURCE_BUNDLE	= ResourceBundle.getBundle(BUNDLE_NAME);

	private Messages() {
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

	// Progress

	public static String getProgressStartTimeMsg() {
		return getProgressStartTimeMsg(new Date());
	}

	public static String getProgressStartTimeMsg(Date date) {
		Object[] a = {date};
		return MessageFormat.format(getString("Progress.startTime"), a);
	}

	public static String getProgressEndTimeMsg() {
		return getProgressEndTimeMsg(new Date());
	}

	public static String getProgressEndTimeMsg(Date date) {
		Object[] a = {date};
		return MessageFormat.format(getString("Progress.endTime"), a);
	}

	public static String getProgressTotalTimeMsg(long time) {
		Object[] a = {time};
		return MessageFormat.format(getString("Progress.totalTime"), a);
	}

	public static String getProgressSeparatorObjects() {
		return getString("Progress.separatorObject");
	}

	public static String getProgressTotalStepsMsg(int totalSteps) {
		Object[] a = {totalSteps};
		return MessageFormat.format(getString("Progress.totalSteps"), a);
	}

	// Exception

	public static String getExceptionFileParserLineMsg(int lineNumber, String line) {
		Object[] a = {lineNumber, line};
		return MessageFormat.format(getStringMandatory("Exception.fileParser.line"), a);
	}

	// KOLOader

	public static String koLoaderChooseFile() {
		return getString("KOLoader.chooseFile");
	}

	public static int koLoaderStepShowInterval() {
		return getInt("KOLoader.stepShowInterval");
	}

	public static String koLoaderFinalMsg(int step) {
		Object[] a = {step};
		return MessageFormat.format(getStringMandatory("KOLoader.final"), a);

	}

	public static String koLoaderInitMsg(String path) {
		Object[] a = {path};
		return MessageFormat.format(getStringMandatory("KOLoader.init"), a);
	}

}
