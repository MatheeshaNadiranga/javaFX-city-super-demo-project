package lk.acpt.citysuper.service;

import lk.acpt.citysuper.dto.Items;

import java.sql.SQLException;
import java.util.List;

public interface ItemService {
    boolean deleteItem(int id) throws SQLException, ClassNotFoundException;
    boolean addItem(Items item) throws SQLException, ClassNotFoundException;
    boolean updateItem(int id,String description,double price,int quantity) throws SQLException, ClassNotFoundException;
    List<Items> getAllItems() throws SQLException, ClassNotFoundException;
    Items searchItem(int id) throws SQLException, ClassNotFoundException;
}
