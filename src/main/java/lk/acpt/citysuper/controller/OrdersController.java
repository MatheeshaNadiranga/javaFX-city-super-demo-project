package lk.acpt.citysuper.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.acpt.citysuper.dto.Customers;
import lk.acpt.citysuper.dto.Items;
import lk.acpt.citysuper.dto.Order;
import lk.acpt.citysuper.dto.OrderDetails;
import lk.acpt.citysuper.service.CustomerService;
import lk.acpt.citysuper.service.ItemService;
import lk.acpt.citysuper.service.OrdersService;
import lk.acpt.citysuper.service.impl.CustomerServiceImpl;
import lk.acpt.citysuper.service.impl.ItemServiceImpl;
import lk.acpt.citysuper.service.impl.OrdersServiceImpl;

import java.sql.SQLException;
import java.time.LocalDate;

public class OrdersController {
    @FXML
    private TableColumn<OrderDetails, Integer> colItemId;

    @FXML
    private TableColumn<OrderDetails, String> colItemName;

    @FXML
    private TableColumn<OrderDetails, Integer> colQty;

    @FXML
    private TableColumn<OrderDetails, Double> colTotal;

    @FXML
    private TableColumn<OrderDetails, Double> colUnitPrice;

    @FXML
    private Label lblNetTotal;

    @FXML
    private TableView<OrderDetails> tblOrderItems;

    @FXML
    private TextField txtCustomerId;

    @FXML
    private TextField txtCustomerName;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtItemId;

    @FXML
    private TextField txtItemName;

    @FXML
    private TextField txtOrderId;

    @FXML
    private TextField txtOrderQty;

    @FXML
    private TextField txtStockQty;

    @FXML
    private TextField txtUnitPrice;
    ObservableList<OrderDetails> observableList = FXCollections.observableArrayList();
    OrdersService ordersService = new OrdersServiceImpl();


    void nextId() throws SQLException, ClassNotFoundException {
        int order_id = ordersService.getNextOrderId();
        txtOrderId.setText(String.valueOf(order_id));
    }
    @FXML
    public void initialize() throws SQLException, ClassNotFoundException {
        colItemId.setCellValueFactory(new PropertyValueFactory<>("item_id"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        nextId();

    }

    @FXML
    void btnAdd(ActionEvent event) throws SQLException, ClassNotFoundException {

        int qty = Integer.parseInt(txtOrderQty.getText());
        int id = Integer.parseInt(txtItemId.getText());
        int stck = Integer.parseInt(txtStockQty.getText());
        if (qty <= stck) {
            String name = txtItemName.getText();
            double price = Double.parseDouble(txtUnitPrice.getText());
            double total = price * qty;

            observableList.add(new OrderDetails(id, name, price, qty, total));
            tblOrderItems.setItems(observableList);
            netTotal();

        }else{

        }

    }

    @FXML
    void btnPlaceOrder(ActionEvent event) throws SQLException, ClassNotFoundException {

        if (txtCustomerId.getText().isEmpty()) {
            System.out.println("Please select a customer before placing order.");
            return;
        }

        int customerId = Integer.parseInt(txtCustomerId.getText());


        if (observableList.isEmpty()) {
            System.out.println("No items in the order.");
            return;
        }


        Order order = new Order();
        order.setOrderId(Integer.parseInt(txtOrderId.getText()));
        order.setCustomerId(customerId);
        order.setOrderDate(String.valueOf(LocalDate.parse(txtDate.getText())));
        order.setOrderDetail(FXCollections.observableArrayList(observableList));
        double total = netTotal();
        order.setTotalPrice(total);

        OrdersService ordersService = new OrdersServiceImpl();


        boolean success = ordersService.placeOrder(order);

        if (success) {
            System.out.println("Order placed successfully!");
            observableList.clear();
            tblOrderItems.refresh();
            netTotal();
            nextId();
        } else {
            System.out.println("Failed to place order.");
        }
    }


    @FXML
    void btnSearchCustomer(ActionEvent event) throws SQLException, ClassNotFoundException {
        int id = Integer.parseInt(txtCustomerId.getText());
        CustomerService customerService = new CustomerServiceImpl();
        Customers customer = customerService.searchCustomer(id);
        txtCustomerName.setText(customer.getName());
        LocalDate today = LocalDate.now();
        txtDate.setText(today.toString());
    }

    @FXML
    void btnSearchItem(ActionEvent event) throws SQLException, ClassNotFoundException {
        int id = Integer.parseInt(txtItemId.getText());
        ItemService itemService = new ItemServiceImpl();
        Items item = itemService.searchItem(id);
        txtItemName.setText(item.getDescription());
        txtStockQty.setText(String.valueOf(item.getQuantity()));
        txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
    }
    double netTotal() {
        double netTotal = 0;
        for (OrderDetails orderDetails : observableList) {
            netTotal += orderDetails.getTotal();
        }
        lblNetTotal.setText(String.valueOf(netTotal));
        return  netTotal;
    }
}
