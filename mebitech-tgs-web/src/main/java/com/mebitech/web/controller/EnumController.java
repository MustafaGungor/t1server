package com.mebitech.web.controller;

import com.mebitech.core.api.rest.IResponseFactory;
import com.mebitech.core.api.rest.enums.RestResponseStatus;
import com.mebitech.core.api.rest.responses.IRestResponse;
import com.mebitech.web.controller.utils.AController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sedaozcan on 16.12.2016.
 */
@Controller
@RequestMapping("/resources/enum")
public class EnumController extends AController {

    private static Logger logger = LoggerFactory.getLogger(EnumController.class);
    @Autowired
    private IResponseFactory responseFactory;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public IRestResponse list(@RequestParam(value = "enumString") String enumString)
            throws IOException {
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("enum",getEnumUtil().getEnumByName(enumString));
        return responseFactory.createResponse(RestResponseStatus.OK, "Success", retMap);
    }
}
