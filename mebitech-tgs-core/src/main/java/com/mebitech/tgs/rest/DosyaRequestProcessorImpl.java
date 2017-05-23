package com.mebitech.tgs.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mebitech.core.api.persistence.filter.IFilterAndPager;
import com.mebitech.rest.util.BaseRequestProcessor;
import com.mebitech.tgs.core.api.persistence.dao.IDosyaDao;
import com.mebitech.tgs.core.api.persistence.entities.IDosya;
import com.mebitech.core.api.rest.IResponseFactory;
import com.mebitech.core.api.rest.enums.RestResponseStatus;
import com.mebitech.tgs.core.api.rest.processors.IDosyaRequestProcessor;
import com.mebitech.core.api.rest.responses.IRestResponse;

public class DosyaRequestProcessorImpl extends BaseRequestProcessor implements IDosyaRequestProcessor {
    private static Logger logger = LoggerFactory.getLogger(DosyaRequestProcessorImpl.class);
    private IDosyaDao dosyaDao;

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
        List<? extends IDosya> dosyaler = (List<? extends IDosya>) dosyaDao.findByProperties(propertiesMap, null, null);
        logger.debug("Found Dosya: {}", dosyaler);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            resultMap.put("Dosya", dosyaler);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return responseFactory.createResponse(RestResponseStatus.OK, "Records listed.", resultMap);
    }

    @Override
    public IRestResponse add(IDosya obj) {
        IDosya objc = null;
        try {
            objc = (IDosya) dosyaDao.save(obj);
            createResponse(objc);
        } catch (Exception ex) {
            Error(ex.getMessage());
        }
        return getResponse();
    }

    @Override
    public IRestResponse update(IDosya obj) {
        IDosya objc = null;
        try {
            objc = (IDosya) dosyaDao.update(obj);
            createResponse(objc);
        } catch (Exception ex) {
            Error(ex.getMessage());
        }
        return getResponse();
    }

    @Override
    public IRestResponse delete(Long id) {
        try {
            dosyaDao.delete(id);
            createResponse(id);
        } catch (Exception ex) {
            Error(ex.getMessage());
        }
        return getResponse();
    }

    @Override
    public IRestResponse getProperties() {
        createResponse(dosyaDao.getProperties());
        return getResponse();
    }

    @Override
    public IRestResponse findById(Long id) {
        createResponse(dosyaDao.find(id));
        return getResponse();
    }

    @Override
    public IRestResponse findByFilters(IFilterAndPager filterAndPager) {
        Map<String, Object> retMap = dosyaDao.findByFilters(filterAndPager);
        createResponse(retMap.get("data"), (Long) retMap.get("count"));
        return getResponse();
    }

    public void setDosyaDao(IDosyaDao dosyaDao) {
        this.dosyaDao = dosyaDao;
    }
}