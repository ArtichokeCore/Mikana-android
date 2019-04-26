package com.artichokecore.mikana.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public final class KanaManager {

    public static final boolean WITH_REPETITION = true;
    public static final boolean WITHOUT_REPETITION = false;

    private Kana currentKana, lastKana;
    private Syllabary currentSyllabary;

    private List<List<Kana>>[] kanaRows;

    private List<Kana> selectedKanas, unusedKanas;

    private static KanaManager singleton;

    /**
     * Constructor. Initialize all data structures and load JSON from local data.
     * @param kanaStream InputStream pointing to file with all static data.
     * @exception IOException On input error.
     * @exception JSONException Data format error.
     */
    private KanaManager(InputStream kanaStream) throws IOException, JSONException {
        setKanaRows(new ArrayList[2]);

        setSelectedKanas(new ArrayList<Kana>());
        setUnusedKanas(new LinkedList<Kana>());
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

    /**
     * Singleton call method.
     * @return KanaManager Thisd returns the static instance of KanaManager or null if it's not created.
     */
    public static KanaManager getInstance() {
        return singleton;
    }

    /**
     * Loads all Kana static data from InputStream and add to kanaRow.
     * @param kanaStream InputStream pointing to file with all static data.
     * @exception IOException On input error.
     * @exception JSONException Data format error.
     */
    private void loadDataFromJSON(InputStream kanaStream) throws IOException, JSONException {

        int streamSize = kanaStream.available();
        byte[] buffer = new byte[streamSize];
        kanaStream.read(buffer);
        kanaStream.close();

        String json = new String(buffer, "UTF-8");
        JSONObject allJSONData = new JSONObject(json);

        parseSyllabary(allJSONData.getJSONArray(Kana.HIRAGANA_ATTR), Syllabary.HIRAGANA);
        parseSyllabary(allJSONData.getJSONArray(Kana.KATAKANA_ATTR), Syllabary.KATAKANA);
    }

    /**
     * Add all kanas to kanaRow from JSONArray.
     * @param jsonSyllabary JSONObject with all Hiragana or Katakana rows.
     * @param syllabary Indicates the current syllabary.
     * @exception JSONException Data format error.
     */
    private void parseSyllabary(JSONArray jsonSyllabary, Syllabary syllabary) throws JSONException {
        for(int rowIndex = 0; rowIndex < jsonSyllabary.length(); rowIndex++) {
            List<Kana> kanaRow = new LinkedList<>();
            JSONArray jsonRow = jsonSyllabary.getJSONArray(rowIndex);
            for(int kanaIndex = 0; kanaIndex < jsonRow.length(); kanaIndex++) {
                JSONObject jsonKana = jsonRow.getJSONObject(kanaIndex);
                Kana loadedKana = new Kana(syllabary,
                        jsonKana.getString(Kana.JAPANESE_KANA_ATTR),
                        jsonKana.getString(Kana.ROMAJI_ATTR)
                );
                kanaRow.add(loadedKana);
            }
            this.kanaRows[syllabary.ordinal()].add(kanaRow);
        }
    }

    /**
     * Select the first row of Kanas of the current syllabary.
     */
    public void selectFirstRow() {

        getSelectedKanas().clear();
        getSelectedKanas().addAll(
                kanaRows[getCurrentSyllabaryOrd()].get(0));
        getUnusedKanas().clear();
    }

    /**
     * Change current kana selecting randomly a kana from selected kanas.
     * @param withRepetition a kana will not be returned again until all others have been returned at least once.
     * @exception JSONException Data format error.
     * @return Kana returns current random kana.
     */
    public Kana selectRandomKana(boolean withRepetition) {

        Random rnd = new Random();
        int randomIndex;

        if(withRepetition == WITH_REPETITION) {
            randomIndex = rnd.nextInt(getSelectedKanas().size());
            setLastKana(getCurrentKana());
            setCurrentKana(getSelectedKanas().get(randomIndex));
        } else {
            if(getUnusedKanas().isEmpty())
                reAddAllUnusedKanas();

            setLastKana(getCurrentKana());

            do {
                randomIndex = rnd.nextInt(getUnusedKanas().size());
                setCurrentKana(getUnusedKanas().get(randomIndex));
            } while (getLastKana() == getCurrentKana() && getUnusedKanas().size() > 1);

            getUnusedKanas().remove(randomIndex);
        }

        return getCurrentKana();
    }

    /**
     * Add all selectedKanas to unusedKanas
     */
    private void reAddAllUnusedKanas() {
        getUnusedKanas().clear();
        getUnusedKanas().addAll(getSelectedKanas());
    }

    /**
     * Clear selected kanas.
     */
    public void unselectKanas() {
        getSelectedKanas().clear();
        getUnusedKanas().clear();
    }

    /**
     * Check if Kana it is included
     * @param kana to check if it is included as selected kana.
     * @return boolean This returns true if that kana is included in selected kanas.
     */
    public boolean isSelected(Kana kana) {
        return getSelectedKanas().contains(kana);
    }

    /**
     * Check if Kana it is included
     * @param row pointer of kanaRows.
     * @param column pointer of kanaRows.
     * @return boolean This returns true if referenced kana is included in selected kanas.
     */
    public boolean isSelected(int row, int column) {
        return isSelected(getKana(row, column));
    }

    /**
     * Check if Kana exist.
     * @param row pointer of kanaRows.
     * @param column pointer of kanaRows.
     * @return boolean This returns true if referenced kana exists.
     */
    public boolean exist(int row, int column) {
        return row < getKanaRows()[getCurrentSyllabaryOrd()].size() &&
                column < getKanaRows()[getCurrentSyllabaryOrd()].get(row).size();
    }

    /**
     * Get especific kana from index.
     * @param row pointer of kanaRows.
     * @param column pointer of kanaRows.
     * @return Kana This returns referenced kana.
     */
    public Kana getKana(int row, int column) {
        return getKanaRows()[getCurrentSyllabaryOrd()].get(row).get(column);
    }

    /**
     * Add kana from index to selected kanas
     * @param row pointer of kanaRows.
     * @param column pointer of kanaRows.
     */
    public void selectKana(int row, int column) {
        getSelectedKanas().add(getKanaRows()[getCurrentSyllabaryOrd()]
                .get(row).get(column)
        );
    }

    // Getters & Setters

    public List<List<Kana>>[] getKanaRows() {
        return kanaRows;
    }

    public List<List<Kana>> getCurrentSyllabaryRows() {
        return kanaRows[getCurrentSyllabaryOrd()];
    }

    private void setKanaRows(List<List<Kana>>[] kanaRows) {
        for(int i = 0; i < kanaRows.length; i++) {
            kanaRows[i] = new ArrayList<>();
        }
        this.kanaRows = kanaRows;
    }

    public Syllabary getCurrentSyllabary() {
        return currentSyllabary;
    }

    /**
     * Change current kana selecting randomly a kana from selected kanas.
     * @return int returns current Syllabary ordinal number. Katakana = 1, Hiragana = 0.
     */
    public int getCurrentSyllabaryOrd() {
        return currentSyllabary.ordinal();
    }

    public void setCurrentSyllabary(Syllabary currentSyllabary) {
        this.currentSyllabary = currentSyllabary;
    }

    public List<Kana> getSelectedKanas() {
        return selectedKanas;
    }

    private void setSelectedKanas(List<Kana> selectedKanas) {
        this.selectedKanas = selectedKanas;
    }

    public List<Kana> getUnusedKanas() {
        return unusedKanas;
    }

    public void setUnusedKanas(List<Kana> unusedKanas) {
        this.unusedKanas = unusedKanas;
    }

    public Kana getCurrentKana() {
        return currentKana;
    }

    public void setCurrentKana(Kana currentKana) {
        this.currentKana = currentKana;
    }

    public Kana getLastKana() {
        return lastKana;
    }

    public void setLastKana(Kana lastKana) {
        this.lastKana = lastKana;
    }
}
