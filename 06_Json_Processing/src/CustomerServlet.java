import javax.json.*;
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
            String option = req.getParameter("option");

            //the media type of content of the response
            resp.setContentType("application/json"); //MIME Types (Multipurpose Internet Mail Extensions)

            //Create DB Connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Thogakade", "root", "19990202Ravi@:&pra");

            switch (option){
                case "SEARCH":
                    break;
                case "GETALL":

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
            break;

            }
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

        resp.setContentType("application/json"); //MIME Types (Multipurpose Internet Mail Extensions)


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
                resp.setStatus(HttpServletResponse.SC_CREATED);//201
                response.add("status", 201);
                response.add("message", "Successfully Added");
                response.add("data", "");
                writer.print(response.build());
            }

        } catch (ClassNotFoundException e) {
            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status", 400);
            response.add("message", "Error");
            response.add("data", e.getLocalizedMessage());
            writer.print(response.build());

            resp.setStatus(HttpServletResponse.SC_OK);//200
            e.printStackTrace();

        } catch (SQLException throwables) {
            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status", 400);
            response.add("message", "Error");
            response.add("data", throwables.getLocalizedMessage());
            writer.print(response.build());

            resp.setStatus(HttpServletResponse.SC_OK);//200
            throwables.printStackTrace();
        }

    }

    //Delete Customer
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String customerID = req.getParameter("CusID");
        System.out.println(customerID);

        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Thogakade", "root", "19990202Ravi@:&pra");
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Customer WHERE customerID=?");
            preparedStatement.setObject(1, customerID);

            if (preparedStatement.executeUpdate()>0){
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("status",200);
                objectBuilder.add("data","");
                objectBuilder.add("message","Successfully Deleted");
                writer.print(objectBuilder.build());

            }else{
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("status",400);
                objectBuilder.add("data","Wrong Id Inserted");
                objectBuilder.add("message","");
                writer.print(objectBuilder.build());
            }
        } catch (ClassNotFoundException e) {
            resp.setStatus(200);

            e.printStackTrace();
            //resp.sendError(500,e.getMessage());
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("status",500);
            objectBuilder.add("message","Error");
            objectBuilder.add("data",e.getLocalizedMessage());
            writer.print(objectBuilder.build());

        }catch (SQLException throwables){
            throwables.printStackTrace();
            // resp.sendError(500,throwables.getMessage());
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("status",500);
            objectBuilder.add("message","Error");
            objectBuilder.add("data",throwables.getLocalizedMessage());
            writer.print(objectBuilder.build());
        }
    }


    //Update Customer
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String customerID = jsonObject.getString("id");
        String customerName = jsonObject.getString("name");
        String customerAddress = jsonObject.getString("address");
        String customerSalary = jsonObject.getString("salary");
        System.out.println(customerID+" "+customerName+" "+customerAddress+" "+customerSalary);

        PrintWriter writer = resp.getWriter();

        resp.setContentType("application/json");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Thogakade", "root", "19990202Ravi@:&pra");

            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Customer SET name=?,address=?,salary=? WHERE customerID=?");
            preparedStatement.setObject(1, customerName);
            preparedStatement.setObject(2, customerAddress);
            preparedStatement.setObject(3, customerSalary);
            preparedStatement.setObject(4, customerID);


            if (preparedStatement.executeUpdate() > 0) {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("status",200);
                objectBuilder.add("message","Successfully Updated");
                objectBuilder.add("data","");
                writer.print(objectBuilder.build());
            }else {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("status",400);
                objectBuilder.add("message","Update Failed");
                objectBuilder.add("data","");
                writer.print(objectBuilder.build());
            }

        } catch (ClassNotFoundException e) {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("status",500);
            objectBuilder.add("message","Update Failed");
            objectBuilder.add("data",e.getLocalizedMessage());
            writer.print(objectBuilder.build());

        } catch (SQLException throwables) {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("status",500);
            objectBuilder.add("message","Update Failed");
            objectBuilder.add("data",throwables.getLocalizedMessage());
            writer.print(objectBuilder.build());
        }
    }
}
