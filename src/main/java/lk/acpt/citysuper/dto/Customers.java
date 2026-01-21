package lk.acpt.citysuper.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customers {
    int id;
    String name;
    String address;
    int phoneNumber;


}
