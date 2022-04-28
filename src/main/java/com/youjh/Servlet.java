package com.youjh;

import com.youjh.handler.HandlerAdapter;
import com.youjh.handler.HandlerMapping;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Map;

public class Servlet extends HttpServlet {
    private String contextConfig;
    static Collection<HandlerMapping> handlerMappings;
    static Collection<HandlerAdapter> handlerAdapters;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object handlerMapping = getHandlerMapping(req);
        HandlerAdapter handlerAdapter = getHandlerAdapter(handlerMapping);
        Object result = null;
        try {
            result = handlerAdapter.handle(req, resp, handlerMapping);
        } catch (Exception e) {
            e.printStackTrace();
        }
        PrintWriter printWriter = resp.getWriter();
        printWriter.println(result);

    }

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(servletConfig.getInitParameter("contextConfig"));
        Map<String, HandlerMapping> beansOfType = classPathXmlApplicationContext.getBeansOfType(HandlerMapping.class);
        handlerMappings = beansOfType.values();
    }

    private Object getHandlerMapping(HttpServletRequest request){
        if(handlerMappings != null){
            for(HandlerMapping map : handlerMappings){
                Object handler = map.getHandler(request.getRequestURI());
                return handler;
            }
        }
        return null;
    }

    private HandlerAdapter getHandlerAdapter(Object handle){
        if(handlerAdapters != null){
            for(HandlerAdapter map : handlerAdapters){
                boolean support = map.support(handle);
                if(support){
                    return map;
                }
            }
        }
        return null;
    }
}
