package lk.acpt.citysuper.service;

import lk.acpt.citysuper.dto.Customers;

import java.sql.SQLException;
import java.util.List;

public interface CustomerService {
    boolean addCustomer(Customers customers) throws SQLException, ClassNotFoundException;
    boolean updateCustomer(int id,String name,int phoneNumber,String address) throws SQLException, ClassNotFoundException;
    boolean deleteCustomer(int id) throws SQLException, ClassNotFoundException;
    List<Customers> getAllCustomers() throws SQLException, ClassNotFoundException;
    Customers searchCustomer(int id) throws SQLException, ClassNotFoundException;

}
