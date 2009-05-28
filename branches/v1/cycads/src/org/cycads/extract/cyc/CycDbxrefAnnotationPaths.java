/*
 * Created on 22/04/2009
 */
package org.cycads.extract.cyc;

import java.util.List;

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.synonym.Dbxref;

public interface CycDbxrefAnnotationPaths
{
	public Dbxref getDbxref();

	public String getAccession();

	public String getDbName();

	public List<List<Annotation>> getAnnotationPaths();

	public void addAnnotationPath(List<Annotation> annotationPath);

	public double getScore();
}