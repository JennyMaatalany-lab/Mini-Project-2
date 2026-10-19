package cedarline.app.models;

import javafx.beans.property.*;

public class Product {
    private final SimpleStringProperty sku;
    private final SimpleStringProperty name;
    private final SimpleStringProperty category;
    private final SimpleStringProperty supplier;
    private final SimpleIntegerProperty quantity;
    private final SimpleDoubleProperty unitPrice;
    private final SimpleIntegerProperty reorderLevel;
    private final SimpleStringProperty location;

    public Product(String sku, String name, String category, String supplier, int quantity, double unitPrice, int reorderLevel, String location) {
        this.sku = new SimpleStringProperty(sku);
        this.name = new SimpleStringProperty(name);
        this.category = new SimpleStringProperty(category);
        this.supplier = new SimpleStringProperty(supplier);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.unitPrice = new SimpleDoubleProperty(unitPrice);
        this.reorderLevel = new SimpleIntegerProperty(reorderLevel);
        this.location = new SimpleStringProperty(location);
    }

    public String getSku() {
        return sku.get();
    }

    public StringProperty skuProperty() {
        return sku;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getCategory() {
        return category.get();
    }

    public void setCategory(String category) {
        this.category.set(category);
    }

    public StringProperty categoryProperty() {
        return category;
    }

    public String getSupplier() {
        return supplier.get();
    }

    public void setSupplier(String supplier) {
        this.supplier.set(supplier);
    }

    public StringProperty supplierProperty() {
        return supplier;
    }

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice.get();
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice.set(unitPrice);
    }

    public DoubleProperty unitPriceProperty() {
        return unitPrice;
    }

    public int getReorderLevel() {
        return reorderLevel.get();
    }

    public void setReorderLevel(int reorderLevel) {
        this.reorderLevel.set(reorderLevel);
    }

    public IntegerProperty reorderLevelProperty() {
        return reorderLevel;
    }

    public String getLocation() {
        return location.get();
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public StringProperty locationProperty() {
        return location;
    }
}
