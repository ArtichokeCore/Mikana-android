package com.artichokecore.mikana.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.artichokecore.mikana.R;
import com.artichokecore.mikana.model.KanaManager;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;

public class InitActivity extends AppCompatActivity {

    private static final int INIT_DELAY = 2000;
    private static final String SYLLABARIES_FILE_NAME = "syllabaries.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                loadKanas();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, INIT_DELAY);

    }

    private void loadKanas() {

        try {
            InputStream syllabariesStream = getAssets().open(SYLLABARIES_FILE_NAME);

            KanaManager kanaManager;
            kanaManager = KanaManager.getInstance(syllabariesStream);
            kanaManager.loadSelectedKanas(getApplicationContext());

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }


    }
}
