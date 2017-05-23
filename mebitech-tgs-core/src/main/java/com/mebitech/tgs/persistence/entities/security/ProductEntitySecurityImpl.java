package com.mebitech.tgs.persistence.entities.security;

import com.mebitech.core.api.persistence.entities.security.IEntitySecurity;
import com.mebitech.core.api.persistence.enums.CrudType;

public class ProductEntitySecurityImpl implements IEntitySecurity{

	private CrudType[] crudTypes;
	private String[] entityAttributes;

	@Override
	public void addCrudType(CrudType[] crudType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addEntityAttributes(String[] attirubeName) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public CrudType[] getCrudType() {
		// TODO Auto-generated method stub
		return this.crudTypes;
	}

	@Override
	public String[] getEntityAttributes() {
		// TODO Auto-generated method stub
		return this.entityAttributes;
	}

}
