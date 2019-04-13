package me.kabi404.mikana.model;

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

    private static final int ROW_FIRST_KANA_INDEX = 0;

    public static final boolean WITH_REPETITION = true;
    public static final boolean WITHOUT_REPETITION = false;

    private Kana currentKana;
    private Kana lastKana;

    private List<List<Kana>>[] kanaRows;
    private Syllabary currentSyllabary;

    private List<Kana> selectedKanas;
    private List<Kana> unusedKanas;

    private static KanaManager singleton;

    private KanaManager(InputStream kanaStream) throws IOException, JSONException {
        setKanaRows(new ArrayList[2]);
        setCurrentSyllabary(Syllabary.HIRAGANA);
        setSelectedKanas(new ArrayList<Kana>());
        setUnusedKanas(new LinkedList<Kana>());

        loadDataFromJSON(kanaStream);
    }

    public static KanaManager getInstance(InputStream kanaStream) throws IOException, JSONException {
        if(singleton == null)
            singleton = new KanaManager(kanaStream);

        return singleton;
    }

    public static KanaManager getInstance() {
        return singleton;
    }

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

    public void selectAllSyllabary(Syllabary syllabary) {

        setSelectedKanas(new ArrayList<Kana>());

        for(int rowIndex = 0; rowIndex < this.kanaRows[syllabary.ordinal()].size(); rowIndex++) {
            selectedKanas.addAll(kanaRows[syllabary.ordinal()].get(rowIndex));
        }

        setUnusedKanas(new LinkedList<Kana>());
    }

    public void selectFirstRow() {

        setSelectedKanas(new ArrayList<Kana>());

        selectedKanas.addAll(kanaRows[currentSyllabary.ordinal()].get(ROW_FIRST_KANA_INDEX));

        setUnusedKanas(new LinkedList<Kana>());
    }

    public void selectRandomKana(boolean withRepetition) {

        Random rnd = new Random();
        int randomIndex;

        if(withRepetition == WITH_REPETITION) {
            randomIndex = rnd.nextInt(selectedKanas.size());
            lastKana = currentKana;
            currentKana = selectedKanas.get(randomIndex);
        } else {
            if(unusedKanas.isEmpty())
                reAddAllUnusedKanas();

            lastKana = currentKana;

            do {
                randomIndex = rnd.nextInt(unusedKanas.size());
                currentKana = unusedKanas.get(randomIndex);
            } while (lastKana == currentKana && unusedKanas.size() > 1);

            unusedKanas.remove(randomIndex);
        }
    }

    private void reAddAllUnusedKanas() {
        unusedKanas.clear();
        unusedKanas.addAll(selectedKanas);
    }

    // Getters & Setters

    public List<List<Kana>>[] getKanaRows() {
        return kanaRows;
    }

    public List<List<Kana>> getCurrentSyllabaryRows() {
        return kanaRows[currentSyllabary.ordinal()];
    }

    public void setKanaRows(List<List<Kana>>[] kanaRows) {
        for(int i = 0; i < kanaRows.length; i++) {
            kanaRows[i] = new ArrayList<List<Kana>>();
        }
        this.kanaRows = kanaRows;
    }

    public Syllabary getCurrentSyllabary() {
        return currentSyllabary;
    }

    public void setCurrentSyllabary(Syllabary currentSyllabary) {
        this.currentSyllabary = currentSyllabary;
    }

    public List<Kana> getSelectedKanas() {
        return selectedKanas;
    }

    public void setSelectedKanas(List<Kana> selectedKanas) {
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

}
