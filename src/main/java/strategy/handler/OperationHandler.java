package strategy.handler;

import java.util.List;

public interface OperationHandler {
    void commitOperation(String[] rawData, List<String> queries);
}
