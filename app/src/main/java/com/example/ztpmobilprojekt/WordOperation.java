package com.example.ztpmobilprojekt;

public class WordOperation {
    public enum Type{
        ADD,REMOVE,MODIFY
    }

    private final Type operationType;
    private final Word word;

    public WordOperation(Type operationType, Word word){
        this.operationType = operationType;
        this.word = word;
    }

    public Type getType(){
        return operationType;
    }
    public Word getWord(){
        return word;
    }
}
