package lk.ijse.gdse66.BackEnd.servlet;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import javafx.collections.ObservableList;
import lk.ijse.gdse66.BackEnd.bo.custom.OrderBO;
import lk.ijse.gdse66.BackEnd.bo.custom.impl.OrderBOImpl;
import lk.ijse.gdse66.BackEnd.dto.OrderDTO;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/order")
public class OrderServlet extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    DataSource dataSource;

    OrderBO orderBO = new OrderBOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.addHeader("Access-Control-Allow-Origin", "*");
        try {

            String option = req.getParameter("option");
            Connection connection = dataSource.getConnection();
            PrintWriter writer = resp.getWriter();

            switch (option){
                case "Id":
                    JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
                    jsonObjectBuilder.add("oid", orderBO.generateNewOrderId(connection));
                    writer.print(jsonObjectBuilder.build());
                    break;

                case "GetAll":
                    ObservableList<OrderDTO> allOrders = orderBO.getAllOrders(connection);
                    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

                    for (OrderDTO ordersDTO : allOrders) {

                        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                        objectBuilder.add("oid", ordersDTO.getOid());
                        objectBuilder.add("date", String.valueOf(ordersDTO.getDate()));
                        objectBuilder.add("customerID", ordersDTO.getCustomerID());
                        objectBuilder.add("total", ordersDTO.getTotal());
                        objectBuilder.add("subTotal", ordersDTO.getSubTotal());
                        objectBuilder.add("discount", ordersDTO.getDiscount());
                        arrayBuilder.add(objectBuilder.build());

                    }

                    JsonObjectBuilder response = Json.createObjectBuilder();
                    response.add("status", 200);
                    response.add("message", "Done");
                    response.add("data", arrayBuilder.build());
                    writer.print(response.build());

                    break;
            }


        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, HEAD");
    }
}
