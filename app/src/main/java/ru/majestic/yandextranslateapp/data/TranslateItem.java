package ru.majestic.yandextranslateapp.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

/**
 * Created by arkadiy.zakharov on 27.03.2017.
 */

@Table(name = "translate_item")
public class TranslateItem extends Model {

    @Column(name = "source")
    private String source;

    @Column(name = "translate")
    private String translate;

    @Column(name = "translateTime")
    private Date translateTime;

    @Column(name = "langSource")
    private String langSource;

    @Column(name = "langDist")
    private String langDist;

    @Column(name = "favorite")
    private Boolean favorite;

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

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }
}
