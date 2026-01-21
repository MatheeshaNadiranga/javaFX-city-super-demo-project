package lk.acpt.citysuper.service;

import lk.acpt.citysuper.dto.Items;
import lk.acpt.citysuper.dto.Order;

import java.sql.SQLException;

public interface OrdersService {
    Items addOrder() throws SQLException, ClassNotFoundException;
    int getNextOrderId() throws SQLException, ClassNotFoundException;
    boolean placeOrder(Order order) throws SQLException, ClassNotFoundException;
}
