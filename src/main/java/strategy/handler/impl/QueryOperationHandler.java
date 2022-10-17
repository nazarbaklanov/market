package strategy.handler.impl;

import dao.OrderDao;
import java.util.List;
import java.util.NoSuchElementException;
import model.Order;
import model.OrderType;
import strategy.handler.OperationHandler;

public class QueryOperationHandler implements OperationHandler {
    private static final String SEPARATOR = ",";
    private static final String BEST_BID = "best_bid";
    private static final String BEST_ASK = "best_ask";
    private static final int TYPE_INDEX = 1;
    private static final int PRICE_INDEX = 2;

    private final OrderDao orderDao;

    public QueryOperationHandler(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public void commitOperation(String[] rawData, List<String> queries) {
        String output;
        if (rawData[TYPE_INDEX].equalsIgnoreCase(BEST_BID)) {
            Order order = orderDao.getMaxOrder(OrderType.BID).orElseThrow(
                    () -> new NoSuchElementException("No exist: " + BEST_BID)
            );
            output = order.getPrice() + SEPARATOR + order.getSize();
        } else if (rawData[TYPE_INDEX].equalsIgnoreCase(BEST_ASK)) {
            Order order = orderDao.getMinOrder(OrderType.ASK).orElseThrow(
                    () -> new NoSuchElementException("No exist: " + BEST_ASK)
            );
            output = order.getPrice() + SEPARATOR + order.getSize();
        } else {
            output = String.valueOf(orderDao.getSize(Integer.parseInt(rawData[PRICE_INDEX])));
        }
        queries.add(output);
    }
}
