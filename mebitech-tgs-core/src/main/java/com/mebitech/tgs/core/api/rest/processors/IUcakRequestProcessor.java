package com.mebitech.tgs.core.api.rest.processors;

import com.mebitech.core.api.rest.processors.IBaseRequestProcessor;
import com.mebitech.core.api.rest.responses.IRestResponse;
import com.mebitech.core.api.persistence.filter.IFilterAndPager;
import com.mebitech.tgs.core.api.persistence.entities.IUcak;

public interface IUcakRequestProcessor extends IBaseRequestProcessor {
    IRestResponse list(String hostname, String dn, String uid);

    IRestResponse add(IUcak ucak);

    IRestResponse update(IUcak ucak);

    IRestResponse delete(Long id);

    IRestResponse getProperties();

    IRestResponse findById(Long id);

    IRestResponse findByFilters(IFilterAndPager filterAndPager);

}
