package com.mebitech.web.controller;


import com.mebitech.core.api.log.IOperationLogService;
import com.mebitech.core.api.persistence.enums.CrudType;
import com.mebitech.core.api.rest.IResponseFactory;
import com.mebitech.core.api.rest.responses.IRestResponse;
import com.mebitech.tgs.core.api.rest.processors.IProductRequestProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;


/**
 * Controller for product related operations.
 *
 */
@Controller
@RequestMapping("/resources/product")
public class ProductResourceController
{

    private static Logger logger = LoggerFactory.getLogger( ProductResourceController.class );

    @Autowired
    private IResponseFactory responseFactory;

    @Autowired
    private IProductRequestProcessor productProcessor;

    @Autowired
    private IOperationLogService logService;


    /**
     * List product according to given parameters.
     *
     * @param hostname
     * @param dn
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public IRestResponse listProducts( @RequestParam(value = "hostname", required = false) String hostname,
        @RequestParam(value = "dn", required = false) String dn,
        @RequestParam(value = "uid", required = false) String uid, HttpServletRequest request )
        throws UnsupportedEncodingException
    {
        try
        {
            logService.saveLog( uid, CrudType.READ, "Get Product List", null, hostname );
        }
        catch ( Exception e1 )
        {
            logger.error( e1.getMessage(), e1 );
        }
        logger.info( "Request received. URL: '/resources/product/list'", new Object[] { hostname, dn, uid } );
        IRestResponse restResponse = productProcessor.list( hostname, dn, uid );
        logger.debug( "Completed processing request, returning result: {}", restResponse.toJson() );
        return restResponse;
    }

}
