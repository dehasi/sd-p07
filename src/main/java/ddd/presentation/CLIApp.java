package ddd.presentation;

import ddd.application.ApplicationConfiguration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class CLIApp {

    public static void main(String[] args) throws IOException {
        var userInputService = new ApplicationConfiguration().userInputService(args);

        var reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.print("> ");
            String input = reader.readLine();

            if (input.startsWith("exit")) {
                break;
            }

            String output = userInputService.exec(input);
            System.out.println(output);
        }
    }
}
