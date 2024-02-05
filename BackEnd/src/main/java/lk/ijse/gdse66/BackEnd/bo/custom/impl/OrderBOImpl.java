package lk.ijse.gdse66.BackEnd.bo.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.gdse66.BackEnd.Entity.Order;
import lk.ijse.gdse66.BackEnd.bo.custom.OrderBO;
import lk.ijse.gdse66.BackEnd.dao.custom.OrderDAO;
import lk.ijse.gdse66.BackEnd.dao.custom.impl.OrderDAOImpl;
import lk.ijse.gdse66.BackEnd.dto.OrderDTO;

import java.sql.Connection;
import java.sql.SQLException;

public class OrderBOImpl implements OrderBO {

    OrderDAO orderDAO = new OrderDAOImpl();

    @Override
    public String generateNewOrderId(Connection connection) throws SQLException, ClassNotFoundException {
        return orderDAO.generateNewOrderId(connection);
    }

    @Override
    public ObservableList<OrderDTO> getAllOrders(Connection connection) throws SQLException, ClassNotFoundException {
        ObservableList<Order> orders = orderDAO.getAll(connection);

        ObservableList<OrderDTO> obList = FXCollections.observableArrayList();

        for (Order temp : orders) {
            OrderDTO ordersDTO = new OrderDTO(
                    temp.getOid(),
                    temp.getDate(),
                    temp.getCustomerID(),
                    temp.getTotal(),
                    temp.getSubTotal(),
                    temp.getDiscount()
            );

            obList.add(ordersDTO);
        }
        return obList;
    }
}
