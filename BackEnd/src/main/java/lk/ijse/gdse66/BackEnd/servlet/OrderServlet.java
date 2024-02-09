package lk.ijse.gdse66.BackEnd.servlet;

import jakarta.json.*;
import javafx.collections.ObservableList;
import lk.ijse.gdse66.BackEnd.Entity.OrderDetails;
import lk.ijse.gdse66.BackEnd.bo.BOFactory;
import lk.ijse.gdse66.BackEnd.bo.custom.OrderBO;
import lk.ijse.gdse66.BackEnd.bo.custom.impl.OrderBOImpl;
import lk.ijse.gdse66.BackEnd.dto.OrderDTO;
import lk.ijse.gdse66.BackEnd.dto.OrderDetailDTO;

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
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/order")
public class OrderServlet extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    DataSource dataSource;

    OrderBO orderBO = (OrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDERS);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("order-doget");
        resp.setContentType("application/json");
        resp.addHeader("Access-Control-Allow-Origin", "*");
        try {

            String option = req.getParameter("option");
            Connection connection = dataSource.getConnection();
            PrintWriter writer = resp.getWriter();

            switch (option){
                case "Id":
                    try {
                        String orderId = orderBO.generateNewOrderId(connection);

                        JsonObjectBuilder ordID = Json.createObjectBuilder();
                        if (orderId != null) {
                            ordID.add("oid", orderId);
                        } else {
                            System.out.println("id not genarate");
                            // Handle the case where orderId is null, if needed.
                        }
                        writer.print(ordID.build());


                    } catch (SQLException | ClassNotFoundException e) {

                        JsonObjectBuilder rjo = Json.createObjectBuilder();
                        rjo.add("state", "Error");
                        rjo.add("message", e.getLocalizedMessage());
                        rjo.add("data", "");
                        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        resp.getWriter().print(rjo.build());
                    }

                    break;

                case "get":
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

                case "orderDetail":
                    ObservableList<OrderDetailDTO> allOrderDetail = orderBO.getAllOrdersDetails(connection);
                    JsonArrayBuilder arrayBuilder1 = Json.createArrayBuilder();

                    for (OrderDetailDTO orderDTO : allOrderDetail) {
                        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                        objectBuilder.add("oid", orderDTO.getOid());
                        objectBuilder.add("itemCode", orderDTO.getItemCode());
                        objectBuilder.add("qty", orderDTO.getQty());
                        objectBuilder.add("unitPrice", orderDTO.getUnitPrice());
                        objectBuilder.add("total", orderDTO.getTotal());
                        arrayBuilder1.add(objectBuilder.build());

                    }

                    JsonObjectBuilder response1 = Json.createObjectBuilder();
                    response1.add("status", 200);
                    response1.add("message", "Done");
                    response1.add("data", arrayBuilder1.build());
                    writer.print(response1.build());

                    break;

            }
            connection.close();

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("order-dopost");


        try {
            resp.setContentType("application/json");
            Connection connection = dataSource.getConnection();
            PrintWriter writer = resp.getWriter();

            resp.addHeader("Access-Control-Allow-Origin", "*");

            JsonReader reader = Json.createReader(req.getReader());
            JsonObject jsonObject = reader.readObject();
            JsonArray OrderDetaill = jsonObject.getJsonArray("OrderDetail");

            ArrayList<OrderDetailDTO> order = new ArrayList<>();
            for (JsonValue orderDetail: OrderDetaill) {
                JsonObject asJsonObject = orderDetail.asJsonObject();
                order.add(new OrderDetailDTO(
                        asJsonObject.getString("oid"),
                        asJsonObject.getString("itemCode"),
                        Integer.parseInt(asJsonObject.getString("qty")),
                        Double.parseDouble(asJsonObject.getString("unitPrice")),
                        Double.parseDouble(asJsonObject.getString("total"))
                ));
            }

            OrderDTO ordersDTO = new OrderDTO(
                    jsonObject.getString("oid"),
                    Date.valueOf(jsonObject.getString("date")),
                    jsonObject.getString("customerID"),
                    Double.parseDouble(jsonObject.getString("total")),
                    Double.parseDouble(jsonObject.getString("subTotal")),
                    Integer.parseInt(jsonObject.getString("discount")),
                    order
            );



            if (orderBO.saveOrder(connection, ordersDTO)){
                resp.setStatus(HttpServletResponse.SC_OK);
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("status",resp.getStatus());
                objectBuilder.add("message","Successfully Added");
                objectBuilder.add("data","");

                writer.print(objectBuilder.build());
            }

        } catch (SQLException | ClassNotFoundException throwables) {

            resp.setStatus(HttpServletResponse.SC_OK);
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("status", resp.getStatus());
            objectBuilder.add("message", "Error");
            objectBuilder.add("data", throwables.getLocalizedMessage());
            throwables.printStackTrace();

            throwables.printStackTrace();
        }finally {
            Connection connection = null;
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, HEAD");
    }
}
