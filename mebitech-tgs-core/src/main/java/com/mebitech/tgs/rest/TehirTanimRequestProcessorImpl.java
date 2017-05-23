package com.mebitech.tgs.rest;

import java.util.*;

import com.mebitech.rest.util.BaseRequestProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mebitech.core.api.persistence.filter.IFilterAndPager;
import com.mebitech.tgs.core.api.persistence.dao.ITehirTanimDao;
import com.mebitech.tgs.core.api.persistence.entities.ITehirTanim;
import com.mebitech.core.api.rest.enums.RestResponseStatus;
import com.mebitech.tgs.core.api.rest.processors.ITehirTanimRequestProcessor;
import com.mebitech.core.api.rest.responses.IRestResponse;

public class TehirTanimRequestProcessorImpl extends BaseRequestProcessor implements ITehirTanimRequestProcessor {
    private static Logger logger = LoggerFactory.getLogger(TehirTanimRequestProcessorImpl.class);
    private ITehirTanimDao tehirTanimDao;

    @Override
    public IRestResponse list(String hostname, String dn, String uid) {
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
        List<? extends ITehirTanim> tehirTanimler = (List<? extends ITehirTanim>) tehirTanimDao.findByProperties(propertiesMap, null, null);
        logger.debug("Found TehirTanim: {}", tehirTanimler);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            resultMap.put("TehirTanim", tehirTanimler);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return responseFactory.createResponse(RestResponseStatus.OK, "Records listed.", resultMap);
    }

    @Override
    public IRestResponse add(ITehirTanim obj) {
        ITehirTanim objc = null;
        try {
            objc = (ITehirTanim) tehirTanimDao.save(obj);
            createResponse(objc);
        } catch (Exception ex) {
            Error(ex.getMessage());
        }
        return getResponse();
    }

    @Override
    public IRestResponse update(ITehirTanim obj) {
        ITehirTanim objc = null;
        try {
            objc = (ITehirTanim) tehirTanimDao.update(obj);
            createResponse(objc);
        } catch (Exception ex) {
            Error(ex.getMessage());
        }
        return getResponse();
    }

    @Override
    public IRestResponse delete(Long id) {
        try {
            tehirTanimDao.delete(id);
            createResponse(id);
        } catch (Exception ex) {
            Error(ex.getMessage());
        }
        return getResponse();
    }

    @Override
    public IRestResponse getProperties() {
        createResponse(tehirTanimDao.getProperties());
        return getResponse();
    }

    @Override
    public IRestResponse findById(Long id) {
        createResponse(tehirTanimDao.find(id));
        return getResponse();
    }

    @Override
    public IRestResponse findByFilters(IFilterAndPager filterAndPager) {
        Map<String, Object> retMap = tehirTanimDao.findByFilters(filterAndPager);
        createResponse(retMap.get("data"), (Long) retMap.get("count"));
        return getResponse();
    }

    public void setTehirTanimDao(ITehirTanimDao tehirTanimDao) {
        this.tehirTanimDao = tehirTanimDao;
    }
}