import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Created By Ravindu Prathibha
 * @created 4/30/2024 - 11:27 AM
 * @project Java_EE
 */
@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Do Get Method Invoked");

        BasicDataSource bds = new BasicDataSource();

        bds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        bds.setUrl("jdbc:mysql://localhost:3306/Thogakade");
        bds.setUsername("root");
        bds.setPassword("19990202Ravi@:&pra");
        bds.setMaxTotal(5);
        bds.setInitialSize(5);

        ServletContext servletContext = req.getServletContext();
        servletContext.setAttribute("bds",bds);

        try {
            Connection connection = bds.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT  * FROM Customer");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String id = resultSet.getString(1);
                System.out.println(id);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = req.getServletContext();
        BasicDataSource bds = (BasicDataSource) servletContext.getAttribute("bds");

        try {
            Connection connection = bds.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT  * FROM Customer");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String id = resultSet.getString(1);
                System.out.println(id);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
