package servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/hello")
public class MyServlet extends HttpServlet {

    //Life Cycle Of Servlet
    public MyServlet() {
        System.out.println("Object is created");
    }

    @Override
    public void init(ServletConfig config) throws ServletException {

        System.out.println("I'm servlet now");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("Get Request is Generated");
    }

    @Override
    public void destroy() {

        System.out.println("The end");
    }
}
