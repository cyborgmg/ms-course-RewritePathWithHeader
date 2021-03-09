package com.devsuperior.hrapigatewayzuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.core.env.Environment;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.REQUEST_URI_KEY;

public class RewritePathWithHeader extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(RewritePathWithHeader.class);

    @Autowired
    private RouteLocator routeLocator;

    @Autowired
    private UrlPathHelper urlPathHelper;

    private Route route;

    private String modifiedRequestPath;

    @Autowired
    private Environment env;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER + 1;
    }

    protected Route route(HttpServletRequest request){
        String requestURI = urlPathHelper.getPathWithinApplication(request);
        return routeLocator.getMatchingRoute(requestURI);
    }

    @Override
    public boolean shouldFilter() {

        this.route = route(RequestContext.getCurrentContext().getRequest());
        this.modifiedRequestPath = env.getProperty(String.format("zuul.routes.%s.RewritePathWithHeader",route.getId()));

        return !StringUtils.isEmpty(this.modifiedRequestPath);
    }

    @Override
    public Object run() {

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            this.modifiedRequestPath = this.modifiedRequestPath.replace(key, value);
        }
        ctx.put(REQUEST_URI_KEY, this.modifiedRequestPath);

        return null;
    }

}
