package org.personal.Wordle.logic;
import org.personal.Wordle.domain.AttemptManager;
import org.personal.Wordle.domain.WordManager;

import java.io.IOException;

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

    public AttemptManager attemptManager() { return this.attempts; }
    public WordManager wordManager() { return this.words; }

    public boolean guessWord(String input) {
        if (input.isEmpty()) {
            return false;
        }
        if (!isInputLengthValid(input)) {
            return false;
        }
        if (!isInputInDictionary(input)) {
            return false;
        }

        if (!words.getDailyWord().equals(input)) {
            attempts.incrementAttempt();
        }

        return words.getDailyWord().equals(input);
    }

    public boolean isInputLengthValid(String input) {
        return input.length()==5;
    }

    public boolean isInputInDictionary(String input) {
        return words.isInWordsFile(input);
    }
}
