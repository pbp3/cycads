/*
 * Created on 20/10/2008
 */
package org.cycads.ui;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.cycads.entities.factory.EntityFactory;
import org.cycads.entities.sequence.Organism;

public class Tools
{

	public static File getFileToOpen(String[] args, int pos, String fileNameDefault, String fileChooserMsg) {
		String fileName;
		if (args.length > pos) {
			fileName = args[pos];
			return new File(fileName);
		}
		fileName = fileNameDefault;
		File file = null;
		if (fileName != null) {
			file = new File(fileName);
			if (file.canRead()) {
				return file;
			}
		}
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle(fileChooserMsg);
		if (file != null) {
			fc.setSelectedFile(file);
		}
		int returnVal = fc.showOpenDialog(null);
		if (returnVal != JFileChooser.APPROVE_OPTION) {
			return null;
		}
		return fc.getSelectedFile();
	}

	public static File getFileToSave(String[] args, int pos, String fileNameDefault, String fileChooserMsg) {
		String fileName;
		File file = null;
		if (args.length > pos) {
			fileName = args[pos];
			file = new File(fileName);
			return file;
		}
		else {
			fileName = fileNameDefault;
		}
		if (fileName != null) {
			file = new File(fileName);
		}
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle(fileChooserMsg);
		if (file != null) {
			fc.setSelectedFile(file);
		}
		int returnVal = fc.showSaveDialog(null);
		if (returnVal != JFileChooser.APPROVE_OPTION) {
			return null;
		}
		return fc.getSelectedFile();
	}
	
	public static File getFileToSaveFrom(String[] args, int pos, String directoryNameDefault, String fileChooserMsg, String prefix, String suffix) {
		String directoryName;
		File file = null;
		if (args.length > pos) {
			directoryName = args[pos];
			if (!directoryName.endsWith("/")) {
				directoryName = directoryName + "/";
			}
			file = new File(directoryName + prefix + suffix);
			return file;
		}
		else {
			directoryName = directoryNameDefault;
		}
		if (directoryName != null) {
			if (!directoryName.endsWith("/")) {
				directoryName = directoryName + "/";
			}
			file = new File(directoryName + prefix + suffix);
		}
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle(fileChooserMsg);
		if (file != null) {
			fc.setSelectedFile(file);
		}
		int returnVal = fc.showSaveDialog(null);
		if (returnVal != JFileChooser.APPROVE_OPTION) {
			return null;
		}
		return fc.getSelectedFile();
	}

	public static File getDirectoryToSave(String[] args, int pos, String directoryNameDefault,
			String directoryChooserMsg) {
		String fileName;
		File file = null;
		if (args.length > pos) {
			fileName = args[pos];
			file = new File(fileName);
			if (file.isDirectory()) {
				try {
					file.createNewFile();
					if (file.canRead()) {
						return file;
					}
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		else {
			fileName = directoryNameDefault;
		}
		if (fileName != null) {
			file = new File(fileName);
		}
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setDialogTitle(directoryChooserMsg);
		if (file != null) {
			fc.setSelectedFile(file);
		}
		int returnVal = fc.showSaveDialog(null);
		if (returnVal != JFileChooser.APPROVE_OPTION) {
			return null;
		}
		return fc.getSelectedFile();
	}

	public static Organism getOrCreateOrganism(String[] args, int pos, int organismNumberDefault,
			String organismNumberChooserMsg, String organismNameDefault, String organismNameChooserMsg,
			EntityFactory entityFactory) {
		int orgID;
		String organismName;
		if (args.length > pos) {
			orgID = Integer.parseInt(args[pos]);
		}
		else {
			orgID = organismNumberDefault;
		}
		if (args.length > pos + 1) {
			organismName = args[pos + 1];
		}
		else {
			organismName = organismNameDefault;
		}

		Organism organism = null;
		String respDialog;
		try {
			if (args.length > pos) {
				organism = entityFactory.getOrganism(orgID);
				if (organism == null) {
					organism = entityFactory.createOrganism(orgID, organismName);
				}
			}
		}
		catch (Exception ex) {
		}
		while (organism == null) {
			respDialog = JOptionPane.showInputDialog(organismNumberChooserMsg, orgID);
			if (respDialog == null) {
				return null;
			}
			try {
				orgID = Integer.parseInt(respDialog);
				organism = entityFactory.getOrganism(orgID);
				if (organism == null) {
					organismName = JOptionPane.showInputDialog(organismNameChooserMsg, organismName);
					organism = entityFactory.createOrganism(orgID, organismName);
				}
			}
			catch (Exception ex) {
			}
		}
		return organism;
	}

	public static Organism getOrganism(String[] args, int pos, int organismNumberDefault,
			String organismNumberChooserMsg, EntityFactory entityFactory) {
		int orgID = organismNumberDefault;
		Organism organism = null;
		String respDialog;
		try {
			if (args.length > pos) {
				orgID = Integer.parseInt(args[pos]);
				organism = entityFactory.getOrganism(orgID);
			}
		}
		catch (Exception ex) {
		}
		while (organism == null) {
			respDialog = JOptionPane.showInputDialog(organismNumberChooserMsg, orgID);
			if (respDialog == null) {
				return null;
			}
			try {
				orgID = Integer.parseInt(respDialog);
				organism = entityFactory.getOrganism(orgID);
			}
			catch (Exception ex) {
			}
		}
		return organism;
	}

	public static Integer getInteger(String[] args, int pos, int valueDefault, String messageDefault) {
		String value;
		if (args.length > pos) {
			value = args[pos];
		}
		else {
			value = JOptionPane.showInputDialog(messageDefault, valueDefault);
			if (value == null) {
				return null;
			}
		}
		return new Integer(value);
	}

	public static Integer getIntegerOptional(String[] args, int pos, int valueDefault) {
		if (args.length > pos) {
			return new Integer(args[pos]);
		}
		else {
			return valueDefault;
		}
	}

	public static Double getDouble(String[] args, int pos, double valueDefault, String messageDefault) {
		String value;
		if (args.length > pos) {
			value = args[pos];
		}
		else {
			value = JOptionPane.showInputDialog(messageDefault, valueDefault);
			if (value == null) {
				return null;
			}
		}
		return new Double(value);
	}

	public static String getString(String[] args, int pos, String valueDefault, String messageDefault) {
		String value;
		if (args.length > pos) {
			value = args[pos];
		}
		else {
			value = JOptionPane.showInputDialog(messageDefault, valueDefault);
			if (value == null) {
				return null;
			}
		}
		return value;
	}

	public static Boolean getBoolean(String[] args, int pos, String messageDefault) {
		String value;
		if (args.length > pos) {
			value = args[pos];
			if (value.equalsIgnoreCase("y") || value.equalsIgnoreCase("yes")) {
				return true;
			}
			return new Boolean(value);
		}
		return (JOptionPane.showConfirmDialog(null, messageDefault, messageDefault, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION);
	}

	public static String[] split(String value, String separator) {
		if (value == null) {
			return null;
		}
		if (separator == null || separator.length() == 0) {
			return new String[] {value};
		}
		else {
			return value.split(separator, -2);
		}
	}

}
