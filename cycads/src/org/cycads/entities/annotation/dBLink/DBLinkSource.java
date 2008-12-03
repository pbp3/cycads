package org.cycads.entities.annotation.dBLink;

import org.cycads.entities.annotation.AnnotationMethod;

public interface DBLinkSource<D extends DBLink<S, R>, S extends DBLinkSource< ? , ? , ? >, R extends DBRecord< ? , ? , ? >>
{
	public D createDBLink(AnnotationMethod method, R record);

	public D createDBLink(AnnotationMethod method, String accession, String dbName);

}
