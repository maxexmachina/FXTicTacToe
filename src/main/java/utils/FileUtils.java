package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileUtils {

    public static List<String> readAll(String path) throws FileNotFoundException {
        List<String> result = new ArrayList<>();
        try (Scanner scanner = new Scanner(new FileReader(path))) {
            while (scanner.hasNext()) {
                result.add(scanner.nextLine());
            }
            return result;
        }
    }

    public static void writeAll(String path, List<String> lines) throws IOException {
        try (FileWriter fw = new FileWriter(path)) {
            for (String line: lines) {
                fw.write(line + "\n");
            }
        }
    }

    public static void copy(String sourceFile, String destinationFile) throws IOException {
        String line;
        try (Scanner scanner = new Scanner(new FileReader(sourceFile));
             FileWriter fw = new FileWriter(destinationFile)) {
                while (scanner.hasNext()) {
                    line = scanner.nextLine();
                    fw.write(line + "\n");
                }
        }
    }

    public static boolean delete(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
            return true;
        }
        return false;
    }
}
