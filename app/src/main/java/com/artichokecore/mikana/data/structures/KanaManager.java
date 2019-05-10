package com.artichokecore.mikana.data.structures;

import android.content.Context;

import com.artichokecore.mikana.config.Path;
import com.artichokecore.mikana.config.StaticConfig;
import com.artichokecore.mikana.data.facade.KanaFacade;
import com.artichokecore.mikana.data.model.Kana;
import com.artichokecore.mikana.data.model.Syllabary;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

public final class KanaManager {

    private Kana currentKana;
    private Syllabary currentSyllabary;

    private KanaMatrix kanaMatrix;
    private KanaSelector selector;

    private static KanaManager singleton;

    /**
     * Constructor. Initialize all data structures and load JSON from local data.
     * @param kanaStream InputStream pointing to file with all static data.
     * @exception IOException On input error.
     * @exception JSONException Data format error.
     */
    private KanaManager(InputStream kanaStream) throws IOException, JSONException {
        setKanaMatrix(new KanaMatrix());

        setSelector(new KanaSelector());
        loadDataFromJSON(kanaStream);

        setCurrentSyllabary(Syllabary.HIRAGANA);
    }

    /**
     * Singleton call method. Return KanaManager or create if it doesn't exist.
     * @param kanaStream InputStream pointing to file with all static data.
     * @exception IOException On input error.
     * @exception JSONException Data format error.
     * @return KanaManager Call to another method that return the static instance of KanaManager.
     */
    public static KanaManager getInstance(InputStream kanaStream) throws IOException, JSONException {
        if(singleton == null)
            singleton = new KanaManager(kanaStream);

        return KanaManager.getInstance();
    }

    public void loadSelectedKanas(Context context) {

        try {
            Syllabary loadSyl = KanaFacade.loadSelectedKanas(context, getKanaMatrix(), getSelector());
            setCurrentSyllabary(loadSyl);
        } catch (IOException e) {
            selectFirstRow();
        }
    }

    public void saveSelectedKanas(Context context) {

        KanaFacade.saveSelectedKanas(context, getCurrentSyllabary(), getSelector(), getKanaMatrix());
    }

    /**
     * Singleton call method.
     * @return KanaManager Thisd returns the static instance of KanaManager or null if it's not created.
     */
    public static KanaManager getInstance() {
        return singleton;
    }


    private void loadDataFromJSON(InputStream kanaStream) throws IOException, JSONException {
        setKanaMatrix(KanaFacade.loadKanaMatrix(kanaStream));
    }

    /**
     * Select the first row of Kanas of the current syllabary.
     */
    public void selectFirstRow() {
        List<Kana> firstRow = getKanaMatrix().getFirstRow(currentSyllabary);
        getSelector().selectKanas(firstRow);
    }

    /**
     * Change current kana selecting randomly a kana from selected kanas.
     * @return Kana returns current random kana.
     */
    public Kana selectRandomKana() {
        setCurrentKana(getSelector().getRandomKana());
        return getCurrentKana();
    }

    /**
     * Check if Kana it is included
     * @param kana to check if it is included as selected kana.
     * @return boolean This returns true if that kana is included in selected kanas.
     */
    public boolean isSelected(Kana kana) {
        return getSelector().getSelectedKanas().contains(kana);
    }

    /**
     * Check if Kana exist.
     * @param row pointer of kanaRows.
     * @param column pointer of kanaRows.
     * @return boolean This returns true if referenced kana exists.
     */
    public boolean exist(int row, int column) {
        return row < getKanaMatrix().getSyllabaryMatrix(getCurrentSyllabary()).size() &&
                column < getKanaMatrix().getKanaRow(row, getCurrentSyllabary()).size();
    }

    /**
     * Get especific kana from index.
     * @param row pointer of kanaRows.
     * @param column pointer of kanaRows.
     * @return Kana This returns referenced kana.
     */
    public Kana getKana(int row, int column) {
        return getKanaMatrix().getKanaByPos(row, column, getCurrentSyllabary());
    }

    /**
     * Add kana from index to selected kanas
     * @param selectedKanas
     */
    public void selectKanas(List<Kana> selectedKanas) {
        getSelector().selectKanas(selectedKanas);
    }

    public void selectKanas(List<String> selectedKanaChars, Syllabary syllabary) {
        List<Kana> selectedKanas = new LinkedList<>();

        for(String kanaChar: selectedKanaChars) {
            selectedKanas.add(getKanaMatrix().getKanaChar(kanaChar, syllabary));
        }

        selectKanas(selectedKanas);
    }


    // Getters & Setters

    public List<List<Kana>> getCurrentSyllabaryRows() {
        return getKanaMatrix().getSyllabaryMatrix(getCurrentSyllabary());
    }

    public Syllabary getCurrentSyllabary() {
        return currentSyllabary;
    }

    public void setCurrentSyllabary(Syllabary currentSyllabary) {
        this.currentSyllabary = currentSyllabary;
    }

    public Kana getCurrentKana() {
        return currentKana;
    }

    public void setCurrentKana(Kana currentKana) {
        this.currentKana = currentKana;
    }

    public KanaMatrix getKanaMatrix() {
        return kanaMatrix;
    }

    public void setKanaMatrix(KanaMatrix kanaMatrix) {
        this.kanaMatrix = kanaMatrix;
    }

    public KanaSelector getSelector() {
        return selector;
    }

    public void setSelector(KanaSelector selector) {
        this.selector = selector;
    }
}
