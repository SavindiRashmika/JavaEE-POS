package lk.ijse.gdse66.BackEnd.dao.custom;

import lk.ijse.gdse66.BackEnd.Entity.Item;
import lk.ijse.gdse66.BackEnd.dao.CrudDAO;

import java.sql.Connection;
import java.sql.SQLException;

public interface ItemDAO extends CrudDAO<Item, String, Connection> {
    boolean updateQtyOnHand(Connection connection, String id, int qty) throws SQLException, ClassNotFoundException;
}

