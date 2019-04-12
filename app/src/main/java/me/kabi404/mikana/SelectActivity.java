package me.kabi404.mikana;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import me.kabi404.mikana.adapter.KanaRowsAdapter;
import me.kabi404.mikana.model.Kana;
import me.kabi404.mikana.model.KanaManager;
import me.kabi404.mikana.model.Syllabary;

public class SelectActivity extends AppCompatActivity {

    private KanaManager kanaManager;
    private ListView kanaRows;
    private RadioButton hiraganaRadio, katakanaRadio;
    KanaRowsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        kanaManager = KanaManager.getInstance();

        kanaRows = (ListView) findViewById(R.id.kanaRows);
        adapter = new KanaRowsAdapter(this);
        kanaRows.setAdapter(adapter);

        hiraganaRadio = (RadioButton) findViewById(R.id.hiraganaRadio);
        katakanaRadio = (RadioButton) findViewById(R.id.katakanaRadio);

    }

    public void onUpdatePressed(View view) {

        Syllabary currentSyllabary = hiraganaRadio.isChecked() ? Syllabary.HIRAGANA : Syllabary.KATAKANA;
        kanaManager.setCurrentSyllabary(currentSyllabary);
        kanaManager.setSelectedKanas(new LinkedList<Kana>());

        for(int rowIndex = 0; rowIndex < kanaRows.getChildCount(); rowIndex++) {
            View rowView = kanaRows.getChildAt(rowIndex);

            CheckBox[] checks = new CheckBox[5];

            checks[0] = rowView.findViewById(R.id.check1);
            checks[1] = rowView.findViewById(R.id.check2);
            checks[2] = rowView.findViewById(R.id.check3);
            checks[3] = rowView.findViewById(R.id.check4);
            checks[4] = rowView.findViewById(R.id.check5);

            List<Kana> row = kanaManager.getCurrentSyllabaryRows().get(rowIndex);

            for(int columnIndex = 0; columnIndex < row.size(); columnIndex++) {
                if(checks[columnIndex].isChecked()) {
                    kanaManager.getSelectedKanas().add(row.get(columnIndex));
                }
            }

        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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
