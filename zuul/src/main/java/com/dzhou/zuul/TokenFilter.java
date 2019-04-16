package com.dzhou.zuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
@Component
public class TokenFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }
//    @Value("${/dev}")
    public String eurekas;
    @Override
    public Object run() throws ZuulException {
        RequestContext context= RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        System.out.print(eurekas);
        String token=request.getParameter("token");
        if(StringUtils.isEmpty(token)){
            context.setSendZuulResponse(false); //这个地方主注意
            context.setResponseStatusCode(401);
            try {
                context.getResponse().setCharacterEncoding("UTF-8");
                context.getResponse().getWriter().write("你写错了");
            } catch (IOException e) {
//                e.printStackTrace();
            }
            return null;
        }
        return null;
    }
}
