package org.cycads.entities.annotation.dBLink;

import org.cycads.entities.annotation.AnnotationMethod;

public interface DBLinkSource<D extends DBLink< ? , ? , R, M>, R extends DBRecord< ? , ? , ? , ? >, M extends AnnotationMethod>
{
	public D createDBLink(M method, R target);

	public D createDBLink(M method, String accession, String dbName);

	public D createDBLink(String method, R target);

	public D createDBLink(String method, String accession, String dbName);

}
