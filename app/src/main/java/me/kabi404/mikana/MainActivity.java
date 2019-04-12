package me.kabi404.mikana;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;

import me.kabi404.mikana.model.KanaManager;
import me.kabi404.mikana.score.Score;

public final class MainActivity extends AppCompatActivity {

    private static final String SYLLABARIES_FILE_NAME = "syllabaries.json";

    private KanaManager kanaManager;
    private Score score;

    private TextView kanaView;
    private TextView answerView;
    private TextView scoreView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        kanaView = findViewById(R.id.kanaView);
        answerView = findViewById(R.id.answerView);
        scoreView = findViewById(R.id.scoreView);

        score = Score.getInstance();
        score.restart();
        scoreView.setText(score.toString());

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
    }

    public void onSelectPressed(View view) {
        Intent intent = new Intent(this, SelectActivity.class);
        startActivity(intent);
    }

    public void onInfoPressed(View view) {
        answerView.setText(kanaManager.getCurrentKana().getRomaji());

        score.infoButtonPressed();
        scoreView.setText(score.toString());
    }

    public void onNextPressed(View view) {
        answerView.setText("");
        kanaManager.selectRandomKana(KanaManager.WITH_REPETITION);
        kanaView.setText(kanaManager.getCurrentKana().getKanaChar());

        score.nextButtonPressed();
        scoreView.setText(score.toString());
    }

}
