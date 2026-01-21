package lk.acpt.citysuper.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Items {
    int id;
    String description;
    double unitPrice;
    int quantity;
}
