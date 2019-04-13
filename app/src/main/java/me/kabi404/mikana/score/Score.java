package me.kabi404.mikana.score;

public final class Score {

    private int nextButton;
    private int infoButton;
    private boolean hasFailed;

    private static Score singleton;

    private Score() {
        nextButton = -1;
        infoButton = 0;
        hasFailed = false;
    }

    public static Score getInstance() {
        if(Score.singleton == null)
            Score.singleton = new Score();

        return Score.singleton;
    }

    public void nextButtonPressed(){
        if(!hasFailed)
            nextButton++;
        else
            hasFailed = false;
    }

    public void infoButtonPressed() {
        infoButton++;
        hasFailed = true;
    }

    public int getScore() {
        return nextButton - infoButton;
    }

    public int getNextButton() {
        return nextButton;
    }

    public void setNextButton(int nextButton) {
        this.nextButton = nextButton;
    }

    public int getInfoButton() {
        return infoButton;
    }

    public void setInfoButton(int infoButton) {
        this.infoButton = infoButton;
    }

    public void restart() {
        nextButton = -1;
        infoButton = 0;
        hasFailed = false;
    }

    public int getProgress() {
        return getProgress(100);
    }

    public int getProgress(int maxValue) {
        int total = nextButton;
        int success = nextButton - infoButton;
        return total == 0 ? 0 : (maxValue * success) / total;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("Success: ").append(getNextButton()).append("\n");
        builder.append("Failed: ").append(getInfoButton()).append("\n");
        builder.append("Score: ").append(getScore());

        return builder.toString();
    }
}
