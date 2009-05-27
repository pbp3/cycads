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

public class SimpleCycDbxrefAnnotation implements CycDbxrefAnnotation
{

	private final ScoreSystemCollection		scoreSystems;
	private final String					accession;
	private final String					dbName;
	private final List<List<Annotation>>	annotationPaths	= new ArrayList<List<Annotation>>();
	private double							score			= 0;

	public SimpleCycDbxrefAnnotation(String dbName, String accession, ScoreSystemCollection scoreSystems) {
		this.dbName = dbName;
		this.accession = accession;
		this.scoreSystems = scoreSystems;
	}

	public SimpleCycDbxrefAnnotation(String dbName, String accession, List<Annotation> annotationsList,
			ScoreSystemCollection scoreSystems) {
		this.dbName = dbName;
		this.accession = accession;
		this.scoreSystems = scoreSystems;
		addAnnotationPath(annotationsList);
	}

	public String getAccession() {
		return accession;
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
				scoreAnnotation = scoreSystem.getScore(scoreDbl);
				scoreAnnotationPath = scoreAnnotationPath * scoreAnnotation;
			}
			score = score + scoreAnnotationPath;
		}
	}

	public double getScore() {
		return score;
	}

	@Override
	public String getDbName() {
		return dbName;
	}

}
