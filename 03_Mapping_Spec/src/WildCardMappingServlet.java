import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Created By Ravindu Prathibha
 * @created 4/7/2024 - 3:47 PM
 * @project Java_EE
 */

//Wild Card Mapping
@WebServlet(urlPatterns = "/items/*")
public class WildCardMappingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String servletPath = req.getServletPath();
        System.out.println("Servlet path "+servletPath);

        String method = req.getMethod();
        System.out.println("Method"+method);

        String pathInfo = req.getPathInfo();
        System.out.println("Path info"+pathInfo);

        String contextPath = req.getContextPath();
        System.out.println("Context path "+contextPath);

        System.out.println("=========================");
        System.out.println("customer request Wild Card Mapping");
    }
}
