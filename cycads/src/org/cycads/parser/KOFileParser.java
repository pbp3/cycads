/*
 * Created on 16/03/2009
 */
package org.cycads.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.factory.EntityFactory;
import org.cycads.entities.synonym.KO;
import org.cycads.general.Messages;
import org.cycads.general.ParametersDefault;
import org.cycads.ui.progress.Progress;

public class KOFileParser
{

	public static final String	EC_TAG	= ParametersDefault.koFileECTag();
	EntityFactory				factory;
	Progress					progress;
	KO							ko;
	AnnotationMethod			methodDBLink, methodEC;
	String						definition;

	public KOFileParser(EntityFactory factory, Progress progress, AnnotationMethod methodDBLink,
			AnnotationMethod methodEC) {
		this.factory = factory;
		this.progress = progress;
		this.methodDBLink = methodDBLink;
		this.methodEC = methodEC;
	}

	public void parse(File f) throws IOException, FileParserException {
		parse(new BufferedReader(new FileReader(f)));
	}

	public void parse(String fileName) throws IOException, FileParserException {
		parse(new BufferedReader(new FileReader(fileName)));
	}

	public void parse(BufferedReader br) throws IOException, FileParserException {
		String line;
		String data;
		String dbName = "";
		String tag = "", newTag;
		String def;
		int ecPos;
		String[] ecs;

		int lineNumber = 0;
		while ((line = br.readLine()) != null) {
			lineNumber++;
			try {
				if (line.length() > 0 && !line.startsWith(ParametersDefault.koFileCommentStr())) {
					if (line.startsWith(ParametersDefault.koFileEndRecordStr())) {
						finishKO();
						tag = "";
					}
					else {
						newTag = line.substring(0, ParametersDefault.koFileDataPos()).trim();
						data = line.substring(ParametersDefault.koFileDataPos()).trim();
						if (newTag.length() > 0) {
							tag = newTag;
						}
						if (tag.equals(ParametersDefault.koFileEntryTag())) {
							initKO(data.substring(0, 6));
							dbName = "";
						}
						else if (tag.equals(ParametersDefault.koFileDBLinksTag())) {
							String[] strs = data.split(ParametersDefault.koFileDBLinkSubTagSeparator());
							if (strs.length == 2) {
								dbName = strs[0].trim();
								data = strs[1];
							}
							else if (strs.length != 1 || (strs.length == 1 && dbName.length() == 0)) {
								throw new ParseException(data, 0);
							}
							String[] accessions = data.split(ParametersDefault.koFileDBLinkAccessionSeparator());
							for (String accession : accessions) {
								if (accession.length() > 0) {
									newDBLink(ParametersDefault.koFileDBLinkDbName(dbName), accession);
								}
							}
						}
						else {
							if (tag.equals(ParametersDefault.koFileDefinitionTag())) {
								// remove EC of data string
								ecPos = data.indexOf(EC_TAG);
								if (ecPos >= 0) {
									def = data.substring(0, ecPos);
									data = data.substring(ecPos);
									tag = EC_TAG;
								}
								else {
									def = data;
								}
								if (def.length() > 0) {
									newDefinition(def.trim());
								}
							}
							if (tag.equals(EC_TAG)) {
								data = data.replaceAll(ParametersDefault.koFileECDeleteExpression(), "");
								ecs = data.split(ParametersDefault.koFileECSeparator());
								for (String ec : ecs) {
									if (ec.length() > 0) {
										addEc(ec);
									}
								}
							}
						}
					}
				}
			}
			catch (Exception e) {
				throw new FileParserException(Messages.getExceptionFileParserLineMsg(lineNumber, line), e);
			}
		}
	}

	protected void addEc(String ec) {
		ko.addEcAnnotation(methodEC, ec);
	}

	protected void finishKO() {
		if (!definition.equals(ko.getDefinition()) && definition.length() > 0) {
			ko.setDefinition(definition);
		}
		progress.completeStep();
	}

	protected void initKO(String koNumber) {
		ko = factory.getKO(koNumber);
		definition = "";
	}

	protected void newDBLink(String dbName, String accession) {
		ko.addAnnotation(factory.getDbxref(dbName, accession), methodDBLink, null, null);
	}

	protected void newDefinition(String def) {
		definition += def;
	}

}
