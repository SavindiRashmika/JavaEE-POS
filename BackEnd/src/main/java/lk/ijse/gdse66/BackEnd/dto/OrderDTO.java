package lk.ijse.gdse66.BackEnd.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;

@Data
public class OrderDTO {
    private String oid;
    private Date date;
    private String customerID;
    private double total;
    private double subTotal;
    private double discount;
    private ArrayList<OrderDetailDTO> orderDetail;

    public OrderDTO(String oid, Date date, String customerID, double total, double subTotal, double discount, ArrayList<OrderDetailDTO> orderDetail) {
        this.oid = oid;
        this.date = date;
        this.customerID = customerID;
        this.total = total;
        this.subTotal = subTotal;
        this.discount = discount;
        this.orderDetail = orderDetail;
    }

    public OrderDTO(String oid, Date date, String customerID, double total, double subTotal, double discount) {
        this.oid = oid;
        this.date = date;
        this.customerID = customerID;
        this.total = total;
        this.subTotal = subTotal;
        this.discount = discount;
    }
}
