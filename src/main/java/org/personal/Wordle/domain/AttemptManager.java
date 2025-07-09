package org.personal.Wordle.domain;

public class AttemptManager {
    private int attempts;

    public AttemptManager() {
        this.attempts=1;
    }

    public int getAttempts() {
        return this.attempts;
    }

    public void incrementAttempt() {
        this.attempts++;
    }

    public boolean isGameOver() {
        return this.attempts>6;
    }
}
