package lk.ijse.gdse66.BackEnd.dao.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.gdse66.BackEnd.Entity.Customer;
import lk.ijse.gdse66.BackEnd.dao.CrudUtil;
import lk.ijse.gdse66.BackEnd.dao.custom.CustomerDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public boolean add(Customer customer, Connection connection) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(connection,"INSERT INTO company.customer(id,name,address) VALUES(?,?,?)",customer.getId(),
                customer.getName(),customer.getAddress());
    }

    @Override
    public boolean delete(String id, Connection connection) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(connection,"DELETE FROM Customer WHERE id=?", id);
    }

    @Override
    public ObservableList<Customer> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery(connection, "SELECT * FROM Customer");

        ObservableList<Customer> obList = FXCollections.observableArrayList();

        while (resultSet.next()) {
            Customer customer = new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            );

            obList.add(customer);
        }

        return obList;
    }

    @Override
    public boolean update(Customer customer, Connection connection) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(connection, "UPDATE company.customer SET name=?,address=? WHERE id=?",
                customer.getName(),
                customer.getAddress(),
                customer.getId());

    }

    @Override
    public Customer search(String s, Connection connection) throws SQLException, ClassNotFoundException {
        return null;
    }
}
