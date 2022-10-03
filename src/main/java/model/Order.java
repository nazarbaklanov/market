package model;

public class Order {
    private OrderType type;
    private int price;
    private int size;

    public Order(OrderType type, int price, int size) {
        this.type = type;
        this.price = price;
        this.size = size;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
