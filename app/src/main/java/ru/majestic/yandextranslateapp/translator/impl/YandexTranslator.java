package ru.majestic.yandextranslateapp.translator.impl;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.majestic.yandextranslateapp.api.APIsHandler;
import ru.majestic.yandextranslateapp.api.responses.translate.TranslateResponse;
import ru.majestic.yandextranslateapp.data.LanguageInfo;
import ru.majestic.yandextranslateapp.translator.ITranslator;
import ru.majestic.yandextranslateapp.ui.utils.StringUtils;

/**
 * Created by Admin on 26.03.2017.
 */

public class YandexTranslator implements ITranslator {

    private TranslationListener translationListener;

    private LanguageInfo languageFrom;
    private LanguageInfo languageTo;

    private Call<TranslateResponse> callTranslate;

    /**
     * Обрабатывает ответ сервера от translate
     */
    private Callback<TranslateResponse> callbackTranslate = new Callback<TranslateResponse>() {
        @Override
        public void onResponse(Call<TranslateResponse> call, Response<TranslateResponse> response) {
            if (response.isSuccessful()) {
                TranslateResponse translateResponse = response.body();

                if (translateResponse.getCode() == TranslateResponse.CODE_SUCCESS) {
                    if (translationListener != null) {
                        translationListener.onTranslateSuccess(StringUtils.strArrayToStr(translateResponse.getText()));
                    }

                } else {
                    if (translationListener != null) {
                        translationListener.onTranslateFailed(translateResponse.getMessage());
                    }
                }

            } else {
                if (translationListener != null) {
                    translationListener.onTranslateFailed("Не удалось перевести текст");
                }
            }
        }

        @Override
        public void onFailure(Call<TranslateResponse> call, Throwable t) {
            if (!call.isCanceled()) {
                if (translationListener != null) {
                    translationListener.onTranslateFailed("Не удалось перевести текст");
                }
            }
        }
    };

    public YandexTranslator() {
        languageFrom = new LanguageInfo("ru", "Русский");
        languageTo = new LanguageInfo("en", "Английский");
    }

    @Override
    public void translateAsync(String text) {
        if (callTranslate != null && callTranslate.isExecuted()) callTranslate.cancel();

        callTranslate = APIsHandler.getInstance().getYandexTranslateAPI().translate(String.format("%s-%s", languageFrom.getLang(), languageTo.getLang()), text);
        callTranslate.enqueue(callbackTranslate);
    }

    @Override
    public void cancelTranslate() {
        if (callTranslate != null && callTranslate.isExecuted()) callTranslate.cancel();
    }

    @Override
    public void setLanguageFrom(LanguageInfo langFrom) {
        this.languageFrom = langFrom;
    }

    @Override
    public LanguageInfo getLanguageFrom() {
        return languageFrom;
    }

    @Override
    public void setLanguageTo(LanguageInfo languageTo) {
        this.languageTo = languageTo;
    }

    @Override
    public LanguageInfo getLanguageTo() {
        return languageTo;
    }

    @Override
    public void setTranslationListener(TranslationListener translationListener) {
        this.translationListener = translationListener;
    }
}
