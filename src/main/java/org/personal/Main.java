package org.personal;
import org.personal.Wordle.logic.GameLogic;
import org.personal.Wordle.ui.TextUI;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        testGame();
    }

    public static void testGame() throws IOException {
        GameLogic gl = new GameLogic(true);
        Scanner sc = new Scanner(System.in);
        TextUI ui = new TextUI(sc, gl, true);

        /*
        wordle with increasing difficulty
        guess x letter word with y guesses, where x=input and y=x.length()+1
        create a dictionary file with how many x letter words
        prevent players from inputting non-words like asdxz, xlkams
        choose that dictionary file and play it with y guesses
        every dictionary file can only be played once per day

        using java, java swing for ui, mysql for database
        will upgrade to springboot and react, maybe
         */
        ui.start();
    }
}