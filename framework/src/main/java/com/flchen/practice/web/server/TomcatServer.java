package com.flchen.practice.web.server;

import com.flchen.practice.web.servlet.DispatchServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

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

        DispatchServlet dispatchServlet = new DispatchServlet();
        Tomcat.addServlet(context, "dispatchServlet", dispatchServlet).setAsyncSupported(true);
        context.addServletMappingDecoded("/", "dispatchServlet");
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
