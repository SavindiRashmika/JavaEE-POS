package lk.ijse.gdse66.BackEnd.dao.custom;

import lk.ijse.gdse66.BackEnd.Entity.OrderDetails;
import lk.ijse.gdse66.BackEnd.dao.CrudDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;


public interface OrderDetailsDAO extends CrudDAO<OrderDetails, String, Connection> {
    ArrayList<OrderDetails> searchOrderDetail(String id, Connection connection) throws SQLException, ClassNotFoundException;
}
