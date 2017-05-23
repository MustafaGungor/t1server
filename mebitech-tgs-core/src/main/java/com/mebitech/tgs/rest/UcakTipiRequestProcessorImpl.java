package com.mebitech.tgs.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mebitech.core.api.persistence.filter.IFilterAndPager;
import com.mebitech.rest.util.BaseRequestProcessor;
import com.mebitech.tgs.core.api.persistence.dao.IUcakTipiDao;
import com.mebitech.tgs.core.api.persistence.entities.IUcakTipi;
import com.mebitech.core.api.rest.IResponseFactory;
import com.mebitech.core.api.rest.enums.RestResponseStatus;
import com.mebitech.tgs.core.api.rest.processors.IUcakTipiRequestProcessor;
import com.mebitech.core.api.rest.responses.IRestResponse;

public class UcakTipiRequestProcessorImpl extends BaseRequestProcessor implements IUcakTipiRequestProcessor {
    private static Logger logger = LoggerFactory.getLogger(UcakTipiRequestProcessorImpl.class);
    private IUcakTipiDao ucakTipiDao;

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
        List<? extends IUcakTipi> ucakTipiler = (List<? extends IUcakTipi>) ucakTipiDao.findByProperties(propertiesMap, null, null);
        logger.debug("Found UcakTipi: {}", ucakTipiler);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            resultMap.put("UcakTipi", ucakTipiler);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return responseFactory.createResponse(RestResponseStatus.OK, "Records listed.", resultMap);
    }

    @Override
    public IRestResponse add(IUcakTipi obj) {
        IUcakTipi objc = null;
        try {
            objc = (IUcakTipi) ucakTipiDao.save(obj);
            createResponse(objc);
        } catch (Exception ex) {
            Error(ex.getMessage());
        }
        return getResponse();
    }

    @Override
    public IRestResponse update(IUcakTipi obj) {
        IUcakTipi objc = null;
        try {
            objc = (IUcakTipi) ucakTipiDao.update(obj);
            createResponse(objc);
        } catch (Exception ex) {
            Error(ex.getMessage());
        }
        return getResponse();
    }

    @Override
    public IRestResponse delete(Long id) {
        try {
            ucakTipiDao.delete(id);
            createResponse(id);
        } catch (Exception ex) {
            Error(ex.getMessage());
        }
        return getResponse();
    }

    @Override
    public IRestResponse getProperties() {
        createResponse(ucakTipiDao.getProperties());
        return getResponse();
    }

    @Override
    public IRestResponse findById(Long id) {
        createResponse(ucakTipiDao.find(id));
        return getResponse();
    }

    @Override
    public IRestResponse findByFilters(IFilterAndPager filterAndPager) {
        Map<String, Object> retMap = ucakTipiDao.findByFilters(filterAndPager);
        createResponse(retMap.get("data"), (Long) retMap.get("count"));
        return getResponse();
    }

    public void setUcakTipiDao(IUcakTipiDao ucakTipiDao) {
        this.ucakTipiDao = ucakTipiDao;
    }
}