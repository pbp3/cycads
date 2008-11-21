/*
 * Created on 19/11/2008
 */
package org.cycads.util;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.regex.Pattern;

import org.cycads.general.Messages;

public class Split
{
	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.print(Messages.splitUsage());
			return;
		}
		BufferedReader br;
		String filePrefix = args[0];

		try {
			br = new BufferedReader(new FileReader(args[0]));
			Pattern pattern = Pattern.compile(args[1]);
			int maxLines = Integer.parseInt(args[2]);
			int patternsInFile = 0;
			int part = 1;

			PrintStream out = new PrintStream(new FileOutputStream(filePrefix + "." + part, false));
			String line;
			while ((line = br.readLine()) != null) {
				if (pattern.matcher(line).find()) {
					patternsInFile++;
					if (patternsInFile > maxLines) {
						patternsInFile = 1;
						part++;
						out = new PrintStream(new FileOutputStream(filePrefix + "." + part, false));
					}
				}
				out.println(line);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
