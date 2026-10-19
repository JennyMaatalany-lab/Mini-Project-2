package cedarline.app.models;

public enum Department {
    HR("HR Department"),
    CLIENTS("Clients Department"),
    INVENTORY("Inventory Department"),
    ORDERS("Orders Department");

    private final String label;

    Department(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
