package ru.majestic.yandextranslateapp.api.responses.translate;

import java.util.List;

/**
 * Created by arkadiy.zakharov on 22.03.2017.
 *
 * Response for URL: translate
 *
 */

public class TranslateResponse {

    public static final int CODE_SUCCESS = 200;            //Операция выполнена успешно

    public static final int CODE_WRONG_API_KEY = 401;      //Неправильный API-ключ
    public static final int CODE_API_KEY_BLOCKED = 402;    //API-ключ заблокирован
    public static final int CODE_REQUESTS_LIMIT = 404;     //Превышено суточное ограничение на объем переведенного текста
    public static final int CODE_LONG_TEXT = 413;          //Превышен максимально допустимый размер текста
    public static final int CODE_CANT_BE_TRANSLATE = 422;  //Текст не может быть переведен

    public static final int CODE_WRONG_LANG = 501;         //Заданное направление перевода не поддерживается

    private int code;
    private String message;
    private String lang;
    private List<String> text;

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

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public List<String> getText() {
        return text;
    }

    public void setText(List<String> text) {
        this.text = text;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TranslateResponse{");
        sb.append("code=").append(code);
        sb.append(", lang='").append(lang).append('\'');
        sb.append(", text=").append(text);
        sb.append('}');
        return sb.toString();
    }
}
