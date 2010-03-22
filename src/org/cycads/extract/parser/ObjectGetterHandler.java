package org.cycads.extract.parser;

import org.cycads.extract.objectsGetter.ObjectsGetterChangeObject;
import org.cycads.extract.objectsGetter.validator.CompNumber;
import org.cycads.extract.objectsGetter.validator.CompRegex;

public interface ObjectGetterHandler {

	public void newChanger(ObjectsGetterChangeObject changer);

	public void startFilter();

	public void endFilter();

	public void newCompRegex(CompRegex compRegex);

	public void newCompNumber(CompNumber compNumber);
}
