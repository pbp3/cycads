/*
 * Created on 20/10/2008
 */
package org.cycads.ui;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.cycads.entities.biojava.Method;
import org.cycads.entities.biojava.MethodType;
import org.cycads.entities.biojava.Organism;
import org.cycads.exceptions.DBObjectNotFound;
import org.cycads.general.CacheCleanerListener;

public abstract class Arguments {

	public static Arguments getInstanceDefault() {
		return new ArgumentsBJ();
	}

	public abstract CacheCleanerListener getCacheCleanerSession();

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
			if (file.canRead()) {
				return file;
			}
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

	public Organism getOrganism(String[] args, int pos, int organismNumberDefault, String organismChooserMsg) {
		int orgID = organismNumberDefault;
		Organism organism = null;
		String respDialog;
		try {
			if (args.length > pos) {
				orgID = Integer.parseInt(args[pos]);
				organism = createOrganismObject(orgID);
			}
		}
		catch (Exception ex) {
		}
		while (organism == null) {
			respDialog = JOptionPane.showInputDialog(organismChooserMsg, orgID);
			if (respDialog == null) {
				return null;
			}
			try {
				orgID = Integer.parseInt(respDialog);
				organism = createOrganismObject(orgID);
			}
			catch (Exception ex) {
			}
		}
		return organism;
	}

	protected abstract Organism createOrganismObject(int orgID) throws DBObjectNotFound;

	public Method getMethod(MethodType methodType, String[] args, int pos, String methodNameDefault,
			String methodChooserMsg) {
		String methodName;
		if (args.length > pos) {
			methodName = args[pos];
		}
		else {
			methodName = JOptionPane.showInputDialog(methodChooserMsg, methodNameDefault);
			if (methodName == null) {
				return null;
			}
		}
		return methodType.getOrCreateMethod(methodName);
	}

	public Method getMethod(String methodType, String[] args, int pos, String methodNameDefault, String methodChooserMsg) {
		return getMethod(createMethodTypeObject(methodType), args, pos, methodNameDefault, methodChooserMsg);

	}

	protected abstract MethodType createMethodTypeObject(String methodType);

	public static Integer getInteger(String[] args, int pos, String messageDefault, int valueDefault) {
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

	public static String getString(String[] args, int pos, String messageDefault, String valueDefault) {
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

}
