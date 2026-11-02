package cedarline.app.models;

import cedarline.app.util.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Product store backed by the Products table of the MySQL database (JDBC).
 * One shared instance, so every product window and its table use the same list.
 *
 * The ObservableList is a copy of the table kept in memory so the TableView can show it.
 * Every change is first written to the database; the list is only changed if that worked.
 */
public class ProductsStore {

    /** The database stores a number (3001, 3002...), the screen shows it as PRD-3001. */
    private static final String ID_PREFIX = "PRD-";

    private static final ProductsStore INSTANCE = new ProductsStore();

    private final ObservableList<Product> products = FXCollections.observableArrayList();

    private ProductsStore() {
    }

    public static ProductsStore getInstance() {
        return INSTANCE;
    }

    public ObservableList<Product> getProductsList() {
        return products;
    }

    /** SELECT: reloads every product from the database into the list. */
    public void loadProducts() throws SQLException {
        String sql = "SELECT product_id, name, category, supplier, quantity, unit_price, reorder_level, location FROM Products ORDER BY product_id";

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            products.clear();
            while (rs.next()) {
                products.add(new Product(
                        ID_PREFIX + rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getString("supplier"),
                        rs.getInt("quantity"),
                        rs.getDouble("unit_price"),
                        rs.getInt("reorder_level"),
                        rs.getString("location")));
            }
        }
    }

    /** INSERT: the database creates the id (AUTO_INCREMENT) and we read it back. */
    public Product addProduct(String name, String category, String supplier, int quantity, double unitPrice, int reorderLevel, String location) throws SQLException {
        String sql = "INSERT INTO Products (name, category, supplier, quantity, unit_price, reorder_level, location) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, name);
            stmt.setString(2, category);
            stmt.setString(3, supplier);
            stmt.setInt(4, quantity);
            stmt.setDouble(5, unitPrice);
            stmt.setInt(6, reorderLevel);
            stmt.setString(7, location);
            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                keys.next();
                Product product = new Product(ID_PREFIX + keys.getInt(1), name, category, supplier, quantity, unitPrice, reorderLevel, location);
                products.add(product);
                return product;
            }
        }
    }

    /** DELETE: removes the row from the database, then from the list. */
    public void deleteProduct(Product product) throws SQLException {
        if (product == null) {
            return;
        }
        String sql = "DELETE FROM Products WHERE product_id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, numericId(product));
            stmt.executeUpdate();
        }
        products.remove(product);
    }

    /** UPDATE: changes the row in the database, then the existing object (the table refreshes itself). */
    public void updateProduct(Product product, String name, String category, String supplier, int quantity, double unitPrice, int reorderLevel, String location) throws SQLException {
        if (product == null) {
            return;
        }
        String sql = "UPDATE Products SET name = ?, category = ?, supplier = ?, quantity = ?, unit_price = ?, reorder_level = ?, location = ? WHERE product_id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, category);
            stmt.setString(3, supplier);
            stmt.setInt(4, quantity);
            stmt.setDouble(5, unitPrice);
            stmt.setInt(6, reorderLevel);
            stmt.setString(7, location);
            stmt.setInt(8, numericId(product));
            stmt.executeUpdate();
        }

        product.setName(name);
        product.setCategory(category);
        product.setSupplier(supplier);
        product.setQuantity(quantity);
        product.setUnitPrice(unitPrice);
        product.setReorderLevel(reorderLevel);
        product.setLocation(location);
    }

    /** "PRD-3003" -> 3003 */
    private int numericId(Product product) {
        return Integer.parseInt(product.getSku().substring(ID_PREFIX.length()));
    }
}
