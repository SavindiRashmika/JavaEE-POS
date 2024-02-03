package lk.ijse.gdse66.BackEnd.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDTO {

    private String oid;
    private String itemCode;
    private int qty;
    private double unitPrice;
    private double total;
}
