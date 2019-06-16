package com.flchen.practice.web.handler;

import com.flchen.practice.web.mvc.Controller;
import com.flchen.practice.web.mvc.RequestMapping;
import com.flchen.practice.web.mvc.RequestParam;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * author fl.chen
 * Date 2019-06-15
 * Time 10:12
 **/
public class HandlerManager {

    public static List<MappingHandler> mappingHandlers = new ArrayList<>();

    public static void resolveMappingHandler(List<Class<?>> classes) {
        for(Class<?> cls : classes) {
            if(!cls.isAnnotationPresent(Controller.class)) {
                System.out.println("***** not Controller: " + cls.getName());
                continue;
            }
            System.out.println("ResolveMappingHandler: " + cls.getName());
            parseHandlerFromController(cls);
        }
    }

    private static void parseHandlerFromController(Class<?> cls) {
        Method[] methods = cls.getDeclaredMethods();

        System.out.println("------ do parse: " + cls.getName());
        for(Method method : methods) {
            if (!method.isAnnotationPresent(RequestMapping.class)) {
                return;
            }

            String uri = method.getDeclaredAnnotation(RequestMapping.class).value();

            List<String> params = new ArrayList<>();
            List<Parameter> parameters = Arrays.asList(method.getParameters());
            for(Parameter parameter : parameters) {
                if (parameter.isAnnotationPresent(RequestParam.class)) {
                    params.add(parameter.getDeclaredAnnotation(RequestParam.class).value());
                }
            }
            String[] args = params.toArray(new String[params.size()]);

            MappingHandler mappingHandler = new MappingHandler(uri, method, cls, args);
            System.out.println("Add to Mapping list: " + cls.getName());
            mappingHandlers.add(mappingHandler);
        }
    }
}
