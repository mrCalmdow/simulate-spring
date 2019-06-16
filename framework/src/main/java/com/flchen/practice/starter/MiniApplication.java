package com.flchen.practice.starter;

import com.flchen.practice.core.ClassScanner;
import com.flchen.practice.web.handler.HandlerManager;
import com.flchen.practice.web.server.TomcatServer;
import org.apache.catalina.LifecycleException;

import java.io.IOException;
import java.util.List;

/**
 * author fl.chen
 * Date 2019-06-14
 * Time 09:40
 **/
public class MiniApplication {

    public static void run(Class<?> cls, String[] args) {

        System.out.println("Hello, mini spring!");

        TomcatServer tomcatServer = new TomcatServer(args);
        try {
            tomcatServer.startServer();
            List<Class<?>> classes = ClassScanner.scannerClass(cls.getPackage().getName());

            System.out.println("------- scan class begin");
            HandlerManager.resolveMappingHandler(classes);
            System.out.println("------- scan class end");

            HandlerManager.mappingHandlers.forEach(e -> {
                System.out.println("------- mappingHandler: " + e.identityInfo());
            });
            for (Class<?> clazz : classes) {
                System.out.println(clazz.getName());
            }

        } catch (LifecycleException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
