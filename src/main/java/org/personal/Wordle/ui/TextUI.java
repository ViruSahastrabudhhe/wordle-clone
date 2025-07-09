package org.personal.Wordle.ui;

import org.personal.Wordle.domain.WordManager;
import org.personal.Wordle.logic.GameLogic;
import java.util.Scanner;

public class TextUI {
    private Scanner sc;
    private GameLogic gl;
    private boolean isTesting;

    public TextUI(Scanner sc, GameLogic gl) {
        this.sc=sc;
        this.gl=gl;
    }
    public TextUI(Scanner sc, GameLogic gl, boolean isTesting) {
        this(sc, gl);
        this.isTesting=isTesting;
    }

    public void start() {
        System.out.println("Guess the daily word!");
        if (isTesting) {
            System.out.println("Testing purposes: " + gl.wordManager().getDailyWord());
        }
        System.out.println();

        while (true) {
            if (gl.attemptManager().isGameOver()) {
                break;
            }

            System.out.print(gl.attemptManager().getAttempts() + ": ");
            String input = sc.nextLine();
            String sanitizedInput = WordManager.sanitizeInput(input);

            if (input.equals("quit")) {
                break;
            }

            if (gl.guessWord(sanitizedInput)) {
                System.out.println(indicate(sanitizedInput));
                break;
            }

            showErrors(sanitizedInput);
        }

        gameOver();
    }

    public void showErrors(String input) {
        if (input.isEmpty()) {
            System.out.println("Input must not be empty!");
            return;
        }

        if (!gl.isInputLengthValid(input)) {
            System.out.println("Input must be a 5-letter word!");
            return;
        }

        if (!gl.isInputInDictionary(input)) {
            System.out.println("Input must be a valid word!");
            return;
        }

        System.out.println(indicate(input));
    }

    public String indicate(String input) {
        StringBuilder indicator = new StringBuilder();
        char[] inputToChar = input.toCharArray();
        char[] dailyToChar = gl.wordManager().getDailyWord().toCharArray();

        for (int i = 0; i<input.length(); i++) {
            if (inputToChar[i]==dailyToChar[i]) {
                indicator.append(inputToChar[i]).append("/ ");
                dailyToChar[i]='*';
                continue;
            }

            if (gl.wordManager().isInDailyWord(inputToChar[i], dailyToChar)) {
                for (int j = 0;j<input.length(); j++) {
                    if (inputToChar[i]==dailyToChar[j]) {
                        indicator.append(inputToChar[i]).append("* ");
                        dailyToChar[j]='*';
                    }
                }
                continue;
            }

            indicator.append(inputToChar[i]).append(" ");
        }
        return indicator.toString();
    }

    public void gameOver() {
        StringBuilder endText = new StringBuilder();
        System.out.println();
        endText.append("You guessed the correct word!\n")
                .append("Total attempts: ").append(gl.attemptManager().getAttempts())
                .append("\nThe word of the day is ")
                .append(gl.wordManager().getDailyWord()).append(" ")
                .append("\nSee you tomorrow!");

        if (gl.attemptManager().isGameOver()) {
            endText.append("You failed to guess the correct word!\n")
                    .append("Total attempts: ").append(gl.attemptManager().getAttempts())
                    .append("\nThe word of the day is ")
                    .append(gl.wordManager().getDailyWord()).append(" ")
                    .append("\nSee you tomorrow!");
        }

        System.out.println(endText);
    }

}
