import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Created By Ravindu Prathibha
 * @created 4/11/2024 - 2:20 PM
 * @project Java_EE
 */

@WebServlet(urlPatterns = "/json")
public class JSONServlet extends HttpServlet {

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       /* System.out.println("Method Invoked");

        ServletInputStream inputStream = req.getInputStream();

        int read;
        while ((read=inputStream.read())!=-1){
            System.out.print((char) read);
        }*/


        System.out.println("Do Put Request Received");
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String customerID = jsonObject.getString("id");
        String customerName = jsonObject.getString("name");
        String customerAddress = jsonObject.getString("address");
        String customerSalary = jsonObject.getString("salary");
        System.out.println(customerID + " " + customerName + " " + customerAddress + " " + customerSalary);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");

        // How to generate a single JSON object using JSON processing
      /*  JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

        objectBuilder.add("id","C001");
        objectBuilder.add("name","Tonny");
        objectBuilder.add("address","Colombo");
        objectBuilder.add("salary",100000.00);

        JsonObject build = objectBuilder.build();

        PrintWriter writer = resp.getWriter();
        writer.print(build);*/


        // How to generate a  JSON Array using JSON processing
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        objectBuilder.add("id","C001");
        objectBuilder.add("name","Tonny");
        objectBuilder.add("address","Colombo");
        objectBuilder.add("salary",100000.00);

        JsonObjectBuilder objectBuilder2 = Json.createObjectBuilder();
        objectBuilder2.add("id","C002");
        objectBuilder2.add("name","CJ");
        objectBuilder2.add("address","USA");
        objectBuilder2.add("salary",400000.00);

        arrayBuilder.add(objectBuilder.build());
        arrayBuilder.add(objectBuilder2.build());

        PrintWriter writer = resp.getWriter();
        writer.print(arrayBuilder.build());


    }
}
