/*
 * Created on 23/10/2009
 */
package org.cycads.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.annotation.Association;
import org.cycads.entities.factory.EntityAnnotationFactory;
import org.cycads.entities.factory.EntityDbxrefFactory;
import org.cycads.entities.factory.EntityTypeFactory;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.SimpleNote;
import org.cycads.entities.note.Type;
import org.cycads.entities.synonym.Dbxref;

public abstract class BasicEntityAbstract implements BasicEntity
{

	protected EntityDbxrefFactory< ? >					dbxrefFactory;
	private final Hashtable<String, Collection<String>>	synonyms		= new Hashtable<String, Collection<String>>();
	protected EntityTypeFactory< ? >					typeFactory;
	private final Hashtable<String, Collection<String>>	notes			= new Hashtable<String, Collection<String>>();

	protected EntityAnnotationFactory					annotationFactory;
	private final Collection<Association< ? , ? >>		associations	= new ArrayList<Association< ? , ? >>();
	private final Collection<Annotation< ? , ? >>		annotations		= new ArrayList<Annotation< ? , ? >>();

	public BasicEntityAbstract(EntityTypeFactory< ? > typeFactory, EntityDbxrefFactory< ? > dbxrefFactory,
			EntityAnnotationFactory annotationFactory) {
		this.annotationFactory = annotationFactory;
		this.dbxrefFactory = dbxrefFactory;
		this.typeFactory = typeFactory;
	}

	@Override
	public <TA extends BasicEntity> Association< ? , TA> addAssociation(TA target, Collection<Type> associationTypes) {
		Association< ? , TA> ret = annotationFactory.createAssociation(this, target, associationTypes);
		associations.add(ret);
		return ret;
	}

	@Override
	public <TA extends BasicEntity> Collection<Association< ? , TA>> getAssociations(TA target,
			Collection<Type> associationTypes) {
		Collection<Association< ? , TA>> ret = new ArrayList<Association< ? , TA>>();
		for (Association< ? , ? > association : associations) {
			boolean isTypes = true;
			if (associationTypes != null) {
				for (Type type : associationTypes) {
					if (!association.getTypes().contains(type)) {
						isTypes = false;
						break;
					}
				}
			}
			if (isTypes && (target == null || association.getTarget().equals(target))) {
				ret.add((Association< ? , TA>) association);
			}
		}
		return ret;
	}

	@Override
	public <TA extends BasicEntity> Annotation< ? , TA> addAnnotation(TA target, AnnotationMethod method,
			String score, Collection<Type> annotationTypes) {
		Annotation< ? , TA> ret = annotationFactory.createAnnotation(this, target, annotationTypes, method, score);
		annotations.add(ret);
		return ret;
	}

	@Override
	public <TA extends BasicEntity> Collection< ? extends Annotation< ? , TA>> getAnnotations(TA target,
			AnnotationMethod method, Collection<Type> annotationTypes) {
		Collection<Annotation< ? , TA>> ret = new ArrayList<Annotation< ? , TA>>();
		for (Annotation< ? , ? > annotation : annotations) {
			boolean isTypes = true;
			if (annotationTypes != null) {
				for (Type type : annotationTypes) {
					if (!annotation.getTypes().contains(type)) {
						isTypes = false;
						break;
					}
				}
			}
			if (isTypes && (target == null || annotation.getTarget().equals(target))
				&& (method == null || method.equals(annotation.getAnnotationMethod()))) {
				ret.add((Annotation< ? , TA>) annotation);
			}
		}
		return ret;
	}

	public Collection< ? extends Annotation< ? , ? >> getAnnotationsByType(Type targetType, AnnotationMethod method,
			Collection<Type> annotationTypes) {
		Collection<Annotation< ? , ? >> ret = new ArrayList<Annotation< ? , ? >>();
		for (Annotation< ? , ? > annotation : annotations) {
			boolean isTypes = true;
			if (annotationTypes != null) {
				for (Type type : annotationTypes) {
					if (!annotation.getTypes().contains(type)) {
						isTypes = false;
						break;
					}
				}
			}
			if (isTypes && (targetType == null || annotation.getTarget().getEntityType().equals(targetType))
				&& (method == null || method.equals(annotation.getAnnotationMethod()))) {
				ret.add((Annotation< ? , ? >) annotation);
			}
		}
		return ret;
	}

	@Override
	public Collection< ? extends Annotation< ? , ? >> getAnnotationsBySynonym(Dbxref synonym) {
		return getAnnotationsBySynonym(synonym.getDbName(), synonym.getAccession());
	}

