package org.cycads.general;

import java.text.MessageFormat;
import java.util.Date;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages
{
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

	//Progress

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

	//LoadTaxonomy

	public static String taxonomyLoaderNodesFinalMsg() {
		return getString("TaxonomyLoader.nodesFinal");
	}

	public static String taxonomyLoaderNodesFinalMsg(int totalNodes) {
		Object[] a = {totalNodes};
		return MessageFormat.format(taxonomyLoaderNodesFinalMsg(), a);
	}

	public static String taxonomyLoaderNamesFinalMsg() {
		return getString("TaxonomyLoader.namesFinal");
	}

	public static String taxonomyLoaderNamesFinalMsg(int totalNames) {
		Object[] a = {totalNames};
		return MessageFormat.format(taxonomyLoaderNamesFinalMsg(), a);
	}

	public static String taxonomyLoaderNodesInitMsg(String fileNodesName) {
		Object[] a = {fileNodesName};
		return MessageFormat.format(getString("TaxonomyLoader.nodesInit"), a);
	}

	public static String taxonomyLoaderNamesInitMsg(String fileNamesName) {
		Object[] a = {fileNamesName};
		return MessageFormat.format(getString("TaxonomyLoader.namesInit"), a);
	}

	public static String taxonomyLoaderChooseNodesFile() {
		return getString("TaxonomyLoader.chooseNodesFile");
	}

	public static String taxonomyLoaderChooseNamesFile() {
		return getString("TaxonomyLoader.chooseNamesFile");
	}

	//koToECLoader

	public static String koToECChooseFile() {
		return getString("KOToECLoader.chooseFile");
	}

	public static String koToECLoaderParsingError(String line) {
		Object[] a = {line};
		return MessageFormat.format(getString("KOToECLoader.ParsingError"), a);
	}

	public static String koToECLoaderFinalMsg() {
		return getString("KOToECLoader.final");
	}

	public static String koToECLoaderInitMsg(String fileKo2EcName) {
		Object[] a = {fileKo2EcName};
		return MessageFormat.format(getString("KOToECLoader.init"), a);
	}

	//koToGOLoader

	public static String koToGOChooseFile() {
		return getString("KOToGOLoader.chooseFile");
	}

	public static String koToGOLoaderParsingError(String line) {
		Object[] a = {line};
		return MessageFormat.format(getString("KOToGOLoader.ParsingError"), a);
	}

	public static String koToGOLoaderFinalMsg() {
		return getString("KOToGOLoader.final");
	}

	public static String koToGOLoaderInitMsg(String fileKo2GOName) {
		Object[] a = {fileKo2GOName};
		return MessageFormat.format(getString("KOToGOLoader.init"), a);
	}

}
