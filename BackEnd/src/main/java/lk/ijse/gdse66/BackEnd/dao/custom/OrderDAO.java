package lk.ijse.gdse66.BackEnd.dao.custom;

import lk.ijse.gdse66.BackEnd.Entity.Order;
import lk.ijse.gdse66.BackEnd.dao.CrudDAO;


import java.sql.Connection;
import java.sql.SQLException;

public interface OrderDAO extends CrudDAO<Order, String, Connection> {

    String generateNewOrderId(Connection connection) throws SQLException, ClassNotFoundException;
}