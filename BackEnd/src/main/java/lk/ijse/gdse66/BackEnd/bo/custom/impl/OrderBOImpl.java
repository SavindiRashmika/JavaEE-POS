package lk.ijse.gdse66.BackEnd.bo.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.gdse66.BackEnd.Entity.Item;
import lk.ijse.gdse66.BackEnd.Entity.Order;
import lk.ijse.gdse66.BackEnd.Entity.OrderDetails;
import lk.ijse.gdse66.BackEnd.bo.custom.OrderBO;
import lk.ijse.gdse66.BackEnd.dao.DAOFactory;
import lk.ijse.gdse66.BackEnd.dao.custom.ItemDAO;
import lk.ijse.gdse66.BackEnd.dao.custom.OrderDAO;
import lk.ijse.gdse66.BackEnd.dao.custom.OrderDetailsDAO;
import lk.ijse.gdse66.BackEnd.dao.custom.impl.ItemDAOImpl;
import lk.ijse.gdse66.BackEnd.dao.custom.impl.OrderDAOImpl;
import lk.ijse.gdse66.BackEnd.dao.custom.impl.OrderDetailsDAOImpl;
import lk.ijse.gdse66.BackEnd.dto.ItemDTO;
import lk.ijse.gdse66.BackEnd.dto.OrderDTO;
import lk.ijse.gdse66.BackEnd.dto.OrderDetailDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderBOImpl implements OrderBO {

    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERS);
    OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAILS);
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public String generateNewOrderId(Connection connection) throws SQLException, ClassNotFoundException {
        return orderDAO.generateNewOrderId(connection);
    }

    @Override
    public ObservableList<OrderDTO> getAllOrders(Connection connection) throws SQLException, ClassNotFoundException {
        ObservableList<Order> orders = orderDAO.getAll(connection);
        ObservableList<OrderDTO> obList = FXCollections.observableArrayList();

        for (Order temp : orders) {
            OrderDTO ordersDTO = new OrderDTO(
                    temp.getOid(),
                    temp.getDate(),
                    temp.getCustomerID(),
                    temp.getTotal(),
                    temp.getSubTotal(),
                    temp.getDiscount()
            );

            obList.add(ordersDTO);
        }
        return obList;
    }

    @Override
    public ObservableList<OrderDetailDTO> getAllOrdersDetails(Connection connection) throws SQLException, ClassNotFoundException {
        ObservableList<OrderDetails> orderDetail = orderDetailsDAO.getAll(connection);
        ObservableList<OrderDetailDTO> obList = FXCollections.observableArrayList();

        for (OrderDetails temp : orderDetail) {
            OrderDetailDTO ordersDetailDTO = new OrderDetailDTO(
                    temp.getOid(),
                    temp.getItemCode(),
                    temp.getQty(),
                    temp.getUnitPrice(),
                    temp.getTotal()
            );

            obList.add(ordersDetailDTO);
        }
        return obList;
    }


    @Override
    public boolean saveOrderDetail(Connection connection, OrderDetailDTO orderDetailDTO) throws SQLException, ClassNotFoundException {
        OrderDetails orderDetails = new OrderDetails(
                orderDetailDTO.getOid(),
                orderDetailDTO.getItemCode(),
                orderDetailDTO.getQty(),
                orderDetailDTO.getUnitPrice(),
                orderDetailDTO.getTotal()
        );

        boolean isOrderDetailsAdded = orderDetailsDAO.add(orderDetails, connection);

        if (!isOrderDetailsAdded) {
            System.out.println("Failed to save OrderDetails to the database.");
            return false;
        }
        return true;
    }




    @Override
    public boolean saveOrder(Connection connection, OrderDTO ordersDTO) throws SQLException, ClassNotFoundException {
        try {
            connection.setAutoCommit(false);

            Order orders = new Order(
                    ordersDTO.getOid(),
                    ordersDTO.getDate(),
                    ordersDTO.getCustomerID(),
                    ordersDTO.getTotal(),
                    ordersDTO.getSubTotal(),
                    ordersDTO.getDiscount()
            );

            boolean isOrderAdded = orderDAO.add(orders, connection);

            if (isOrderAdded) {
                for (OrderDetailDTO odDTO : ordersDTO.getOrderDetail()) {
                    OrderDetails orderDetails = new OrderDetails(
                            odDTO.getOid(),
                            odDTO.getItemCode(),
                            odDTO.getQty(),
                            odDTO.getUnitPrice(),
                            odDTO.getTotal()
                    );

                    boolean isOrderDetailsAdded = orderDetailsDAO.add(orderDetails, connection);

                    if (!isOrderDetailsAdded) {
                        connection.rollback();
                        System.out.println("Failed to save OrderDetails to the database.");
                        return false;
                    }

                    Item item = itemDAO.search(orderDetails.getItemCode(), connection);
                    item.setQtyOnHand(item.getQtyOnHand() - orderDetails.getQty());

                    boolean isItemUpdated = itemDAO.update(new Item(
                            item.getCode(),
                            item.getDescription(),
                            item.getQtyOnHand(),
                            item.getUnitPrice()
                    ), connection);

                    if (!isItemUpdated) {
                        connection.rollback();
                        System.out.println("Failed to update Item quantity on hand.");
                        return false;
                    }
                }
                connection.commit();
            } else {
                connection.rollback();
                System.out.println("Failed to save Order to the database.");
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            connection.rollback();
            e.printStackTrace();
            System.out.println("Exception occurred: " + e.getMessage());
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
        return true;
    }



    @Override
    public boolean updateQtyOnHand(Connection connection, String id, int qty) throws SQLException, ClassNotFoundException {
        return itemDAO.updateQtyOnHand(connection, id, qty);
    }

    @Override
    public ObservableList<OrderDetailDTO> getAllOrderDetails(Connection connection) throws SQLException, ClassNotFoundException {
        ObservableList<OrderDetails> orderDetails = orderDetailsDAO.getAll(connection);

        ObservableList<OrderDetailDTO> obList = FXCollections.observableArrayList();

        for (OrderDetails temp : orderDetails) {
            OrderDetailDTO orderDetailsDTO = new OrderDetailDTO(
                    temp.getOid(), temp.getItemCode(), temp.getQty(), temp.getUnitPrice(), temp.getTotal()
            );

            obList.add(orderDetailsDTO);
        }
        return obList;
    }

    @Override
    public ArrayList<OrderDetailDTO> searchOrderDetails(String orderId, Connection connection) throws SQLException, ClassNotFoundException {
       /* ArrayList<OrderDetails> orderDetails = orderDetailsDAO.searchOrderDetail(orderId, connection);
        ArrayList<OrderDetailDTO> orderDetailDTOS = new ArrayList<>();
        for (OrderDetails orderDetail : orderDetails) {
            orderDetailDTOS.add(new OrderDetailDTO(
                    orderDetail.getOid(),
                    orderDetail.getItemCode(),
                    orderDetail.getQty(),
                    orderDetail.getUnitPrice(),
                    orderDetail.getTotal()
            ));
        }*/
//        return orderDetailDTOS;
        return null;
    }
}
