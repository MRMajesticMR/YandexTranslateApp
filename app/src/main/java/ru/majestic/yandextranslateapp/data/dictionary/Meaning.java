package ru.majestic.yandextranslateapp.data.dictionary;

/**
 * Created by arkadiy.zakharov on 28.03.2017.
 */

public class Meaning {

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Meaning{");
        sb.append("text='").append(text).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
