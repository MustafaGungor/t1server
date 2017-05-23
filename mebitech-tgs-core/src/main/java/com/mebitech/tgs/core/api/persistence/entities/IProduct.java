package com.mebitech.tgs.core.api.persistence.entities;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import com.mebitech.core.api.persistence.entities.IEntity;
import com.mebitech.core.api.persistence.entities.security.IEntitySecurity;

/**
 * IIl entity class is responsible for storing il records.
 * 
 *
 */
public interface IProduct extends IEntity<IEntitySecurity> {

	/**
	 * 
	 * @return
	 */
	Boolean getDeleted();

	/**
	 * 
	 * @return
	 */
	int getProductId();

	/**
	 * 
	 * @return
	 */
	Map<String, String> getProperties();
	
	/**
	 * 
	 * @return
	 */
	String getProductAdi();

	/**
	 * 
	 * @return
	 */
	Date getModifyDate();

	/**
	 * 
	 * @return JSON string representation of this instance
	 */
	String toJson();

	void applyEntitySecurity(IEntitySecurity entitySecurity);
}
