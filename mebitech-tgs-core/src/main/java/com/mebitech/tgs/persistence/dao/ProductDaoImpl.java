package com.mebitech.tgs.persistence.dao;

import com.mebitech.core.api.persistence.PropertyOrder;
import com.mebitech.core.api.persistence.enums.OrderType;
import com.mebitech.tgs.core.api.persistence.dao.IProductDao;
import com.mebitech.tgs.core.api.persistence.entities.IProduct;
import com.mebitech.tgs.persistence.entities.ProductImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/**
 * Provides database access for product. CRUD operations for product and their
 * property records should be handled via this service only.
 * 
 * @see com.mebitech.eFatura.core.api.persistence.dao.IProductDao
 *
 */
@SuppressWarnings("unchecked")
public class ProductDaoImpl implements IProductDao {

	private static Logger logger = LoggerFactory.getLogger(ProductDaoImpl.class);

	private EntityManager entityManager;

	public void init() {
		logger.info("Initializing il DAO.");
	}

	public void destroy() {
		logger.info("Destroying il DAO.");
	}

	@Override
	public IProduct save(IProduct il) {
		ProductImpl ProductImpl = new ProductImpl((IProduct) il);
		entityManager.persist(ProductImpl);
		logger.debug("IProduct object persisted: {}", ProductImpl.toString());
		return ProductImpl;
	}



	@Override
	public ProductImpl update(IProduct il) {
		ProductImpl ProductImpl = new ProductImpl(il);
		ProductImpl = entityManager.merge(ProductImpl);
		logger.debug("IProduct object merged: {}", ProductImpl.toString());
		return ProductImpl;
	}

	@Override
	public void delete(Long ilId) {
		ProductImpl ProductImpl = entityManager.find(ProductImpl.class, ilId);
		// Never truly delete, just mark as deleted!
		ProductImpl.setDeleted(true);
		ProductImpl = entityManager.merge(ProductImpl);
		logger.debug("IProduct object marked as deleted: {}", ProductImpl.toString());
	}

	@Override
	public ProductImpl find(Long ilId) {
		ProductImpl ProductImpl = entityManager.find(ProductImpl.class, ilId);
		logger.debug("IProduct object found: {}", ProductImpl.toString());
		return ProductImpl;
	}

	@Override
	public List<? extends IProduct> findAll(Class<? extends IProduct> obj, Integer maxResults) {
		List<ProductImpl> ilList = entityManager
				.createQuery("select t from " + ProductImpl.class.getSimpleName() + " t", ProductImpl.class)
				.getResultList();
		logger.debug("IProduct objects found: {}", ilList);
		return ilList;
	}

	@Override
	public List<? extends IProduct> findByProperty(Class<? extends IProduct> obj, String propertyName, Object propertyValue,
			Integer maxResults) {
		TypedQuery<ProductImpl> query = entityManager.createQuery(
				"select t from " + ProductImpl.class.getSimpleName() + " t where t." + propertyName + "= :propertyValue",
				ProductImpl.class).setParameter("propertyValue", propertyValue);
		if (maxResults > 0) {
			query = query.setMaxResults(maxResults);
		}
		List<ProductImpl> ilList = query.getResultList();
		logger.debug("IProduct objects found: {}", ilList);
		return ilList;
	}

	@Override
	public List<? extends IProduct> findByProperties(Class<? extends IProduct> obj, Map<String, Object> propertiesMap,
			List<PropertyOrder> orders, Integer maxResults) {
		orders = new ArrayList<PropertyOrder>();
		// TODO
		// PropertyOrder ord = new PropertyOrder("name", OrderType.ASC);
		// orders.add(ord);
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProductImpl> criteria = (CriteriaQuery<ProductImpl>) builder.createQuery(ProductImpl.class);
		Root<ProductImpl> from = (Root<ProductImpl>) criteria.from(ProductImpl.class);
		criteria.select(from);
		Predicate predicate = null;

		if (propertiesMap != null) {
			Predicate pred = null;
			for (Entry<String, Object> entry : propertiesMap.entrySet()) {
				if (entry.getValue() != null && !entry.getValue().toString().isEmpty()) {
					String[] key = entry.getKey().split(".");
					if (key.length > 1) {
						Join<Object, Object> join = null;
						for (int i = 0; i < key.length - 1; i++) {
							join = join != null ? join.join(key[i]) : from.join(key[i]);
						}
						pred = builder.equal(join.get(key[key.length - 1]), entry.getValue());
					} else {
						pred = builder.equal(from.get(entry.getKey()), entry.getValue());
					}
					predicate = predicate == null ? pred : builder.and(predicate, pred);
				}
			}
			if (predicate != null) {
				criteria.where(predicate);
			}
		}

		if (orders != null && !orders.isEmpty()) {
			List<Order> orderList = new ArrayList<Order>();
			for (PropertyOrder order : orders) {
				orderList.add(order.getOrderType() == OrderType.ASC ? builder.asc(from.get(order.getPropertyName()))
						: builder.desc(from.get(order.getPropertyName())));
			}
			criteria.orderBy(orderList);
		}

		List<ProductImpl> list = null;
		if (null != maxResults) {
			list = entityManager.createQuery(criteria).setMaxResults(maxResults).getResultList();
		} else {
			list = entityManager.createQuery(criteria).getResultList();
		}

		return list;
	}
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


	

}
