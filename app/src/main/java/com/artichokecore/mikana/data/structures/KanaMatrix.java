package com.artichokecore.mikana.data.structures;

import com.artichokecore.mikana.data.model.Kana;
import com.artichokecore.mikana.data.model.Syllabary;

import java.util.ArrayList;
import java.util.List;

public final class KanaMatrix {

    private List<List<Kana>> [] matrix;

    public KanaMatrix() {
        setMatrix(new ArrayList[2]);
    }

    public void addRow(List<Kana> kanaRow, Syllabary syllabary) {
        getMatrix()[syllabary.ordinal()].add(kanaRow);
    }

    public List getFirstRow(Syllabary syllabary) {
        return getSyllabaryMatrix(syllabary).get(0);
    }

    public Kana getKanaByPos(int row, int column, Syllabary syllabary) {
        return getKanaRow(row, syllabary).get(column);
    }

    public int[] getKanaPos(Kana kana, Syllabary syllabary) {
        List<List<Kana>> matrix = getSyllabaryMatrix(syllabary);
        for(int i = 0; i < matrix.size(); i++) {
            for(int j = 0; j < matrix.get(i).size(); j++) {
                if(kana.equals(getKanaByPos(i, j, syllabary))){
                    int [] pos = {i, j};
                    return pos;
                }
            }
        }

        return null; //TODO manejar este error, aunque nunca deberia pasar o refactorizar con whiles
    }

    public List<Kana> getKanaRow(int row, Syllabary syllabary) {
        return getSyllabaryMatrix(syllabary).get(row);
    }

    public List<List<Kana>> getSyllabaryMatrix(Syllabary syllabary) {
        return getMatrix()[syllabary.ordinal()];
    }

    public List<List<Kana>>[] getMatrix() {
        return matrix;
    }

    public void setMatrix(List<List<Kana>>[] matrix) {
        this.matrix = matrix;
        this.matrix[Syllabary.HIRAGANA.ordinal()] = new ArrayList<>();
        this.matrix[Syllabary.KATAKANA.ordinal()] = new ArrayList<>();
    }
}
