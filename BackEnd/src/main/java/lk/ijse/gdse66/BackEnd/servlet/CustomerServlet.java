package lk.ijse.gdse66.BackEnd.servlet;

import jakarta.json.*;
import javafx.collections.ObservableList;
import lk.ijse.gdse66.BackEnd.bo.custom.CustomerBO;
import lk.ijse.gdse66.BackEnd.bo.custom.impl.CustomerBOImpl;
import lk.ijse.gdse66.BackEnd.dto.CustomerDTO;

import java.io.*;
import java.net.BindException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;

@WebServlet(name = "customerServlet", urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {
    @Resource(name = "java:comp/env/jdbc/pool")
    DataSource dataSource;
    CustomerBO customerBO = new CustomerBOImpl();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doget");
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        try {
            Connection connection = dataSource.getConnection();
            ObservableList<CustomerDTO> allCustomer = customerBO.getAllCustomer(connection);
            JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

            for (CustomerDTO customer: allCustomer) {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("id", customer.getId());
                objectBuilder.add("name", customer.getName());
                objectBuilder.add("address", customer.getAddress());

                jsonArrayBuilder.add(objectBuilder.build());
            }
            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status", 200);
            response.add("message","Done");
            response.add("data", jsonArrayBuilder.build());

            writer.print(response.build());

        connection.close();

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        resp.addHeader("Access-Control-Allow-Origin", "*");

        try {
            Connection connection = dataSource.getConnection();

            CustomerDTO customerDTO = new CustomerDTO(
                    req.getParameter("txtCustomerID"),
                    req.getParameter("txtCustomerName"),
                    req.getParameter("txtCustomerAddress")
            );

            try {
                if (customerBO.addCustomer(connection, customerDTO)) {
                    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                    resp.setStatus(HttpServletResponse.SC_CREATED);
                    objectBuilder.add("status", 200);
                    objectBuilder.add("message", "Successfully Added");
                    objectBuilder.add("data", "");
                    writer.print(objectBuilder.build());
                }
            } catch (ClassNotFoundException e) {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("status", 400);
                objectBuilder.add("message", "Error");
                objectBuilder.add("data", e.getLocalizedMessage());
                writer.print(objectBuilder.build());
                resp.setStatus(HttpServletResponse.SC_OK);
                e.printStackTrace();
            }

            connection.close();

        } catch (SQLException e) {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("status", 400);
            objectBuilder.add("message", "Error");
            objectBuilder.add("data", e.getLocalizedMessage());
            writer.print(objectBuilder.build());
            resp.setStatus(HttpServletResponse.SC_OK);
            e.printStackTrace();
        }
        System.out.println("dopost");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Access-Control-Allow-Origin", "*");
        System.out.println("do delete");

        String custId = req.getParameter("txtCustomerID");
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();

        try {
            Connection connection = dataSource.getConnection();

            if (customerBO.deleteCustomer(connection, custId)) {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("status", 200);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Successfully Deleted");
                writer.print(objectBuilder.build());
            } else {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("status", 400);
                objectBuilder.add("data", "Wrong Id Inserted");
                objectBuilder.add("message", "");
                writer.print(objectBuilder.build());
            }

            connection.close();

        } catch (SQLException | ClassNotFoundException e) {
            resp.setStatus(200);
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("status", 500);
            objectBuilder.add("message", "Error");
            objectBuilder.add("data", e.getLocalizedMessage());
            writer.print(objectBuilder.build());
            e.printStackTrace();
        }

    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, HEAD");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doput");
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();

        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();

        try {
            Connection connection = dataSource.getConnection();

            CustomerDTO customerDTO = new CustomerDTO(
                    jsonObject.getString("id"),
                    jsonObject.getString("name"),
                    jsonObject.getString("address")
            );

            if (customerBO.updateCustomer(connection, customerDTO)) {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("status", 200);
                objectBuilder.add("message", "Successfully Updated");
                objectBuilder.add("data", "");
                writer.print(objectBuilder.build());
            } else {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("status", 400);
                objectBuilder.add("message", "Update Failed");
                objectBuilder.add("data", "");
                writer.print(objectBuilder.build());
            }

            connection.close();

        } catch (SQLException | ClassNotFoundException e) {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("status", 500);
            objectBuilder.add("message", "Update Failed");
            objectBuilder.add("data", e.getLocalizedMessage());
            writer.print(objectBuilder.build());
            e.printStackTrace();
        }
    }

    public void destroy() {
    }
}