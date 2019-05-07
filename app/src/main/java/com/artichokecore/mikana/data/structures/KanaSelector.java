package com.artichokecore.mikana.data.structures;

import com.artichokecore.mikana.model.Kana;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

final class KanaSelector {

    private List<Kana> selectedKanas;
    private List<Kana> unusedKanas;

    protected KanaSelector() {
        setSelectedKanas(new ArrayList<Kana>());
        setUnusedKanas(new LinkedList<Kana>());
    }

    protected KanaSelector(List<Kana> selectedKanas) {
        this();
        selectKanas(selectedKanas);
    }

    protected void selectKanas(List<Kana> selectedKanas) {
        getSelectedKanas().clear();
        getSelectedKanas().addAll(selectedKanas);

        randomizeSelectedKanas();
    }

    protected void selectKana(Kana selectedKana) {
        getSelectedKanas().add(selectedKana);
    }

    protected Kana getRandomKana() {
        Kana randomKana = getUnusedKanas().get(0);
        getUnusedKanas().remove(0);

        if(getUnusedKanas().size() <= 0)
            randomizeSelectedKanas();

        return randomKana;
    }

    private void randomizeSelectedKanas() {
        ArrayList<Kana> kanas = (ArrayList<Kana>) getSelectedKanas();
        Random rnd = new Random();

        for(int i = 0; i < kanas.size(); i++) {
            int random = rnd.nextInt(kanas.size());
            Kana currentKana = kanas.get(i);
            Kana randomKana = kanas.get(random);

            kanas.set(i, randomKana);
            kanas.set(random, currentKana);
        }

        getUnusedKanas().clear();
        getUnusedKanas().addAll(getSelectedKanas());
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
}
