package org.personal.Wordle.domain;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class WordManager {
    private final List<String> words;
    private String dailyWord;

    public WordManager() throws IOException {
        this.words = new ArrayList<>();
        this.dailyWord = "";
        readWordsFile();
        setDailyWord();
    }
    public WordManager(boolean isTesting) throws IOException {
        this.words = new ArrayList<>();
        this.dailyWord = "";
        readWordsFile();
        setDailyWord(isTesting);
    }

    private void readWordsFile() throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get("src/main/resources/dictionary.txt"))) {
            stream.forEach(this.words::add);
        } catch (IOException e) {
            throw new IOException("Invalid file!");
        }
//        Files.lines(Paths.get("src/main/resources/dictionary.txt")).forEach(this.words::add);
    }

    public List<String> getWords() {
        return this.words;
    }

    /*
    if this was the getDailyWord(), it would randomize this.dailyWord every time it's called
    hence, changing this to setDailyWord() will set this.dailyWord once, not randomizing it
    every time the method is called
    you must only call this method once because it will change the current this.dailyWord
    making it go cuckoo crazy and fuck the gameLogic up
     */
    private void setDailyWord() {
        try {
            int randomNum = (int) (Math.random() * this.words.size());
            this.dailyWord=this.words.get(randomNum);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    private void setDailyWord(boolean isTesting) {
        if (isTesting) {
            this.dailyWord="CREAM";
            return;
        }

        try {
            int randomNum = (int) (Math.random() * this.words.size());
            this.dailyWord=this.words.get(randomNum);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public String getDailyWord() {
        return this.dailyWord;
    }

    public boolean isInWordsFile(String input) {
        return this.words.contains(input);
    }

    public boolean contains(char input, char[] daily) {
        for (char c : daily) {
            if (input == c) {
                return true;
            }
        }
        return false;
    }
}
