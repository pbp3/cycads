package org.cycads.entities.annotation.dBLink;

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationMethod;

public interface DBLinkAnnot<DBLINK_TYPE extends DBLinkAnnot< ? , ? , ? , ? >, S extends DBLinkAnnotSource< ? , ? , ? >, R extends DBRecord< ? , ? , ? , ? >, M extends AnnotationMethod>
		extends Annotation<DBLINK_TYPE, S, M>
{

}