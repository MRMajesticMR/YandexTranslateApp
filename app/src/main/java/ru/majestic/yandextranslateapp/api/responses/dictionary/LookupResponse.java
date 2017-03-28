package ru.majestic.yandextranslateapp.api.responses.dictionary;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.majestic.yandextranslateapp.data.dictionary.DictionaryEntry;

/**
 * Created by arkadiy.zakharov on 28.03.2017.
 */

public class LookupResponse {

    public static final int CODE_OK = 200; //Операция выполнена успешно.

    public static final int CODE_KEY_INVALID = 401; //Ключ API невалиден.
    public static final int CODE_KEY_BLOCKED = 402; //Ключ API заблокирован.
    public static final int CODE_DAILY_REQ_LIMIT_EXCEEDED = 403; //Превышено суточное ограничение на количество запросов.
    public static final int CODE_TEXT_TOO_LONG = 413; //Превышен максимальный размер текста.

    public static final int CODE_LANG_NOT_SUPPORTED = 501; //Заданное направление перевода не поддерживается.


    private int code;

    private String message;

    @SerializedName("def")
    private List<DictionaryEntry> dictionaryEntries;

    public LookupResponse() {
        code = LookupResponse.CODE_OK;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DictionaryEntry> getDictionaryEntries() {
        return dictionaryEntries;
    }

    public void setDictionaryEntries(List<DictionaryEntry> dictionaryEntries) {
        this.dictionaryEntries = dictionaryEntries;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("LookupResponse{");
        sb.append("code=").append(code);
        sb.append(", message='").append(message).append('\'');
        sb.append(", dictionaryEntries=").append(dictionaryEntries);
        sb.append('}');
        return sb.toString();
    }
}
