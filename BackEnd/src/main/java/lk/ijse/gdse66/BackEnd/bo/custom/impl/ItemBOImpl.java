package lk.ijse.gdse66.BackEnd.bo.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.gdse66.BackEnd.Entity.Item;
import lk.ijse.gdse66.BackEnd.bo.custom.ItemBO;
import lk.ijse.gdse66.BackEnd.dao.custom.ItemDAO;
import lk.ijse.gdse66.BackEnd.dao.custom.impl.ItemDAOImpl;
import lk.ijse.gdse66.BackEnd.dto.ItemDTO;

import java.sql.Connection;
import java.sql.SQLException;

public class ItemBOImpl implements ItemBO {

    ItemDAO itemDAO = new ItemDAOImpl();

    @Override
    public boolean addItem(Connection connection, ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        Item item = new Item(
                itemDTO.getCode(),itemDTO.getDescription(),itemDTO.getQtyOnHand(),itemDTO.getUnitPrice()

        );
        return itemDAO.add(item,connection);
    }

    @Override
    public ObservableList<ItemDTO> getAllItem(Connection connection) throws SQLException, ClassNotFoundException {
        ObservableList<Item> items = itemDAO.getAll(connection);

        ObservableList<ItemDTO> obList = FXCollections.observableArrayList();

        for (Item temp : items) {
            ItemDTO itemDTO = new ItemDTO(
                    temp.getCode(),temp.getDescription(),temp.getQtyOnHand(),temp.getUnitPrice()
            );

            obList.add(itemDTO);
        }
        return obList;
    }

    @Override
    public boolean updateItem(Connection connection, ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        Item item = new Item(
                itemDTO.getCode(),itemDTO.getDescription(),itemDTO.getQtyOnHand(),itemDTO.getUnitPrice()

        );
        return itemDAO.update(item,connection);

    }

    @Override
    public boolean deleteItem(Connection connection, String id) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(id, connection);
    }

}
