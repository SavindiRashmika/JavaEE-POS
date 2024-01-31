package lk.ijse.gdse66.BackEnd.dao.custom;

import lk.ijse.gdse66.BackEnd.Entity.Customer;
import lk.ijse.gdse66.BackEnd.dao.CrudDAO;

import java.sql.Connection;
import java.sql.SQLException;

public interface CustomerDAO extends CrudDAO<Customer, String, Connection> {
}
