package com.mebitech.tgs.core.api.persistence.dao;

import java.util.List;
import java.util.Map;

import com.mebitech.core.api.persistence.IBaseDao;
import com.mebitech.core.api.persistence.PropertyOrder;
import com.mebitech.tgs.core.api.persistence.entities.IProduct;


/**
 * Provides il database operations.
 * 
 * @see
 *
 */
public interface IProductDao extends IBaseDao<IProduct> {

	/**
	 * 
	 * @param product
	 * @return
	 */
	IProduct save(IProduct product);

	/**
	 * 
	 * @param product
	 * @return
	 */
	IProduct update(IProduct product);

	/**
	 * 
	 * @param productId
	 */
	void delete(Long productId);

	/**
	 * 
	 * @param productId
	 * @return
	 */
	IProduct find(Long productId);

	/**
	 * 
	 * @return
	 */
	List<? extends IProduct> findAll(Class<? extends IProduct> obj, Integer maxResults);

	/**
	 * 
	 * @return
	 */
	List<? extends IProduct> findByProperty(Class<? extends IProduct> obj, String propertyName, Object propertyValue,
			Integer maxResults);

	/**
	 * 
	 * @return
	 */
	List<? extends IProduct> findByProperties(Class<? extends IProduct> obj, Map<String, Object> propertiesMap,
			List<PropertyOrder> orders, Integer maxResults);


}
