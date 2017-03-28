package ru.majestic.yandextranslateapp.data.dictionary;

import com.google.gson.annotations.SerializedName;

/**
 * Created by arkadiy.zakharov on 28.03.2017.
 */

public class Synonym {

    private String text;

    @SerializedName("pos")
    private String partOfSpeech;

    private String time;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Synonym{");
        sb.append("text='").append(text).append('\'');
        sb.append(", partOfSpeech='").append(partOfSpeech).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
