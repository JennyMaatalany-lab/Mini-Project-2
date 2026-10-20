package cedarline.app.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * In-memory store for clients (no database in Mini Project 2).
 * One shared instance so the data survives closing and reopening the window.
 */
public class ClientsStore {

    private static final ClientsStore INSTANCE = new ClientsStore();

    private final ObservableList<Client> clients = FXCollections.observableArrayList();
    private int idCounter = 2000;

    private ClientsStore() {
        addClient("Bustan Bakery Group", "Rita Khoury", "+961 9 640 112", "orders@bustanbakery.example", "Maameltein Rd", "Jounieh", "Wholesale");
        addClient("Cedar Ridge Hotel", "Fadi Aoun", "+961 9 915 330", "reservations@cedarridge.example", "Old Souk Rd", "Byblos", "Hotel / Restaurant");
        addClient("Mina Pharmacy", "Nour Salameh", "+961 6 431 207", "contact@minapharmacy.example", "Mina Seafront", "Tripoli", "Pharmacy");
        addClient("Tarazi Hardware", "Hadi Tarazi", "+961 7 720 845", "sales@tarazihardware.example", "Riad El Solh St", "Saida", "Retail");
        addClient("Zahle Fresh Market", "Maya Daher", "+961 8 801 466", "info@zahlefresh.example", "Berdawni Rd", "Zahle", "Wholesale");
    }

    public static ClientsStore getInstance() {
        return INSTANCE;
    }

    public ObservableList<Client> getClientsList() {
        return clients;
    }

    public Client addClient(String name, String contactPerson, String phone, String email, String address, String city, String clientType) {
        Client client = new Client(nextId(), name, contactPerson, phone, email, address, city, clientType);
        clients.add(client);
        return client;
    }

    public void deleteClient(Client client) {
        if (client != null) {
            clients.remove(client);
        }
    }

    public void updateClient(Client client, String name, String contactPerson, String phone, String email, String address, String city, String clientType) {
        if (client != null) {
            client.setName(name);
            client.setContactPerson(contactPerson);
            client.setPhone(phone);
            client.setEmail(email);
            client.setAddress(address);
            client.setCity(city);
            client.setClientType(clientType);
        }
    }

    private String nextId() {
        idCounter++;
        return "CL-" + idCounter;
    }
}
