package com.artichokecore.mikana.data.model;

public final class Kana {

    public static final String HIRAGANA_ATTR = "hiragana";
    public static final String KATAKANA_ATTR = "katakana";
    public static final String ROMAJI_ATTR = "romaji";
    public static final String JAPANESE_KANA_ATTR = "character";

    private Syllabary syllabary;
    private String kanaChar;
    private String romaji;

    public Kana(Syllabary syllabary, String kanaChar, String romaji) {
        setSyllabary(syllabary);
        setKanaChar(kanaChar);
        setRomaji(romaji);
    }

    public Syllabary getSyllabary() {
        return syllabary;
    }

    private void setSyllabary(Syllabary syllabary) {
        this.syllabary = syllabary;
    }

    public String getKanaChar() {
        return kanaChar;
    }

    private void setKanaChar(String kanaChar) {
        this.kanaChar = kanaChar;
    }

    public String getRomaji() {
        return romaji;
    }

    private void setRomaji(String romaji) {
        this.romaji = romaji;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Kana kana = (Kana) o;

        if (getSyllabary() != kana.getSyllabary()) return false;
        if (!getKanaChar().equals(kana.getKanaChar())) return false;
        return getRomaji().equals(kana.getRomaji());
    }

    @Override
    public int hashCode() {
        int result = getSyllabary().hashCode();
        result = 31 * result + getKanaChar().hashCode();
        result = 31 * result + getRomaji().hashCode();
        return result;
    }
}