	@Override
	public Collection< ? extends Annotation< ? , ? >> getAnnotationsBySynonym(String dbName, String accession) {
		Collection<Annotation< ? , ? >> ret = new ArrayList<Annotation< ? , ? >>();
		for (Annotation< ? , ? > annotation : annotations) {
			if (annotation.isSynonym(dbName, accession)) {
				ret.add((Annotation< ? , ? >) annotation);
			}
		}
		return ret;
	}

	@Override
	public Dbxref addSynonym(String dbName, String accession) {
		Collection<String> accessions = synonyms.get(dbName);
		if (accessions == null) {
			accessions = new TreeSet<String>();
			synonyms.put(dbName, accessions);
		}
		if (accessions.add(accession)) {
			return dbxrefFactory.getDbxref(dbName, accession);
		}
		else {
			return null;
		}
	}

	@Override
	public Dbxref addSynonym(Dbxref dbxref) {
		String dbName = dbxref.getDbName();
		String accession = dbxref.getAccession();
		Collection<String> accessions = synonyms.get(dbName);
		if (accessions == null) {
			accessions = new TreeSet<String>();
			synonyms.put(dbName, accessions);
		}
		accessions.add(accession);
		return dbxref;
	}

	@Override
	public Dbxref getSynonym(String dbName, String accession) {
		Collection<String> values = synonyms.get(dbName);
		if (values == null || !values.contains(accession)) {
			return null;
		}
		return dbxrefFactory.getDbxref(dbName, accession);
	}

	@Override
	public Collection<Dbxref> getSynonyms() {
		Collection<Dbxref> ret = new ArrayList<Dbxref>();
		Set<Entry<String, Collection<String>>> entries = synonyms.entrySet();
		for (Entry<String, Collection<String>> entry : entries) {
			String dbName = entry.getKey();
			for (String accession : entry.getValue()) {
				ret.add(dbxrefFactory.getDbxref(dbName, accession));
			}
		}
		return ret;
	}

	@Override
	public Collection<Dbxref> getSynonyms(String dbName) {
		Collection<Dbxref> ret = new ArrayList<Dbxref>();
		Collection<String> accessions = synonyms.get(dbName);
		for (String accession : accessions) {
			ret.add(dbxrefFactory.getDbxref(dbName, accession));
		}
		return ret;
	}

	@Override
	public boolean isSynonym(Dbxref dbxref) {
		return isSynonym(dbxref.getDbName(), dbxref.getAccession());
	}

	@Override
	public boolean isSynonym(String dbName, String accession) {
		return (getSynonym(dbName, accession) != null);
	}

	@Override
	public void addNote(String noteType, String value) {
		Note note = new SimpleNote(getNoteType(noteType), value);
		Collection<String> values = notes.get(noteType);
		if (values == null) {
			values = new TreeSet<String>();
			notes.put(noteType, values);
		}
		values.add(value);
	}

	@Override
	public void addNote(Type noteType, String value) {
		Note note = new SimpleNote(noteType, value);
		Collection<String> values = notes.get(noteType);
		if (values == null) {
			values = new TreeSet<String>();
			notes.put(noteType.getName(), values);
		}
		values.add(value);
	}

	@Override
	public Note getNote(String noteType, String value) {
		Collection<String> values = notes.get(noteType);
		if (values == null || !values.contains(value)) {
			return null;
		}
		return new SimpleNote(getNoteType(noteType), value);
	}

	@Override
	public Type getNoteType(String noteType) {
		return typeFactory.getType(noteType);
	}

	@Override
	public String getNoteValue(String noteType) {
		Collection<String> values = notes.get(noteType);
		if (values == null || values.isEmpty()) {
			return null;
		}
		return values.iterator().next();
	}

	@Override
	public Collection<Note> getNotes() {
		Collection<Note> ret = new ArrayList<Note>();
		Set<Entry<String, Collection<String>>> entries = notes.entrySet();
		for (Entry<String, Collection<String>> entry : entries) {
			Type type = getNoteType(entry.getKey());
			for (String value : entry.getValue()) {
				ret.add(new SimpleNote(type, value));
			}
		}
		return ret;
	}

	@Override
	public Collection<Note> getNotes(String noteType) {
		Collection<Note> ret = new ArrayList<Note>();
		Type type = getNoteType(noteType);
		Collection<String> values = notes.get(noteType);
		if (values != null) {
			for (String value : values) {
				ret.add(new SimpleNote(type, value));
			}
		}
		return ret;
	}

	@Override
	public Collection<String> getNotesValues(String noteType) {
		return notes.get(noteType);
	}

	@Override
	public void setNoteValue(String noteType, String value) {
		Collection<String> values = notes.get(noteType);
		if (values != null) {
			values.clear();
		}
		else {
			values = new TreeSet<String>();
			notes.put(noteType, values);
		}
		values.add(value);
	}

}
