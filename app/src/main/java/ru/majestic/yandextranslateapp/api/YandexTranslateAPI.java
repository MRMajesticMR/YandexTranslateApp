package ru.majestic.yandextranslateapp.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ru.majestic.yandextranslateapp.api.responses.translate.GetLangsResponse;
import ru.majestic.yandextranslateapp.api.responses.translate.TranslateResponse;

/**
 * Created by arkadiy.zakharov on 22.03.2017.
 */

public interface YandexTranslateAPI {

    /**
     * Максимальная длина текста для перевода
     */
    int MAX_TEXT_LENGTH = 10000;

    /**
     * Перевод текста на заданный язык.
     *
     * @param lang - Направление перевода.
     *             Может задаваться одним из следующих способов:
     *             В виде пары кодов языков («с какого»-«на какой»), разделенных дефисом. Например, en-ru обозначает перевод с английского на русский.
     *             В виде кода конечного языка (например ru). В этом случае сервис пытается определить исходный язык автоматически.
     *
     * @param text - Текст, который необходимо перевести.
     *             Ограничения:
     *             Для POST-запросов максимальный размер передаваемого текста составляет 10 000 символов.
     *             В GET-запросах ограничивается не размер передаваемого текста, а размер всей строки запроса, которая кроме текста может содержать и другие параметры.
     *             Максимальный размер строки — от 2 до 10 КБ (зависит от версии используемого браузера).
     * @return
     */
    @FormUrlEncoded
    @POST("translate")
    Call<TranslateResponse> translate(
            @Query("lang") String lang,
            @Field("text") String text
    );


    /**
     * Получение списка направлений перевода, поддерживаемых сервисом.
     *
     * @param ui - Обязательный параметр.
     * В ответе список поддерживаемых языков будет перечислен в поле langs вместе с расшифровкой кодов языков. Названия языков будут выведены на языке, код которого соответствует этому параметру.
     * @return
     */
    @POST("getLangs")
    Call<GetLangsResponse> getLangs(
            @Query("ui") String ui
    );

}
