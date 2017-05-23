package com.mebitech.tgs.core.api.rest.processors;

import com.mebitech.core.api.rest.processors.IBaseRequestProcessor;
import com.mebitech.core.api.rest.responses.IRestResponse;
import com.mebitech.core.api.persistence.filter.IFilterAndPager;
import com.mebitech.tgs.core.api.persistence.entities.ITehirTanim;

public interface ITehirTanimRequestProcessor extends IBaseRequestProcessor {
    IRestResponse list(String hostname, String dn, String uid);

    IRestResponse add(ITehirTanim tehirTanim);

    IRestResponse update(ITehirTanim tehirTanim);

    IRestResponse delete(Long id);

    IRestResponse getProperties();

    IRestResponse findById(Long id);

    IRestResponse findByFilters(IFilterAndPager filterAndPager);

}
