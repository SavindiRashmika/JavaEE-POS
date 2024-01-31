package lk.ijse.gdse66.BackEnd.bo.custom;

import javafx.collections.ObservableList;
import lk.ijse.gdse66.BackEnd.bo.SuperBO;
import lk.ijse.gdse66.BackEnd.dto.CustomerDTO;

import java.sql.Connection;
import java.sql.SQLException;

public interface CustomerBO extends SuperBO {

      boolean addCustomer(Connection connection, CustomerDTO customerDTO) throws SQLException, ClassNotFoundException;

      ObservableList<CustomerDTO> getAllCustomer(Connection connection) throws SQLException, ClassNotFoundException;

      boolean updateCustomer(Connection connection, CustomerDTO customerDTO) throws SQLException, ClassNotFoundException;

      boolean deleteCustomer(Connection connection, String custId) throws SQLException, ClassNotFoundException;
}
