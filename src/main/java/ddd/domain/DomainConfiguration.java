package ddd.domain;

public class DomainConfiguration {
    /*
    It's also possible to keep implemntation in one service, i.e. FileService
    * */

    public CatService catService(FileRepository fileRepository) {
        return new CatService(fileRepository);
    }

    public LineCountService lineCountService(FileRepository fileRepository) {
        return new LineCountService(fileRepository);
    }
}
