package com.mebitech.web.controller;

import com.mebitech.core.api.log.IOperationLogService;
import com.mebitech.core.api.rest.IResponseFactory;
import com.mebitech.core.api.rest.enums.RestResponseStatus;
import com.mebitech.core.api.rest.responses.IRestResponse;
import com.mebitech.persistence.filter.FilterAndPagerImpl;
import com.mebitech.tgs.core.api.rest.processors.IUcusTuruRequestProcessor;
import com.mebitech.tgs.persistence.entities.UcusTuruImpl;
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
@RequestMapping("/resources/ucusTurus")
public class UcusTuruResourceController extends AController {
    private static Logger logger = LoggerFactory.getLogger(UcusTuruResourceController.class);
    @Autowired
    private IResponseFactory responseFactory;
    @Autowired
    private IUcusTuruRequestProcessor ucusTuruProcessor;
    @Autowired
    private IOperationLogService logService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response)
            throws UnsupportedEncodingException {
        FilterAndPagerImpl filterAndPager = getFilters(request);
        IRestResponse restResponse = ucusTuruProcessor.findByFilters(filterAndPager);
        response.addHeader("X-Total-Count", String.valueOf(restResponse.getTotal()));
        return restResponse.getData();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Object add(@RequestBody String json) {
        try {
            UcusTuruImpl obj = objectMapper.readValue(json, UcusTuruImpl.class);
            obj.setCreateDate(new Date());
            return ucusTuruProcessor.add(obj).getData();
        } catch (Exception ex) {
            return responseFactory.createResponse(RestResponseStatus.ERROR, "Hata : " + ex.getMessage());
        }
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    @ResponseBody
    public Object update(@PathVariable("id") Long id,@RequestBody String json) {
        try {
            UcusTuruImpl obj = objectMapper.readValue(json, UcusTuruImpl.class);
            return ucusTuruProcessor.update(obj).getData();
        } catch (Exception ex) {
            return responseFactory.createResponse(RestResponseStatus.ERROR, "Hata : " + ex.getMessage());
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public IRestResponse delete(@PathVariable("id") Long id) {
        return ucusTuruProcessor.delete(id);
    }

    @RequestMapping(value = "/getProperties", method = RequestMethod.GET)
    @ResponseBody
    public IRestResponse getProperties() {
        return ucusTuruProcessor.getProperties();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public IRestResponse findById(@PathVariable("id") Long id) {
        return ucusTuruProcessor.findById(id);
    }

}