package org.cycads.entities.note;

public class SimpleType implements Type {

	String name, description;
	
	public SimpleType(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Type)) {
			return false;
		}
		Type type = (Type) obj;

		return this.getName().equals(type.getName());
	}

	@Override
	public int compareTo(Type o) {
		return getName().compareTo(o.getName());
	}
	

}
