package lk.ijse.gdse66.BackEnd.dao.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.gdse66.BackEnd.Entity.Order;
import lk.ijse.gdse66.BackEnd.dao.CrudUtil;
import lk.ijse.gdse66.BackEnd.dao.custom.OrderDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public boolean add(Order order, Connection connection) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String s, Connection connection) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ObservableList<Order> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery(connection, "SELECT * FROM Order");

        ObservableList<Order> obList = FXCollections.observableArrayList();

        while (resultSet.next()) {
            Order orders = new Order(
                    resultSet.getString(1),
                    resultSet.getDate(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getDouble(5),
                    resultSet.getDouble(6)
            );

            obList.add(orders);
        }

        return obList;
    }

    @Override
    public boolean update(Order order, Connection connection) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewOrderId(Connection connection) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery(connection, "SELECT oid FROM orders ORDER BY oid DESC LIMIT 1");

        if (resultSet.next()){
            return resultSet.getString(1);
        }else {
            return null;
        }
    }
}
