package ru.majestic.yandextranslateapp.api.responses.translate;

import java.util.Map;

/**
 * Created by Admin on 26.03.2017.
 */

public class GetLangsResponse {

    private Map<String, String> langs;

    public Map<String, String> getLangs() {
        return langs;
    }

    public void setLangs(Map<String, String> langs) {
        this.langs = langs;
    }

    @Override
    public String toString() {
        return "GetLangsResponse{" +
                "langs=" + langs +
                '}';
    }
}
