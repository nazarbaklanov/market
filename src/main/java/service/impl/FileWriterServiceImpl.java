package service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import service.FileWriterService;

public class FileWriterServiceImpl implements FileWriterService {
    @Override
    public void write(List<String> data, String fileName) {
        if (data.isEmpty()) {
            return;
        }
        try {
            Files.writeString(Path.of(fileName),
                    String.join(System.lineSeparator(), data) + "\n",
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException("Cannot write file: " + fileName, e);
        }
    }
}
