package db;

import java.util.TreeMap;
import model.Order;

public class Storage {
    public static final TreeMap<Integer, Order> orders = new TreeMap<>();
}
