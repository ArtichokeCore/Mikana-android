package com.artichokecore.mikana.model;

public final class Kana {

    protected static final String HIRAGANA_ATTR = "hiragana";
    protected static final String KATAKANA_ATTR = "katakana";
    protected static final String ROMAJI_ATTR = "romaji";
    protected static final String JAPANESE_KANA_ATTR = "character";

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
}
