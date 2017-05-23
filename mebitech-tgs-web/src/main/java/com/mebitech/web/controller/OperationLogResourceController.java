package com.mebitech.web.controller;

import com.mebitech.core.api.log.IOperationLogService;
import com.mebitech.core.api.rest.responses.IRestResponse;
import com.mebitech.persistence.filter.FilterAndPagerImpl;
import com.mebitech.web.controller.utils.AController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * Created by seda on 5.12.2016.
 */
@Controller
@RequestMapping("/resources/operationLog")
public class OperationLogResourceController extends AController{
    private static Logger logger = LoggerFactory.getLogger(OperationLogResourceController.class);
    @Autowired
    private IOperationLogService operationLogService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public IRestResponse list(HttpServletRequest request)
            throws UnsupportedEncodingException {
        FilterAndPagerImpl filterAndPager = getFilters(request);
        IRestResponse restResponse = operationLogService.list(filterAndPager);
        return restResponse;
    }
}