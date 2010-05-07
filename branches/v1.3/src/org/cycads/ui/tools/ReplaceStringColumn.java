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

public class ReplaceStringColumn
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
		Integer column = Tools.getInteger(args, 1, 0, "Column to replace");
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
		String regexReplace = Tools.getString(args, 4, "", "Expression to replace:");
		if (regexReplace == null) {
			return;
		}
		String replaceValue = Tools.getString(args, 5, "", "Value to put:");
		if (replaceValue == null) {
			return;
		}
		File fileOut = Tools.getFileToSave(args, 6, file.getName() + "." + column, "Choose the file to save");
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
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.length() > 0 && !line.startsWith(lineComment)) {
					String[] strs = Tools.split(line, separator);
					if (strs.length > column) {
						strs[column] = strs[column].replaceAll(regexReplace, replaceValue);
						for (int i = 0; i < strs.length - 1; i++) {
							out.print(strs[i]);
							out.print('\t');
						}
						out.println(strs[strs.length - 1]);
					}
					else {
						throw new RuntimeException(line);
					}
				}
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
