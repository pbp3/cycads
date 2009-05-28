package org.cycads.extract.cyc;

import java.util.List;

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.synonym.Dbxref;

public class SimpleCycDbxref extends SimpleCycValue implements CycDbxref
{

	Dbxref	dbxref;

	public SimpleCycDbxref(Dbxref dbxref, List<Annotation> annotations) {
		super(dbxref.toString(), annotations);
		this.dbxref = dbxref;
	}

	public SimpleCycDbxref(Dbxref dbxref) {
		super(dbxref.toString());
		this.dbxref = dbxref;
	}

	@Override
	public Dbxref getDbxref() {
		return dbxref;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CycDbxref)) {
			return false;
		}
		CycDbxref o = (CycDbxref) obj;
		return getDbxref().equals(o.getDbxref());
	}

}
