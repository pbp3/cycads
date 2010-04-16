package org.cycads.entities.factory;

import org.cycads.entities.BasicEntity;
import org.cycads.entities.note.Type;

public class BasicTypeFactory implements EntityTypeFactory<Type> {

	BasicEntity basicEntity;

	public BasicTypeFactory(BasicEntity basicEntity) {
		this.basicEntity = basicEntity;
	}

	@Override
	public Type getType(String typeName) {
		return basicEntity.getNoteType(typeName);
	}
	
	
}
