package com.mebitech.web.controller.utils;


import com.mebitech.core.api.caching.ICacheService;
import com.mebitech.core.api.persistence.entities.ILevel;
import com.mebitech.core.api.persistence.entities.IUser;
import com.mebitech.core.api.persistence.entities.IUserLevel;
import com.mebitech.tgs.util.EnumUtil;
import com.mebitech.persistence.filter.FilterAndPagerImpl;
import com.mebitech.persistence.filter.FilterImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.PrincipalCollection;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tayipdemircan on 16.11.2016.
 */
public abstract class AController {

    @Autowired
    private ICacheService cacheService;

    protected ObjectMapper objectMapper = new ObjectMapper();

    protected EnumUtil enumUtil = new EnumUtil();

    protected FilterAndPagerImpl getFilters(HttpServletRequest request) {
        boolean reactFilter = false;
        String filter = request.getParameter("filter"); //for robeJS
        if (filter == null && request.getParameter("_filter") != null) {
            reactFilter = true;
            filter = request.getParameter("_filter");
        }
        String fullQueryString = request.getParameter("_q");
        String page = request.getParameter("page") != null ? request.getParameter("page") : "0";
        String start = request.getParameter("start") != null ? request.getParameter("start") : (request.getParameter("_offset") != null ? request.getParameter("_offset") : "0");
        String limit = request.getParameter("limit") != null ? request.getParameter("limit") : (request.getParameter("_limit") != null && !request.getParameter("_limit").equals("1") ? request.getParameter("_limit") : "0");

        return getFilters(filter, Integer.valueOf(page), Integer.valueOf(start), Integer.valueOf(limit), reactFilter, fullQueryString);
    }

    protected FilterAndPagerImpl getFilters(String filter, Integer page, Integer start, Integer limit, boolean reactFilter, String fullQueryString) {
        FilterAndPagerImpl filterAndPager = new FilterAndPagerImpl();
        if (start != null)
            filterAndPager.setStart(start);
        if (limit != null)
            filterAndPager.setLimit(limit);
        if (page != null)
            filterAndPager.setPage(page);
        if(fullQueryString != null)
            filterAndPager.setFullQueryString(fullQueryString);
        try {
            if (filter != null && !filter.equals("")) {
                List<FilterImpl> myObjects = new ArrayList<FilterImpl>();
                if(!reactFilter) {
                     myObjects = objectMapper.readValue(filter, new TypeReference<List<FilterImpl>>() {
                    });
                    filterAndPager.setFilters(myObjects);
                } else {
                    //kodu~=asd,adi~=ert
                    String[] filterList = filter.split(",");
                    for(String s : filterList){
                        if(s.indexOf("~=") > -1) {
                            String[] fieldQuery = s.split("~=");
                            FilterImpl filterObj = new FilterImpl();
                            filterObj.setProperty(fieldQuery[0]);
                            filterObj.setValue("%" + fieldQuery[1] + "%");
                            filterObj.setOperator("LIKE");
                            myObjects.add(filterObj);
                        } else if(s.indexOf("=") > -1) {
                            String[] fieldQuery = s.split("=");
                            FilterImpl filterObj = new FilterImpl();
                            filterObj.setProperty(fieldQuery[0]);
                            filterObj.setValue(fieldQuery[1]);
                            filterObj.setOperator("=");
                            myObjects.add(filterObj);
                        }
                    }
                    filterAndPager.setFilters(myObjects);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filterAndPager;
    }

    /**
     * @return session userLevel
     */
    protected IUserLevel getUserLevel() {
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        //System.out.println("getUserLevel::"+principals.toString());
        if (principals != null) {
            AuthenticationToken token = principals.oneByType(UsernamePasswordToken.class);
            if (token != null && token.getPrincipal() != null) {
                String[] principalsArr = token.getPrincipal().toString().split("#");
                String id = "";
                if (principalsArr.length <= 2)
                    id = principalsArr[1];
                else
                    id = principalsArr[2];

                IUserLevel userLevel = (IUserLevel) cacheService.get("userLevel:" + id);
                //System.out.println(userLevel.toString());
                return userLevel;
            }
        }
        return null;
    }

    /**
     * @return session user
     */
    protected IUser getUser() {
        IUserLevel userLevel = getUserLevel();
        if (userLevel != null)
            return userLevel.getUser();
        return null;
    }

    /**
     * @return level of session user
     */
    protected ILevel getLevel() {
        IUserLevel userLevel = getUserLevel();
        if (userLevel != null)
            return userLevel.getLevel();
        return null;
    }


    public EnumUtil getEnumUtil() {
        return enumUtil;
    }
}
