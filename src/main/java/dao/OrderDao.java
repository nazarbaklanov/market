package dao;

import java.util.Optional;
import model.Order;
import model.OrderType;

public interface OrderDao {
    void update(OrderType type, int price, int size);

    Order getOrder(Integer price);

    Integer getSize(Integer price);

    Optional<Order> getMaxOrder(OrderType type);

    Optional<Order> getMinOrder(OrderType type);

    void clear();
}
