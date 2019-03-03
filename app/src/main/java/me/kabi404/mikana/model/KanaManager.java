package me.kabi404.mikana.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public final class KanaManager {

    private static final String HIRAGANA_ATTR = "hiragana";
    private static final String KATAKANA_ATTR = "katakana";

    private static final String ROMAJI_ATTR = "romaji";
    private static final String JAPANESE_KANA_ATTR = "character";

    private List<List<Kana>> kanaRows;
    private Syllabary currentSyllabary;

    private List<Kana> selectedKanas;
    private List<Integer> unusedKanas;

    private static KanaManager singleton;

    private KanaManager(InputStream kanaStream) throws IOException, JSONException {
        setKanaRows(new LinkedList<List<Kana>>());
        setCurrentSyllabary(Syllabary.HIRAGANA);
        setSelectedKanas(new ArrayList<Kana>());
        setUnusedKanas(new LinkedList<Integer>());

        loadDataFromJSON(kanaStream);
    }

    public static KanaManager getInstance(InputStream kanaStream) throws IOException, JSONException {
        if(singleton == null)
            singleton = new KanaManager(kanaStream);

        return singleton;
    }

    private void loadDataFromJSON(InputStream kanaStream) throws IOException, JSONException {

        int streamSize = kanaStream.available();
        byte[] buffer = new byte[streamSize];
        kanaStream.read(buffer);
        kanaStream.close();

        String json = new String(buffer, "UTF-8");
        JSONObject allJSONData = new JSONObject(json);

        parseSyllabary(allJSONData.getJSONArray(HIRAGANA_ATTR), Syllabary.HIRAGANA);
        parseSyllabary(allJSONData.getJSONArray(KATAKANA_ATTR), Syllabary.KATAKANA);

    }

    private void parseSyllabary(JSONArray jsonSyllabary, Syllabary syllabary) throws JSONException {
        for(int rowIndex = 0; rowIndex < jsonSyllabary.length(); rowIndex++) {
            List<Kana> kanaRow = new ArrayList<>();
            JSONArray jsonRow = jsonSyllabary.getJSONArray(rowIndex);
            for(int kanaIndex = 0; kanaIndex < jsonRow.length(); kanaIndex++) {
                JSONObject jsonKana = jsonRow.getJSONObject(kanaIndex);
                Kana loadedKana = new Kana(syllabary,
                        jsonKana.getString(JAPANESE_KANA_ATTR),
                        jsonKana.getString(ROMAJI_ATTR)
                );
                kanaRow.add(loadedKana);
            }
            this.kanaRows.add(kanaRow);
        }
    }

    public List<List<Kana>> getKanaRows() {
        return kanaRows;
    }

    public void setKanaRows(List<List<Kana>> kanaRows) {
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

    public List<Integer> getUnusedKanas() {
        return unusedKanas;
    }

    public void setUnusedKanas(List<Integer> unusedKanas) {
        this.unusedKanas = unusedKanas;
    }

}
