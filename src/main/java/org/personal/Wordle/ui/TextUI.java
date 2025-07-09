package org.personal.Wordle.ui;

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
            System.out.print(gl.attemptHandler().getAttempts() + ": ");
            String input = sc.nextLine();

            if (input.equals("quit")) {
                break;
            }

            if (gl.guessWord(input)) {
                break;
            }

            if (gl.attemptHandler().isGameOver()) {
                break;
            }
        }

        gameOver();
    }

    public void gameOver() {
        System.out.println();
        String endText="You guessed the correct word!\n" +
                    "The word of the day is " + gl.wordManager().getDailyWord() + " " +
                    "\nSee you tomorrow!";

        if (gl.attemptHandler().isGameOver()) {
            endText="You failed to guess the correct word!\n" +
                    "The word of the day is " + gl.wordManager().getDailyWord() + " " +
                    "\nSee you tomorrow!";
        }

        System.out.println(endText);
    }
}
