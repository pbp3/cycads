package org.cycads.entities.annotation.dBLink;

import org.cycads.entities.annotation.AnnotationMethod;

public interface DBLinkSource<D extends DBLink<S, R, M>, S extends DBLinkSource< ? , ? , ? , ? >, R extends DBRecord< ? , ? , ? , ? >, M extends AnnotationMethod>
{
	public D createDBLink(M method, R record);

	public D createDBLink(M method, String accession, String dbName);

}
