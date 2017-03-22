package ru.majestic.yandextranslateapp.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.majestic.yandextranslateapp.BuildConfig;
import ru.majestic.yandextranslateapp.api.interceptors.YandexTranslateAddKeyInterceptor;

/**
 * Created by arkadiy.zakharov on 22.03.2017.
 *
 * Класс, который осуществляет доступ ко всем API, используемым в приложении.
 *
 *
 */

public class APIsHandler {

    private static final APIsHandler ourInstance = new APIsHandler();

    //Ключ Яндекс.Переводчик API
    private static final String YANDEX_TRANSLATE_KEY = "trnsl.1.1.20170321T133200Z.b9e9b8e49fda5adc.effc390f5d260d861086b8ab57acddee00a94910";

    private YandexTranslateAPI yandexTranslateAPI;

    public static APIsHandler getInstance() {
        return ourInstance;
    }

    private APIsHandler() {
        initYandexTranslateAPI();
    }

    /**
     * Доступ к Яндекс.Переводчик API
     * @return
     */
    public YandexTranslateAPI getYandexTranslateAPI() {
        return yandexTranslateAPI;
    }

    //===== <PRIVATE_METHODS> =====

    /**
     * Настройка API Яндекс.Переводчик
     */
    private void initYandexTranslateAPI() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.addInterceptor(new YandexTranslateAddKeyInterceptor(YANDEX_TRANSLATE_KEY));

        //Логирование необходимо только во время отладки
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            httpClientBuilder.addInterceptor(httpLoggingInterceptor);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://translate.yandex.net/api/v1.5/tr.json/")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .client(httpClientBuilder.build())
                .build();

        yandexTranslateAPI = retrofit.create(YandexTranslateAPI.class);
    }
    //===== </PRIVATE_METHODS> =====
}
