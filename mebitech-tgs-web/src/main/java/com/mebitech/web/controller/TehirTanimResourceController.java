package com.mebitech.web.controller;

import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.mebitech.web.controller.utils.AController;
import com.mebitech.core.api.rest.enums.RestResponseStatus;
import com.mebitech.persistence.filter.FilterAndPagerImpl;
import com.mebitech.core.api.log.IOperationLogService;
import com.mebitech.core.api.persistence.enums.CrudType;
import com.mebitech.core.api.rest.IResponseFactory;
import com.mebitech.tgs.core.api.rest.processors.ITehirTanimRequestProcessor;
import com.mebitech.tgs.persistence.entities.TehirTanimImpl;
import com.mebitech.core.api.rest.responses.IRestResponse;

@Controller
@RequestMapping("/resources/tehirTanims")
public class TehirTanimResourceController extends AController {
    private static Logger logger = LoggerFactory.getLogger(TehirTanimResourceController.class);
    @Autowired
    private IResponseFactory responseFactory;
    @Autowired
    private ITehirTanimRequestProcessor tehirTanimProcessor;
    @Autowired
    private IOperationLogService logService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response)
            throws UnsupportedEncodingException {
        FilterAndPagerImpl filterAndPager = getFilters(request);
        IRestResponse restResponse = tehirTanimProcessor.findByFilters(filterAndPager);
        response.addHeader("X-Total-Count", String.valueOf(restResponse.getTotal()));
        return restResponse.getData();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Object add(@RequestBody String json) {
        try {
            TehirTanimImpl obj = objectMapper.readValue(json, TehirTanimImpl.class);
            obj.setCreateDate(new Date());
            return tehirTanimProcessor.add(obj).getData();
        } catch (Exception ex) {
            return responseFactory.createResponse(RestResponseStatus.ERROR, "Hata : " + ex.getMessage());
        }
    }

    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Object update(@PathVariable("id") Long id, @RequestBody String json) {
        try {
            TehirTanimImpl obj = objectMapper.readValue(json, TehirTanimImpl.class);
            return tehirTanimProcessor.update(obj).getData();
        } catch (Exception ex) {
            return responseFactory.createResponse(RestResponseStatus.ERROR, "Hata : " + ex.getMessage());
        }
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public IRestResponse delete(@PathVariable("id") Long id) {
        return tehirTanimProcessor.delete(id);
    }

    @RequestMapping(value = "/getProperties", method = RequestMethod.GET)
    @ResponseBody
    public IRestResponse getProperties() {
        return tehirTanimProcessor.getProperties();
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    @ResponseBody
    public IRestResponse findById(@PathVariable("id") Long id) {
        return tehirTanimProcessor.findById(id);
    }

}