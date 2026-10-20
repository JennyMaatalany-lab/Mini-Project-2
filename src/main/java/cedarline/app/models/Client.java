package cedarline.app.models;

import javafx.beans.property.*;

public class Client {
    private final SimpleStringProperty clientId;
    private final SimpleStringProperty name;
    private final SimpleStringProperty contactPerson;
    private final SimpleStringProperty phone;
    private final SimpleStringProperty email;
    private final SimpleStringProperty address;
    private final SimpleStringProperty city;
    private final SimpleStringProperty clientType;

    public Client(String clientId, String name, String contactPerson, String phone, String email, String address, String city, String clientType) {
        this.clientId = new SimpleStringProperty(clientId);
        this.name = new SimpleStringProperty(name);
        this.contactPerson = new SimpleStringProperty(contactPerson);
        this.phone = new SimpleStringProperty(phone);
        this.email = new SimpleStringProperty(email);
        this.address = new SimpleStringProperty(address);
        this.city = new SimpleStringProperty(city);
        this.clientType = new SimpleStringProperty(clientType);
    }

    public String getClientId() {
        return clientId.get();
    }

    public StringProperty clientIdProperty() {
        return clientId;
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

    public String getContactPerson() {
        return contactPerson.get();
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson.set(contactPerson);
    }

    public StringProperty contactPersonProperty() {
        return contactPerson;
    }

    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public StringProperty addressProperty() {
        return address;
    }

    public String getCity() {
        return city.get();
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public StringProperty cityProperty() {
        return city;
    }

    public String getClientType() {
        return clientType.get();
    }

    public void setClientType(String clientType) {
        this.clientType.set(clientType);
    }

    public StringProperty clientTypeProperty() {
        return clientType;
    }
}
