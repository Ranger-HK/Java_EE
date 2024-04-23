import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/**
 * @Created By Ravindu Prathibha
 * @created 4/11/2024 - 2:20 PM
 * @project Java_EE
 */

@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Create DB Connection
        try {
            resp.setContentType("application/json"); //MIME Types (Multipurpose Internet Mail Extensions)
            resp.addHeader("Institute","IJSE");
            resp.addHeader("Course","GDSE");
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String customerID = req.getParameter("customerID");
        String customerName = req.getParameter("customerName");
        String customerAddress = req.getParameter("customerAddress");
        String customerSalary = req.getParameter("customerSalary");
        System.out.println(customerID+" "+customerName+" "+customerAddress+" "+customerSalary);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Thogakade", "root", "19990202Ravi@:&pra");
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Customer VALUES (?,?,?,?)");
            preparedStatement.setObject(1,customerID);
            preparedStatement.setObject(2,customerName);
            preparedStatement.setObject(3,customerAddress);
            preparedStatement.setObject(4,customerSalary);
            boolean b = preparedStatement.executeUpdate()>0;
            PrintWriter writer = resp.getWriter();

            if (b){
                writer.write("Customer Added");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String customerID = req.getParameter("CusID");
        System.out.println(customerID);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Thogakade", "root", "19990202Ravi@:&pra");
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Customer WHERE customerID=?");
            preparedStatement.setObject(1,customerID);

            boolean b = preparedStatement.executeUpdate()>0;
            PrintWriter writer = resp.getWriter();

            if (b){
                writer.write("Customer Deleted");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
