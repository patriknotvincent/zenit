package main.java.zenit.zencodearea;

public enum CompletionType {
    METHOD,
    VARIABLE;

    @Override
    public String toString(){
        switch(this){
            case METHOD:
                return "m";
            case VARIABLE:
                return "v";
            default:
                return "unknown";
        }
    }
}
