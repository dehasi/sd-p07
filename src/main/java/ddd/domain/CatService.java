package ddd.domain;

public class CatService {

    final FileRepository fileRepository;

    CatService(FileRepository fileRepository) {this.fileRepository = fileRepository;}

    public String cat(String path) {
        FileEntity file = fileRepository.findBy(path);
        return file.content;
    }
}
