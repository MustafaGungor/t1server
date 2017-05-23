package com.mebitech.tgs.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mebitech.core.api.persistence.filter.IFilterAndPager;
import com.mebitech.rest.util.BaseRequestProcessor;
import com.mebitech.tgs.core.api.persistence.dao.IUcusTuruDao;
import com.mebitech.tgs.core.api.persistence.entities.IUcusTuru;
import com.mebitech.core.api.rest.IResponseFactory;
import com.mebitech.core.api.rest.enums.RestResponseStatus;
import com.mebitech.tgs.core.api.rest.processors.IUcusTuruRequestProcessor;
import com.mebitech.core.api.rest.responses.IRestResponse;

public class UcusTuruRequestProcessorImpl extends BaseRequestProcessor implements IUcusTuruRequestProcessor {
    private static Logger logger = LoggerFactory.getLogger(UcusTuruRequestProcessorImpl.class);
    private IUcusTuruDao ucusTuruDao;

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
        List<? extends IUcusTuru> ucusTuruler = (List<? extends IUcusTuru>) ucusTuruDao.findByProperties(propertiesMap, null, null);
        logger.debug("Found UcusTuru: {}", ucusTuruler);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            resultMap.put("UcusTuru", ucusTuruler);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return responseFactory.createResponse(RestResponseStatus.OK, "Records listed.", resultMap);
    }

    @Override
    public IRestResponse add(IUcusTuru obj) {
        IUcusTuru objc = null;
        try {
            objc = (IUcusTuru) ucusTuruDao.save(obj);
            createResponse(objc);
        } catch (Exception ex) {
            Error(ex.getMessage());
        }
        return getResponse();
    }

    @Override
    public IRestResponse update(IUcusTuru obj) {
        IUcusTuru objc = null;
        try {
            objc = (IUcusTuru) ucusTuruDao.update(obj);
            createResponse(objc);
        } catch (Exception ex) {
            Error(ex.getMessage());
        }
        return getResponse();
    }

    @Override
    public IRestResponse delete(Long id) {
        try {
            ucusTuruDao.delete(id);
            createResponse(id);
        } catch (Exception ex) {
            Error(ex.getMessage());
        }
        return getResponse();
    }

    @Override
    public IRestResponse getProperties() {
        createResponse(ucusTuruDao.getProperties());
        return getResponse();
    }

    @Override
    public IRestResponse findById(Long id) {
        createResponse(ucusTuruDao.find(id));
        return getResponse();
    }

    @Override
    public IRestResponse findByFilters(IFilterAndPager filterAndPager) {
        Map<String, Object> retMap = ucusTuruDao.findByFilters(filterAndPager);
        createResponse(retMap.get("data"), (Long) retMap.get("count"));
        return getResponse();
    }

    public void setUcusTuruDao(IUcusTuruDao ucusTuruDao) {
        this.ucusTuruDao = ucusTuruDao;
    }
}