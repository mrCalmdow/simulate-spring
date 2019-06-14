package com.flchen.practice.starter;

import com.flchen.practice.web.server.TomcatServer;
import org.apache.catalina.LifecycleException;

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
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }
}
