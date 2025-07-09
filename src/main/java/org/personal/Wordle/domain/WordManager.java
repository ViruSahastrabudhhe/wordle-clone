package org.personal.Wordle.domain;
import java.io.IOException;
import java.nio.file.Files;
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
    }

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
        setDailyWord();
    }

    public String getDailyWord() {
        return this.dailyWord;
    }
    public List<String> getWords() {
        return this.words;
    }

    public boolean isInWordsFile(String input) {
        return this.words.contains(input);
    }

    public boolean isInDailyWord(char input, char[] daily) {
        for (char c : daily) {
            if (input == c) {
                return true;
            }
        }
        return false;
    }

    public static String sanitizeInput(String input) {
        String inputToUpper = input.toUpperCase();
        return inputToUpper.trim();
    }
}
