/*
 * Created on 05/01/2009
 */
package org.cycads.ui.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

import org.cycads.general.Messages;
import org.cycads.ui.Tools;

public class RemoveLineStringColumn
{

	public static void main(String[] args) {
		File file = Tools.getFileToOpen(args, 0, "", Messages.generalChooseFileToLoad());
		if (file == null) {
			return;
		}
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
		}
		catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return;
		}
		Integer column = Tools.getInteger(args, 1, 0, "Column to remove");
		if (column == null || column < 0) {
			return;
		}
		String separator = Tools.getString(args, 2, "", "Column separator:");
		if (separator == null) {
			return;
		}
		String lineComment = Tools.getString(args, 3, "", "Line comment:");
		if (lineComment == null) {
			return;
		}
		String regexRemove = Tools.getString(args, 4, "", "Expression to remove:");
		if (regexRemove == null) {
			return;
		}
		File fileOut = Tools.getFileToSave(args, 5, file.getName() + "." + column, "Choose the file to save");
		if (fileOut == null) {
			return;
		}
		PrintStream out;
		try {
			out = new PrintStream(fileOut);
		}
		catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return;
		}
		try {
			String line;
			int error = 0;
			int lineOk = 0;
			int lineRemoved = 0;
			while ((line = br.readLine()) != null) {
				if (line.length() > 0 && !line.startsWith(lineComment)) {
					String[] strs = Tools.split(line, separator);
					if (strs.length > column) {
						if (!strs[column].matches(regexRemove)) {
							out.println(line);
						}
						else {
							lineRemoved++;
						}
						lineOk++;
					}
					else {
						error++;
					}
				}
				else {
					out.println(line);
				}
			}
			System.out.println("Lines processed:" + lineOk);
			System.out.println("Lines removed:" + lineRemoved);
			System.out.println("Without column:" + error);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
