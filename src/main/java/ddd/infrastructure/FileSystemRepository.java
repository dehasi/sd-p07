package ddd.infrastructure;

import ddd.domain.FileEntity;
import ddd.domain.FileRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.util.stream.Collectors.joining;

public class FileSystemRepository implements FileRepository {

    private final String basePath;

    public FileSystemRepository(String basePath) {
        this.basePath = basePath;
    }

    @Override public FileEntity findBy(String path) {

        try {
            Path path1 = Path.of(basePath + path);
            String content = Files.lines(path1).collect(joining("\n"));
            return new FileEntity(path, path1.getFileName().toString(), content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
