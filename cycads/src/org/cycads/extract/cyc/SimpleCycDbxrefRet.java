package org.cycads.extract.cyc;

import java.util.List;

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.synonym.Dbxref;

public class SimpleCycDbxrefRet extends SimpleCycValueRet implements CycDbxrefRet
{

	Dbxref	dbxref;

	public SimpleCycDbxrefRet(Dbxref dbxref, List<Annotation> annotations) {
		super(dbxref.toString(), annotations);
		this.dbxref = dbxref;
	}

	public SimpleCycDbxrefRet(Dbxref dbxref) {
		super(dbxref.toString());
		this.dbxref = dbxref;
	}

	@Override
	public Dbxref getDbxref() {
		return dbxref;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CycDbxrefRet)) {
			return false;
		}
		CycDbxrefRet o = (CycDbxrefRet) obj;
		return getDbxref().equals(o.getDbxref());
	}

}
