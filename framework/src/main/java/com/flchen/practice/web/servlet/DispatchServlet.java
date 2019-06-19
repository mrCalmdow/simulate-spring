package com.flchen.practice.web.servlet;

import com.flchen.practice.web.handler.HandlerManager;
import com.flchen.practice.web.handler.MappingHandler;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * author fl.chen
 * Date 2019-06-14
 * Time 13:52
 **/
public class DispatchServlet implements Servlet {
    @Override
    public void init(ServletConfig config) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {

        System.out.println("------ entry dispatch service");
        for(MappingHandler mappingHandler : HandlerManager.mappingHandlers) {

            System.out.println("------ try handler : " + mappingHandler.identityInfo());
            try {
                if (mappingHandler.handler(req, res)) {
                    System.out.println("------ handler successful");
                    return;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
