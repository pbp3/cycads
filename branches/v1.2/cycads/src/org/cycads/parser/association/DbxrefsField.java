/*
 * Created on 29/09/2009
 */
package org.cycads.parser.association;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.cycads.entities.annotation.AnnotationFilter;
import org.cycads.entities.annotation.AnnotationFinder;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.annotation.DbxrefAnnotable;
import org.cycads.entities.annotation.DbxrefDbxrefAnnotation;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.Noteble;
import org.cycads.entities.note.Type;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.entities.synonym.HasSynonyms;

public class DbxrefsField<X extends Dbxref>
		implements Noteble, HasSynonyms<Dbxref>, AnnotationFinder<DbxrefDbxrefAnnotation, X, Type, AnnotationMethod>,
		DbxrefAnnotable<DbxrefDbxrefAnnotation, X, AnnotationMethod>
{
	List<X>	dbxrefs	= new ArrayList<X>();

	public void add(X dbxref) {
		dbxrefs.add(dbxref);
	}

	public List<X> getListDbxref() {
		return dbxrefs;
	}

	@Override
	public void addNote(String noteType, String value) {
		for (X dbxref : getListDbxref()) {
			dbxref.addNote(noteType, value);
		}
	}

	@Override
	public Note getNote(String noteType, String value) {
		Note ret = null;
		for (X dbxref : getListDbxref()) {
			ret = dbxref.getNote(noteType, value);
			if (ret != null) {
				break;
			}
		}
		return ret;
	}

	@Override
	public Type getNoteType(String noteType) {
		Type ret = null;
		for (X dbxref : getListDbxref()) {
			ret = dbxref.getNoteType(noteType);
			if (ret != null) {
				break;
			}
		}
		return ret;
	}

	@Override
	public String getNoteValue(String noteType) {
		String ret = null;
		for (X dbxref : getListDbxref()) {
			ret = dbxref.getNoteValue(noteType);
			if (ret != null) {
				break;
			}
		}
		return ret;
	}

	@Override
	public Collection<Note> getNotes() {
		Collection<Note> ret = new ArrayList<Note>();
		for (X dbxref : getListDbxref()) {
			ret.addAll(dbxref.getNotes());
		}
		return ret;
	}

	@Override
	public Collection<Note> getNotes(String noteType) {
		Collection<Note> ret = new ArrayList<Note>();
		for (X dbxref : getListDbxref()) {
			ret.addAll(dbxref.getNotes(noteType));
		}
		return ret;
	}

	@Override
	public Collection<String> getNotesValues(String noteType) {
		Collection<String> ret = new ArrayList<String>();
		for (X dbxref : getListDbxref()) {
			ret.addAll(dbxref.getNotesValues(noteType));
		}
		return ret;
	}

	@Override
	public void setNoteValue(String noteType, String value) {
		for (X dbxref : getListDbxref()) {
			dbxref.setNoteValue(noteType, value);
		}
	}

	@Override
	public Dbxref addSynonym(String dbName, String accession) {
		Dbxref ret = null;
		for (X dbxref : getListDbxref()) {
			if (ret == null) {
				ret = dbxref.addSynonym(dbName, accession);
			}
			else {
				dbxref.addSynonym(ret);
			}
		}
		return ret;
	}

	@Override
	public void addSynonym(Dbxref syn) {
		for (X dbxref : getListDbxref()) {
			dbxref.addSynonym(syn);
		}
	}

	@Override
	public Dbxref getSynonym(String dbName, String accession) {
		Dbxref ret = null;
		for (X dbxref : getListDbxref()) {
			ret = dbxref.getSynonym(dbName, accession);
			if (ret != null) {
				break;
			}
		}
		return ret;
	}

	@Override
	public Collection< ? extends Dbxref> getSynonyms() {
		Collection<Dbxref> ret = new ArrayList<Dbxref>();
		for (X dbxref : getListDbxref()) {
			ret.addAll(dbxref.getSynonyms());
		}
		return ret;
	}

	@Override
	public Collection< ? extends Dbxref> getSynonyms(String dbName) {
		Collection<Dbxref> ret = new ArrayList<Dbxref>();
		for (X dbxref : getListDbxref()) {
			ret.addAll(dbxref.getSynonyms(dbName));
		}
		return ret;
	}

	@Override
	public boolean isSynonym(Dbxref syn) {
		if (getListDbxref().isEmpty()) {
			return false;
		}
		for (X dbxref : getListDbxref()) {
			if (!dbxref.isSynonym(syn)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isSynonym(String dbName, String accession) {
		if (getListDbxref().isEmpty()) {
			return false;
		}
		for (X dbxref : getListDbxref()) {
			if (!dbxref.isSynonym(dbName, accession)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Collection< ? extends DbxrefDbxrefAnnotation> getAnnotations(AnnotationMethod method,
			Collection<Type> types, X synonym) {
		Collection<DbxrefDbxrefAnnotation> ret = new ArrayList<DbxrefDbxrefAnnotation>();
		for (X dbxref : getListDbxref()) {
			ret.addAll(dbxref.getAnnotations(method, types, synonym));
		}
		return ret;
	}

	@Override
	public Collection< ? extends DbxrefDbxrefAnnotation> getAnnotations(AnnotationFilter<DbxrefDbxrefAnnotation> filter) {
		Collection<DbxrefDbxrefAnnotation> ret = new ArrayList<DbxrefDbxrefAnnotation>();
		for (X dbxref : getListDbxref()) {
			ret.addAll(dbxref.getAnnotations(filter));
		}
		return ret;
	}

	@Override
	public Collection< ? extends DbxrefDbxrefAnnotation> getDbxrefAnnotations(AnnotationMethod method, X otherDbxref) {
		Collection<DbxrefDbxrefAnnotation> ret = new ArrayList<DbxrefDbxrefAnnotation>();
		for (X dbxref : getListDbxref()) {
			ret.addAll(dbxref.getDbxrefAnnotations(method, otherDbxref));
		}
		return ret;
	}

	@Override
	public Collection< ? extends DbxrefDbxrefAnnotation> getDbxrefAnnotations(String dbxrefDbname) {
		Collection<DbxrefDbxrefAnnotation> ret = new ArrayList<DbxrefDbxrefAnnotation>();
		for (X dbxref : getListDbxref()) {
			ret.addAll(dbxref.getDbxrefAnnotations(dbxrefDbname));
		}
		return ret;
	}

	@Override
	public DbxrefDbxrefAnnotation addDbxrefAnnotation(AnnotationMethod method, X otherDbxref) {
		DbxrefDbxrefAnnotation ret = null;
		for (X dbxref : getListDbxref()) {
			ret = (DbxrefDbxrefAnnotation) dbxref.addDbxrefAnnotation(method, otherDbxref);
		}
		return ret;
	}

	@Override
	public DbxrefDbxrefAnnotation createDbxrefAnnotation(AnnotationMethod method, X otherDbxref) {
		DbxrefDbxrefAnnotation ret = null;
		for (X dbxref : getListDbxref()) {
			ret = (DbxrefDbxrefAnnotation) dbxref.createDbxrefAnnotation(method, otherDbxref);
		}
		return ret;
	}

}
