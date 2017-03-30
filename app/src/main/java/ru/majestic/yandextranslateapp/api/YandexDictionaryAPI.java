package ru.majestic.yandextranslateapp.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import ru.majestic.yandextranslateapp.api.responses.dictionary.LookupResponse;

/**
 * Created by arkadiy.zakharov on 28.03.2017.
 */

public interface YandexDictionaryAPI {

    int FLAG_FAMILY = 0x0001; //применить семейный фильтр
    int FLAG_SHORT_POS = 0x0002; //отображать названия частей речи в краткой форме
    int FLAG_MORPHO = 0x0004; //включает поиск по форме слова
    int FLAG_POS_FILTER= 0x0008; //включает фильтр, требующий соответствия частей речи искомого слова и перевода

    @FormUrlEncoded
    @POST("lookup")
    Call<LookupResponse> lookup(
            @Field("lang") String lang,
            @Field("text") String text,
            @Field("ui") String ui,
            @Field("flags") int flags
    );

}