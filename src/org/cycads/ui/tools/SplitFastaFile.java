/*
 * Created on 01/09/11
 * fasta source file must follow the conventions of the FASTA Defline Format:
 * GenBank                           gi|gi-number|gb|accession|locus
 * EMBL Data Library                 gi|gi-number|emb|accession|locus
 * DDBJ, DNA Database of Japan       gi|gi-number|dbj|accession|locus
 * NBRF PIR                          pir||entry
 * Protein Research Foundation       prf||name
 * SWISS-PROT                        sp|accession|name
 * Brookhaven Protein Data Bank (1)  pdb|entry|chain
 * Brookhaven Protein Data Bank (2)  entry:chain|PDBID|CHAIN|SEQUENCE
 * Patents                           pat|country|number
 * GenInfo Backbone Id               bbs|number
 * General database identifier       gnl|database|identifier
 * NCBI Reference Sequence           ref|accession|locus
 * Local Sequence identifier         lcl|identifier
 * 
 * use: SplitFastaFile <fastaFile> <column> <columnSeparatorRegex> <patternRegex> <fileExtension> <outputDirectory>
 * example: SplitFastaFile assembly2_scaffolds.fasta 3 "\|" "GL\d+" ".fa" dirout
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

public class SplitFastaFile {
	
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
		
		Integer column = Tools.getInteger(args, 1, 0, "Column to search");

		String separator = Tools.getString(args, 2, "", "Column separator:");
		if (separator == null) {
			separator = "|";
		}
		String regex = Tools.getString(args, 3, "", "Expression as outfile name prefix to search for:");
		if (regex == null) {
			return;
		}
		String fileExtension = Tools.getString(args, 4, "", "File extension:");
		if (fileExtension == null) {
			fileExtension = ".fa";
		}
		
		try {
			PrintStream out;
			String line;
			int headerLineError = 0;
			int headerLineOk = 0;
			Pattern pattern = Pattern.compile(regex);
			String selectedText, seqName;
			Matcher matcher;
			Boolean newFileStarted = false;
			
			out = null;
			while ((line = br.readLine()) != null) {
				if (line.length() > 0 && line.startsWith(">")) {
					if (column == null || column < 0) {
						selectedText = line;
					}
					else {
						String [] tags = Tools.split(line, separator);
						if (column < tags.length) {
							selectedText = tags[column];
						}
						else {
							selectedText = line;
						}
					}
					matcher = pattern.matcher(selectedText);
					if (matcher.find()) {
						seqName = matcher.group();
						if (out != null) {
							out.close(); // close any open file to prevent system max_open_files
						}
						File outFile = Tools.getFileToSaveFrom(args, 5, "", "Choose a directory to write in:", seqName, fileExtension);
						if (outFile == null) {
							return;
						}
						out = new PrintStream(outFile);
						out.println(line);
						out.flush();
						newFileStarted = true;
						headerLineOk++;
					}
					else {
						newFileStarted = false;
						headerLineError++;
					}
				}
				else if (newFileStarted) {
					out.println(line);
					out.flush();
				}
			}
			if (out != null) {
				out.close();
			}
			System.out.println("Processed:" + headerLineOk);
			System.out.println("Errors:" + headerLineError);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
