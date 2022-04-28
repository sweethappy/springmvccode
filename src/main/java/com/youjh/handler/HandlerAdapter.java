package com.youjh.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerAdapter {

    public boolean support(Object handler);

    public Object handle(HttpServletRequest request, HttpServletResponse response,Object handler) throws  Exception;
}
