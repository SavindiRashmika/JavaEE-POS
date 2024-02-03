package lk.ijse.gdse66.BackEnd.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private String oid;
    private Date date;
    private String customerID;
    private double total;
    private double subTotal;
    private double discount;
    private ArrayList<OrderDetailDTO> orderDetail;
}
