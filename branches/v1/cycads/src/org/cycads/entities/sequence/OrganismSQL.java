/*
 * Created on 03/03/2009
 */
package org.cycads.entities.sequence;

import java.util.Collection;

import org.cycads.entities.annotation.AnnotationFilter;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.note.Type;
import org.cycads.entities.synonym.Dbxref;

public class OrganismSQL implements Organism
{

	@Override
	public Sequence createNewSequence(double version) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sequence getSequence(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection getSequences() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection getSequences(double version) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection getSequences(Dbxref synonym) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection getSubsequences(Dbxref synonym) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public Collection getSubseqAnnotations(Dbxref synonym) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection getSubseqAnnotations(Type type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection getSubseqAnnotations(AnnotationMethod method) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection getSubseqAnnotations(AnnotationMethod method, Type type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection getSubseqAnnotations(AnnotationFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}

}
