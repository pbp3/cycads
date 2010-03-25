/*
 * Created on 23/03/2010
 */
package org.cycads.extract.parser;

import org.cycads.extract.general.AnnotationClustersGetter;
import org.cycads.extract.general.AnnotationClustersGetterRepository;
import org.cycads.extract.general.AnnotationWaysGetter;
import org.cycads.extract.general.ConfigAnnotationClustersGetterRepository;
import org.cycads.extract.objectsGetter.ObjectsGetterChangeObject;
import org.cycads.extract.objectsGetter.changeObject.ChangeToAccession;
import org.cycads.extract.objectsGetter.changeObject.ChangeToAnnotationTypes;
import org.cycads.extract.objectsGetter.changeObject.ChangeToAnnotationsAsSource;
import org.cycads.extract.objectsGetter.changeObject.ChangeToAnnotationsAsTarget;
import org.cycads.extract.objectsGetter.changeObject.ChangeToBegin;
import org.cycads.extract.objectsGetter.changeObject.ChangeToDB;
import org.cycads.extract.objectsGetter.changeObject.ChangeToEnd;
import org.cycads.extract.objectsGetter.changeObject.ChangeToMethod;
import org.cycads.extract.objectsGetter.changeObject.ChangeToName;
import org.cycads.extract.objectsGetter.changeObject.ChangeToNoteType;
import org.cycads.extract.objectsGetter.changeObject.ChangeToNoteValue;
import org.cycads.extract.objectsGetter.changeObject.ChangeToNotes;
import org.cycads.extract.objectsGetter.changeObject.ChangeToOrganism;
import org.cycads.extract.objectsGetter.changeObject.ChangeToParent;
import org.cycads.extract.objectsGetter.changeObject.ChangeToScore;
import org.cycads.extract.objectsGetter.changeObject.ChangeToSeq;
import org.cycads.extract.objectsGetter.changeObject.ChangeToSource;
import org.cycads.extract.objectsGetter.changeObject.ChangeToString;
import org.cycads.extract.objectsGetter.changeObject.ChangeToSubseq;
import org.cycads.extract.objectsGetter.changeObject.ChangeToSynonyms;
import org.cycads.extract.objectsGetter.changeObject.ChangeToTarget;
import org.cycads.extract.objectsGetter.changeObject.ChangeToType;
import org.cycads.extract.objectsGetter.changeObject.ChangeToVersion;
import org.cycads.extract.objectsGetter.validator.CompNumber;
import org.cycads.extract.objectsGetter.validator.CompRegex;
import org.cycads.extract.objectsGetter.validator.CompRegexMatch;
import org.cycads.extract.objectsGetter.validator.CompRegexNotMatch;
import org.cycads.extract.objectsGetter.validator.NumberEqual;
import org.cycads.extract.objectsGetter.validator.NumberGeq;
import org.cycads.extract.objectsGetter.validator.NumberGreater;
import org.cycads.extract.objectsGetter.validator.NumberLeq;
import org.cycads.extract.objectsGetter.validator.NumberLess;
import org.cycads.extract.objectsGetter.validator.NumberNotEqual;
import org.cycads.parser.ParserException;

public class SimpleAnnotationWaysGetterReader implements AnnotationWaysGetterReader
{
	AnnotationWaysGetterHandler			handler;
	AnnotationClustersGetterRepository	clusterRepository;

	public SimpleAnnotationWaysGetterReader(AnnotationWaysGetterHandler handler,
			AnnotationClustersGetterRepository clusterRepository) {
		this.handler = handler;
		this.clusterRepository = clusterRepository;
	}

	public SimpleAnnotationWaysGetterReader(AnnotationClustersGetterRepository clusterRepository) {
		this(new SimpleAnnotationWaysGetterHandler(), clusterRepository);

	}

	public SimpleAnnotationWaysGetterReader(AnnotationWaysGetterHandler handler) {
		this(handler, new ConfigAnnotationClustersGetterRepository());

	}

