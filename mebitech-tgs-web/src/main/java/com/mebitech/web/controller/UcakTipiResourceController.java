package com.mebitech.web.controller;

import com.mebitech.core.api.log.IOperationLogService;
import com.mebitech.core.api.rest.IResponseFactory;
import com.mebitech.core.api.rest.enums.RestResponseStatus;
import com.mebitech.core.api.rest.responses.IRestResponse;
import com.mebitech.persistence.filter.FilterAndPagerImpl;
import com.mebitech.tgs.core.api.rest.processors.IUcakTipiRequestProcessor;
import com.mebitech.tgs.persistence.entities.UcakTipiImpl;
import com.mebitech.web.controller.utils.AController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Date;

@Controller
@RequestMapping("/resources/ucakTipis")
public class UcakTipiResourceController extends AController {
    private static Logger logger = LoggerFactory.getLogger(UcakTipiResourceController.class);
    @Autowired
    private IResponseFactory responseFactory;
    @Autowired
    private IUcakTipiRequestProcessor ucakTipiProcessor;
    @Autowired
    private IOperationLogService logService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response)
            throws UnsupportedEncodingException {
        FilterAndPagerImpl filterAndPager = getFilters(request);
        IRestResponse restResponse = ucakTipiProcessor.findByFilters(filterAndPager);
        response.addHeader("X-Total-Count", String.valueOf(restResponse.getTotal()));
        return restResponse.getData();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Object add(@RequestBody String json) {
        try {
            UcakTipiImpl obj = objectMapper.readValue(json, UcakTipiImpl.class);
            obj.setCreateDate(new Date());
            return ucakTipiProcessor.add(obj).getData();
        } catch (Exception ex) {
            return responseFactory.createResponse(RestResponseStatus.ERROR, "Hata : " + ex.getMessage());
        }
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    @ResponseBody
    public Object update(@PathVariable("id") Long id,@RequestBody String json) {
        try {
            UcakTipiImpl obj = objectMapper.readValue(json, UcakTipiImpl.class);
            return ucakTipiProcessor.update(obj).getData();
        } catch (Exception ex) {
            return responseFactory.createResponse(RestResponseStatus.ERROR, "Hata : " + ex.getMessage());
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public IRestResponse delete(@PathVariable("id") Long id) {
        return ucakTipiProcessor.delete(id);
    }

    @RequestMapping(value = "/getProperties", method = RequestMethod.GET)
    @ResponseBody
    public IRestResponse getProperties() {
        return ucakTipiProcessor.getProperties();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public IRestResponse findById(@PathVariable("id") Long id) {
        return ucakTipiProcessor.findById(id);
    }

}