package com.mebitech.web.controller;

import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

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
import com.mebitech.tgs.core.api.rest.processors.IUcakRequestProcessor;
import com.mebitech.tgs.persistence.entities.UcakImpl;
import com.mebitech.core.api.rest.responses.IRestResponse;

@Controller
@RequestMapping("/resources/ucaks")
public class UcakResourceController extends AController {
    private static Logger logger = LoggerFactory.getLogger(UcakResourceController.class);
    @Autowired
    private IResponseFactory responseFactory;
    @Autowired
    private IUcakRequestProcessor ucakProcessor;
    @Autowired
    private IOperationLogService logService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response)
            throws UnsupportedEncodingException {
        FilterAndPagerImpl filterAndPager = getFilters(request);
        IRestResponse restResponse = ucakProcessor.findByFilters(filterAndPager);
        response.addHeader("X-Total-Count", String.valueOf(restResponse.getTotal()));
        return restResponse.getData();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Object add(@RequestBody String json) {
        try {
            UcakImpl obj = objectMapper.readValue(json, UcakImpl.class);
            obj.setCreateDate(new Date());
            return ucakProcessor.add(obj).getData();
        } catch (Exception ex) {
            return responseFactory.createResponse(RestResponseStatus.ERROR, "Hata : " + ex.getMessage());
        }
    }

    @RequestMapping(value="/{id}",method = RequestMethod.PUT)
    @ResponseBody
    public Object update(@PathVariable("id") Long id,@RequestBody String json) {
        try {
            UcakImpl obj = objectMapper.readValue(json, UcakImpl.class);
            return ucakProcessor.update(obj).getData();
        } catch (Exception ex) {
            return responseFactory.createResponse(RestResponseStatus.ERROR, "Hata : " + ex.getMessage());
        }
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public IRestResponse delete(@PathVariable("id") Long id) {
        return ucakProcessor.delete(id);
    }

    @RequestMapping(value = "/getProperties", method = RequestMethod.GET)
    @ResponseBody
    public IRestResponse getProperties() {
        return ucakProcessor.getProperties();
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    @ResponseBody
    public IRestResponse findById(@PathVariable("id") Long id) {
        return ucakProcessor.findById(id);
    }

}