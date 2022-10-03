package service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import service.FileReaderService;

public class FileReaderServiceImpl implements FileReaderService {
    @Override
    public List<String> read(String fileName) {
        Path path = Path.of(fileName);
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException("Cannot read file: " + fileName, e);
        }
    }
}