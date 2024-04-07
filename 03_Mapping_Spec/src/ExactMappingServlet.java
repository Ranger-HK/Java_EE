import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Created By Ravindu Prathibha
 * @created 4/7/2024 - 3:13 PM
 * @project Java_EE
 */

//Exact Mapping
@WebServlet(urlPatterns = "/customer")
public class ExactMappingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("customer request Extract Mapping");
    }
}
