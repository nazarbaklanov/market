package strategy.handler;

import dao.OrderDao;
import java.util.List;
import model.Order;
import model.OrderType;

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
            Order order = orderDao.getMaxOrder(OrderType.BID);
            output = order.getPrice() + SEPARATOR + order.getSize();
        } else if (rawData[TYPE_INDEX].equalsIgnoreCase(BEST_ASK)) {
            Order order = orderDao.getMinOrder(OrderType.ASK);
            output = order.getPrice() + SEPARATOR + order.getSize();
        } else {
            output = String.valueOf(orderDao.get(Integer.parseInt(rawData[PRICE_INDEX])));
        }
        queries.add(output);
    }
}
