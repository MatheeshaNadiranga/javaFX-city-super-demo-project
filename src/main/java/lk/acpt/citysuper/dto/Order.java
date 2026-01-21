package lk.acpt.citysuper.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    int orderId;
    int customerId;
    String orderDate;
    double totalPrice;
    List<OrderDetails> orderDetail;
}
