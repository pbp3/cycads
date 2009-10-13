/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association;

import org.cycads.general.Messages;
import org.cycads.parser.ParserException;

public class DefaultNameOverwrite implements TypeNameTransformer
{
	String	typeNameDefault;

	public DefaultNameOverwrite(String typeNameDefault) {
		if (typeNameDefault == null || typeNameDefault.length() == 0 || typeNameDefault.equals(this.TYPE_NAME_GENERIC)) {
			typeNameDefault = null;
		}
		this.typeNameDefault = typeNameDefault;
	}

	@Override
	public String getTypeName(String typeName, String value) throws ParserException {
		if (typeNameDefault == null || typeNameDefault.length() == 0) {
			if (typeName == null || typeName.length() == 0) {
				throw new ParserException(Messages.invalidTypeNameException(typeName));
			}
			return typeName;
		}
		return typeNameDefault;
	}
}
