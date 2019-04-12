package me.kabi404.mikana.score;

public final class Score {

    private int nextButton;
    private int infoButton;

    private static Score singleton;

    private Score() {
        nextButton = -1;
        infoButton = 0;
    }

    public static Score getInstance() {
        if(Score.singleton == null)
            Score.singleton = new Score();

        return Score.singleton;
    }

    public void nextButtonPressed(){
        nextButton++;
    }

    public void infoButtonPressed() {
        infoButton++;
    }

    public int getScore() {
        return nextButton - (infoButton * 2);
    }

    public int getNextButton() {
        return nextButton - infoButton;
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
    }

    public int getProgress() {
        int total = nextButton;
        int success = nextButton - infoButton;
        return total == 0 ? 0 : (100 * success) / total;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("Next: ").append(getNextButton()).append("\n");
        builder.append("Show: ").append(getInfoButton()).append("\n");
        builder.append("Score: ").append(getScore());

        return builder.toString();
    }
}
