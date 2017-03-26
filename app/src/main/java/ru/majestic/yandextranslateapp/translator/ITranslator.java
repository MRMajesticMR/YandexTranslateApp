package ru.majestic.yandextranslateapp.translator;

import ru.majestic.yandextranslateapp.data.LanguageInfo;

/**
 * Created by Admin on 26.03.2017.
 */

public interface ITranslator {

    /**
     * Запускает процесс перевода текста
     *
     * @param text - текст для перевода
     */
    void translateAsync(String text);


    /**
     * Отменяет перевод, если он в процессе.
     */
    void cancelTranslate();


    /**
     * Указывается язык, с которого осуществляется перевод.
     * @param languageFrom - языка, с которого переводится текст. Null, если язык необходимо определить.
     */
    void setLanguageFrom(LanguageInfo languageFrom);

    /**
     * Возвращает язык, с которого производится перевод
     *
     * @return
     */
    LanguageInfo getLanguageFrom();

    /**
     * Указывается язык, на который осуществляется перевод.
     * @param languageTo - язык, на который переводится текст. Null, если язык необходимо определить.
     */
    void setLanguageTo(LanguageInfo languageTo);

    /**
     * Возвращает язык, на который производится перевод
     * @return
     */
    LanguageInfo getLanguageTo();

    /**
     * Устанаваливается слушатель.
     *
     * @param translationListener
     */
    void setTranslationListener(TranslationListener translationListener);


    /**
     * Интерфейс, случающий результат перепода.
     */
    interface TranslationListener {

        /**
         * Вызывается в случае успешного перевода.
         *
         * @param result - результат перевода.
         */
        void onTranslateSuccess(String result);

        /**
         * Вызывается в случае не успешного перевода.
         *
         * @param reason - описание причины провала.
         */
        void onTranslateFailed(String  reason);

    }

}
