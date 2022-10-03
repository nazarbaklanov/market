import java.util.HashMap;
import java.util.List;
import java.util.Map;
import dao.OrderDao;
import dao.OrderDaoImpl;
import model.OperationType;
import service.FileCreatorService;
import service.FileReaderService;
import service.FileWriterService;
import service.ProcessorService;
import service.impl.FileCreatorServiceImpl;
import service.impl.FileReaderServiceImpl;
import service.impl.FileWriterServiceImpl;
import service.impl.ProcessorServiceImpl;
import strategy.OperationHandlerStrategy;
import strategy.OperationHandlerStrategyImpl;
import strategy.handler.OperationHandler;
import strategy.handler.OrderOperationHandler;
import strategy.handler.QueryOperationHandler;
import strategy.handler.UpdateOperationHandler;

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

        FileReaderService readerService = new FileReaderServiceImpl();
        List<String> stringList = readerService.read(INPUT_FILE_NAME);

        OperationHandlerStrategy operationHandlerStrategy = new OperationHandlerStrategyImpl(operationHandlerMap);

        ProcessorService processorService = new ProcessorServiceImpl(operationHandlerStrategy);
        List<String> data = processorService.process(stringList);

        FileCreatorService fileCreatorService = new FileCreatorServiceImpl();
        fileCreatorService.createFile(OUTPUT_FILE_NAME);

        FileWriterService fileWriterService = new FileWriterServiceImpl();
        fileWriterService.write(data, OUTPUT_FILE_NAME);
    }
}
