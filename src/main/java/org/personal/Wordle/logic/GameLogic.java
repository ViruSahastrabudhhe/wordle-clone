package org.personal.Wordle.logic;
import org.personal.Wordle.domain.AttemptManager;
import org.personal.Wordle.domain.WordManager;

import java.io.IOException;
import java.util.Arrays;

public class GameLogic {
    private AttemptManager attempts;
    private WordManager words;

    public GameLogic() throws IOException {
        this.words=new WordManager();
        this.attempts=new AttemptManager();
    }
    public GameLogic(boolean isTesting) throws IOException {
        this.words=new WordManager(isTesting);
        this.attempts=new AttemptManager();
    }

    public AttemptManager attemptHandler() { return this.attempts; }
    public WordManager wordManager() { return this.words; }

    public boolean guessWord(String word) {
        String input = sanitizeInput(word);

        if (!this.isInputLengthValid(input)) {
            return false;
        }

        if (!this.isInputInDictionary(input)) {
            return false;
        }

        if (!words.getDailyWord().equals(input)) {
            attempts.incrementAttempt();
        }

        System.out.println(indicate(input));
        return words.getDailyWord().equals(input);
    }

    private boolean isInputLengthValid(String input) {
        if (input.isEmpty()) {
            System.out.println("Input must not be empty!");
            return false;
        }

        if (input.length()!=5) {
            System.out.println("Input must be a 5 letter word!");
            return false;
        }

        return true;
    }
    private boolean isInputInDictionary(String input) {
        if (!words.isInWordsFile(input)) {
            System.out.println("Input must be an actual word!");
            return false;
        }

        return true;
    }

    public String indicate(String input) {
        StringBuilder indicator = new StringBuilder();
        char[] inputToChar = input.toCharArray();
        char[] dailyToChar = words.getDailyWord().toCharArray();

        for (int i = 0; i<input.length(); i++) {
            if (inputToChar[i]==dailyToChar[i]) {
                indicator.append(inputToChar[i]).append("/ ");
                dailyToChar[i]='*';
                continue;
            }

            if (words.contains(inputToChar[i], dailyToChar)) {
                for (int j = 0;j<input.length(); j++) {
                    if (inputToChar[i]==dailyToChar[j]) {
                        indicator.append(inputToChar[i]).append("* ");
                        dailyToChar[j]='*';
                    }
                }
                continue;
            }

            indicator.append(inputToChar[i]).append(" ");
            // if input letter is not equal to the current daily letter,
            // but input letter is in the daily word, indicate
            // FIXME: more than 1 letter will be indicated even tho there's only 1 instance of the letter in the daily word
        }
        return indicator.toString();
    }

    public static String sanitizeInput(String input) {
        String inputToUpper = input.toUpperCase();
        return inputToUpper.trim();
    }
}
