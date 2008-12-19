package org.cycads.entities.annotation.dBLink;

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationMethod;

public interface DBLinkAnnotation<DBLINK_TYPE extends DBLinkAnnotation< ? , ? , ? , ? >, S extends DBLinkAnnotationSource< ? , ? , ? >, R extends DBRecord< ? , ? , ? , ? >, M extends AnnotationMethod>
		extends Annotation<DBLINK_TYPE, S, M>, ExternalSynonym<S, R>
{
	public R getDBRecordTarget();

}