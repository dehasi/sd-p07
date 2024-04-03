package ddd.application;

import ddd.domain.CatService;
import ddd.domain.LineCountService;

import java.util.List;

public class UserInputService {

    final CatService catService;
    final LineCountService lineCountService;

    UserInputService(CatService catService, LineCountService lineCountService) {
        this.catService = catService;
        this.lineCountService = lineCountService;
    }

    public String exec(String input) {
        assert input != null; // Input validators can be separate application services
        List<String> split = List.of(input.split("\\s+"));
        String command = split.get(0);

        switch (command) {
            case "cat": {
                assert split.size() == 2;
                // input/output converters/validators can be separate application services
                String path = split.get(1);
                return catService.cat(path);
            }
            case "lc": {
                assert split.size() == 2;
                String path = split.get(1);
                // this String.valueOf can be moved in a separate converter class
                return String.valueOf(lineCountService.lc(path));
            }
            case "help":
                return "cat\nlc"; // Help can be in HelpService, but it's an Application service, not Domain service
            default:
                return "Unknown command: " + input;
        }
    }
}
