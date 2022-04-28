package com.youjh.handler;

import com.youjh.requestMap.RequestMapping;
import com.youjh.requestMap.RequestMappingInfo;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component
public class AnnocationHandlerMapping implements HandlerMapping{
    static Map<String,RequestMappingInfo> map = new HashMap<>();

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        Method[] declaredMethods = bean.getClass().getDeclaredMethods();
        for(Method method:declaredMethods){
            RequestMappingInfo requestMappingInfo = ceateRequestMappingInfo(method, bean);
            map.put(requestMappingInfo.getUrl(),requestMappingInfo);
        }
        return true;
    }


    //定义一个对象
    private RequestMappingInfo ceateRequestMappingInfo(Method method,Object bean){
        RequestMappingInfo requestMappingInfo = new RequestMappingInfo();
        if(method.isAnnotationPresent(RequestMapping.class)){
            requestMappingInfo.setMethod(method);
            requestMappingInfo.setObject(bean);
            requestMappingInfo.setUrl(method.getDeclaredAnnotation(RequestMapping.class).value());
        }
        return requestMappingInfo;
    }

    @Override
    public Object getHandler(String url) {
        return map.get(url);
    }
}
