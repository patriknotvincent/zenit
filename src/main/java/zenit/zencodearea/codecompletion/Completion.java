package zenit.zencodearea.codecompletion;

/**
 * Represents variable and method completion suggestion for the ZencodeArea.
 */
public class Completion {
    private String name;
    private CompletionType completionType;
    private int id;
    private static int nextId = 0;

    public Completion(String name, CompletionType completionType) {
        this.name = name;
        this.completionType = completionType;
        this.id = nextId++;
    }

    public String getName() {
        return name;
    }

    public CompletionType getCompletionType() {
        return completionType;
    }
}
