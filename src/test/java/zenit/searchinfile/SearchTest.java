package zenit.searchinfile;

import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import junit.framework.Assert;
import org.junit.jupiter.api.Test;
import zenit.ZenithTestBase;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

class SearchTest extends ZenithTestBase {

    private final String FIELD_SEARCH_INPUT = "fldInputField";
    private final String FIELD_REPLACE_INPUT = "fldReplaceWord";
    private final String BUTTON_SCROLL_UP = "btnUp";
    private final String BUTTON_SCROLL_DOWN = "btnDown";
    private final String BUTTON_ESCAPE = "btnEsc";
    private final String BUTTON_REPLACE_FIRST = "btnReplaceOne";
    private final String BUTTON_REPLACE_ALL = "btnReplaceAll";
    private final String LABEL_SEARCH_OCCURRENCES = "lblOccurrences";
    private final String FIELD_FILE_TREE = null;

    private String testFileTXT = "/";
    private String testFileJava = "/";

    @Test
    void getFileLocations() throws IOException {
        String os = System.getProperty("osName");
        Path start = Paths.get("/");
        String filePatternTXT = "SearchTest.txt";
        String filePatternJava = "SearchTest.java";

        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {

                AclFileAttributeView aclView = Files.getFileAttributeView(dir, AclFileAttributeView.class);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    @Test
    void searchInTextFile() throws Exception {
        // Get test file
        Path foo = Path.of("src/testfiles/SearchTest.java");
        InputStream inputStream = getClass().getResourceAsStream(foo.toString());
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        File bar = new File("src/testfiles/SearchTest.java");

        // Getting the search bar, entering input and searching
        String query = "main";

        Node tree = find("treeView");


        push(KeyCode.CONTROL, KeyCode.F)
                .clickOn(FIELD_SEARCH_INPUT)
                .write(query);
        // Actual test
        Assert.assertEquals("1/1", find(LABEL_SEARCH_OCCURRENCES).getAccessibleText());
    }

    @Test
    void searchInJavaFile() throws Exception {
        File foo = new File("src/testfiles/SearchTest.java");

    }

    @Test
    void clearZen() {
    }

    @Test
    void cleanZen() {
    }

    @Test
    void replaceAll() {
    }

    @Test
    void replaceOne() {
    }

    @Test
    void jumpDown() {
    }

    @Test
    void jumpUp() {
    }

    @Test
    void searchWindowLabelsTest() {

    }
}