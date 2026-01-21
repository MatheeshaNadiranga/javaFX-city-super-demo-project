package lk.acpt.citysuper.dto;

public class OrderDetails {

    private int item_id;
    private String description;
    private double price;
    private int qty;
    private double total;

    public OrderDetails(int item_id, String description, double price, int qty, double total) {
        this.item_id = item_id;
        this.description = description;
        this.price = price;
        this.qty = qty;
        this.total = total;
    }

    public int getItem_id() {
        return item_id;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getQty() {
        return qty;
    }

    public double getTotal() {
        return total;
    }
}

