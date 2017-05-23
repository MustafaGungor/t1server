package com.mebitech.web.controller;

import com.mebitech.core.api.rest.IResponseFactory;
import com.mebitech.core.api.rest.enums.RestResponseStatus;
import com.mebitech.core.api.rest.processors.IPermissionRequestProcessor;
import com.mebitech.core.api.rest.processors.IUserRequestProcessor;
import com.mebitech.core.api.rest.responses.IRestResponse;
import com.mebitech.web.controller.utils.AController;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * Created by tayipdemircan on 24.10.2016.
 */
@Controller
@RequestMapping("/resources/login")
public class LoginController extends AController {

    static HashMap<String,UserDto> sessionList = new HashMap<String, UserDto>();

    @Autowired
    private IUserRequestProcessor userRequestProcessor;

    @Autowired
    private IPermissionRequestProcessor permissionRequestProcessor;

    @Autowired
    private IResponseFactory responseFactory;

    @RequestMapping(value = "/dologin" , method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public IRestResponse login(){
        Long id = null;
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        if(principals != null) {
            AuthenticationToken token = principals.oneByType(UsernamePasswordToken.class);
            if(token != null && token.getPrincipal() != null) {
                String[] principalsArr = token.getPrincipal().toString().split("#");
                if(principalsArr.length <= 2)
                    id = Long.valueOf(principalsArr[1]);
                else
                    id = Long.valueOf(principalsArr[2]);
            }
        }
        return permissionRequestProcessor.getAuthorizedMenus(id,null);
    }

    @RequestMapping(value = "/logout" , method = {RequestMethod.GET})
    @ResponseBody
    public IRestResponse logout(){
        SecurityUtils.getSubject().logout();
        return responseFactory.createResponse(RestResponseStatus.OK, "Success");
    }

}
