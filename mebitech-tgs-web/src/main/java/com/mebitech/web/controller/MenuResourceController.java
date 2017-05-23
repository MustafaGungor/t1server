package com.mebitech.web.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mebitech.core.api.persistence.filter.IFilter;
import com.mebitech.core.api.rest.enums.RestResponseStatus;
import com.mebitech.persistence.entities.MenuImpl;
import com.mebitech.persistence.filter.FilterAndPagerImpl;
import com.mebitech.persistence.filter.FilterImpl;
import com.mebitech.web.controller.utils.AController;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.mebitech.core.api.log.IOperationLogService;
import com.mebitech.core.api.persistence.enums.CrudType;
import com.mebitech.core.api.rest.IResponseFactory;
import com.mebitech.core.api.rest.processors.IMenuRequestProcessor;
import com.mebitech.core.api.rest.responses.IRestResponse;

@Controller
@RequestMapping("/resources/menus")
public class MenuResourceController extends AController {
    private static Logger logger = LoggerFactory.getLogger(TehirTanimResourceController.class);
    @Autowired
    private IResponseFactory responseFactory;
    @Autowired
    private IMenuRequestProcessor menuProcessor;
    @Autowired
    private IOperationLogService logService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response)
            throws UnsupportedEncodingException {
        FilterAndPagerImpl filterAndPager = getFilters(request);
        IRestResponse restResponse = menuProcessor.findByFilters(filterAndPager);
        response.addHeader("X-Total-Count", String.valueOf(restResponse.getTotal()));
        return restResponse.getData();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Object add(@RequestBody String json) {
        try {
            MenuImpl obj = objectMapper.readValue(json, MenuImpl.class);
            obj.setCreateDate(new Date());
            return menuProcessor.add(obj).getData();
        } catch (Exception ex) {
            return responseFactory.createResponse(RestResponseStatus.ERROR, "Hata : " + ex.getMessage());
        }
    }

    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Object update(@PathVariable("id") Long id, @RequestBody String json) {
        try {
            MenuImpl obj = objectMapper.readValue(json, MenuImpl.class);
            return menuProcessor.update(obj).getData();
        } catch (Exception ex) {
            return responseFactory.createResponse(RestResponseStatus.ERROR, "Hata : " + ex.getMessage());
        }
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public IRestResponse delete(@PathVariable("id") Long id) {
        return menuProcessor.delete(id);
    }

    @RequestMapping(value = "/getProperties", method = RequestMethod.GET)
    @ResponseBody
    public IRestResponse getProperties() {
        return menuProcessor.getProperties();
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object findById(@PathVariable("id") Long id) {
        return menuProcessor.findById(id).getData();
    }

    @RequestMapping(value="/roots", method = RequestMethod.GET)
    @ResponseBody
    public Object getRoots(HttpServletRequest request, HttpServletResponse response) {
        FilterAndPagerImpl filter = new FilterAndPagerImpl();
        filter.setFilters(new ArrayList<IFilter>());
        FilterImpl filterImpl = new FilterImpl();
        filterImpl.setOperator("<");
        filterImpl.setProperty("index");
        filterImpl.setValue(1);
        filterImpl.setType("int");
        filter.getFilters().add(filterImpl);
        IRestResponse restResponse = menuProcessor.findByFilters(filter);
        response.addHeader("X-Total-Count", String.valueOf(restResponse.getTotal()));
        return restResponse.getData();
    }

    @RequestMapping(value="/getMenuList", method = RequestMethod.GET)
    @ResponseBody
    public Object getMenuList() {
        IRestResponse restResponse = menuProcessor.getMenuList();
        List<Object> retList = new ArrayList<Object>();
        retList.add(restResponse.getData());
        return retList;
    }
}