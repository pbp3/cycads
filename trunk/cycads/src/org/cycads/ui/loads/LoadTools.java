/*
 * Created on 20/10/2008
 */
package org.cycads.ui.loads;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.cycads.entities.Method;
import org.cycads.entities.MethodType;
import org.cycads.entities.Organism;
import org.cycads.exceptions.DBObjectNotFound;

public abstract class LoadTools
{
	public static File getFile(String[] args, int pos, String fileNameDefault, String fileChooserMsg) {
		String fileName;
		if (args.length > pos) {
			fileName = args[pos];
		}
		else {
			fileName = fileNameDefault;
		}
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

	public abstract Organism createOrganismObject(int orgID) throws DBObjectNotFound;

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

	public abstract MethodType createMethodTypeObject(String methodType);

}
