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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.cycads.general.Messages;
import org.cycads.ui.Tools;

public class RetrieveStringColumn
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
		Integer column = Tools.getInteger(args, 1, 0, "Column to retrieve");
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
		String regexRetrieve = Tools.getString(args, 4, "", "Expression to retrieve:");
		if (regexRetrieve == null) {
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
			int error = 0;
			int lineOk = 0;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.length() > 0 && !line.startsWith(lineComment)) {
					String[] strs = Tools.split(line, separator);
					if (strs.length > column) {
						strs[column] = retrieveColumn(strs[column], regexRetrieve);
						for (int i = 0; i < strs.length - 1; i++) {
							out.print(strs[i]);
							out.print('\t');
						}
						out.println(strs[strs.length - 1]);
						lineOk++;
					}
					else {
						error++;
					}
				}
			}
			System.out.println("Processed:" + lineOk);
			System.out.println("Without column:" + error);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String retrieveColumn(String text, String regexRetrieve) {
		StringBuffer ret = new StringBuffer();
		Pattern p = Pattern.compile(regexRetrieve);
		Matcher m = p.matcher(text);
		while (m.find()) {
			ret.append(text.substring(m.start(), m.end()));
		}
		return ret.toString();
	}
}
