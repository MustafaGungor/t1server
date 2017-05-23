package com.mebitech.web.controller;

import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import com.mebitech.core.api.rest.processors.IIlRequestProcessor;
import com.mebitech.persistence.entities.IlImpl;
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
import com.mebitech.core.api.rest.IResponseFactory;
import com.mebitech.core.api.rest.responses.IRestResponse;

@Controller
@RequestMapping("/resources/il")
public class IlResourceController extends AController {
    private static Logger logger = LoggerFactory.getLogger(IlResourceController.class);
    @Autowired
    private IResponseFactory responseFactory;
    @Autowired
    private IIlRequestProcessor IlProcessor;
    @Autowired
    private IOperationLogService logService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public IRestResponse list(HttpServletRequest request)
            throws UnsupportedEncodingException {
        FilterAndPagerImpl filterAndPager = getFilters(request);
        IRestResponse restResponse = IlProcessor.findByFilters(filterAndPager);
        return restResponse;
    }

    @RequestMapping(value = "/add", method = RequestMethod.PUT)
    @ResponseBody
    public IRestResponse add(@RequestBody String json) {
        try {
            IlImpl obj = objectMapper.readValue(json, IlImpl.class);
            obj.setCreateDate(new Date());
            return IlProcessor.add(obj);
        } catch (Exception ex) {
            return responseFactory.createResponse(RestResponseStatus.ERROR, "Hata : " + ex.getMessage());
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public IRestResponse update(@RequestBody String json) {
        try {
            IlImpl obj = objectMapper.readValue(json, IlImpl.class);
            return IlProcessor.update(obj);
        } catch (Exception ex) {
            return responseFactory.createResponse(RestResponseStatus.ERROR, "Hata : " + ex.getMessage());
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public IRestResponse delete(@RequestParam(value = "id") Long id) {
        return IlProcessor.delete(id);
    }

    @RequestMapping(value = "/getProperties", method = RequestMethod.GET)
    @ResponseBody
    public IRestResponse getProperties() {
        return IlProcessor.getProperties();
    }

    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    @ResponseBody
    public IRestResponse findById(@RequestParam(value = "id") Long id) {
        return IlProcessor.findById(id);
    }

}