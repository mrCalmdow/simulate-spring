package com.flchen.practice.web.servlet;

import javax.servlet.*;
import java.io.IOException;

/**
 * author fl.chen
 * Date 2019-06-14
 * Time 13:52
 **/
public class TestServlet implements Servlet {
    @Override
    public void init(ServletConfig config) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {

        res.getOutputStream().println("<h1>Hello, this is test servlet!</h1>");
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
