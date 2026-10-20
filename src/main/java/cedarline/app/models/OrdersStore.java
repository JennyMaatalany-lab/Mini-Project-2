package cedarline.app.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * In-memory store for orders (no database in Mini Project 2).
 * One shared instance so the data survives closing and reopening the window.
 */
public class OrdersStore {

    private static final OrdersStore INSTANCE = new OrdersStore();

    private final ObservableList<Order> orders = FXCollections.observableArrayList();
    private int idCounter = 5000;

    private OrdersStore() {
        addOrder("Bustan Bakery Group", "Olive Oil 5L Tin", 10, 385.00, "Maameltein Rd, Jounieh", "2026-10-02", "Delivered");
        addOrder("Cedar Ridge Hotel", "Cedar Wood Shelf Unit", 4, 580.00, "Old Souk Rd, Byblos", "2026-10-09", "Shipped");
        addOrder("Mina Pharmacy", "Thermal Receipt Rolls (50 pack)", 12, 252.00, "Mina Seafront, Tripoli", "2026-10-12", "Pending");
        addOrder("Tarazi Hardware", "LED Panel Light 60W", 40, 510.00, "Riad El Solh St, Saida", "2026-10-15", "Pending");
    }

    public static OrdersStore getInstance() {
        return INSTANCE;
    }

    public ObservableList<Order> getOrdersList() {
        return orders;
    }

    public Order addOrder(String clientName, String productName, int quantity, double price, String destination, String orderDate, String status) {
        Order order = new Order(nextId(), clientName, productName, quantity, price, destination, orderDate, status);
        orders.add(order);
        return order;
    }

    public void deleteOrder(Order order) {
        if (order != null) {
            orders.remove(order);
        }
    }

    public void updateOrder(Order order, String clientName, String productName, int quantity, double price, String destination, String orderDate, String status) {
        if (order != null) {
            order.setClientName(clientName);
            order.setProductName(productName);
            order.setQuantity(quantity);
            order.setPrice(price);
            order.setDestination(destination);
            order.setOrderDate(orderDate);
            order.setStatus(status);
        }
    }

    private String nextId() {
        idCounter++;
        return "ORD-" + idCounter;
    }
}
