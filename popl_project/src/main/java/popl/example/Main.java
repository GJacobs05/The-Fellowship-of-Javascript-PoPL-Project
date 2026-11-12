package popl.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello world!");

     
        String inputCode = Files.readString(Paths.get("popl_project\\src\\main\\java\\popl\\example\\project_deliverable_1.py"));

        System.out.println("Input");
        System.out.println(inputCode);
        System.out.println("Yippee");
    }
}