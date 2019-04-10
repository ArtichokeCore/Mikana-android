package me.kabi404.mikana.model;

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

    public void setSyllabary(Syllabary syllabary) {
        this.syllabary = syllabary;
    }

    public String getKanaChar() {
        return kanaChar;
    }

    public void setKanaChar(String kanaChar) {
        this.kanaChar = kanaChar;
    }

    public String getRomaji() {
        return romaji;
    }

    public void setRomaji(String romaji) {
        this.romaji = romaji;
    }
}
