package lk.ijse.gdse66.BackEnd.bo.custom;

import javafx.collections.ObservableList;
import lk.ijse.gdse66.BackEnd.bo.SuperBO;
import lk.ijse.gdse66.BackEnd.dto.OrderDTO;
import lk.ijse.gdse66.BackEnd.dto.OrderDetailDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderBO extends SuperBO {

    String generateNewOrderId(Connection connection) throws SQLException, ClassNotFoundException;

    ObservableList<OrderDTO> getAllOrders(Connection connection) throws SQLException, ClassNotFoundException;

    ObservableList<OrderDetailDTO> getAllOrdersDetails(Connection connection) throws SQLException, ClassNotFoundException;

    boolean saveOrder(Connection connection, OrderDTO ordersDTO) throws SQLException, ClassNotFoundException;

    boolean saveOrderDetail(Connection connection, OrderDetailDTO orderDetailDTO) throws SQLException, ClassNotFoundException;

    boolean updateQtyOnHand(Connection connection, String id, int qty) throws SQLException, ClassNotFoundException;

    ObservableList<OrderDetailDTO> getAllOrderDetails(Connection connection) throws SQLException, ClassNotFoundException;

    ArrayList<OrderDetailDTO> searchOrderDetails(String orderId, Connection connection) throws SQLException, ClassNotFoundException;

}
