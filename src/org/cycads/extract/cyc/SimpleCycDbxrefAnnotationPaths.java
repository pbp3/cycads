/*
 * Created on 14/04/2009
 */
package org.cycads.extract.cyc;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.extract.score.TransformScore;
import org.cycads.extract.score.AnnotationScoreSystem;

public class SimpleCycDbxrefAnnotationPaths implements CycDbxrefAnnotationPaths
{

	private final AnnotationScoreSystem		scoreSystems;
	private final List<List<Annotation>>	annotationPaths	= new ArrayList<List<Annotation>>();
	private double							score			= 0;
	private final Dbxref					dbxref;

	public SimpleCycDbxrefAnnotationPaths(Dbxref dbxref, AnnotationScoreSystem scoreSystems) {
		this.dbxref = dbxref;
		this.scoreSystems = scoreSystems;
	}

	public SimpleCycDbxrefAnnotationPaths(Dbxref dbxref, List<Annotation> annotationsList,
			AnnotationScoreSystem scoreSystems) {
		this.dbxref = dbxref;
		this.scoreSystems = scoreSystems;
		addAnnotationPath(annotationsList);
	}

	@Override
	public Dbxref getDbxref() {
		return dbxref;
	}

	@Override
	public String getDbName() {
		return dbxref.getDbName();
	}

	public String getAccession() {
		return dbxref.getAccession();
	}

	public List<List<Annotation>> getAnnotationPaths() {
		return annotationPaths;
	}

	public void addAnnotationPath(List<Annotation> annotationPath) {
		if (annotationPath != null && !annotationPath.isEmpty()) {
			annotationPaths.add(annotationPath);
			TransformScore transformScore;
			double scoreAnnotation;
			String scoreNote;
			double scoreAnnotationPath = 1;
			for (Annotation annot : annotationPath) {
				transformScore = scoreSystems.getTransformScore(annot.getAnnotationMethod());
				scoreNote = annot.getScore();
				Double scoreDbl = null;
				if (scoreNote != null) {
					NumberFormat numberFormat;
					if (scoreNote.indexOf('%') != -1) {
						numberFormat = NumberFormat.getPercentInstance(Locale.US);
					}
					else if (scoreNote.indexOf('E') != -1) {
						numberFormat = NumberFormat.getNumberInstance(Locale.US);
					}
					else {
						numberFormat = NumberFormat.getNumberInstance(Locale.US);
					}
					try {
						scoreDbl = numberFormat.parse(scoreNote).doubleValue();
					}
					catch (ParseException e) {
						e.printStackTrace();
						throw new RuntimeException(e);
					}
				}
				scoreAnnotation = transformScore.getScoreDbl(scoreDbl);
				scoreAnnotationPath = scoreAnnotationPath * scoreAnnotation;
			}
			score = score + scoreAnnotationPath;
		}
	}

	public double getScore() {
		return score;
	}

}
