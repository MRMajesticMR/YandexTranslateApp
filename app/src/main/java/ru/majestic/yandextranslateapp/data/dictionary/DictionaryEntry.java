package ru.majestic.yandextranslateapp.data.dictionary;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by arkadiy.zakharov on 28.03.2017.
 */

public class DictionaryEntry {

    private String text;

    @SerializedName("pos")
    private String partOfSpeed;

    @SerializedName("ts")
    private String transcription;

    @SerializedName("gen")
    private String gender;

    private String anm; //Одушевленное/неодушевленное

    @SerializedName("def")
    private List<Translate> translates;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPartOfSpeed() {
        return partOfSpeed;
    }

    public void setPartOfSpeed(String partOfSpeed) {
        this.partOfSpeed = partOfSpeed;
    }

    public String getTranscription() {
        return transcription;
    }

    public void setTranscription(String transcription) {
        this.transcription = transcription;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAnm() {
        return anm;
    }

    public void setAnm(String anm) {
        this.anm = anm;
    }

    public List<Translate> getTranslates() {
        return translates;
    }

    public void setTranslates(List<Translate> translates) {
        this.translates = translates;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DictionaryEntry{");
        sb.append("text='").append(text).append('\'');
        sb.append(", partOfSpeed='").append(partOfSpeed).append('\'');
        sb.append(", transcription='").append(transcription).append('\'');
        sb.append(", gender='").append(gender).append('\'');
        sb.append(", anm='").append(anm).append('\'');
        sb.append(", translates=").append(translates);
        sb.append('}');
        return sb.toString();
    }
}
