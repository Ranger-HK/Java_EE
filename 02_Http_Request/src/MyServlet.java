import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Created By Ravindu Prathibha
 * @created 4/4/2024 - 8:56 PM
 * @project Java_EE
 */

@WebServlet("/hi")
public class MyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Requests Awa");
        PrintWriter writer = resp.getWriter();
        writer.write("Response Send");
    }
}
