package com.myshop.common.context;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * Công cụ lấy request / response
 **/
public class RequestScopeContext {

    public static HttpServletResponse getHttpResponse() {
        ServletRequestAttributes reqAttrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert reqAttrs != null;
        return reqAttrs.getResponse();
    }

    public static HttpServletRequest getHttpRequest() {
        ServletRequestAttributes reqAttrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert reqAttrs != null;
        return reqAttrs.getRequest();
    }


}
