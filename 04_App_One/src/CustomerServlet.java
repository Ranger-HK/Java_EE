import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Created By Ravindu Prathibha
 * @created 4/7/2024 - 7:02 PM
 * @project Java_EE
 */

@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Create DB Connection
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Thogakade", "root", "19990202Ravi@:&pra");
            ResultSet rst = connection.prepareStatement("SELECT  * FROM Customer").executeQuery();
            String allRecords = "";

            //Access the record and generate a json object
            while (rst.next()) {
                String customerId = rst.getString(1);
                String name = rst.getString(2);
                String address = rst.getString(3);
                double salary = rst.getDouble(4);
                System.out.println(customerId + " " + name + " " + address + " " + salary);

                //Convert One Record To JSon
                String customer = "{\"customerId\":\"" + customerId + "\",\"name\":\"" + name + "\",\"address\":\"" + address + "\",\"salary\":" + salary + "},";
                allRecords = allRecords + customer;
            }

            //Array Create and fix ', ' problem
            String finalJson = "[" + allRecords.substring(0, allRecords.length() - 1) + "]";

            //print response
            PrintWriter writer = resp.getWriter();
            writer.write(finalJson);
            //possible response types -->> text,xml,html,jason
            /*resp.setContentType("application/json");*/

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
