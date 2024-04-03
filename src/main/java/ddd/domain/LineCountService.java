package ddd.domain;

public class LineCountService {

    final FileRepository fileRepository;

    LineCountService(FileRepository fileRepository) {this.fileRepository = fileRepository;}

    public int lc(String path) {
        // instead of using Repository, we can use CatService
        FileEntity file = fileRepository.findBy(path);
        if (file == null) return 0;

        return file.content.split("\n").length;
    }
}
