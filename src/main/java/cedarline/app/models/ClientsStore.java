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
 * Client store backed by the Clients table of the MySQL database (JDBC).
 * One shared instance, so every client window and its table use the same list.
 *
 * The ObservableList is a copy of the table kept in memory so the TableView can show it.
 * Every change is first written to the database; the list is only changed if that worked.
 */
public class ClientsStore {

    /** The database stores a number (2001, 2002...), the screen shows it as CL-2001. */
    private static final String ID_PREFIX = "CL-";

    private static final ClientsStore INSTANCE = new ClientsStore();

    private final ObservableList<Client> clients = FXCollections.observableArrayList();

    private ClientsStore() {
    }

    public static ClientsStore getInstance() {
        return INSTANCE;
    }

    public ObservableList<Client> getClientsList() {
        return clients;
    }

    /** SELECT: reloads every client from the database into the list. */
    public void loadClients() throws SQLException {
        String sql = "SELECT client_id, name, contact_person, phone, email, address, city, client_type FROM Clients ORDER BY client_id";

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            clients.clear();
            while (rs.next()) {
                clients.add(new Client(
                        ID_PREFIX + rs.getInt("client_id"),
                        rs.getString("name"),
                        rs.getString("contact_person"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getString("city"),
                        rs.getString("client_type")));
            }
        }
    }

    /** INSERT: the database creates the id (AUTO_INCREMENT) and we read it back. */
    public Client addClient(String name, String contactPerson, String phone, String email, String address, String city, String clientType) throws SQLException {
        String sql = "INSERT INTO Clients (name, contact_person, phone, email, address, city, client_type) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, name);
            stmt.setString(2, contactPerson);
            stmt.setString(3, phone);
            stmt.setString(4, email);
            stmt.setString(5, address);
            stmt.setString(6, city);
            stmt.setString(7, clientType);
            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                keys.next();
                Client client = new Client(ID_PREFIX + keys.getInt(1), name, contactPerson, phone, email, address, city, clientType);
                clients.add(client);
                return client;
            }
        }
    }

    /** DELETE: removes the row from the database, then from the list. */
    public void deleteClient(Client client) throws SQLException {
        if (client == null) {
            return;
        }
        String sql = "DELETE FROM Clients WHERE client_id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, numericId(client));
            stmt.executeUpdate();
        }
        clients.remove(client);
    }

    /** UPDATE: changes the row in the database, then the existing object (the table refreshes itself). */
    public void updateClient(Client client, String name, String contactPerson, String phone, String email, String address, String city, String clientType) throws SQLException {
        if (client == null) {
            return;
        }
        String sql = "UPDATE Clients SET name = ?, contact_person = ?, phone = ?, email = ?, address = ?, city = ?, client_type = ? WHERE client_id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, contactPerson);
            stmt.setString(3, phone);
            stmt.setString(4, email);
            stmt.setString(5, address);
            stmt.setString(6, city);
            stmt.setString(7, clientType);
            stmt.setInt(8, numericId(client));
            stmt.executeUpdate();
        }

        client.setName(name);
        client.setContactPerson(contactPerson);
        client.setPhone(phone);
        client.setEmail(email);
        client.setAddress(address);
        client.setCity(city);
        client.setClientType(clientType);
    }

    /** "CL-2003" -> 2003 */
    private int numericId(Client client) {
        return Integer.parseInt(client.getClientId().substring(ID_PREFIX.length()));
    }
}
