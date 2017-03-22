package ru.majestic.yandextranslateapp.api.interceptors;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by arkadiy.zakharov on 22.03.2017.
 *
 * Интерсептор добавляет ключик от Яндекс.Переводчик API в каждый запрос.
 */

public class YandexTranslateAddKeyInterceptor implements Interceptor {

    private final String key;

    public YandexTranslateAddKeyInterceptor(String key) {
        this.key = key;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        HttpUrl originalHttpUrl = originalRequest.url();

        HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter("key", key)
                .build();

        Request.Builder requestBuilder = originalRequest.newBuilder().url(url);

        return chain.proceed(requestBuilder.build());
    }
}