	public SimpleAnnotationWaysGetterReader() {
		this(new SimpleAnnotationWaysGetterHandler(), new ConfigAnnotationClustersGetterRepository());

	}

	@Override
	public AnnotationWaysGetter parse(String loc) throws ParserException {
		handler.startLoc();
		ObjectsGetterChangeObject changer;
		int posEndFilter;
		while (loc != null && loc.length() != 0) {
			switch (loc.charAt(0)){
				case '.':
					if (loc.charAt(1) == '[') {
						// change to AnnotationCluster
						int posEndClusterName = loc.indexOf(']');
						if (posEndClusterName < 0) {
							throw new ParserException("AnnotationClusterName without close ']'. Loc=" + loc);
						}
						String annotationClusterName = loc.substring(2, posEndClusterName);
						AnnotationClustersGetter annotClustersGetter = clusterRepository.getAnnotationClusterGetter(annotationClusterName);
						if (annotClustersGetter == null) {
							throw new ParserException("AnnotationClusterName '" + annotationClusterName
								+ "' not found. Loc=" + loc);
						}
						handler.newAnnotClustersGetter(annotClustersGetter);
						loc = loc.substring(posEndClusterName + 1);
					}
					else {
						String changerStr = loc.substring(1, 3);
						if (changerStr.equalsIgnoreCase("AS")) {
							changer = new ChangeToAnnotationsAsSource();
						}
						else if (changerStr.equalsIgnoreCase("AT")) {
							changer = new ChangeToAnnotationsAsTarget();
						}
						else if (changerStr.equalsIgnoreCase("SO")) {
							changer = new ChangeToSource();
						}
						else if (changerStr.equalsIgnoreCase("TA")) {
							changer = new ChangeToTarget();
						}
						else if (changerStr.equalsIgnoreCase("AY")) {
							changer = new ChangeToAnnotationTypes();
						}
						else if (changerStr.equalsIgnoreCase("ME")) {
							changer = new ChangeToMethod();
						}
						else if (changerStr.equalsIgnoreCase("SC")) {
							changer = new ChangeToScore();
						}
						else if (changerStr.equalsIgnoreCase("EN")) {
							changer = new ChangeToEnd();
						}
						else if (changerStr.equalsIgnoreCase("BE")) {
							changer = new ChangeToBegin();
						}
						else if (changerStr.equalsIgnoreCase("NA")) {
							changer = new ChangeToName();
						}
						else if (changerStr.equalsIgnoreCase("OR")) {
							changer = new ChangeToOrganism();
						}
						else if (changerStr.equalsIgnoreCase("SE")) {
							changer = new ChangeToSeq();
						}
						else if (changerStr.equalsIgnoreCase("VE")) {
							changer = new ChangeToVersion();
						}
						else if (changerStr.equalsIgnoreCase("SS")) {
							changer = new ChangeToSubseq();
						}
						else if (changerStr.equalsIgnoreCase("TY")) {
							changer = new ChangeToType();
						}
						else if (changerStr.equalsIgnoreCase("SY")) {
							changer = new ChangeToSynonyms();
						}
						else if (changerStr.equalsIgnoreCase("NO")) {
							changer = new ChangeToNotes();
						}
						else if (changerStr.equalsIgnoreCase("NT")) {
							changer = new ChangeToNoteType();
						}
						else if (changerStr.equalsIgnoreCase("NV")) {
							changer = new ChangeToNoteValue();
						}
						else if (changerStr.equalsIgnoreCase("DB")) {
							changer = new ChangeToDB();
						}
						else if (changerStr.equalsIgnoreCase("AC")) {
							changer = new ChangeToAccession();
						}
						else if (changerStr.equalsIgnoreCase("ST")) {
							changer = new ChangeToString();
						}
						else if (changerStr.equalsIgnoreCase("PA")) {
							changer = new ChangeToParent();
						}
						else {
							throw new ParserException(changerStr + " is not a valid changer String. Loc=" + loc);
						}
						handler.newChanger(changer);
						loc = loc.substring(3);
					}
					break;
				case '(':
					handler.startFilter();
					loc = loc.substring(1);
					break;
				case ')':
					handler.endFilter();
					loc = loc.substring(1);
					break;
				case '#':
					posEndFilter = loc.indexOf(')');
					if (posEndFilter < 0) {
						throw new ParserException("CompRegex without close filter ')'. Loc=" + loc);
					}
					CompRegex compRegex;
					char compRegexChr = loc.charAt(1);
					if (compRegexChr == '=') {
						compRegex = new CompRegexMatch(loc.substring(2, posEndFilter));
					}
					else if (compRegexChr == '!') {
						compRegex = new CompRegexNotMatch(loc.substring(2, posEndFilter));
					}
					else {
						throw new ParserException(compRegexChr + " is not a valid compRegex comparator. Loc=" + loc);
					}
					handler.newCompRegex(compRegex);
					loc = loc.substring(posEndFilter + 1);
					break;
				case '=':
					posEndFilter = loc.indexOf(')');
					if (posEndFilter < 0) {
						throw new ParserException("CompNumber without close filter ')'. Loc=" + loc);
					}
					CompNumber compNumber;
					char compNumberChr = loc.charAt(1);
					String numberStr = loc.substring(2, posEndFilter);
					Number number;
					try {
						number = new Double(numberStr);
					}
					catch (NumberFormatException e) {
						throw new ParserException(numberStr + " is not a valid number. Loc=" + loc, e);
					}
					switch (compNumberChr){
						case '=':
							compNumber = new NumberEqual(number);
							break;
						case '!':
							compNumber = new NumberNotEqual(number);
							break;
						case '<':
							compNumber = new NumberLeq(number);
							break;
						case '>':
							compNumber = new NumberGeq(number);
							break;
						default:
							throw new ParserException(compNumberChr + " is not a valid compNumber comparator. Loc="
								+ loc);
					}
					handler.newCompNumber(compNumber);
					loc = loc.substring(posEndFilter + 1);
					break;
				case '<':
					posEndFilter = loc.indexOf(')');
					if (posEndFilter < 0) {
						throw new ParserException("CompNumber without close filter ')'. Loc=" + loc);
					}
					compNumberChr = loc.charAt(1);
					numberStr = loc.substring(2, posEndFilter);
					try {
						number = new Double(numberStr);
					}
					catch (NumberFormatException e) {
						throw new ParserException(numberStr + " is not a valid number. Loc=" + loc, e);
					}
					switch (compNumberChr){
						case '=':
							compNumber = new NumberLeq(number);
							break;
						case '<':
							compNumber = new NumberLess(number);
							break;
						default:
							throw new ParserException(compNumberChr + " is not a valid compNumber comparator. Loc="
								+ loc);
					}
					handler.newCompNumber(compNumber);
					loc = loc.substring(posEndFilter + 1);
					break;
				case '>':
					posEndFilter = loc.indexOf(')');
					if (posEndFilter < 0) {
						throw new ParserException("CompNumber without close filter ')'. Loc=" + loc);
					}
					compNumberChr = loc.charAt(1);
					numberStr = loc.substring(2, posEndFilter);
					try {
						number = new Double(numberStr);
					}
					catch (NumberFormatException e) {
						throw new ParserException(numberStr + " is not a valid number. Loc=" + loc, e);
					}
					switch (compNumberChr){
						case '=':
							compNumber = new NumberGeq(number);
							break;
						case '>':
							compNumber = new NumberGreater(number);
							break;
						default:
							throw new ParserException(compNumberChr + " is not a valid compNumber comparator. Loc="
								+ loc);
					}
					handler.newCompNumber(compNumber);
					loc = loc.substring(posEndFilter + 1);
					break;
				default:
					throw new ParserException("Can't identify the next step:" + loc);
			}
		}
		return handler.endLoc();
	}
}
