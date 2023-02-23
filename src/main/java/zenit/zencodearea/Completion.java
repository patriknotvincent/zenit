package main.java.zenit.zencodearea;

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
