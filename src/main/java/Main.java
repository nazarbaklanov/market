import dao.OrderDao;
import dao.OrderDaoImpl;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.OperationType;
import service.FileCreatorService;
import service.FileWriterService;
import service.ProcessorService;
import service.impl.FileCreatorServiceImpl;
import service.impl.FileWriterServiceImpl;
import service.impl.ProcessorServiceImpl;
import strategy.OperationHandlerStrategy;
import strategy.OperationHandlerStrategyImpl;
import strategy.handler.OperationHandler;
import strategy.handler.impl.OrderOperationHandler;
import strategy.handler.impl.QueryOperationHandler;
import strategy.handler.impl.UpdateOperationHandler;

public class Main {
    private static final String INPUT_FILE_NAME = "input.txt";
    private static final String OUTPUT_FILE_NAME = "output.txt";

    public static void main(String[] args) {
        OrderDao orderDao = new OrderDaoImpl();
        orderDao.clear();

        Map<OperationType, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(OperationType.UPDATE, new UpdateOperationHandler(orderDao));
        operationHandlerMap.put(OperationType.ORDER, new OrderOperationHandler(orderDao));
        operationHandlerMap.put(OperationType.QUERY, new QueryOperationHandler(orderDao));

        OperationHandlerStrategy operationHandlerStrategy
                = new OperationHandlerStrategyImpl(operationHandlerMap);

        FileCreatorService fileCreatorService = new FileCreatorServiceImpl();
        fileCreatorService.createFile(OUTPUT_FILE_NAME);

        ProcessorService processorService = new ProcessorServiceImpl(operationHandlerStrategy);
        FileWriterService fileWriterService = new FileWriterServiceImpl();

        List<String> dataToWrite = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE_NAME))) {
            String strings;
            while ((strings = reader.readLine()) != null) {
                if (strings.isEmpty()) {
                    continue;
                }
                dataToWrite.addAll(processorService.process(strings));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error read from input file: " + INPUT_FILE_NAME);
        }
        fileWriterService.write(dataToWrite, OUTPUT_FILE_NAME);
    }
}
