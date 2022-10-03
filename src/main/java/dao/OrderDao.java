package dao;

import model.Order;
import model.OrderType;

public interface OrderDao {
    void update(OrderType type, int price, int size);

    Integer get(int price);

    Order getMaxOrder(OrderType type);

    Order getMinOrder(OrderType type);

    void clear();
}
