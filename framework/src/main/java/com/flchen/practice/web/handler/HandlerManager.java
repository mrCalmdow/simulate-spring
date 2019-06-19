package com.flchen.practice.web.handler;

import com.flchen.practice.web.mvc.Controller;
import com.flchen.practice.web.mvc.RequestMapping;
import com.flchen.practice.web.mvc.RequestParam;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

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
//                System.out.println("***** not Controller: " + cls.getName());
                continue;
            }
//            System.out.println("ResolveMappingHandler: " + cls.getName());
            parseHandlerFromController(cls);
        }
    }

    private static void parseHandlerFromController(Class<?> cls) {
        Method[] methods = cls.getDeclaredMethods();

//        System.out.println("------ do parse: " + cls.getName());
        for(Method method : methods) {
            if (!method.isAnnotationPresent(RequestMapping.class)) {
                return;
            }

            String uri = method.getDeclaredAnnotation(RequestMapping.class).value();

            Map<String, Class<?>> params = new HashMap<>();
            List<Parameter> parameters = Arrays.asList(method.getParameters());
            for(Parameter parameter : parameters) {
                if (parameter.isAnnotationPresent(RequestParam.class)) {
                    String key = parameter.getDeclaredAnnotation(RequestParam.class).value();
                    Class<?> value = parameter.getType();
                    params.put(key, value);
                }
            }

            MappingHandler mappingHandler = new MappingHandler(uri, method, cls, params);
            System.out.println("Add to Mapping list: " + cls.getName());
            mappingHandlers.add(mappingHandler);
        }
    }
}
