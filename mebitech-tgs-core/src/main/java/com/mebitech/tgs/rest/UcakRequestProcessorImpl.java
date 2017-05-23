package com.mebitech.tgs.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mebitech.core.api.persistence.filter.IFilterAndPager;
import com.mebitech.rest.util.BaseRequestProcessor;
import com.mebitech.tgs.core.api.persistence.dao.IUcakDao;
import com.mebitech.tgs.core.api.persistence.entities.IUcak;
import com.mebitech.core.api.rest.IResponseFactory;
import com.mebitech.core.api.rest.enums.RestResponseStatus;
import com.mebitech.tgs.core.api.rest.processors.IUcakRequestProcessor;
import com.mebitech.core.api.rest.responses.IRestResponse;

public class UcakRequestProcessorImpl extends BaseRequestProcessor implements IUcakRequestProcessor {
    private static Logger logger = LoggerFactory.getLogger(UcakRequestProcessorImpl.class);
    private IUcakDao ucakDao;

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
        List<? extends IUcak> ucakler = (List<? extends IUcak>) ucakDao.findByProperties(propertiesMap, null, null);
        logger.debug("Found Ucak: {}", ucakler);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            resultMap.put("Ucak", ucakler);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return responseFactory.createResponse(RestResponseStatus.OK, "Records listed.", resultMap);
    }

    @Override
    public IRestResponse add(IUcak obj) {
        IUcak objc = null;
        try {
            objc = (IUcak) ucakDao.save(obj);
            createResponse(objc);
        } catch (Exception ex) {
            Error(ex.getMessage());
        }
        return getResponse();
    }

    @Override
    public IRestResponse update(IUcak obj) {
        IUcak objc = null;
        try {
            objc = (IUcak) ucakDao.update(obj);
            createResponse(objc);
        } catch (Exception ex) {
            Error(ex.getMessage());
        }
        return getResponse();
    }

    @Override
    public IRestResponse delete(Long id) {
        try {
            ucakDao.delete(id);
            createResponse(id);
        } catch (Exception ex) {
            Error(ex.getMessage());
        }
        return getResponse();
    }

    @Override
    public IRestResponse getProperties() {
        createResponse(ucakDao.getProperties());
        return getResponse();
    }

    @Override
    public IRestResponse findById(Long id) {
        createResponse(ucakDao.find(id));
        return getResponse();
    }

    @Override
    public IRestResponse findByFilters(IFilterAndPager filterAndPager) {
        Map<String, Object> retMap = ucakDao.findByFilters(filterAndPager);
        createResponse(retMap.get("data"), (Long) retMap.get("count"));
        return getResponse();
    }

    public void setUcakDao(IUcakDao ucakDao) {
        this.ucakDao = ucakDao;
    }
}