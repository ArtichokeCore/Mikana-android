package com.artichokecore.mikana.score;

import android.content.Context;

import com.artichokecore.mikana.R;

public final class Score {

    private int nextButton;
    private int infoButton;
    private boolean hasFailed;

    private static Score singleton;
    private Context context;

    private Score(Context context) {
        this.context = context;
        restart();
    }

    /**
     * Singleton call method. Return Score or create if it doesn't exist.
     * @return Score singleton reference.
     */
    public static Score getInstance(Context context) {
        if(Score.singleton == null) {
            Score.singleton = new Score(context);

        }

        return Score.singleton;
    }

    /**
     * Call this method when user press "next" button. Adds 1 point only if last action wasn't failed (info pressed).
     */
    public void nextButtonPressed(){
        if(!hasFailed)
            nextButton++;
        else
            hasFailed = false;
    }

    /**
     * Call this method when user press "info" button. Quits 1 point only if last action wasn't failed (info pressed).
     */
    public void infoButtonPressed() {
        if(!hasFailed)
            infoButton++;

        hasFailed = true;
    }

    /**
     * @return Success point - Fail points.
     */
    public int getScore() {
        return nextButton - infoButton;
    }

    public int getNextButton() {
        return nextButton;
    }

    public int getInfoButton() {
        return infoButton;
    }

    /**
     * Restart all score values.
     */
    public void restart() {
        nextButton = 0;
        infoButton = 0;
        hasFailed = false;
    }

    /**
     * Calculate progress between 0 and 100.
     * @return int score number between 0 and 100.
     */
    public int getProgress() {
        return getProgress(100);
    }

    /**
     * Singleton call method. Return Score or create if it doesn't exist.
     * @param maxValue max posible value of score representation.
     * @return int score number between 0 and specified value.
     */
    public int getProgress(int maxValue) {
        int total = nextButton;
        int success = nextButton - infoButton;
        return total == 0 ? 0 : (maxValue * success) / total;
    }

    /**
     * Generate read-friendly score resume.
     * @return String score data.
     */
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(context.getResources().getString(R.string.success)+": ").append(getNextButton()).append("\n");
        builder.append(context.getResources().getString(R.string.failed)+": ").append(getInfoButton()).append("\n");
        builder.append(context.getResources().getString(R.string.score)+": ").append(getScore());

        return builder.toString();
    }
}
