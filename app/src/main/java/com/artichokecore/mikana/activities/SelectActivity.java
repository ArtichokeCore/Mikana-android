package com.artichokecore.mikana.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.artichokecore.mikana.R;
import com.artichokecore.mikana.adapter.KanaRowsAdapter;
import com.artichokecore.mikana.config.StaticConfig;
import com.artichokecore.mikana.model.KanaManager;
import com.artichokecore.mikana.model.Syllabary;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import static com.artichokecore.mikana.activities.MainActivity.mInterstitialAd;

public final class SelectActivity extends AppCompatActivity implements View.OnClickListener {

    private KanaManager kanaManager;

    private ListView kanaRowsView;
    private RadioButton hiraganaRadio, katakanaRadio;
    private KanaRowsAdapter adapter;
    private CardView updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        kanaManager = KanaManager.getInstance();

        updateButton = findViewById(R.id.updateButton);
        updateButton.setOnClickListener(this);

        setKanaRowsView((ListView) findViewById(R.id.kanaRows));
        setAdapter(new KanaRowsAdapter(this));

        setHiraganaRadio((RadioButton) findViewById(R.id.hiraganaRadio));
        setKatakanaRadio((RadioButton) findViewById(R.id.katakanaRadio));

        if(kanaManager.getCurrentSyllabary() == Syllabary.HIRAGANA)
            getHiraganaRadio().setChecked(true);
        else
            getKatakanaRadio().setChecked(true);

    }

    public void onUpdatePressed(View view) {

        Syllabary currentSyllabary = getHiraganaRadio().isChecked() ? Syllabary.HIRAGANA : Syllabary.KATAKANA;
        kanaManager.setCurrentSyllabary(currentSyllabary);
        kanaManager.unselectKanas();

        for(int rowIndex = 0; rowIndex < getKanaRowsView().getChildCount(); rowIndex++) {
            View rowView = getKanaRowsView().getChildAt(rowIndex);

            CheckBox[] checks = new CheckBox[5];

            checks[0] = rowView.findViewById(R.id.check1);
            checks[1] = rowView.findViewById(R.id.check2);
            checks[2] = rowView.findViewById(R.id.check3);
            checks[3] = rowView.findViewById(R.id.check4);
            checks[4] = rowView.findViewById(R.id.check5);

            for(int columnIndex = 0; columnIndex < checks.length; columnIndex++) {
                if(checks[columnIndex].isChecked()) {
                    kanaManager.selectKana(rowIndex, columnIndex);
                }
            }
        }

        kanaManager.saveSelectedKanas(getApplicationContext());

        mInterstitialAd.setAdListener(new AdListener(){

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });

        if(!StaticConfig.IS_PRO_VERSION && mInterstitialAd.isLoaded()){
            mInterstitialAd.show();
        }else
            Log.d("TAG", "The interstitial wasn't loaded yet.");

        finish();

        Toast.makeText(this, getResources().getText(R.string.updateToast), Toast.LENGTH_SHORT).show();
    }

    public void setToHiragana(View view) {
        kanaManager.setCurrentSyllabary(Syllabary.HIRAGANA);
        adapter.notifyDataSetChanged();
    }

    public void setToKatakana(View view) {
        kanaManager.setCurrentSyllabary(Syllabary.KATAKANA);
        adapter.notifyDataSetChanged();
    }

    // Getters & Setters

    public ListView getKanaRowsView() {
        return kanaRowsView;
    }

    public void setKanaRowsView(ListView kanaRowsView) {
        this.kanaRowsView = kanaRowsView;
    }

    public RadioButton getHiraganaRadio() {
        return hiraganaRadio;
    }

    public void setHiraganaRadio(RadioButton hiraganaRadio) {
        this.hiraganaRadio = hiraganaRadio;
    }

    public RadioButton getKatakanaRadio() {
        return katakanaRadio;
    }

    public void setKatakanaRadio(RadioButton katakanaRadio) {
        this.katakanaRadio = katakanaRadio;
    }

    public KanaRowsAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(KanaRowsAdapter adapter) {
        this.adapter = adapter;
        getKanaRowsView().setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.updateButton:
                onUpdatePressed(v);
        }
    }
}
