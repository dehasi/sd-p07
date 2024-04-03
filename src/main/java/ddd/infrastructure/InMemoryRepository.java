package ddd.infrastructure;

import ddd.domain.FileEntity;
import ddd.domain.FileRepository;

import java.util.Map;

public class InMemoryRepository implements FileRepository {

    Map<String, FileEntity> fileSystem = Map.of(
            "/root/file", new FileEntity("/root/file", "file", """
                    THis is content
                    This is the second line
                    """));

    @Override public FileEntity findBy(String path) {
        return fileSystem.get(path);
    }
}
