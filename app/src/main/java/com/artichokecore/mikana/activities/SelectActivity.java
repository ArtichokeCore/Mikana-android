package com.artichokecore.mikana.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.artichokecore.mikana.R;
import com.artichokecore.mikana.adapter.KanaRowsAdapter;
import com.artichokecore.mikana.data.structures.KanaManager;
import com.artichokecore.mikana.data.model.Syllabary;
import java.util.List;

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
        List<String> selectedKanas = getAdapter().getTemporalCheckdKanas();

        if(selectedKanas.size() <= 1)
            kanaManager.selectFirstRow();
        else
            kanaManager.selectKanas(selectedKanas, currentSyllabary);

        kanaManager.saveSelectedKanas(getApplicationContext());

        finish();
        Toast.makeText(this, getResources().getText(R.string.updateToast), Toast.LENGTH_SHORT).show();
    }

    public void onCheckPressed(View view) {
        CheckBox checkBox = (CheckBox) view;
        if(checkBox.isChecked())
            kanaManager.getSelector().getTemporalSelectedKanas().add(String.valueOf(checkBox.getText()));
        else
            kanaManager.getSelector().getTemporalSelectedKanas().remove(String.valueOf(checkBox.getText()));
    }

    public void setToHiragana(View view) {
        kanaManager.setCurrentSyllabary(Syllabary.HIRAGANA);
        getAdapter().getTemporalCheckdKanas().clear();
        adapter.notifyDataSetChanged();
    }

    public void setToKatakana(View view) {
        kanaManager.setCurrentSyllabary(Syllabary.KATAKANA);
        getAdapter().getTemporalCheckdKanas().clear();
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
