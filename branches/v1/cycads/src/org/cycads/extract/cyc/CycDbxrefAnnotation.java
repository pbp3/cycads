/*
 * Created on 22/04/2009
 */
package org.cycads.extract.cyc;

import java.util.List;

import org.cycads.entities.annotation.Annotation;

public interface CycDbxrefAnnotation
{

	public abstract String getAccession();

	public abstract String getDbName();

	public abstract List<List<Annotation>> getAnnotationPaths();

	public abstract void addAnnotationPath(List<Annotation> annotationPath);

	public abstract double getScore();

}