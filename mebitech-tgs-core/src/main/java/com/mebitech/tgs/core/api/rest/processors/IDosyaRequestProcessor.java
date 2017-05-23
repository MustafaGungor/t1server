package com.mebitech.tgs.core.api.rest.processors;

import com.mebitech.core.api.rest.responses.IRestResponse;
import com.mebitech.core.api.persistence.filter.IFilterAndPager;
import com.mebitech.tgs.core.api.persistence.entities.IDosya;

public interface IDosyaRequestProcessor {
    IRestResponse list(String hostname, String dn, String uid);

    IRestResponse add(IDosya dosya);

    IRestResponse update(IDosya dosya);

    IRestResponse delete(Long id);

    IRestResponse getProperties();

    IRestResponse findById(Long id);

    IRestResponse findByFilters(IFilterAndPager filterAndPager);

}
