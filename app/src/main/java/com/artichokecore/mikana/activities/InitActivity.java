package com.artichokecore.mikana.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.artichokecore.mikana.R;
import com.artichokecore.mikana.config.Path;
import com.artichokecore.mikana.config.StaticConfig;
import com.artichokecore.mikana.data.structures.KanaManager;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;

public final class InitActivity extends AppCompatActivity {

    public static InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //if(!StaticConfig.IS_PRO_VERSION)
                    addAds();

                loadKanas();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                setTheme(R.style.AppTheme_NoActionBar);
                finish();
            }
        }, StaticConfig.INIT_DELAY);

    }

    private void loadKanas() {
        try {
            InputStream syllabariesStream = getAssets().open(Path.SYLLABARIES_FILE_NAME);

            KanaManager kanaManager;
            kanaManager = KanaManager.getInstance(syllabariesStream);
            kanaManager.loadSelectedKanas(getApplicationContext());

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private void addAds() {
        MobileAds.initialize(this, getString(R.string.ID_App_AdMob));
        mInterstitialAd = new InterstitialAd(getApplicationContext());
        mInterstitialAd.setAdUnitId(getString(R.string.ID_Interstitial));
        mInterstitialAd.loadAd(new AdRequest.Builder()
                .addTestDevice(getString(R.string.ID_Device_danllopis))
                .build());

        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
            }
        });
    }
}
