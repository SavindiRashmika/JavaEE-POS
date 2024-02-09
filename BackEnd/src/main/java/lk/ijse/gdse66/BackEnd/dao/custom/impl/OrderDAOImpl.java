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
     public boolean add(Order orders, Connection connection) throws SQLException, ClassNotFoundException {
         return CrudUtil.executeUpdate(connection,"INSERT INTO `orders` VALUES (?,?,?,?,?,?)",orders.getOid(),
                 orders.getDate(), orders.getCustomerID(), orders.getTotal(), orders.getSubTotal(), orders.getDiscount());
     }

    @Override
    public boolean delete(String s, Connection connection) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ObservableList<Order> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery(connection, "SELECT * FROM `orders`");

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
    public Order search(String s, Connection connection) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateNewOrderId(Connection connection) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery(connection, "SELECT oid FROM `orders` ORDER BY oid DESC LIMIT 1");

        if (resultSet.next()){
            return resultSet.getString(1);
        }else {
            return null;
        }
    }

    @Override
    public boolean ifOrderExist(String oid, Connection connection) throws SQLException, ClassNotFoundException {
        return false;
    }


    @Override
    public boolean mangeItems(int qty, String code, Connection connection) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(connection, "UPDATE item SET qtyOnHand=qtyOnHand-? WHERE code=?", qty, code);

    }
}
