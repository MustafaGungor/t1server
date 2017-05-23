package com.mebitech.web.controller.user;

import com.mebitech.core.api.rest.IResponseFactory;
import com.mebitech.core.api.rest.enums.RestResponseStatus;
import com.mebitech.core.api.rest.processors.IRoleRequestProcessor;
import com.mebitech.core.api.rest.responses.IRestResponse;
import com.mebitech.persistence.entities.RoleImpl;
import com.mebitech.persistence.filter.FilterAndPagerImpl;
import com.mebitech.web.controller.utils.AController;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by tayipdemircan on 26.10.2016.
 */
@Controller
@RequestMapping("/resources/roles")
public class RoleController extends AController {

    @Autowired
    private IResponseFactory responseFactory;

    @Autowired
    private IRoleRequestProcessor roleRequestProcessor;

    ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public IRestResponse addNewRole(@RequestBody String json){
        try {
            RoleImpl role = objectMapper.readValue(json,RoleImpl.class);
            return roleRequestProcessor.add(role);
        } catch (Exception e) {
            e.printStackTrace();
            return responseFactory.createResponse(RestResponseStatus.ERROR,"Hata : " + e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public IRestResponse updateRole(@RequestBody String json){
        try {
            RoleImpl role = objectMapper.readValue(json,RoleImpl.class);
            return roleRequestProcessor.update(role);
        } catch (Exception e) {
            e.printStackTrace();
            return responseFactory.createResponse(RestResponseStatus.ERROR,"Hata : " + e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public IRestResponse getAllRoles(HttpServletRequest request){
        try {
            FilterAndPagerImpl filterAndPager = getFilters(request);
            return roleRequestProcessor.findByFilters(filterAndPager);
        } catch (Exception e) {
            e.printStackTrace();
            return responseFactory.createResponse(RestResponseStatus.ERROR,"Hata : " + e.getMessage());
        }
    }

    @RequestMapping(value = "/{id}" , method = RequestMethod.DELETE)
    @ResponseBody
    public IRestResponse deleteRole(@PathVariable("id") Long id){
        try {
            return roleRequestProcessor.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return responseFactory.createResponse(RestResponseStatus.ERROR,"Hata : " + e.getMessage());
        }
    }

    @RequestMapping(value = "/{id}" , method = RequestMethod.GET)
    @ResponseBody
    public IRestResponse findById(@PathVariable("id") Long id){
        try {
            return roleRequestProcessor.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return responseFactory.createResponse(RestResponseStatus.ERROR,"Hata : " + e.getMessage());
        }
    }

}
