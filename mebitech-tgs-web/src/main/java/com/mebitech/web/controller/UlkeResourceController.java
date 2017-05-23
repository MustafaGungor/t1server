package com.mebitech.web.controller;


import com.mebitech.core.api.log.IOperationLogService;
import com.mebitech.core.api.rest.IResponseFactory;
import com.mebitech.core.api.rest.enums.RestResponseStatus;
import com.mebitech.core.api.rest.processors.IUlkeRequestProcessor;
import com.mebitech.core.api.rest.responses.IRestResponse;
import com.mebitech.persistence.entities.UlkeImpl;
import com.mebitech.persistence.filter.FilterAndPagerImpl;
import com.mebitech.web.controller.utils.AController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
@Controller
@RequestMapping("/resources/ulke")
public class UlkeResourceController extends AController {
private static Logger logger = LoggerFactory.getLogger(UlkeResourceController.class);
@Autowired
private IResponseFactory responseFactory;
@Autowired
private IUlkeRequestProcessor ulkeProcessor;
@Autowired
private IOperationLogService logService;
@RequestMapping(value = "/list", method = RequestMethod.GET)
@ResponseBody
public IRestResponse list(HttpServletRequest request)
throws UnsupportedEncodingException {
FilterAndPagerImpl filterAndPager = getFilters(request);
IRestResponse restResponse = ulkeProcessor.findByFilters(filterAndPager);
return restResponse;
}

@RequestMapping(value = "/add" , method = RequestMethod.PUT)
@ResponseBody
public IRestResponse add(@RequestBody String json){
try {
UlkeImpl obj = objectMapper.readValue(json,UlkeImpl.class);
obj.setCreateDate(new Date());
return ulkeProcessor.add(obj);
} catch (Exception ex){
return responseFactory.createResponse(RestResponseStatus.ERROR,"Hata : " + ex.getMessage());
}
}

@RequestMapping(value = "/update" , method = RequestMethod.POST)
@ResponseBody
public IRestResponse update(@RequestBody String json){
try {
UlkeImpl obj = objectMapper.readValue(json,UlkeImpl.class);
return ulkeProcessor.update(obj);
} catch (Exception ex){
return responseFactory.createResponse(RestResponseStatus.ERROR,"Hata : " + ex.getMessage());
}
}

@RequestMapping(value = "/delete" , method = RequestMethod.DELETE)
@ResponseBody
public IRestResponse delete(@RequestParam(value = "id") Long id){
return ulkeProcessor.delete(id);
}

@RequestMapping(value = "/getProperties" , method = RequestMethod.GET)
@ResponseBody
public IRestResponse getProperties() {
return ulkeProcessor.getProperties();
}

@RequestMapping(value = "/getById" , method = RequestMethod.GET)
@ResponseBody
public IRestResponse findById(@RequestParam(value = "id") Long id) {
return ulkeProcessor.findById(id);
}

}