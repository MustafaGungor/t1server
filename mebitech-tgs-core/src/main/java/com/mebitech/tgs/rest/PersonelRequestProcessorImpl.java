package com.mebitech.tgs.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mebitech.core.api.persistence.filter.IFilterAndPager;
import com.mebitech.rest.util.BaseRequestProcessor;
import com.mebitech.tgs.core.api.persistence.dao.IPersonelDao;
import com.mebitech.tgs.core.api.persistence.entities.IPersonel;
import com.mebitech.core.api.rest.IResponseFactory;
import com.mebitech.core.api.rest.enums.RestResponseStatus;
import com.mebitech.tgs.core.api.rest.processors.IPersonelRequestProcessor;
import com.mebitech.core.api.rest.responses.IRestResponse;

public class PersonelRequestProcessorImpl extends BaseRequestProcessor implements IPersonelRequestProcessor {
    private static Logger logger = LoggerFactory.getLogger(PersonelRequestProcessorImpl.class);
    private IPersonelDao personelDao;

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
        List<? extends IPersonel> personeller = (List<? extends IPersonel>) personelDao.findByProperties(propertiesMap, null, null);
        logger.debug("Found Personel: {}", personeller);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            resultMap.put("Personel", personeller);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return responseFactory.createResponse(RestResponseStatus.OK, "Records listed.", resultMap);
    }

    @Override
    public IRestResponse add(IPersonel obj) {
        IPersonel objc = null;
        try {
            if(obj.getSoyadi().equals("")) {
                Error("asdadsasdasd");
                return getResponse();
            }

            objc = (IPersonel) personelDao.save(obj);
            createResponse(objc);
        } catch (Exception ex) {
            Error(ex.getMessage());
        }
        return getResponse();
    }

    @Override
    public IRestResponse update(IPersonel obj) {
        IPersonel objc = null;
        try {
            objc = (IPersonel) personelDao.update(obj);
            createResponse(objc);
        } catch (Exception ex) {
            Error(ex.getMessage());
        }
        return getResponse();
    }

    @Override
    public IRestResponse delete(Long id) {
        try {
            personelDao.delete(id);
            createResponse(id);
        } catch (Exception ex) {
            Error(ex.getMessage());
        }
        return getResponse();
    }

    @Override
    public IRestResponse getProperties() {
        createResponse(personelDao.getProperties());
        return getResponse();
    }

    @Override
    public IRestResponse findById(Long id) {
        createResponse(personelDao.find(id));
        return getResponse();
    }

    @Override
    public IRestResponse findByFilters(IFilterAndPager filterAndPager) {
        Map<String, Object> retMap = personelDao.findByFilters(filterAndPager);
        createResponse(retMap.get("data"), (Long) retMap.get("count"));
        return getResponse();
    }

    public void setPersonelDao(IPersonelDao personelDao) {
        this.personelDao = personelDao;
    }
}