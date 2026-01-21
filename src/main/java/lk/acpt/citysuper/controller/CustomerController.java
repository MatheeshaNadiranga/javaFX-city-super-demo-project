package lk.acpt.citysuper.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.acpt.citysuper.dto.Customers;
import lk.acpt.citysuper.service.CustomerService;
import lk.acpt.citysuper.service.impl.CustomerServiceImpl;

import java.sql.SQLException;
import java.util.List;

public class CustomerController {

    @FXML private TableColumn<Customers, String> colAddress;
    @FXML private TableColumn<Customers, Integer> colCustomerId;
    @FXML private TableColumn<Customers, String> colName;
    @FXML private TableColumn<Customers, Integer> colPhoneNumber;

    @FXML private TextField searchInput;
    @FXML private TableView<Customers> tblCustomers;

    @FXML private TextField txtAddress;
    @FXML private TextField txtCustomerId;
    @FXML private TextField txtName;
    @FXML private TextField txtPhoneNumber;


    @FXML
    public void initialize() throws SQLException, ClassNotFoundException {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        getAllCustomers();

    }
    @FXML
    void btnSave(ActionEvent event) throws SQLException, ClassNotFoundException {

        if (!validateInputs(true)) return;

        int id = Integer.parseInt(txtCustomerId.getText().trim());
        int phone = Integer.parseInt(txtPhoneNumber.getText().trim());

        Customers customers = new Customers(
                id,
                txtName.getText().trim(),
                txtAddress.getText().trim(),
                phone
        );

        CustomerService service = new CustomerServiceImpl();
        boolean success = service.addCustomer(customers);

        showResult(success, "Customer saved successfully", "Failed to save customer");
        if (success) clearInputs();
    }

    /* -------------------- UPDATE -------------------- */

    @FXML
    void btnUpdate(ActionEvent event) throws SQLException, ClassNotFoundException {

        if (!validateInputs(true)) return;

        int id = Integer.parseInt(txtCustomerId.getText().trim());
        int phone = Integer.parseInt(txtPhoneNumber.getText().trim());

        CustomerService service = new CustomerServiceImpl();
        boolean success = service.updateCustomer(
                id,
                txtName.getText().trim(),
                phone,
                txtAddress.getText().trim()
        );

        showResult(success, "Updated successfully!", "Update failed");
        if (success) clearInputs();
    }

    /* -------------------- DELETE -------------------- */

    @FXML
    void btnDelete(ActionEvent event) throws SQLException, ClassNotFoundException {

        if (searchInput.getText().isBlank()) {
            showError("Please enter Customer ID to delete");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(searchInput.getText().trim());
        } catch (NumberFormatException e) {
            showError("Customer ID must be numeric");
            return;
        }

        CustomerService service = new CustomerServiceImpl();
        boolean success = service.deleteCustomer(id);

        showResult(success, "Customer deleted", "Delete failed");
        if (success) clearInputs();
    }

    /* -------------------- SEARCH -------------------- */

    @FXML
    void search(ActionEvent event) throws SQLException, ClassNotFoundException {

        if (searchInput.getText().isBlank()) {
            showError("Enter Customer ID to search");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(searchInput.getText().trim());
        } catch (NumberFormatException e) {
            showError("Customer ID must be numeric");
            return;
        }

        CustomerService service = new CustomerServiceImpl();
        Customers customer = service.searchCustomer(id);

        if (customer != null) {
            txtCustomerId.setText(String.valueOf(customer.getId()));
            txtName.setText(customer.getName());
            txtAddress.setText(customer.getAddress());
            txtPhoneNumber.setText(String.valueOf(customer.getPhoneNumber()));
        } else {
            clearInputs();
            showError("No customer found");
        }
    }

    /* -------------------- UTILITIES -------------------- */

    private boolean validateInputs(boolean requireId) {

        if (requireId && txtCustomerId.getText().isBlank()) {
            showError("Customer ID is required");
            return false;
        }
        if (txtName.getText().isBlank()) {
            showError("Name is required");
            return false;
        }
        if (txtPhoneNumber.getText().isBlank()) {
            showError("Phone number is required");
            return false;
        }

        try {
            if (requireId) Integer.parseInt(txtCustomerId.getText().trim());
            Integer.parseInt(txtPhoneNumber.getText().trim());
        } catch (NumberFormatException e) {
            showError("ID and Phone Number must be numeric");
            return false;
        }

        return true;
    }

    private void clearInputs() {
        txtCustomerId.clear();
        txtName.clear();
        txtAddress.clear();
        txtPhoneNumber.clear();
        searchInput.clear();
    }

    private void showResult(boolean success, String okMsg, String failMsg) {
        Alert alert = new Alert(success ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
        alert.setTitle(success ? "Success" : "Error");
        alert.setHeaderText(null);
        alert.setContentText(success ? okMsg : failMsg);
        alert.showAndWait();
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
    public void getAllCustomers() throws SQLException, ClassNotFoundException {

        CustomerService service = new CustomerServiceImpl();
        List<Customers> customer = service.getAllCustomers();
        ObservableList<Customers> observableList = FXCollections.observableArrayList(customer);
        tblCustomers.setItems(observableList);
    }


}
