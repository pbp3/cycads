/*
 * Created on 14/04/2009
 */
package org.cycads.extract.cyc;

import java.util.ArrayList;
import java.util.List;

import org.cycads.entities.annotation.Annotation;

public class SimpleCycEC implements CycEC {

	private ScoreSystemCollection	scoreSystems;
	private String					ecNumber;
	private List<List<Annotation>>	annotationPaths	= new ArrayList<List<Annotation>>();
	private double					score			= 0;

	public SimpleCycEC(String ecNumber, ScoreSystemCollection scoreSystems) {
		this.ecNumber = ecNumber;
		this.scoreSystems = scoreSystems;
	}

	public SimpleCycEC(String ecNumber, List<Annotation> annotationsList, ScoreSystemCollection scoreSystems) {
		this.ecNumber = ecNumber;
		this.scoreSystems = scoreSystems;
		addAnnotationPath(annotationsList);
	}

	public String getEcNumber() {
		return ecNumber;
	}

	public List<List<Annotation>> getAnnotationPaths() {
		return annotationPaths;
	}

	public void addAnnotationPath(List<Annotation> annotationPath) {
		if (annotationPath != null && !annotationPath.isEmpty()) {
			annotationPaths.add(annotationPath);
			ScoreSystem scoreSystem;
			double scoreAnnotation;
			String scoreNote;
			double scoreAnnotationPath = 1;
			for (Annotation annot : annotationPath) {
				scoreSystem = scoreSystems.getScoreSystem(annot.getAnnotationMethod());
				scoreNote = annot.getNoteValue(PFFileConfig.getScoreAnnotationNoteTypeName());
				if (scoreNote != null) {
					scoreAnnotation = scoreSystem.getScore(Double.parseDouble(scoreNote));
				}
				else {
					scoreAnnotation = scoreSystem.getScore(null);
				}
				scoreAnnotationPath = scoreAnnotationPath * scoreAnnotation;
			}
			score = score + scoreAnnotationPath;
		}
	}

	public double getScore() {
		return score;
	}

}
