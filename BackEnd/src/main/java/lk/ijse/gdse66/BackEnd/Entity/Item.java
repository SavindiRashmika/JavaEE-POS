package lk.ijse.gdse66.BackEnd.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private String code;
    private String description;
    private int qtyOnHand;
    private double unitPrice;
}
