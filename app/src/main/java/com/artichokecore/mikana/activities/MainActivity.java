package com.artichokecore.mikana.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;

import com.artichokecore.mikana.R;
import com.artichokecore.mikana.model.KanaManager;
import com.artichokecore.mikana.score.Score;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public final class MainActivity extends AppCompatActivity {

    private static final String SYLLABARIES_FILE_NAME = "syllabaries.json";
    private static final String EMPTY = "";

    private LinearLayout linearLayout;
    private KanaManager kanaManager;
    private Score score;

    private TextView kanaView, answerView, scoreView;
    private ProgressBar progressBar;

    public static InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.SplashTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, getString(R.string.ID_App_AdMob));
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.ID_Interstitial));
        mInterstitialAd.loadAd(new AdRequest.Builder()
                .addTestDevice(getString(R.string.ID_Device_mpardalm))
                .addTestDevice(getString(R.string.ID_Device_danllopis))
                .build());

        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
            }
        });

        setLinearLayout((LinearLayout) findViewById(R.id.linear_layout));
        setKanaView((TextView) findViewById(R.id.kanaView));
        setAnswerView((TextView) findViewById(R.id.answerView));
        setScoreView((TextView) findViewById(R.id.scoreView));
        setProgressBar((ProgressBar) findViewById(R.id.progressBar));

        score = Score.getInstance(getApplicationContext());

        kanaManager = KanaManager.getInstance();

        if(kanaManager == null) {
            try {
                InputStream syllabariesStream = getAssets().open(SYLLABARIES_FILE_NAME);
                kanaManager = KanaManager.getInstance(syllabariesStream);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }

        kanaManager.loadSelectedKanas(getApplicationContext());

        onNextPressed(null);

        score.restart();
        getScoreView().setText(score.toString());


        setClickListeners();

    }

    private void setClickListeners() {
        View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onInfoPressed(view);
                return true;
            }
        };

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onNextPressed(view);
            }
        };


        linearLayout.setOnLongClickListener(longClickListener);

        getKanaView().setOnLongClickListener(longClickListener);
        getKanaView().setOnClickListener(clickListener);

        getScoreView().setOnLongClickListener(longClickListener);
        getScoreView().setOnClickListener(clickListener);

        getProgressBar().setOnLongClickListener(longClickListener);
        getProgressBar().setOnClickListener(clickListener);
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

    public LinearLayout getLinearLayout(){
        return linearLayout;
    }

    public void setLinearLayout(LinearLayout linearLayout){
        this.linearLayout = linearLayout;
    }
}
