/*
 * Created on 22/03/2010
 */
package org.cycads.extract.objectsGetter.changeObject;

import org.cycads.entities.annotation.Annotation;
import org.cycads.extract.general.AnnotationCluster;
import org.cycads.extract.general.GetterExpressionException;

// LOC "SC"
public class ChangeToScore extends ChangeToOneObject
{

	@Override
	public Object executeMethod(Object obj) throws GetterExpressionException {
		if (obj instanceof Annotation) {
			return ((Annotation) obj).getScore();
		}
		else if (obj instanceof AnnotationCluster) {
			return ((AnnotationCluster) obj).getScore();
		}
		else {
			throw new GetterExpressionException("Object is neither an annotation nor an annotation cluster. Object:"
				+ obj);
		}
	}

}
