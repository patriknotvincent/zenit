package zenit.zencodearea;

/**
 * Represents variable and method completion suggestion for the ZencodeArea.
 */
public class Completion {
    private String name;
    private CompletionType completionType;

    public Completion(String name, CompletionType completionType) {
        this.name = name;
        this.completionType = completionType;
    }

    public String getName() {
        return name;
    }

    public CompletionType getCompletionType() {
        return completionType;
    }
}
