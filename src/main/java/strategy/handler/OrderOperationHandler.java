package strategy.handler;

import dao.OrderDao;
import java.util.List;
import model.Order;
import model.OrderType;

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
        if (rawData[TYPE_INDEX].equals(SELL_TYPE)) {
            Order order = orderDao.getMaxOrder(OrderType.BID);
            orderDao.update(OrderType.BID,
                    order.getPrice(), order.getSize() - Integer.parseInt(rawData[VALUE_INDEX]));
            return;
        }
        if (rawData[TYPE_INDEX].equals(BUY_TYPE)) {
            Order order = orderDao.getMinOrder(OrderType.ASK);
            orderDao.update(OrderType.ASK,
                    order.getPrice(), order.getSize() - Integer.parseInt(rawData[VALUE_INDEX]));
        }
    }
}
