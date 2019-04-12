package me.kabi404.mikana;

import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;

import me.kabi404.mikana.adapter.KanaRowsAdapter;
import me.kabi404.mikana.model.KanaManager;
import me.kabi404.mikana.model.Syllabary;

public class SelectActivity extends AppCompatActivity {

    private ListView kanaRows;
    private RadioButton hiraganaRadio, katakanaRadio;
    KanaRowsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        kanaRows = (ListView) findViewById(R.id.kanaRows);
        adapter = new KanaRowsAdapter(this);
        kanaRows.setAdapter(adapter);

        hiraganaRadio = (RadioButton) findViewById(R.id.hiraganaRadio);
        katakanaRadio = (RadioButton) findViewById(R.id.katakanaRadio);

    }

    public void setToHiragana(View view) {
        KanaManager.getInstance().setCurrentSyllabary(Syllabary.HIRAGANA);
        adapter.notifyDataSetChanged();
    }

    public void setToKatakana(View view) {
        KanaManager.getInstance().setCurrentSyllabary(Syllabary.KATAKANA);
        adapter.notifyDataSetChanged();
    }

}
