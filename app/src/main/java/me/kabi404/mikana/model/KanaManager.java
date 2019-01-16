package me.kabi404.mikana.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public final class KanaManager {

    public static final String KANA_DATA_PATH = "";

    private List<List<Kana>> kanaRows;
    private Syllabary currentSyllabary;

    private List<Kana> selectedKanas;
    private List<Integer> unusedKanas;

    private static KanaManager singleton;

    private KanaManager() {
        setKanaRows(new LinkedList<List<Kana>>());
        setCurrentSyllabary(Syllabary.HIRAGANA);
        setSelectedKanas(new ArrayList<Kana>());
        setUnusedKanas(new LinkedList<Integer>());
    }

    public static KanaManager getInstance() {
        if(singleton == null)
            singleton = new KanaManager();

        return singleton;
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
