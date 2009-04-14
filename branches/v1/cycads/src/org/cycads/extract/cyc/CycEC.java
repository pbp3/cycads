/*
 * Created on 14/04/2009
 */
package org.cycads.extract.cyc;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.cycads.entities.annotation.Annotation;
import org.cycads.general.ParametersDefault;

public class CycEC {

	private Hashtable<String, ScoreSystem>	scoreSystems;
	private String							ecNumber;
	private List<List<Annotation>>			annotationsLists	= new ArrayList<List<Annotation>>();
	private double							score				= 0;

	public CycEC(String ecNumber, Hashtable<String, ScoreSystem> scoreSystems) {
		this.ecNumber = ecNumber;
		this.scoreSystems = scoreSystems;
	}

	public CycEC(String ecNumber, List<Annotation> annotationsList, Hashtable<String, ScoreSystem> scoreSystems) {
		this.ecNumber = ecNumber;
		this.scoreSystems = scoreSystems;
		addAnnotationsList(annotationsList);
	}

	public String getEcNumber() {
		return ecNumber;
	}

	public List<List<Annotation>> getAnnotationsLists() {
		return annotationsLists;
	}

	public void addAnnotationsList(List<Annotation> annotationsList) {
		if (annotationsList != null && !annotationsList.isEmpty()) {
			annotationsLists.add(annotationsList);
			ScoreSystem scoreSystem;
			double scoreAnnotation;
			String scoreNote;
			double scoreAnnotations = 1;
			for (Annotation annot : annotationsList) {
				scoreSystem = scoreSystems.get(annot.getAnnotationMethod().getName());
				scoreNote = annot.getNoteValue(ParametersDefault.getScoreAnnotationNoteTypeName());
				scoreAnnotation = scoreSystem.getScore(Double.parseDouble(scoreNote));
				scoreAnnotations = scoreAnnotations * scoreAnnotation;
			}
			score = score + scoreAnnotations;
		}
	}

	public double getScore() {
		return score;
	}

}
