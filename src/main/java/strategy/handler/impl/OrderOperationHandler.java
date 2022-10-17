package strategy.handler.impl;

import dao.OrderDao;
import db.Storage;
import exception.OperationUnknownException;
import java.util.List;
import model.Order;
import model.OrderType;
import strategy.handler.OperationHandler;

public class OrderOperationHandler implements OperationHandler {
    private static final String SELL_TYPE = "sell";
    private static final String BUY_TYPE = "buy";
    private static final int TYPE_INDEX = 1;
    private static final int VALUE_INDEX = 2;

    private final OrderDao orderDao;

    public OrderOperationHandler(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public void commitOperation(String[] rawData, List<String> queries) {
        int size = Integer.parseInt(rawData[VALUE_INDEX]);
        while (size > 0) {
            Order order;
            switch (rawData[TYPE_INDEX]) {
                case SELL_TYPE:
                    order = orderDao.getMaxOrder(OrderType.BID).orElseThrow(
                            () -> new OperationUnknownException("Not found the best price for: "
                                    + SELL_TYPE));
                    break;
                case BUY_TYPE:
                    order = orderDao.getMinOrder(OrderType.ASK).orElseThrow(
                            () -> new OperationUnknownException("Not found the best price for: "
                                    + SELL_TYPE));
                    break;
                default:
                    throw new OperationUnknownException("Unknown operation command: "
                            + rawData[TYPE_INDEX]);
            }
            int temp;
            if (size > order.getSize()) {
                temp = order.getSize();
                size -= temp;
            } else {
                temp = size;
                size = 0;
            }
            Order orderToWrite = Storage.orders.get(order.getPrice());
            orderToWrite.setSize(order.getSize() - temp);
            orderDao.update(orderToWrite.getType(),
                    orderToWrite.getPrice(), orderToWrite.getSize());
        }
    }
}
