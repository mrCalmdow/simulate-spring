package com.flchen.practice.web.handler;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * author fl.chen
 * Date 2019-06-15
 * Time 10:10
 **/
public class MappingHandler {

    private String uri;
    private Method method;
    private Class<?> controller;
    private String[] args;

    public MappingHandler(String uri, Method method, Class<?> controller, String[] args) {
        this.uri = uri;
        this.method = method;
        this.controller = controller;
        this.args = args;
    }

    public boolean handler(ServletRequest req, ServletResponse res) throws IllegalAccessException, InstantiationException, InvocationTargetException, IOException {
        System.out.println("------ start handler");
        String requestUri = ((HttpServletRequest)req).getRequestURI();
        if (!uri.equalsIgnoreCase(requestUri)) {
            System.out.println("------ URI can't handler");
            return false;
        }

        /**
         * 通过注解定义的参数名从请求数据中取到对应参数名
         */
        Object[] parameters = new Object[args.length];
        for (int i = 0, len = args.length; i < len; i++) {
            Class<?> paramType = args[i].getClass();
            parameters[i] = req.getParameter(args[i]);
            System.out.println("------ parameter: " + i + " : " + parameters[i].toString());
        }

        Object ctl = controller.newInstance();
        Object response = method.invoke(ctl, parameters);
        res.getWriter().println(response.toString());
        return true;
    }

    public String identityInfo() {
        return uri;
    }
}
