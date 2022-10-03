package model;

public enum OperationType {
    UPDATE("u"),
    ORDER("o"),
    QUERY("q");

    private final String type;

    OperationType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
