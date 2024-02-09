package lk.ijse.gdse66.BackEnd.dao.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.gdse66.BackEnd.Entity.OrderDetails;
import lk.ijse.gdse66.BackEnd.dao.CrudUtil;
import lk.ijse.gdse66.BackEnd.dao.custom.OrderDetailsDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {
    @Override
    public boolean add(OrderDetails orderDetails, Connection connection) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(connection,"INSERT INTO `orderdetails` VALUES (?,?,?,?,?)",orderDetails.getOid(),
                orderDetails.getItemCode(), orderDetails.getQty(), orderDetails.getUnitPrice(), orderDetails.getTotal());
    }

    @Override
    public boolean delete(String s, Connection connection) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ObservableList<OrderDetails> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery(connection, "SELECT * FROM orderdetails");

        ObservableList<OrderDetails> obList = FXCollections.observableArrayList();

        while (resultSet.next()) {
            OrderDetails orderDetails = new OrderDetails(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getDouble(4),
                    resultSet.getDouble(5)
            );

            obList.add(orderDetails);
        }

        return obList;
    }

    @Override
    public boolean update(OrderDetails orderDetails, Connection connection) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public OrderDetails search(String s, Connection connection) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<OrderDetails> searchOrderDetail(String id, Connection connection) throws SQLException, ClassNotFoundException {
        return null;
    }
}
