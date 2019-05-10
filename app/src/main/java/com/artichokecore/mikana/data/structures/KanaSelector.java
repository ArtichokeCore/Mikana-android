package com.artichokecore.mikana.data.structures;

import com.artichokecore.mikana.data.model.Kana;
import com.artichokecore.mikana.data.model.Syllabary;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public final class KanaSelector {

    private List<String> temporalSelectedKanas;
    private List<Kana> selectedKanas;
    private List<Kana> unusedKanas;

    private Kana lastKana;

    protected KanaSelector() {
        setSelectedKanas(new ArrayList<Kana>());
        setUnusedKanas(new LinkedList<Kana>());
        setTemporalSelectedKanas(new LinkedList<String>());
    }

    public void selectKanas(List<Kana> selectedKanas) {
        getSelectedKanas().clear();
        getSelectedKanas().addAll(selectedKanas);

        randomizeSelectedKanas();
    }

    protected Kana getRandomKana() {
        Kana randomKana = getUnusedKanas().get(0);
        getUnusedKanas().remove(0);

        if(lastKana == randomKana) {
            Kana auxKana = randomKana;
            randomKana = getUnusedKanas().get(0);
            getUnusedKanas().add(auxKana);
        }

        setLastKana(randomKana);

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

    public Kana getLastKana() {
        return lastKana;
    }

    public void setLastKana(Kana lastKana) {
        this.lastKana = lastKana;
    }

    public List<String> getTemporalSelectedKanas() {
        return temporalSelectedKanas;
    }

    public void setTemporalSelectedKanas(List<String> temporalSelectedKanas) {
        this.temporalSelectedKanas = temporalSelectedKanas;
    }
}
