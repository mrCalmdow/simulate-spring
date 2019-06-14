package com.flchen.practice.web.server;

import com.flchen.practice.web.servlet.TestServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

/**
 * author fl.chen
 * Date 2019-06-14
 * Time 13:37
 **/
public class TomcatServer {

    private Tomcat tomcat;
    private String[] args;

    public TomcatServer(String[] args) {
        this.args = args;
    }

    public void startServer() throws LifecycleException {
        tomcat = new Tomcat();
        tomcat.setPort(8118);
        tomcat.start();

        /**
         * Tomcat --> host -- context --wrapper
         */
        Context context = new StandardContext();
        context.setPath("");
        context.addLifecycleListener(new Tomcat.FixContextListener());

        TestServlet testServlet = new TestServlet();
        Tomcat.addServlet(context, "testServlet", testServlet).setAsyncSupported(true);
        context.addServletMappingDecoded("/test.json", "testServlet");
        tomcat.getHost().addChild(context);

//        File webXml = new File("framework/src/main/conf");
//        System.out.println("***************************webXml == " + webXml.getAbsolutePath());
//        tomcat.addWebapp(tomcat.getHost(), "/web", webXml.getAbsolutePath());

        /**
         * 防止服务器中途退出
         */
        Thread awaitThread = new Thread("tomcat_await_thread") {
            @Override
            public void run() {
                TomcatServer.this.tomcat.getServer().await();
            }
        };
        awaitThread.setDaemon(false);
        awaitThread.start();
    }
}
