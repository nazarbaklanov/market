package service;

import java.util.List;

public interface ProcessorService {
    List<String> process(List<String> rawData);
}
