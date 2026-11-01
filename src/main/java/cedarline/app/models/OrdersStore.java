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
 * Order store backed by the Orders table of the MySQL database (JDBC).
 * One shared instance, so every order window and its table use the same list.
 *
 * The ObservableList is a copy of the table kept in memory so the TableView can show it.
 * Every change is first written to the database; the list is only changed if that worked.
 */
public class OrdersStore {

    /** The database stores a number (5001, 5002...), the screen shows it as ORD-5001. */
    private static final String ID_PREFIX = "ORD-";

    private static final OrdersStore INSTANCE = new OrdersStore();

    private final ObservableList<Order> orders = FXCollections.observableArrayList();

    private OrdersStore() {
    }

    public static OrdersStore getInstance() {
        return INSTANCE;
    }

    public ObservableList<Order> getOrdersList() {
        return orders;
    }

    /** SELECT: reloads every order from the database into the list. */
    public void loadOrders() throws SQLException {
        String sql = "SELECT order_id, client_name, product_name, quantity, price, destination, order_date, status FROM Orders ORDER BY order_id";

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            orders.clear();
            while (rs.next()) {
                orders.add(new Order(
                        ID_PREFIX + rs.getInt("order_id"),
                        rs.getString("client_name"),
                        rs.getString("product_name"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getString("destination"),
                        rs.getDate("order_date").toString(),
                        rs.getString("status")));
            }
        }
    }

    /** INSERT: the database creates the id (AUTO_INCREMENT) and we read it back. */
    public Order addOrder(String clientName, String productName, int quantity, double price, String destination, String orderDate, String status) throws SQLException {
        String sql = "INSERT INTO Orders (client_name, product_name, quantity, price, destination, order_date, status) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, clientName);
            stmt.setString(2, productName);
            stmt.setInt(3, quantity);
            stmt.setDouble(4, price);
            stmt.setString(5, destination);
            stmt.setDate(6, java.sql.Date.valueOf(orderDate));
            stmt.setString(7, status);
            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                keys.next();
                Order order = new Order(ID_PREFIX + keys.getInt(1), clientName, productName, quantity, price, destination, orderDate, status);
                orders.add(order);
                return order;
            }
        }
    }

    /** DELETE: removes the row from the database, then from the list. */
    public void deleteOrder(Order order) throws SQLException {
        if (order == null) {
            return;
        }
        String sql = "DELETE FROM Orders WHERE order_id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, numericId(order));
            stmt.executeUpdate();
        }
        orders.remove(order);
    }

    /** UPDATE: changes the row in the database, then the existing object (the table refreshes itself). */
    public void updateOrder(Order order, String clientName, String productName, int quantity, double price, String destination, String orderDate, String status) throws SQLException {
        if (order == null) {
            return;
        }
        String sql = "UPDATE Orders SET client_name = ?, product_name = ?, quantity = ?, price = ?, destination = ?, order_date = ?, status = ? WHERE order_id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, clientName);
            stmt.setString(2, productName);
            stmt.setInt(3, quantity);
            stmt.setDouble(4, price);
            stmt.setString(5, destination);
            stmt.setDate(6, java.sql.Date.valueOf(orderDate));
            stmt.setString(7, status);
            stmt.setInt(8, numericId(order));
            stmt.executeUpdate();
        }

        order.setClientName(clientName);
        order.setProductName(productName);
        order.setQuantity(quantity);
        order.setPrice(price);
        order.setDestination(destination);
        order.setOrderDate(orderDate);
        order.setStatus(status);
    }

    /** "ORD-5003" -> 5003 */
    private int numericId(Order order) {
        return Integer.parseInt(order.getOrderId().substring(ID_PREFIX.length()));
    }
}
