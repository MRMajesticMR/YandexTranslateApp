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

    @FormUrlEncoded
    @POST("lookup")
    Call<LookupResponse> lookup(
            @Field("lang") String lang,
            @Field("text") String text,
            @Field("ui") String ui
    );

}
