package be.webtechie.wordfilerepair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class App {

    static Path input = Path.of("/home/frank/Documents/Recover/Input");
    static Path output = Path.of("/home/frank/Documents/Recover/Output");

    public static void main(String[] args) throws IOException {
        Files.walk(input)
                .filter(p -> Files.isRegularFile(p)
                        && (p.toString().endsWith(".doc") || p.toString().endsWith(".docx")))
                .forEach(p -> {
                    try {
                        FileChecker.check(p, output);
                    } catch (IOException ex) {
                        System.err.println("IOException: " + ex.getMessage());
                    }
                });
    }
}
