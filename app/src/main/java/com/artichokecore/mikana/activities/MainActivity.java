package com.artichokecore.mikana.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.artichokecore.mikana.R;
import com.artichokecore.mikana.data.structures.KanaManager;
import com.artichokecore.mikana.dialog.InfoDialog;
import com.artichokecore.mikana.score.Score;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;

import static com.artichokecore.mikana.activities.InitActivity.mInterstitialAd;

public final class MainActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private KanaManager kanaManager;
    private Score score;

    private TextView kanaView, answerView, scoreView;
    private ProgressBar progressBar;
    private boolean doubleBackToExitPressedOnce = false;

    private boolean hasSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setLinearLayout((LinearLayout) findViewById(R.id.linear_layout));
        setKanaView((TextView) findViewById(R.id.kanaView));
        setAnswerView((TextView) findViewById(R.id.answerView));
        setScoreView((TextView) findViewById(R.id.scoreView));
        setProgressBar((ProgressBar) findViewById(R.id.progressBar));

        kanaManager = KanaManager.getInstance();
        score = Score.getInstance(getApplicationContext());

        onNextPressed(getKanaView());

        score.restart();
        getScoreView().setText(score.toString());

        setClickListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(hasSelected) {
            startAdd();
            onNextPressed(getKanaView());

            score.restart();
            getScoreView().setText(score.toString());
        }

        hasSelected = false;
    }

    private void startAdd(){
        mInterstitialAd.setAdListener(new AdListener() {

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice(getString(R.string.ID_Device_danllopis)).build());
            }
        });

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
    }

    private void setClickListeners() {
        View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onShowPressed(view);
                return true;
            }
        };

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onNextPressed(view);
            }
        };

        getLinearLayout().setOnLongClickListener(longClickListener);

        getKanaView().setOnLongClickListener(longClickListener);
        getKanaView().setOnClickListener(clickListener);

        getScoreView().setOnLongClickListener(longClickListener);
        getScoreView().setOnClickListener(clickListener);

        getProgressBar().setOnLongClickListener(longClickListener);
        getProgressBar().setOnClickListener(clickListener);
    }

    public void onSelectPressed(View view) {
        hasSelected = true;
        Intent intent = new Intent(this, SelectActivity.class);
        startActivity(intent);
    }

    public void onShowPressed(View view) {

        score.infoButtonPressed();

        getScoreView().setText(score.toString());
        getProgressBar().setProgress(score.getProgress());

        getAnswerView().setText(kanaManager.getCurrentKana().getRomaji());
    }

    public void onNextPressed(View view) {
        getAnswerView().setText("");
        kanaManager.selectRandomKana();
        getKanaView().setText(kanaManager.getCurrentKana().getKanaChar());

        score.nextButtonPressed();
        getScoreView().setText(score.toString());

        getProgressBar().setProgress(score.getProgress());
    }

    public void onInfoPressed(View view){
        DialogFragment infoDialog = new InfoDialog();
        infoDialog.show(getSupportFragmentManager(), "Dialog");
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.exit, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
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
