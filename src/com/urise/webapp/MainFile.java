package com.urise.webapp;

import java.io.File;

public class MainFile {
    public static void main(String[] args) {
        /*String filePath = ".\\.gitignore";

        File file = new File(filePath);

        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error: ", e);
        }*/

        File dir = new File(".\\src\\com\\urise");
        /*System.out.println(dir.isDirectory());
        Arrays.stream(Objects.requireNonNull(dir.list())).forEach(System.out::println);

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/

        recursionHW8(dir, "");
    }

    public static void recursionHW8(File dir, String indent) {
        File[] file = dir.listFiles();
        if (file != null) {
            for (File f : file) {
                if (f.isFile()) {
                    System.out.println(indent + f.getName());
                } else if (f.isDirectory()) {
                    System.out.println(indent + f.getName());
                    recursionHW8(f, indent + "\t");
                }
            }
        }
    }
}
