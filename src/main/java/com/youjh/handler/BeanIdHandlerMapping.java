package com.youjh.handler;

import org.springframework.beans.BeansException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class BeanIdHandlerMapping implements HandlerMapping{
    static Map<String,Object> map = new HashMap<>();
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if (beanName.startsWith("/")){
            map.put(beanName,bean);
        }
        return false;
    }


    @Override
    public Object getHandler(String url) {
        return map.get(url);
    }
}
