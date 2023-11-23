package com.example.translator;

public class Word {
    private final String word_target;
    private final String word_spelling;
    private final String word_explain;

    public Word(String word_target,String word_spelling ,String word_explain) {
        this.word_target = word_target;
        this.word_spelling = word_spelling;
        this.word_explain = word_explain;
    }

    public String getWord_target() {
        return word_target;
    }

    public String getWord_spelling() {
        return word_spelling;
    }

    public String getWord_explain() {
        return word_explain;
    }
}