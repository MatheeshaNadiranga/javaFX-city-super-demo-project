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
import lk.acpt.citysuper.dto.Items;
import lk.acpt.citysuper.service.ItemService;
import lk.acpt.citysuper.service.impl.ItemServiceImpl;

import java.sql.SQLException;
import java.util.List;

public class ItemsController {

    @FXML
    private TableColumn<Items, Integer> colItemId;
    @FXML
    private TableColumn<Items, String> colDescription;
    @FXML
    private TableColumn<Items, Double> colPrice;
    @FXML
    private TableColumn<Items, Integer> colQuantity;

    @FXML
    private TableView<Items> tblItems;
    @FXML
    private TextField searchInput;

    @FXML
    private TextField textID;
    @FXML
    private TextField textDescription;
    @FXML
    private TextField textPrice;
    @FXML
    private TextField txtQuantity;

    /* -------------------- SAVE -------------------- */
    @FXML
    public void initialize() throws SQLException, ClassNotFoundException {
        colItemId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        getAll();
    }
    @FXML
    void btnSave(ActionEvent event) throws SQLException, ClassNotFoundException {

        if (!validateInputs(true)) return;

        int id = Integer.parseInt(textID.getText().trim());
        double price = Double.parseDouble(textPrice.getText().trim());
        int quantity = Integer.parseInt(txtQuantity.getText().trim());

        Items item = new Items(
                id,
                textDescription.getText().trim(),
                price,
                quantity
        );

        ItemService service = new ItemServiceImpl();
        boolean success = service.addItem(item);

        showResult(success, "Item saved successfully", "Failed to save item");
        if (success) clearInputs();
    }

    /* -------------------- UPDATE -------------------- */

    @FXML
    void btnUpdate(ActionEvent event) throws SQLException, ClassNotFoundException {

        if (!validateInputs(true)) return;

        int id = Integer.parseInt(textID.getText().trim());
        double price = Double.parseDouble(textPrice.getText().trim());
        int quantity = Integer.parseInt(txtQuantity.getText().trim());

        ItemService service = new ItemServiceImpl();
        boolean success = service.updateItem(
                id,
                textDescription.getText().trim(),
                price,
                quantity
        );

        showResult(success, "Item updated successfully", "Failed to update item");
        if (success) clearInputs();
    }

    /* -------------------- DELETE -------------------- */

    @FXML
    void btnDelete(ActionEvent event) throws SQLException, ClassNotFoundException {

        if (searchInput.getText().isBlank()) {
            showError("Enter Item ID to delete");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(searchInput.getText().trim());
        } catch (NumberFormatException e) {
            showError("Item ID must be numeric");
            return;
        }

        ItemService service = new ItemServiceImpl();
        boolean success = service.deleteItem(id);

        showResult(success, "Item deleted successfully", "Failed to delete item");
        if (success) clearInputs();
    }

    /* -------------------- SEARCH -------------------- */

    @FXML
    void search(ActionEvent event) throws SQLException, ClassNotFoundException {

        if (searchInput.getText().isBlank()) {
            showError("Enter Item ID to search");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(searchInput.getText().trim());
        } catch (NumberFormatException e) {
            showError("Item ID must be numeric");
            return;
        }

        ItemService service = new ItemServiceImpl();
        Items item = service.searchItem(id);

        if (item != null) {
            textID.setText(String.valueOf(item.getId()));
            textDescription.setText(item.getDescription());
            textPrice.setText(String.valueOf(item.getUnitPrice()));
            txtQuantity.setText(String.valueOf(item.getQuantity()));
        } else {
            clearInputs();
            showError("No item found");
        }
    }

    /* -------------------- UTILITIES -------------------- */

    private boolean validateInputs(boolean requireId) {

        if (requireId && textID.getText().isBlank()) {
            showError("Item ID is required");
            return false;
        }
        if (textDescription.getText().isBlank()) {
            showError("Description is required");
            return false;
        }
        if (textPrice.getText().isBlank()) {
            showError("Price is required");
            return false;
        }
        if (txtQuantity.getText().isBlank()) {
            showError("Quantity is required");
            return false;
        }

        try {
            if (requireId) Integer.parseInt(textID.getText().trim());
            Double.parseDouble(textPrice.getText().trim());
            Integer.parseInt(txtQuantity.getText().trim());
        } catch (NumberFormatException e) {
            showError("Invalid numeric values");
            return false;
        }

        return true;
    }

    private void clearInputs() {
        textID.clear();
        textDescription.clear();
        textPrice.clear();
        txtQuantity.clear();
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
    public void getAll() throws SQLException, ClassNotFoundException {
        ItemService service = new ItemServiceImpl();
        List<Items> item = service.getAllItems();
        ObservableList<Items> items = FXCollections.observableArrayList(item);
        tblItems.setItems(items);

    }
}
