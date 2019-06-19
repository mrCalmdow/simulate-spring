package com.flchen.practice.web.handler;

import com.flchen.practice.beans.BeanFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 * author fl.chen
 * Date 2019-06-15
 * Time 10:10
 **/
public class MappingHandler {

    private String uri;
    private Method method;
    private Class<?> controller;
    private Map<String, Class<?>> args;

    public MappingHandler(String uri, Method method, Class<?> controller, Map<String, Class<?>> args) {
        this.uri = uri;
        this.method = method;
        this.controller = controller;
        this.args = args;
    }

    public boolean handler(ServletRequest req, ServletResponse res) throws Exception {
        System.out.println("------ start handler, current URI: " + uri);
        String requestUri = ((HttpServletRequest)req).getRequestURI();
        System.out.println("------ start handler, request URI: " + requestUri);
        if (!uri.equalsIgnoreCase(requestUri)) {
            System.out.println("------ URI can't handler");
            return false;
        }

        /**
         * 通过注解定义的参数名从请求数据中取到对应参数名
         */
        Object[] parameters = new Object[args.size()];

        int i = 0;
        for (Map.Entry<String, Class<?>> entry : args.entrySet()) {

//            System.out.println("[deal] -- entry<" + entry.getKey() + "," + entry.getValue().getSimpleName() + ">");
            Object obj = req.getParameter(entry.getKey());
//            System.out.println("[request] -- request parameter : " + obj.toString());

//            System.out.println("[cast] --- begin cast.");
            parameters[i++] = castParamType(obj, entry.getValue());
//            System.out.println("[cast] --- end cast.");
        }
//
//        while(i > 0) {
//            System.out.println(i-1 + " **** paramert --- > " + parameters[i - 1]);
//            i--;
//        }
        Object ctl = BeanFactory.getBean(controller);
        Object response = method.invoke(ctl, parameters);
        res.getWriter().println(response.toString());
        return true;
    }

    public String identityInfo() {
        return this.controller.getSimpleName() +  "-->" + uri;
    }

    private <T> T castParamType(Object obj, Class<T> tClass) {

        if (tClass.equals(Integer.class)) {
           return (T) Integer.valueOf(obj.toString());
        } else if (tClass.equals(String.class)) {
            return (T) String.valueOf(obj.toString());
        }
        System.out.println("[error] can't " + "class : " + tClass.getSimpleName() + "  :  object : " + obj);
        return null;
    }
}
