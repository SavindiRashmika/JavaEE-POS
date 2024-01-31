package lk.ijse.gdse66.BackEnd.bo.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.gdse66.BackEnd.Entity.Customer;
import lk.ijse.gdse66.BackEnd.bo.custom.CustomerBO;
import lk.ijse.gdse66.BackEnd.dao.custom.CustomerDAO;
import lk.ijse.gdse66.BackEnd.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.gdse66.BackEnd.dto.CustomerDTO;

import java.sql.Connection;
import java.sql.SQLException;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = new CustomerDAOImpl();

    @Override
    public boolean addCustomer(Connection connection, CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        Customer customer = new Customer(
                customerDTO.getId(), customerDTO.getName(), customerDTO.getAddress()
        );
        return customerDAO.add(customer, connection);
    }

    @Override
    public ObservableList<CustomerDTO> getAllCustomer(Connection connection) throws SQLException, ClassNotFoundException {
        ObservableList<Customer> customers = customerDAO.getAll(connection);
        ObservableList<CustomerDTO> cusList = FXCollections.observableArrayList();

        for (Customer temp: customers) {
            CustomerDTO customerDTO = new CustomerDTO(
                    temp.getId(),
                    temp.getName(),
                    temp.getAddress()
            );
            cusList.add(customerDTO);
        }
        return cusList;
    }

    @Override
    public boolean updateCustomer(Connection connection, CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        Customer customer = new Customer(
                customerDTO.getId(),customerDTO.getName(),customerDTO.getAddress()

        );
        return customerDAO.update(customer,connection);
    }

    @Override
    public boolean deleteCustomer(Connection connection, String custId) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(custId, connection);
    }
}
