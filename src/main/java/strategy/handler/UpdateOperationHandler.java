package strategy.handler;

import dao.OrderDao;
import db.Storage;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import model.Order;
import model.OrderType;

public class UpdateOperationHandler implements OperationHandler {
    private static final int PRICE_INDEX = 1;
    private static final int SIZE_INDEX = 2;
    private static final int TYPE_INDEX = 3;

    private final OrderDao orderDao;

    public UpdateOperationHandler(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public void commitOperation(String[] rawData, List<String> queries) {
        int price = Integer.parseInt(rawData[PRICE_INDEX]);
        int size = Integer.parseInt(rawData[SIZE_INDEX]);
        OrderType type = Arrays.stream(OrderType.values())
                .filter(t -> t.getType().equalsIgnoreCase(rawData[TYPE_INDEX]))
                .findFirst().orElseThrow(
                        () -> new NoSuchElementException("Incorrect type: " + rawData[TYPE_INDEX]));
        boolean flag = false;
        int changeSize = size;
        for (Map.Entry<Integer, Order> entry : Storage.orders.entrySet()) {
            if (type.equals(OrderType.ASK)
                    && entry.getValue().getType().equals(OrderType.BID)
                    && price <= entry.getValue().getPrice()
                    && size <= entry.getValue().getSize()) {
                orderDao.update(entry.getValue().getType(),
                        entry.getValue().getPrice(),
                        entry.getValue().getSize() - size);
                changeSize -= size;
                flag = true;
                break;
            }
            if (type.equals(OrderType.BID)
                    && entry.getValue().getType().equals(OrderType.ASK)
                    && price >= entry.getValue().getPrice()
                    && size <= entry.getValue().getSize()) {
                orderDao.update(entry.getValue().getType(),
                        entry.getValue().getPrice(),
                        entry.getValue().getSize() - size);
                changeSize -= size;
                flag = true;
                break;
            }
        }
        if (!flag) {
            orderDao.update(type, price, size);
        } else if (changeSize != size) {
            orderDao.update(type, price, changeSize);
        }
    }
}
