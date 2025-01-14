package zenit;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class FileFinder {

    static String absolutePath = "";

    public static String findFile(String fileName) throws IOException {
        String os = System.getProperty("os.name");
        Path root;
        if (os.startsWith("Windows")) {
            root = Paths.get("C:\\Users\\");
        } else if (os.startsWith("Mac")) {
            root = Paths.get("/");
        } else {
            root = null;
        }

        Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                if (file.getFileName().toString().equals(fileName)) {
                    absolutePath = String.valueOf(file.toAbsolutePath());
                    return FileVisitResult.TERMINATE;
                }
                return FileVisitResult.CONTINUE;
            }
            @Override
            public FileVisitResult visitFileFailed(Path file, IOException ex) {
                if (ex instanceof AccessDeniedException) {
                    return FileVisitResult.SKIP_SUBTREE; // skip directories that can't be accessed
                }
                return FileVisitResult.CONTINUE;
            }
        });
        return absolutePath;
    }

    public static boolean assertFileOrFolderExists(String fileName) throws IOException {
        String os = System.getProperty("os.name");
        Path root;
        if (os.startsWith("Windows")) {
            root = Paths.get("C:\\Users\\");
        } else if (os.startsWith("Mac")) {
            root = Paths.get("/Users/");
        } else {
            root = null;
        }

        Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                if (file.getFileName().toString().equals(fileName)) {
                    absolutePath = String.valueOf(file.toAbsolutePath());
                    return FileVisitResult.TERMINATE;
                }
                return FileVisitResult.CONTINUE;
            }
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                if (Files.isReadable(dir) && dir.getFileName().toString().equals(fileName)) {
                    absolutePath = String.valueOf(dir.toAbsolutePath());
                    return FileVisitResult.TERMINATE;
                }
                return FileVisitResult.CONTINUE;
            }
            @Override
            public FileVisitResult visitFileFailed(Path file, IOException ex) {
                if (ex instanceof AccessDeniedException) {
                    return FileVisitResult.SKIP_SUBTREE; // skip directories that can't be accessed
                }
                return FileVisitResult.CONTINUE;
            }
        });
        return absolutePath.length() > 0;
    }

}
