package com.youjh.handler;

import com.youjh.requestMap.RequestMappingInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

public class AnnocationHandlerAdapter implements HandlerAdapter{
    @Override
    public boolean support(Object handler) {
        return handler instanceof RequestMappingInfo;
    }

    @Override
    public Object handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, String[]> parameterMap = request.getParameterMap();
        RequestMappingInfo requestMappingInfo = (RequestMappingInfo) handler;
        Method method = requestMappingInfo.getMethod();
        Parameter[] parameters = method.getParameters();

        Object[] paramValue = new Object[parameters.length];
        for (int i=0;i<parameters.length;i++){
            for(Map.Entry<String,String[]> p :parameterMap.entrySet()){
                if(p.getKey().equals(parameters[i].getAnnotation(RequestParam.class).value())){
                    paramValue[i]=p.getValue()[0];
                }
            }
        }
        return method.invoke(requestMappingInfo.getObject(),paramValue);
    }
}
