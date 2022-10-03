package strategy;

import model.OperationType;
import strategy.handler.OperationHandler;

public interface OperationHandlerStrategy {
    OperationHandler getOperationHandler(OperationType type);
}
