package lk.acpt.citysuper.service.impl;

import lk.acpt.citysuper.db.DBConnection;
import lk.acpt.citysuper.dto.Customers;
import lk.acpt.citysuper.service.CustomerService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    @Override
    public boolean addCustomer(Customers customers) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        try {
            PreparedStatement stm =
                    connection.prepareStatement("insert into customers(id, name, address, contact) values(?, ?, ?, ?)");
            stm.setObject(1, customers.getId());
            stm.setObject(2, customers.getName());
            stm.setObject(3, customers.getAddress());
            stm.setObject(4, customers.getPhoneNumber());

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
    public boolean updateCustomer(int id, String name, int phoneNumber, String address) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();

        try {


            PreparedStatement stm =
                    connection.prepareStatement("update customers set name = ?, contact = ?, address = ? where id = ?");
            stm.setObject(1, name);
            stm.setObject(2, phoneNumber);
            stm.setObject(3, address);
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
    public boolean deleteCustomer(int id) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        try {


            PreparedStatement stm =
                    connection.prepareStatement("DELETE FROM customers WHERE id = ?");
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
    public List<Customers> getAllCustomers() throws SQLException, ClassNotFoundException {
        List<Customers> list = new ArrayList<>();
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement stm = connection.prepareStatement("SELECT * FROM customers");
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            Customers customers = new Customers(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("address"),
                    rs.getInt("contact")
            );
            list.add(customers);
        }
        return list;
    }

    @Override
    public Customers searchCustomer(int id) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        try {

            PreparedStatement stm =
                    connection.prepareStatement("SELECT * FROM customers WHERE id = ?");
            stm.setObject(1, id);
            ResultSet rslt = stm.executeQuery();
            if (rslt.next()) {
                return new Customers(
                        rslt.getInt("id"),
                        rslt.getString("name"),
                        rslt.getString("address"),
                        rslt.getInt("contact")
                );
            } else {
                return null;
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}

