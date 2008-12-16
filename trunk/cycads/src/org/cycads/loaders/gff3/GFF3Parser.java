/*
 *                    BioJava development code
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  If you do not have a copy,
 * see:
 *
 *      http://www.gnu.org/copyleft/lesser.html
 *
 * Copyright for this code is held jointly by the individual
 * authors.  These should be listed in @author doc comments.
 *
 * For more information on the BioJava project and its aims,
 * or to join the biojava-l mailing list, visit the home page
 * at:
 *
 *      http://www.biojava.org/
 *
 */

package org.cycads.loaders.gff3;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import org.biojava.bio.BioException;
import org.biojava.bio.program.gff.GFFErrorHandler;
import org.biojava.bio.program.gff.GFFTools;
import org.biojava.bio.program.gff.IgnoreRecordException;
import org.biojava.bio.seq.StrandedFeature;
import org.biojava.utils.ChangeVetoException;
import org.biojava.utils.ParserException;

/**
 * Parse a stream of GFF text into a stream of records and comments.
 * 
 * <p>
 * Developed from {@link org.biojava.bio.program.gff.GFFParser GFFParser}.
 * </p>
 * 
 * @author Matthew Pocock
 */
public class GFF3Parser
{
	private GFFErrorHandler	errors	= GFFErrorHandler.ABORT_PARSING;

	/**
	 * Set the error handler used by this parser.
	 */

	public void setErrorHandler(GFFErrorHandler errors) {
		this.errors = errors;
	}

	/**
	 * Find the error handler used by this parser.
	 */

	public GFFErrorHandler getErrorHandler() {
		return errors;
	}

	/**
	 * Informs <span class="arg">handler</span> of each line of GFF read from <span class="arg">bReader</span>
	 * 
	 * @param bReader the <span class="type">BufferedReader</span> to parse
	 * @param handler the <span class="type">GFF3DocumentHandler</span> that will listen for 'stuff'
	 * 
	 * @throws <span class="type">IOException</span> if for any reason <span class="arg">bReader</span> throws one
	 * @throws <span class="type">BioException</span> if <span class="arg">handler</span> can not correct a parse
	 *             error
	 */

	public void parse(BufferedReader bReader, GFF3DocumentHandler handler, String locator)
			throws IOException, BioException, ParserException {
		handler.startDocument(locator);
		ArrayList aList = new ArrayList();
		int lineNum = 0;
		for (String line = bReader.readLine(); line != null; line = bReader.readLine()) {
			++lineNum;

			try {
				aList.clear();
				if (line.startsWith("#")) {
					handler.commentLine(line.substring(1));
				}
				else if (line.length() == 0) {
				}
				else {
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
					GFF3Record record = createRecord(handler, aList, rest, comment);
					handler.recordLine(record);
				}
			}
			catch (ParserException ex) {
				throw new ParserException(ex, "", locator, lineNum, line);
			}
			catch (IgnoreRecordException ex) {
				// Silently skip any more work on this record
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
	protected GFF3Record createRecord(GFF3DocumentHandler handler, List aList, String rest, String comment)
			throws BioException, ParserException, IgnoreRecordException {
		GFF3Record.Impl record = new GFF3Record.Impl();

		record.setSequenceID((String) aList.get(0));
		record.setSource((String) aList.get(1));
		record.setType((String) aList.get(2));

		int start = -1;
		try {
			start = Integer.parseInt((String) aList.get(3));
		}
		catch (NumberFormatException nfe) {
			start = errors.invalidStart((String) aList.get(3));
		}
		record.setStart(start);

		int end = -1;
		try {
			end = Integer.parseInt((String) aList.get(4));
		}
		catch (NumberFormatException nfe) {
			end = errors.invalidEnd((String) aList.get(4));
		}
		record.setEnd(end);

		String score = (String) aList.get(5);
		if (score == null || score.equals("") || score.equals(".") || score.equals("0")) {
			record.setScore(GFFTools.NO_SCORE);
		}
		else {
			double sc = 0.0;
			try {
				sc = Double.parseDouble(score);
			}
			catch (NumberFormatException nfe) {
				sc = errors.invalidScore(score);
			}
			record.setScore(sc);
		}

		String strand = (String) aList.get(6);
		if (strand == null || strand.equals("") || strand.equals(".")) {
			record.setStrand(StrandedFeature.UNKNOWN.getValue());
		}
		else {
			if (strand.equals("+")) {
				record.setStrand(StrandedFeature.POSITIVE.getValue());
			}
			else if (strand.equals("-")) {
				record.setStrand(StrandedFeature.NEGATIVE.getValue());
			}
			else {
				record.setStrand(StrandedFeature.UNKNOWN.getValue());
			}
		}

		String frame = (String) aList.get(7);
		if (frame.equals(".")) {
			record.setPhase(GFFTools.NO_FRAME);
		}
		else {
			int fr = 0;
			try {
				fr = Integer.parseInt(frame);
			}
			catch (NumberFormatException nfe) {
				fr = errors.invalidFrame(frame);
			}
			record.setPhase(fr);
		}

		if (rest != null) {
			try {
				parseAttribute(rest, record);
			}
			catch (ChangeVetoException cve) {
				throw new BioException("Unable to populate annotations", cve);
			}
		}

		return record;
	}

	/**
	 * Parse <span class="arg">attValList</span> into a <span class="type">Map</span> of attributes and value lists.
	 * <p>
	 * Populates an Annotation instance with Ontology Term keys and string/list values.
	 * </p>
	 * 
	 * @param attValList the <span class="type">String</span> to parse
	 */

	protected void parseAttribute(String attValList, GFF3Record notes) throws ChangeVetoException {
		StringTokenizer sTok = new StringTokenizer(attValList, ";", false);
		while (sTok.hasMoreTokens()) {
			String attVal = sTok.nextToken().trim();
			int spaceIndx = attVal.indexOf("=");
			if (spaceIndx == -1) {
				notes.addNote(notes.createNote("", attVal));
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
							notes.addNote(notes.createNote(attValList.substring(1, quoteIndx), attName));
							attValList = attValList.substring(quoteIndx + 1).trim();
						}
						else {
							notes.addNote(notes.createNote(attValList, attName));
							attValList = "";
						}
					}
					else {
						int commaIndx = attValList.indexOf(",");
						if (commaIndx == -1) {
							notes.addNote(notes.createNote(attValList, attName));
							attValList = "";
						}
						else {
							notes.addNote(notes.createNote(attValList.substring(0, commaIndx), attName));
							attValList = attValList.substring(commaIndx + 1).trim();
						}
					}
				}
			}
		}
	}
}
