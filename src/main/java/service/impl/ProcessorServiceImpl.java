package service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import model.OperationType;
import service.ProcessorService;
import strategy.OperationHandlerStrategy;

public class ProcessorServiceImpl implements ProcessorService {
    private static final String SEPARATOR = ",";
    private static final int TYPE_INDEX = 0;

    private final OperationHandlerStrategy operationHandlerStrategy;

    public ProcessorServiceImpl(OperationHandlerStrategy operationHandlerStrategy) {
        this.operationHandlerStrategy = operationHandlerStrategy;
    }

    @Override
    public List<String> process(List<String> rawData) {
        List<String> output = new ArrayList<>();
        for (String s : rawData) {
            String[] dataRow = s.split(SEPARATOR);
            OperationType type = Arrays.stream(OperationType.values())
                    .filter(o -> o.getType().equals(dataRow[TYPE_INDEX].toLowerCase()))
                    .findFirst()
                    .orElseThrow(() ->
                            new NoSuchElementException("Incorrect operation type: "
                                    + dataRow[TYPE_INDEX]));
            operationHandlerStrategy.getOperationHandler(type).commitOperation(dataRow, output);
        }
        return output;
    }
}
