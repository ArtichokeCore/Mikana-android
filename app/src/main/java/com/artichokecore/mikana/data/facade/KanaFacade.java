package com.artichokecore.mikana.data.facade;

import com.artichokecore.mikana.data.model.Kana;
import com.artichokecore.mikana.data.model.Syllabary;
import com.artichokecore.mikana.data.structures.KanaMatrix;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

public final class KanaFacade {


    /**
     * Loads all Kana static data from InputStream and add to kanaRow.
     * @param kanaStream InputStream pointing to file with all static data.
     * @exception IOException On input error.
     * @exception JSONException Data format error.
     */
    public static KanaMatrix loadKanaMatrix(InputStream kanaStream) throws IOException, JSONException {

        KanaMatrix matrix = new KanaMatrix();

        int streamSize = kanaStream.available();
        byte[] buffer = new byte[streamSize];
        kanaStream.read(buffer);
        kanaStream.close();

        String json = new String(buffer, "UTF-8");
        JSONObject allJSONData = new JSONObject(json);

        parseSyllabary(allJSONData.getJSONArray(Kana.HIRAGANA_ATTR), Syllabary.HIRAGANA, matrix);
        parseSyllabary(allJSONData.getJSONArray(Kana.KATAKANA_ATTR), Syllabary.KATAKANA, matrix);

        return matrix;
    }

    /**
     * Add all kanas to kanaRow from JSONArray.
     * @param jsonSyllabary JSONObject with all Hiragana or Katakana rows.
     * @param syllabary Indicates the current syllabary.
     * @exception JSONException Data format error.
     */
    private static void parseSyllabary(JSONArray jsonSyllabary,
                                       Syllabary syllabary, KanaMatrix matrix) throws JSONException {
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
            matrix.addRow(kanaRow, syllabary);
        }
    }

}
