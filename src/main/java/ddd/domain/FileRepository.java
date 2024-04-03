package ddd.domain;

public interface FileRepository {

    FileEntity findBy(String path);
}
