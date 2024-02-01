package lk.ijse.gdse66.BackEnd.dao.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.gdse66.BackEnd.Entity.Customer;
import lk.ijse.gdse66.BackEnd.Entity.Item;
import lk.ijse.gdse66.BackEnd.dao.CrudUtil;
import lk.ijse.gdse66.BackEnd.dao.custom.ItemDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public boolean delete(String code, Connection connection) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(connection,"DELETE FROM company.item WHERE code=?",code);
    }

    @Override
    public ObservableList<Item> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery(connection, "SELECT * FROM company.item");

        ObservableList<Item> obList = FXCollections.observableArrayList();

        while (resultSet.next()) {
            Item item = new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getDouble(4)
            );

            obList.add(item);
        }

        return obList;
    }

    @Override
    public boolean update(Item item, Connection connection) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(connection, "UPDATE company.item SET description=?,qtyOnHand=?,unitPrice=? WHERE code=?",item.getDescription(),
                item.getQtyOnHand(),item.getUnitPrice(),item.getCode());

    }

    @Override
    public boolean add(Item item, Connection connection) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(connection,"INSERT INTO company.item VALUES(?,?,?,?)",item.getCode(),
                item.getDescription(),item.getQtyOnHand(),item.getUnitPrice());
    }
}
