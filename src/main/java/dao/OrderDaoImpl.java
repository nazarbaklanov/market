package dao;

import db.Storage;
import java.util.Map;
import java.util.Optional;
import model.Order;
import model.OrderType;

public class OrderDaoImpl implements OrderDao {
    @Override
    public void update(OrderType type, int price, int size) {
        if (size == 0 && Storage.orders.containsKey(price)) {
            Storage.orders.remove(price);
        } else {
            Order order = new Order(type, price, size);
            Storage.orders.put(price, order);
        }
    }

    @Override
    public Order getOrder(Integer price) {
        return Storage.orders.get(price);
    }

    @Override
    public Integer getSize(Integer price) {
        if (Storage.orders.containsKey(price)) {
            return Storage.orders.get(price).getSize();
        } else {
            return 0;
        }
    }

    @Override
    public Optional<Order> getMaxOrder(OrderType type) {
        Optional<Order> order = Optional.empty();
        for (Map.Entry<Integer, Order> entry : Storage.orders.entrySet()) {
            if (entry.getValue().getSize() > 0 && entry.getValue().getType().equals(type)) {
                order = Optional.of(new Order(type, entry.getKey(), entry.getValue().getSize()));
            }
        }
        return order;
    }

    @Override
    public Optional<Order> getMinOrder(OrderType type) {
        for (Map.Entry<Integer, Order> entry : Storage.orders.entrySet()) {
            if (entry.getValue().getSize() > 0 && entry.getValue().getType().equals(type)) {
                return Optional.of(new Order(type, entry.getKey(), entry.getValue().getSize()));
            }
        }
        return Optional.empty();
    }

    @Override
    public void clear() {
        Storage.orders.clear();
    }
}
