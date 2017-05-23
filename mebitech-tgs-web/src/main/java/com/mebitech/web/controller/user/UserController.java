package com.mebitech.web.controller.user;

import com.mebitech.core.api.rest.IResponseFactory;
import com.mebitech.core.api.rest.enums.RestResponseStatus;
import com.mebitech.core.api.rest.processors.IUserRequestProcessor;
import com.mebitech.core.api.rest.responses.IRestResponse;
import com.mebitech.persistence.entities.UserImpl;
import com.mebitech.persistence.filter.FilterAndPagerImpl;
import com.mebitech.persistence.filter.FilterImpl;
import com.mebitech.web.controller.utils.AController;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by tayipdemircan on 24.10.2016.
 */
@Controller
@RequestMapping("/resources/users")
public class UserController extends AController {

    @Autowired
    private IResponseFactory responseFactory;

    @Autowired
    private IUserRequestProcessor userRequestProcessor;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Object addNewUser(@RequestBody String user){
        try {
            UserImpl userc = objectMapper.readValue(user,UserImpl.class);
            return userRequestProcessor.add(userc).getData();
        } catch (IOException e) {
            e.printStackTrace();
            return responseFactory.createResponse(RestResponseStatus.ERROR,"Hata : " + e.getMessage(),null);
        }
    }

    @RequestMapping(value="/{id}",method = RequestMethod.PUT)
    @ResponseBody
    public Object updateUser(@PathVariable("id") Long id,@RequestBody String user){
        try {
            UserImpl userc = objectMapper.readValue(user,UserImpl.class);
            return userRequestProcessor.update(userc).getData();
        } catch (IOException e) {
            e.printStackTrace();
            return responseFactory.createResponse(RestResponseStatus.ERROR,"Hata : " + e.getMessage(),null);
        }
    }

    @RequestMapping(value = "/{id}" , method = RequestMethod.DELETE)
    @ResponseBody
    public IRestResponse deleteUser(@PathVariable("id") Long id){
        try {
            return userRequestProcessor.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return responseFactory.createResponse(RestResponseStatus.ERROR,"Hata : " + e.getMessage(),null);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Object findAllUsers(HttpServletRequest request, HttpServletResponse response){
        FilterAndPagerImpl filterAndPager = getFilters(request);
        IRestResponse restResponse = userRequestProcessor.findByFilters(filterAndPager);
        response.addHeader("X-Total-Count", String.valueOf(restResponse.getTotal()));
        return restResponse.getData();


    }

    @RequestMapping(value = "/{id}" , method = RequestMethod.GET)
    @ResponseBody
    public IRestResponse findById(@PathVariable("id") Long id) {
        return userRequestProcessor.findById(id);
    }

    @RequestMapping(value = "/getProperties" , method = RequestMethod.GET)
    @ResponseBody
    public IRestResponse getProperties() {
        return userRequestProcessor.getProperties();
    }

}
