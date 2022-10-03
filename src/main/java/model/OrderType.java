package model;

public enum OrderType {
    ASK("ask"),
    BID("bid");

    private final String type;

    OrderType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
