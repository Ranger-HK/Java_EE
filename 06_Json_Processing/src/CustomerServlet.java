import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
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

    //Get All Customers
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            //the media type of content of the response
            resp.setContentType("application/json"); //MIME Types (Multipurpose Internet Mail Extensions)

            //Create DB Connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Thogakade", "root", "19990202Ravi@:&pra");
            ResultSet rst = connection.prepareStatement("SELECT  * FROM Customer").executeQuery();

            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

            //Access the record and generate a json object
            while (rst.next()) {
                String customerId = rst.getString(1);
                String name = rst.getString(2);
                String address = rst.getString(3);
                double salary = rst.getDouble(4);
                System.out.println(customerId + " " + name + " " + address + " " + salary);

                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("customerId", rst.getString(1));
                objectBuilder.add("name", rst.getString(2));
                objectBuilder.add("address", rst.getString(3));
                objectBuilder.add("salary", rst.getDouble(4));

                arrayBuilder.add(objectBuilder.build());
            }
            //print response
            PrintWriter writer = resp.getWriter();
            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status", "200");
            response.add("message", "Done");
            response.add("data", arrayBuilder.build());

            writer.print(response.build());

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    //Save Customer
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String customerID = req.getParameter("customerID");
        String customerName = req.getParameter("customerName");
        String customerAddress = req.getParameter("customerAddress");
        String customerSalary = req.getParameter("customerSalary");
        System.out.println(customerID + " " + customerName + " " + customerAddress + " " + customerSalary);
        PrintWriter writer = resp.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Thogakade", "root", "19990202Ravi@:&pra");

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Customer VALUES (?,?,?,?)");
            preparedStatement.setObject(1, customerID);
            preparedStatement.setObject(2, customerName);
            preparedStatement.setObject(3, customerAddress);
            preparedStatement.setObject(4, customerSalary);

            if (preparedStatement.executeUpdate() > 0) {
                JsonObjectBuilder response = Json.createObjectBuilder();
                response.add("status", 200);
                response.add("message", "Successfully Added");
                response.add("data", "");
                writer.print(response.build());
            }

        } catch (ClassNotFoundException e) {
            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status", 500);
            response.add("message", "Error");
            response.add("data", e.getLocalizedMessage());
            writer.print(response.build());
            e.printStackTrace();

        } catch (SQLException throwables) {
            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status", 500);
            response.add("message", "Error");
            response.add("data", throwables.getLocalizedMessage());
            writer.print(response.build());
            throwables.printStackTrace();
        }

    }

    //Delete Customer
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String customerID = req.getParameter("CusID");
        System.out.println(customerID);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Thogakade", "root", "19990202Ravi@:&pra");
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Customer WHERE customerID=?");
            preparedStatement.setObject(1, customerID);

            boolean b = preparedStatement.executeUpdate() > 0;
            PrintWriter writer = resp.getWriter();

            if (b) {
                writer.write("Customer Deleted");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    //Update Customer
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String customerID = req.getParameter("customerID");
        String customerName = req.getParameter("customerName");
        String customerAddress = req.getParameter("customerAddress");
        String customerSalary = req.getParameter("customerSalary");
        System.out.println(customerID + " " + customerName + " " + customerAddress + " " + customerSalary);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Thogakade", "root", "19990202Ravi@:&pra");

            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Customer SET name=?,address=?,salary=? WHERE customerID=?");
            preparedStatement.setObject(1, customerName);
            preparedStatement.setObject(2, customerAddress);
            preparedStatement.setObject(3, customerSalary);
            preparedStatement.setObject(4, customerID);
            boolean b = preparedStatement.executeUpdate() > 0;
            PrintWriter writer = resp.getWriter();

            if (b) {
                writer.write("Customer Added");
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            resp.sendError(500, e.getMessage());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            resp.sendError(500, throwables.getMessage());
        }

    }
}
