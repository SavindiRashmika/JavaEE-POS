package lk.ijse.gdse66.BackEnd.bo.custom;

import javafx.collections.ObservableList;
import lk.ijse.gdse66.BackEnd.bo.SuperBO;
import lk.ijse.gdse66.BackEnd.dto.OrderDTO;

import java.sql.Connection;
import java.sql.SQLException;

public interface OrderBO extends SuperBO {

    String generateNewOrderId(Connection connection) throws SQLException, ClassNotFoundException;

    ObservableList<OrderDTO> getAllOrders(Connection connection) throws SQLException, ClassNotFoundException;
}
