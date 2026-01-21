package lk.acpt.citysuper.service.impl;

import lk.acpt.citysuper.db.DBConnection;
import lk.acpt.citysuper.dto.Items;
import lk.acpt.citysuper.dto.Order;
import lk.acpt.citysuper.dto.OrderDetails;
import lk.acpt.citysuper.service.OrdersService;

import java.sql.*;

public class OrdersServiceImpl implements OrdersService {

    @Override
    public Items addOrder() throws SQLException, ClassNotFoundException {

        return null;
    }

    @Override
    public int getNextOrderId() throws SQLException, ClassNotFoundException {
        ResultSet rst =  DBConnection.getDbConnection().getConnection().prepareStatement("SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1").executeQuery();
        if (rst.next()) {
            int id = rst.getInt(1);
            return id+1;
        }
        return 1;

    }

    @Override
    public boolean placeOrder(Order dto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        try {
            connection.setAutoCommit(false);

            System.out.println(dto.getTotalPrice());

            // 1. Insert order
            PreparedStatement stm1 = connection.prepareStatement(
                    "INSERT INTO orders(order_date, cus_id, total, order_id) VALUES(?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            stm1.setObject(1, dto.getOrderDate());
            stm1.setObject(2, dto.getCustomerId()); // must not be null
            stm1.setObject(3, dto.getTotalPrice());
            stm1.setObject(4, dto.getOrderId());

            if (stm1.executeUpdate() > 0) {
                ResultSet keys = stm1.getGeneratedKeys();
                int orderId = dto.getOrderId(); // use existing or generated
                if (keys.next()) {
                    orderId = keys.getInt(1);
                }

                // 2. Insert order details and update stock
                for (OrderDetails detail : dto.getOrderDetail()) {
                    PreparedStatement stm2 = connection.prepareStatement(
                            "INSERT INTO order_details(order_id, item_id, quantity, unit_price) VALUES(?,?,?,?)"
                    );
                    stm2.setObject(1, orderId);          // order_id FK
                    stm2.setObject(2, detail.getItem_id());
                    stm2.setObject(3, detail.getQty());
                    stm2.setObject(4, detail.getPrice());

                    if (stm2.executeUpdate() <= 0) {
                        connection.rollback();
                        return false;
                    }

                    // Update stock
                    PreparedStatement stm3 = connection.prepareStatement(
                            "UPDATE items SET quantity = quantity - ? WHERE id = ?"
                    );
                    stm3.setObject(1, detail.getQty());
                    stm3.setObject(2, detail.getItem_id());

                    if (stm3.executeUpdate() <= 0) {
                        connection.rollback();
                        return false;
                    }
                }


                connection.commit();
                return true;
            }

            connection.rollback();
            return false;

        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }


}
