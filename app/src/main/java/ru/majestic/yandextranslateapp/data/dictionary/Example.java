package ru.majestic.yandextranslateapp.data.dictionary;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by arkadiy.zakharov on 28.03.2017.
 */

public class Example {

    private String text;

    @SerializedName("tr")
    private List<Translate> translates;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Translate> getTranslates() {
        return translates;
    }

    public void setTranslates(List<Translate> translates) {
        this.translates = translates;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Example{");
        sb.append("text='").append(text).append('\'');
        sb.append(", translates=").append(translates);
        sb.append('}');
        return sb.toString();
    }
}
