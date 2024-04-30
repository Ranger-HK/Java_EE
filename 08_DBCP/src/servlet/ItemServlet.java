package servlet;

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

@WebServlet(urlPatterns = "/item")
public class ItemServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ServletContext servletContext = req.getServletContext();
        BasicDataSource bds = (BasicDataSource) servletContext.getAttribute("obs");

        try {
            Connection connection = bds.getConnection();
            PreparedStatement pstm = connection.prepareStatement(" select * from item");
            ResultSet rst = pstm.executeQuery();
            while (rst.next()) {
                System.out.println(rst.getString(1));
            }
            connection.close();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
