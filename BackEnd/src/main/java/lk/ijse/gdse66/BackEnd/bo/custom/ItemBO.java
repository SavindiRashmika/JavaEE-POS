package lk.ijse.gdse66.BackEnd.bo.custom;

import javafx.collections.ObservableList;
import lk.ijse.gdse66.BackEnd.bo.SuperBO;
import lk.ijse.gdse66.BackEnd.dto.ItemDTO;

import java.sql.Connection;
import java.sql.SQLException;

public interface ItemBO extends SuperBO {
    boolean addItem(Connection connection, ItemDTO itemDTO) throws SQLException, ClassNotFoundException;

    ObservableList<ItemDTO> getAllItem(Connection connection) throws SQLException, ClassNotFoundException;

    boolean updateItem(Connection connection, ItemDTO itemDTO) throws SQLException, ClassNotFoundException;

    boolean deleteItem(Connection connection, String id) throws SQLException, ClassNotFoundException;
}
