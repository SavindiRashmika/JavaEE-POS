package lk.ijse.gdse66.BackEnd.dao.custom;

import lk.ijse.gdse66.BackEnd.Entity.Item;
import lk.ijse.gdse66.BackEnd.dao.CrudDAO;

import java.sql.Connection;

public interface ItemDAO extends CrudDAO<Item, String, Connection> {
}
