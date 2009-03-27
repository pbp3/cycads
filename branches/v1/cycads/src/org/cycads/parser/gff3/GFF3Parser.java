/*
 * BioJava development code
 *
 * This code may be freely distributed and modified under the terms of the GNU Lesser General Public Licence. This
 * should be distributed with the code. If you do not have a copy, see:
 *
 * http://www.gnu.org/copyleft/lesser.html
 *
 * Copyright for this code is held jointly by the individual authors. These should be listed in @author doc comments.
 *
 * For more information on the BioJava project and its aims, or to join the biojava-l mailing list, visit the home page
 * at:
 *
 * http://www.biojava.org/
 *
 */

package org.cycads.parser.gff3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class GFF3Parser
{
	public void parse(File file, GFF3DocumentHandler handler) throws IOException {
		parse(new BufferedReader(new FileReader(file)), handler);
	}

	public void parse(BufferedReader bReader, GFF3DocumentHandler handler) throws IOException {
		handler.startDocument();
		ArrayList<String> aList = new ArrayList<String>();
		for (String line = bReader.readLine(); line != null; line = bReader.readLine()) {
			aList.clear();
			if (line.startsWith("#")) {
				handler.commentLine(line.substring(1));
			}
			else if (line.length() != 0) {
				StringTokenizer st = new StringTokenizer(line, "\t", false);
				while (st.hasMoreTokens() && aList.size() < 8) {
					String token = st.nextToken();
					aList.add(token);
				}
				String rest = null;
				String comment = null;
				if (st.hasMoreTokens()) {
					try {
						rest = st.nextToken(((char) 0) + "");
					}
					catch (NoSuchElementException nsee) {
					}
				}
				if (rest != null) {
					int ci = rest.indexOf("#");
					if (ci != -1) {
						comment = rest.substring(ci);
						rest = rest.substring(0, ci);
					}
				}
				GFF3Record record = createRecord(handler, aList, rest);
				handler.recordLine(record);
				if (comment != null && comment.length() > 0) {
					handler.commentLine(comment);
				}
			}
		}
		handler.endDocument();
	}

	/**
	 * Actually turns a list of tokens, some value string and a comment into a <span class="type">GFF3Record</span> and
	 * informs <span class="arg">handler</span>.
	 * 
	 * @param handler a <span class="type">GFF3DocumentHandler</span> to inform of any parse errors, and the completed
	 *            <span class="type">GFF3Record</span>
	 * @param aList a <span class="type">List</span> containing the 8 mandatory GFF columns
	 * @param rest a <span class="type">String</span> representing the unparsed attribute-value text, or <span
	 *            class="kw">null</span> if there is none
	 * @param comment a <span class="type">String</span> containing the comment (without the leading '<code>#</code>'
	 *            character.
	 * @throws <span class="type">BioException</span> if <span class="arg">handler</span> could not correct a parse
	 *             error
	 */
	protected GFF3Record createRecord(GFF3DocumentHandler handler, List<String> aList, String rest) {
		GFF3Record record = new GFF3Record();

		record.setSequenceID(aList.get(0));
		record.setSource(aList.get(1));
		record.setType(aList.get(2));

		record.setStart(Integer.parseInt(aList.get(3)));

		record.setEnd(Integer.parseInt(aList.get(4)));

		String score = aList.get(5);
		if (score == null || score.equals("") || score.equals(".")) {
			record.setScore(GFF3Record.NO_SCORE);
		}
		else {
			record.setScore(Double.parseDouble(score));
		}

		String strand = aList.get(6);
		if (strand == null || strand.equals("") || strand.equals(".")) {
			record.setStrand(GFF3Record.NO_STRAND);
		}
		else {
			if (strand.equals("+")) {
				record.setStrand(GFF3Record.POSITIVE_STRAND);
			}
			else if (strand.equals("-")) {
				record.setStrand(GFF3Record.NEGATIVE_STRAND);
			}
			else {
				record.setStrand(GFF3Record.UNKNOW_STRAND);
			}
		}

		String frame = aList.get(7);
		if (frame.equals(".")) {
			record.setPhase(GFF3Record.NO_PHASE);
		}
		else {
			record.setPhase(Integer.parseInt(frame));
		}

		if (rest != null) {
			parseAttribute(rest, record);
		}
		return record;
	}

	/**
	 * Parse <span class="arg">attValList</span> into a <span class="type">Map</span> of attributes and value lists.
	 * <p>
	 * Populates an Annotation instance with OntologyBJ Term keys and string/list values.
	 * </p>
	 * 
	 * @param attValList the <span class="type">String</span> to parse
	 */

	protected void parseAttribute(String attValList, GFF3Record record) {
		StringTokenizer sTok = new StringTokenizer(attValList, ";", false);
		while (sTok.hasMoreTokens()) {
			String attVal = sTok.nextToken().trim();
			int spaceIndx = attVal.indexOf("=");
			if (spaceIndx == -1) {
				record.addNote(attVal, null);
			}
			else {
				String attName;
				attName = attVal.substring(0, spaceIndx);
				attValList = attVal.substring(spaceIndx + 1).trim();
				while (attValList.length() > 0) {
					if (attValList.startsWith("\"")) {
						// System.out.println("Quoted");
						int quoteIndx = 0;
						do {
							quoteIndx++;
							quoteIndx = attValList.indexOf("\"", quoteIndx);
						}
						while (quoteIndx != -1 && attValList.charAt(quoteIndx - 1) == '\\');
						if (quoteIndx > 0) {
							record.addNote(attName, attValList.substring(1, quoteIndx));
							attValList = attValList.substring(quoteIndx + 1).trim();
						}
						else {
							record.addNote(attName, attValList);
							attValList = "";
						}
					}
					else {
						int commaIndx = attValList.indexOf(",");
						if (commaIndx == -1) {
							record.addNote(attName, attValList);
							attValList = "";
						}
						else {
							record.addNote(attName, attValList.substring(0, commaIndx));
							attValList = attValList.substring(commaIndx + 1).trim();
						}
					}
				}
			}
		}
	}
}
