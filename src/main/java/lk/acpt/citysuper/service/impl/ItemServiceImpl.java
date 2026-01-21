package lk.acpt.citysuper.service.impl;

import lk.acpt.citysuper.db.DBConnection;
import lk.acpt.citysuper.dto.Customers;
import lk.acpt.citysuper.dto.Items;
import lk.acpt.citysuper.service.ItemService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemServiceImpl implements ItemService {
    @Override
    public boolean deleteItem(int id) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        try {


            PreparedStatement stm =
                    connection.prepareStatement("DELETE FROM items WHERE id = ?");
            stm.setObject(1, id);

            int i = stm.executeUpdate();

            if (i > 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public boolean addItem(Items item) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        try {
            PreparedStatement stm =
                    connection.prepareStatement("insert into items(id, description, price, quantity) values(?, ?, ?, ?)");
            stm.setObject(1, item.getId());
            stm.setObject(2, item.getDescription());
            stm.setObject(3, item.getUnitPrice());
            stm.setObject(4, item.getQuantity());

            int i = stm.executeUpdate();

            if (i > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            return false;
        }

    }

    @Override
    public boolean updateItem(int id, String description, double price, int quantity) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();

        try {


            PreparedStatement stm =
                    connection.prepareStatement("update items set description = ?, price = ?, quantity = ? where id = ?");
            stm.setObject(1, description);
            stm.setObject(2, price);
            stm.setObject(3,quantity);
            stm.setObject(4, id);

            int i = stm.executeUpdate();

            if (i > 0) {
                return true;
            } else {
                return false;
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Items> getAllItems() throws SQLException, ClassNotFoundException {
        List<Items> list = new ArrayList<>();
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement stm = connection.prepareStatement("SELECT * FROM items");
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            Items item = new Items(
                    rs.getInt("id"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getInt("quantity")
            );
            list.add(item);
        }

        return list;
    }

    @Override
    public Items searchItem(int id) throws SQLException, ClassNotFoundException {

        Connection connection = DBConnection.getDbConnection().getConnection();
        try {

            PreparedStatement stm =
                    connection.prepareStatement("SELECT * FROM items WHERE id = ?");
            stm.setObject(1, id);
            ResultSet rslt = stm.executeQuery();
            if (rslt.next()) {
                return new Items(
                        rslt.getInt("id"),
                        rslt.getString("description"),
                        rslt.getDouble("price"),
                        rslt.getInt("quantity")
                );
            } else {
                return null;
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
