package com.flchen.practice.starter;

import com.flchen.practice.beans.BeanFactory;
import com.flchen.practice.core.ClassScanner;
import com.flchen.practice.web.handler.HandlerManager;
import com.flchen.practice.web.server.TomcatServer;
import org.apache.catalina.LifecycleException;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * author fl.chen
 * Date 2019-06-14
 * Time 09:40
 **/
public class MiniApplication {

    public static void run(Class<?> cls, String[] args) {

//        System.out.println("Hello, mini spring!");

        TomcatServer tomcatServer = new TomcatServer(args);
        try {
            tomcatServer.startServer();
            List<Class<?>> classes = ClassScanner.scannerClass(cls.getPackage().getName());

            /**
             * beans initialize
             */
            BeanFactory.initBeans(classes);
            Map<Class<?>, Object> beans = BeanFactory.getBeans();
            beans.forEach((k,v) -> {
                System.out.println("Bean: " + k.getSimpleName() + "-->" + v.toString());
            });

//            System.out.println("------- scan class begin");
            /**
             * pares mapping handler
             */
            HandlerManager.resolveMappingHandler(classes);
//            System.out.println("------- scan class end");

            HandlerManager.mappingHandlers.forEach(e -> {
                System.out.println("------- mappingHandler: " + e.identityInfo());
            });
//            for (Class<?> clazz : classes) {
//                System.out.println(clazz.getName());
//            }

        } catch (LifecycleException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
