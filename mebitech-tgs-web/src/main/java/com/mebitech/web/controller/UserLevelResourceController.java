package com.mebitech.web.controller;


import com.mebitech.core.api.log.IOperationLogService;
import com.mebitech.core.api.persistence.enums.CrudType;
import com.mebitech.core.api.rest.IResponseFactory;
import com.mebitech.core.api.rest.processors.IUserLevelRequestProcessor;
import com.mebitech.core.api.rest.responses.IRestResponse;
import com.mebitech.persistence.entities.GroupImpl;
import com.mebitech.persistence.entities.UserLevelImpl;
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

@Controller
@RequestMapping("/resources/userlevel")
public class UserLevelResourceController extends AController {
    private static Logger logger = LoggerFactory.getLogger(UserLevelResourceController.class);
    @Autowired
    private IResponseFactory responseFactory;
    @Autowired
    private IUserLevelRequestProcessor userlevelProcessor;
    @Autowired
    private IOperationLogService logService;



    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public IRestResponse listUserLevel(@RequestParam(value = "hostname", required = false) String hostname,
                                       @RequestParam(value = "dn", required = false) String dn,
                                       @RequestParam(value = "uid", required = false) String uid,
                                       @RequestParam(value = "filter", required = false) String filter,
                                       HttpServletRequest request)
            throws UnsupportedEncodingException {
        try {
            logService.saveLog(uid, CrudType.READ, "Get UserLevel List", null, hostname);
        } catch (Exception e1) {
            logger.error(e1.getMessage(), e1);
        }
        logger.info("Request received. URL: '/resources/userlevel'", new Object[]{hostname, dn, uid});
        IRestResponse restResponse = userlevelProcessor.list(hostname, dn, uid);
        logger.debug("Completed processing request, returning result: {}", restResponse.toJson());
        return restResponse;
    }

    @RequestMapping(value = "/add" , method = RequestMethod.PUT)
    @ResponseBody
    public IRestResponse addNewLevel(@RequestBody Object data){
        HashMap<String,Object> map = (HashMap<String, Object>) data;
        UserLevelImpl userLevel = new UserLevelImpl();
        userLevel.setCreateDate(new Date());
        userLevel.setDeleted(false);
        return userlevelProcessor.add(userLevel,map);
    }

    @RequestMapping(value = "/delete" , method = RequestMethod.DELETE)
    @ResponseBody
    public IRestResponse deleteLevels(@RequestParam(value = "id")Long id){
        return userlevelProcessor.remove(id);
    }

    @RequestMapping(value = "/findById" , method = RequestMethod.GET)
    @ResponseBody
    public IRestResponse findById(@RequestParam(value = "userId", required = false)Long userId, @RequestParam(value = "levelId", required = false)Long levelId){
        return userlevelProcessor.findById(userId,levelId);
    }

    @RequestMapping(value = "/addGroup" , method = RequestMethod.POST)
    @ResponseBody
    public IRestResponse addGroup(@RequestBody Object data){
        HashMap<String,Object> map = (HashMap<String, Object>) data;
        return userlevelProcessor.addGroup(map);
    }

    @RequestMapping(value = "/removeGroup" , method = RequestMethod.POST)
    @ResponseBody
    public IRestResponse removeGroup(@RequestBody Object data){
        HashMap<String,Object> map = (HashMap<String, Object>) data;
        return userlevelProcessor.removeGroup(map);
    }

    @RequestMapping(value = "/getGroupById" , method = RequestMethod.GET)
    @ResponseBody
    public IRestResponse getGroups(@RequestParam(value = "userLevelId") Long id){
        return userlevelProcessor.getGroups(id);
    }

    @RequestMapping(value = "/getGroup" , method = RequestMethod.GET)
    @ResponseBody
    public IRestResponse getGroups(HttpServletRequest request){
        return userlevelProcessor.getGroups(getFilters(request),GroupImpl.class);
    }

    @RequestMapping(value = "/getLevel" , method = RequestMethod.GET)
    @ResponseBody
    public IRestResponse getLevels(HttpServletRequest request){
        return userlevelProcessor.getLevels(getFilters(request));
        /*FilterAndPagerImpl filterAndPager = new FilterAndPagerImpl();
        FilterImpl filter = new FilterImpl();
        filter.setProperty("user.id");
        filter.setValue(401);
        filter.setOperator("=");

        FilterImpl filter2 = new FilterImpl();
        filter2.setProperty("level.name");
        filter2.setValue("%sev%");
        filter2.setOperator("LIKE");

        List<FilterImpl> filters = new ArrayList<FilterImpl>();
        filters.add(filter);
        filters.add(filter2);
        filterAndPager.setFilters(filters);
        return userlevelProcessor.getLevels(filterAndPager);*/
    }


}