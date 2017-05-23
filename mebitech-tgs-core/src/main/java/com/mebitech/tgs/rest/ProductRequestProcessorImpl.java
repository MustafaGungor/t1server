package com.mebitech.tgs.rest;

import com.mebitech.core.api.rest.IResponseFactory;
import com.mebitech.core.api.rest.enums.RestResponseStatus;
import com.mebitech.core.api.rest.responses.IRestResponse;
import com.mebitech.tgs.core.api.persistence.dao.IProductDao;
import com.mebitech.tgs.core.api.persistence.entities.IProduct;
import com.mebitech.tgs.core.api.rest.processors.IProductRequestProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Processor class for handling/processing resource data.
 * 
 *
 */
public class ProductRequestProcessorImpl implements IProductRequestProcessor {

	private static Logger logger = LoggerFactory.getLogger(ProductRequestProcessorImpl.class);

	private IProductDao productDao;
	private IResponseFactory responseFactory;

	@Override
	public IRestResponse list(String hostname, String dn, String uid) {
		// Build search criteria
		Map<String, Object> propertiesMap = new HashMap<String, Object>();
		if (hostname != null && !hostname.isEmpty()) {
			propertiesMap.put("hostname", hostname);
		}
		if (dn != null && !dn.isEmpty()) {
			propertiesMap.put("dn", dn);
		}
		if (uid != null && !uid.isEmpty()) {
			propertiesMap.put("jid", uid);
		}

		// Find desired product
		List<? extends IProduct> products = productDao.findByProperties(IProduct.class, propertiesMap, null, null);
		logger.debug("Found products: {}", products);

		// Construct result map
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			resultMap.put("products", products);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return responseFactory.createResponse(RestResponseStatus.OK, "Records listed.", resultMap);
	}

	
	public void setProductDao(IProductDao productDao) {
		this.productDao = productDao;
	}

	public void setResponseFactory(IResponseFactory responseFactory) {
		this.responseFactory = responseFactory;
	}

}
