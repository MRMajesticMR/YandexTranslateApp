package ru.majestic.yandextranslateapp.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by arkadiy.zakharov on 24.03.2017.
 */

public class LanguageInfo implements Parcelable {

    public static final Creator<LanguageInfo> CREATOR = new Creator<LanguageInfo>() {
        @Override
        public LanguageInfo createFromParcel(Parcel in) {
            return new LanguageInfo(in);
        }

        @Override
        public LanguageInfo[] newArray(int size) {
            return new LanguageInfo[size];
        }
    };

    private String lang;
    private String title;

    public LanguageInfo() {

    }

    public LanguageInfo(String lang, String title) {
        setLang(lang);
        setTitle(title);
    }

    protected LanguageInfo(Parcel in) {
        lang = in.readString();
        title = in.readString();
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("LanguageInfo{");
        sb.append("lang='").append(lang).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(lang);
        dest.writeString(title);
    }
}
