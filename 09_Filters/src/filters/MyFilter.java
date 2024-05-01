package filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.io.PrintWriter;

/*@WebFilter(urlPatterns = {"/customer","/item","/order"})*/ //---> selected url mapping
@WebFilter(urlPatterns = "/*") //---> multi url mapping
//@WebFilter(urlPatterns = "/item") //---> single url mapping
public class MyFilter implements Filter {

    public MyFilter() {
        /*System.out.println("Object Created from MyFilter");*/
        System.out.println("Object Was Created From My Filter Class");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        /*System.out.println("My Filter Initialized");*/
        System.out.println("Now MyFilter Class is a processing Filter");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        /*System.out.println("Do Filter Method called");*/
        System.out.println("First");

        filterChain.doFilter(servletRequest, servletResponse);

        PrintWriter writer = servletResponse.getWriter();
        writer.write("Added from MyFilter");

        System.out.println("Second");
    }

    @Override
    public void destroy() {
        /*System.out.println("My Filter Destroyed");*/
        System.out.println("My Filter Was Destroyed");
    }
}
