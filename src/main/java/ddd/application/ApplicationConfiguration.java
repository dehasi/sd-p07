package ddd.application;

import ddd.domain.CatService;
import ddd.domain.DomainConfiguration;
import ddd.domain.FileRepository;
import ddd.domain.LineCountService;
import ddd.infrastructure.FileSystemRepository;
import ddd.infrastructure.InMemoryRepository;

import java.util.Arrays;


public class ApplicationConfiguration {
    final DomainConfiguration domain = new DomainConfiguration();

    public UserInputService userInputService(String[] profile) {

        FileRepository fileRepository = switch (profile[0]) {
            case "inmem" -> new InMemoryRepository();
            case "fs" -> new FileSystemRepository(profile[1]);
            default -> throw new RuntimeException("unknown profile: " + Arrays.toString(profile));
        };

        CatService catService = domain.catService(fileRepository);
        LineCountService lineCountService = domain.lineCountService(fileRepository);

        return new UserInputService(catService, lineCountService);
    }
}
