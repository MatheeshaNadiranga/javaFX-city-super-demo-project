package lk.acpt.citysuper.service;

import lk.acpt.citysuper.dto.Items;
import lk.acpt.citysuper.dto.Order;

import java.sql.SQLException;

public interface OrdersService {

    int getNextOrderId() throws SQLException, ClassNotFoundException;
    boolean placeOrder(Order order) throws SQLException, ClassNotFoundException;
}
