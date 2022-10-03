package dao;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Optional;
import db.Storage;
import model.Order;
import model.OrderType;

public class OrderDaoImpl implements OrderDao {
    @Override
    public void update(OrderType type, int price, int size) {
        getOrderFromDb(price).orElseGet(() ->
        {
            Order order = new Order(type, price, size);
            Storage.orders.add(order);
            return order;
        }).setSize(size);
    }

    @Override
    public Integer get(int price) {
        return getOrderFromDb(price)
                .map(Order::getSize)
                .orElse(0);
    }

    @Override
    public Order getMaxOrder(OrderType type) {
        return Storage.orders.stream()
                .filter(o -> o.getType().equals(type) && o.getSize() > 0)
                .max(Comparator.comparingInt(Order::getPrice))
                .orElseThrow( () -> new NoSuchElementException("No order with type: "
                        + type + " and size more 0 to present"));
    }

    @Override
    public Order getMinOrder(OrderType type) {
        return Storage.orders.stream()
                .filter(o -> o.getType().equals(type) && o.getSize() > 0)
                .min(Comparator.comparingInt(Order::getPrice))
                .orElseThrow( () -> new NoSuchElementException("No order with type: "
                        + type + " and size more 0 to present"));
    }

    @Override
    public void clear() {
        Storage.orders.clear();
    }

    private Optional<Order> getOrderFromDb(int price) {
        return Storage.orders.stream()
                .filter(o -> o.getPrice() == price)
                .findFirst();
    }
}
