package me.kabi404.mikana.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;

import me.kabi404.mikana.R;
import me.kabi404.mikana.model.KanaManager;
import me.kabi404.mikana.score.Score;

public final class MainActivity extends AppCompatActivity {

    private static final String SYLLABARIES_FILE_NAME = "syllabaries.json";
    private static final String EMPTY = "";

    private KanaManager kanaManager;
    private Score score;

    private TextView kanaView, answerView, scoreView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setKanaView((TextView) findViewById(R.id.kanaView));
        setAnswerView((TextView) findViewById(R.id.answerView));
        setScoreView((TextView) findViewById(R.id.scoreView));
        setProgressBar((ProgressBar) findViewById(R.id.progressBar));

        score = Score.getInstance();

        kanaManager = KanaManager.getInstance();

        if(kanaManager == null) {
            try {
                InputStream syllabariesStream = getAssets().open(SYLLABARIES_FILE_NAME);
                kanaManager = KanaManager.getInstance(syllabariesStream);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }

        if(kanaManager.getSelectedKanas().isEmpty())
            kanaManager.selectFirstRow();

        onNextPressed(null);

        score.restart();
        getScoreView().setText(score.toString());
    }

    public void onSelectPressed(View view) {
        Intent intent = new Intent(this, SelectActivity.class);
        startActivity(intent);
    }

    public void onInfoPressed(View view) {

        score.infoButtonPressed();

        getScoreView().setText(score.toString());
        getProgressBar().setProgress(score.getProgress());

        getAnswerView().setText(kanaManager.getCurrentKana().getRomaji());
    }

    public void onNextPressed(View view) {
        getAnswerView().setText(EMPTY);
        kanaManager.selectRandomKana(KanaManager.WITHOUT_REPETITION);
        getKanaView().setText(kanaManager.getCurrentKana().getKanaChar());

        score.nextButtonPressed();
        getScoreView().setText(score.toString());

        getProgressBar().setProgress(score.getProgress());
    }

    // Getters & Setters

    public TextView getKanaView() {
        return kanaView;
    }

    public void setKanaView(TextView kanaView) {
        this.kanaView = kanaView;
    }

    public TextView getAnswerView() {
        return answerView;
    }

    public void setAnswerView(TextView answerView) {
        this.answerView = answerView;
    }

    public TextView getScoreView() {
        return scoreView;
    }

    public void setScoreView(TextView scoreView) {
        this.scoreView = scoreView;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }
}
