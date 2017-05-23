package com.mebitech.web.controller;

import com.mebitech.core.api.log.IOperationLogService;
import com.mebitech.core.api.persistence.entities.ILevel;
import com.mebitech.core.api.rest.IResponseFactory;
import com.mebitech.core.api.rest.enums.RestResponseStatus;
import com.mebitech.core.api.rest.processors.ILevelRequestProcessor;
import com.mebitech.core.api.rest.responses.IRestResponse;
import com.mebitech.persistence.entities.LevelImpl;
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
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/resources/level")
public class LevelResourceController extends AController {
    private static Logger logger = LoggerFactory.getLogger(LevelResourceController.class);
    @Autowired
    private IResponseFactory responseFactory;
    @Autowired
    private ILevelRequestProcessor levelProcessor;
    @Autowired
    private IOperationLogService logService;

//    ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public IRestResponse listLevel(HttpServletRequest request)
            throws UnsupportedEncodingException {
        FilterAndPagerImpl filterAndPager = getFilters(request);
        IRestResponse restResponse = levelProcessor.findByFilters(filterAndPager);
        return restResponse;
    }

    @RequestMapping(value = "/add" , method = RequestMethod.PUT)
    @ResponseBody
    public IRestResponse addNewLevel(@RequestBody String json){
        try {
            LevelImpl level = objectMapper.readValue(json,LevelImpl.class);
            level.setCreateDate(new Date());
            return levelProcessor.add(level);
        } catch (Exception ex){
            return responseFactory.createResponse(RestResponseStatus.ERROR,"Hata : " + ex.getMessage());
        }
    }

    @RequestMapping(value = "/update" , method = RequestMethod.POST)
    @ResponseBody
    public IRestResponse updateLevel(@RequestBody String json){
        try {
            Map<String, Object> map = objectMapper.readValue(json, HashMap.class);

            //LevelImpl level = objectMapper.readValue(json,LevelImpl.class);
            return levelProcessor.update((ILevel) map);
        } catch (Exception ex){
            return responseFactory.createResponse(RestResponseStatus.ERROR,"Hata : " + ex.getMessage());
        }
    }

    @RequestMapping(value = "/delete" , method = RequestMethod.DELETE)
    @ResponseBody
    public IRestResponse deleteLevel(@RequestParam(value = "id") Long id){
        return levelProcessor.delete(id);
    }

    @RequestMapping(value = "/getProperties" , method = RequestMethod.GET)
    @ResponseBody
    public IRestResponse getProperties() {
        return levelProcessor.getProperties();
    }

    @RequestMapping(value = "/getById" , method = RequestMethod.GET)
    @ResponseBody
    public IRestResponse findById(@RequestParam(value = "id") Long id) {
        return levelProcessor.findById(id);
    }

}