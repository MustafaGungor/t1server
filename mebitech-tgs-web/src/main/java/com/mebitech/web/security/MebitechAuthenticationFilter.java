package com.mebitech.web.security;

import com.mebitech.core.api.caching.ICacheService;
import com.mebitech.core.api.configuration.IConfigurationService;
import com.mebitech.core.api.log.IOperationLogService;
import com.mebitech.core.api.persistence.enums.CrudType;
import com.mebitech.web.controller.UserDto;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

//import org.apache.directory.api.util.Base64;

/**
 * Main filter class which is used to authenticate requests.
 */
public class MebitechAuthenticationFilter extends AuthenticatingFilter {

    private static Logger logger = LoggerFactory.getLogger(MebitechAuthenticationFilter.class);

    @Autowired
    private IOperationLogService logService;

    @Autowired
    private IConfigurationService configurationService;

    @Autowired
    private ICacheService cacheService;

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {

        logger.debug("parsing token from request header");
        HttpServletRequest req = (HttpServletRequest) request;

        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) { /*report an error*/ }

        if (jb != null && !jb.toString().equals("")) {
            UserDto user = new ObjectMapper().readValue(jb.toString(), UserDto.class);
            return new UsernamePasswordToken(user.getUserName(), user.getPassword(),true);
        }

       return new UsernamePasswordToken("","",false);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        logger.debug("will try to authenticate now...");
        HttpServletRequest req = (HttpServletRequest) request;

        if(req.getMethod().equals("OPTIONS") || req.getRequestURI().indexOf("level/list") > -1) {
            return true;
        }
        return executeLogin(request, response);

    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        AuthenticationToken token = createToken(request, response);
        if (token == null) {
            String msg = "createToken method implementation returned null. A valid non-null AuthenticationToken " +
                    "must be created in order to execute a login attempt.";
            return onLoginFailure(null, null, request, response);
        }
        try {
            Subject subject = getSubject(request, response);
            subject.login(token);
            return onLoginSuccess(token, subject, request, response);
        } catch (AuthenticationException e) {
            return onLoginFailure(token, e, request, response);
        }
    }
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
                                     ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String user = req.getHeader("username");
        try {
            logService.saveLog(user, CrudType.LOGIN, "Unauthorized access request", null, getHost(request));
        } catch (Exception e1) {
            logger.error(e1.getMessage(), e1);
        }
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {

//        HttpServletRequest req = (HttpServletRequest) request;
////        System.out.println(req.getUserPrincipal());
////        System.out.println(req.getHeader("Cookie"));
////        System.out.println(req.getSession());
//
//
//        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
//        if(principals != null) {
//            AuthenticationToken token = principals.oneByType(UsernamePasswordToken.class);
//
//            //System.out.println(token.getPrincipal().toString());
//            String[] principalsArr = token.getPrincipal().toString().split("#");
//
//            //if (principalsArr.length > 2 && !principalsArr[2].equals(""))
//            if (req.getRequestURI().indexOf("dologin") <= -1 && req.getRequestURI().indexOf("logout") <= -1 && req.getRequestURI().indexOf("level/list") <= -1 && req.getUserPrincipal() != null) {
//                List permissions = (ArrayList) cacheService.get("permissions:"+principalsArr[2]);
//                if(permissions != null && !hasPermission(req.getRequestURI(),permissions)) {
//                    ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_FORBIDDEN);
//                    return false;
//                }
//            }
//        }

        return super.preHandle(request, response);
    }

    public static boolean hasPermission(String uri, List permissionList){

        for (int i = 0; i < permissionList.size() ; i++) {
            Object module = permissionList.get(i);
            try {
                List tmpList = new ArrayList((Set)module.getClass().getMethod("getFormList").invoke(module));
                for (int j = 0; j < tmpList.size(); j++) {
                    Object form = tmpList.get(j);
                    String formPath = (String) form.getClass().getMethod("getPath").invoke(form);
                    List permissions = new ArrayList((Set)form.getClass().getMethod("getPermissionList").invoke(form));
                    for (int k = 0; k < permissions.size() ; k++) {
                        Object permission = permissions.get(k);
                        String permissionPath = (String) permission.getClass().getMethod("getPath").invoke(permission);

                        if(uri.indexOf(formPath+permissionPath) > -1){
                            return true;
                        }
                    }
                }
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }

        return false;
    }
}
