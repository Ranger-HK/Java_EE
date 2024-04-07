import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Created By Ravindu Prathibha
 * @created 4/7/2024 - 4:14 PM
 * @project Java_EE
 */

//Extension Mapping --> jpg,mp4,gif
//@WebServlet(urlPatterns = {"/ab","/cd"})
@WebServlet(urlPatterns = "*.mp4")
public class ExtensionMappingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("customer request Extension Mapping");
    }
}
