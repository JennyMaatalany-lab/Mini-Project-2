package cedarline.app.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * In-memory store for products (no database in Mini Project 2).
 * One shared instance so the data survives closing and reopening the window.
 */
public class ProductsStore {

    private static final ProductsStore INSTANCE = new ProductsStore();

    private final ObservableList<Product> products = FXCollections.observableArrayList();
    private int idCounter = 3000;

    private ProductsStore() {
        addProduct("Olive Oil 5L Tin", "Pantry", "Koura Olive Co.", 120, 38.50, 30, "Warehouse A / Shelf 1");
        addProduct("Cedar Wood Shelf Unit", "Furniture", "Bsharri Woodworks", 18, 145.00, 5, "Warehouse B / Bay 2");
        addProduct("LED Panel Light 60W", "Electrical", "LumaTech Lebanon", 260, 12.75, 50, "Warehouse A / Shelf 7");
        addProduct("Thermal Receipt Rolls (50 pack)", "Stationery", "PaperLine SARL", 90, 21.00, 20, "Warehouse A / Shelf 4");
        addProduct("Steel Storage Crate", "Warehouse", "MetalWorks Zouk", 54, 27.90, 15, "Yard / Row 2");
    }

    public static ProductsStore getInstance() {
        return INSTANCE;
    }

    public ObservableList<Product> getProductsList() {
        return products;
    }

    public Product addProduct(String name, String category, String supplier, int quantity, double unitPrice, int reorderLevel, String location) {
        Product product = new Product(nextId(), name, category, supplier, quantity, unitPrice, reorderLevel, location);
        products.add(product);
        return product;
    }

    public void deleteProduct(Product product) {
        if (product != null) {
            products.remove(product);
        }
    }

    public void updateProduct(Product product, String name, String category, String supplier, int quantity, double unitPrice, int reorderLevel, String location) {
        if (product != null) {
            product.setName(name);
            product.setCategory(category);
            product.setSupplier(supplier);
            product.setQuantity(quantity);
            product.setUnitPrice(unitPrice);
            product.setReorderLevel(reorderLevel);
            product.setLocation(location);
        }
    }

    private String nextId() {
        idCounter++;
        return "PRD-" + idCounter;
    }
}
