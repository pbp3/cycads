/*
 * Created on 14/04/2009
 */
package org.cycads.extract.cyc;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.cycads.entities.annotation.Annotation;
import org.cycads.general.ParametersDefault;

public class SimpleCycEC implements CycEC {

	private Hashtable<String, ScoreSystem>	scoreSystems;
	private String							ecNumber;
	private List<List<Annotation>>			annotationPaths	= new ArrayList<List<Annotation>>();
	private double							score				= 0;

	public SimpleCycEC(String ecNumber, Hashtable<String, ScoreSystem> scoreSystems) {
		this.ecNumber = ecNumber;
		this.scoreSystems = scoreSystems;
	}

	public SimpleCycEC(String ecNumber, List<Annotation> annotationsList, Hashtable<String, ScoreSystem> scoreSystems) {
		this.ecNumber = ecNumber;
		this.scoreSystems = scoreSystems;
		addAnnotationPath(annotationsList);
	}

	/* (non-Javadoc)
	 * @see org.cycads.extract.cyc.CycEC#getEcNumber()
	 */
	public String getEcNumber() {
		return ecNumber;
	}

	/* (non-Javadoc)
	 * @see org.cycads.extract.cyc.CycEC#getAnnotationPaths()
	 */
	public List<List<Annotation>> getAnnotationPaths() {
		return annotationPaths;
	}

	/* (non-Javadoc)
	 * @see org.cycads.extract.cyc.CycEC#addAnnotationPath(java.util.List)
	 */
	public void addAnnotationPath(List<Annotation> annotationPath) {
		if (annotationPath != null && !annotationPath.isEmpty()) {
			annotationPaths.add(annotationPath);
			ScoreSystem scoreSystem;
			double scoreAnnotation;
			String scoreNote;
			double scoreThisAnnotationPath = 1;
			for (Annotation annot : annotationPath) {
				scoreSystem = scoreSystems.get(annot.getAnnotationMethod().getName());
				scoreNote = annot.getNoteValue(ParametersDefault.getScoreAnnotationNoteTypeName());
				scoreAnnotation = scoreSystem.getScore(Double.parseDouble(scoreNote));
				scoreThisAnnotationPath = scoreThisAnnotationPath * scoreAnnotation;
			}
			score = score + scoreThisAnnotationPath;
		}
	}

	/* (non-Javadoc)
	 * @see org.cycads.extract.cyc.CycEC#getScore()
	 */
	public double getScore() {
		return score;
	}

}
