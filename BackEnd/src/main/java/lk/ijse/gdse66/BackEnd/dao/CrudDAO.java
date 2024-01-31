package lk.ijse.gdse66.BackEnd.dao;

import javafx.collections.ObservableList;
import lk.ijse.gdse66.BackEnd.Entity.Customer;

import java.sql.Connection;
import java.sql.SQLException;

public interface CrudDAO <T, ID,C> extends SuperDAO {

    boolean add(T t, Connection connection) throws SQLException, ClassNotFoundException;

    boolean delete(ID id, Connection connection) throws SQLException, ClassNotFoundException;

    ObservableList<Customer> getAll(Connection connection) throws SQLException, ClassNotFoundException;

    boolean update(Customer customer, Connection connection) throws SQLException, ClassNotFoundException;
}