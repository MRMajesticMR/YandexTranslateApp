package ru.majestic.yandextranslateapp.data;

import java.util.Date;

/**
 * Created by arkadiy.zakharov on 27.03.2017.
 */

public class TranslateHistoryItem {

    private String source;
    private String translate;
    private Date translateTime;
    private String langSource;
    private String langDist;
    private boolean favorite;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public Date getTranslateTime() {
        return translateTime;
    }

    public void setTranslateTime(Date translateTime) {
        this.translateTime = translateTime;
    }

    public String getLangSource() {
        return langSource;
    }

    public void setLangSource(String langSource) {
        this.langSource = langSource;
    }

    public String getLangDist() {
        return langDist;
    }

    public void setLangDist(String langDist) {
        this.langDist = langDist;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
