package strategy.handler;

import dao.OrderDao;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
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
                .filter(t -> t.getType().equals(rawData[TYPE_INDEX]))
                .findFirst().orElseThrow(
                        () -> new NoSuchElementException("Incorrect type: " + rawData[TYPE_INDEX]));
        orderDao.update(type, price, size);
    }
}
