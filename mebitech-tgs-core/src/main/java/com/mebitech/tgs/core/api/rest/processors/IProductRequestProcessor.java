package com.mebitech.tgs.core.api.rest.processors;

import com.mebitech.core.api.rest.responses.IRestResponse;

/**
 * 
 *
 */
public interface IProductRequestProcessor {

	/**
	 * 
	 * @param hostname
	 * @param dn
	 * @return
	 */
	IRestResponse list(String hostname, String dn, String uid);


}
