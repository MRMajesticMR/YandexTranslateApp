package ru.majestic.yandextranslateapp.ui.activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.majestic.yandextranslateapp.R;
import ru.majestic.yandextranslateapp.api.APIsHandler;
import ru.majestic.yandextranslateapp.api.responses.translate.GetLangsResponse;
import ru.majestic.yandextranslateapp.data.LanguageInfo;
import ru.majestic.yandextranslateapp.ui.adapters.SelectLanguageRecyclerViewAdapter;
import ru.majestic.yandextranslateapp.ui.adapters.comparators.LanguagesComparator;
import ru.majestic.yandextranslateapp.ui.utils.DimensionsConverter;

public class SelectLanguageActivity extends AppCompatActivity {

    private static final String LOG_TAG = SelectLanguageActivity.class.getSimpleName();

    public static final String EXTRA_RESULT_LANGUAGE_INFO = "EXTRA_RESULT_LANGUAGE_INFO";

    private static final int TOOLBAR_MAX_ELEVATION = 8;

    private static final String DEFAULT_UI = "ru";

    private int recyclerViewScrolledY = 0;

    private SelectLanguageRecyclerViewAdapter selectLanguageRecyclerViewAdapter = new SelectLanguageRecyclerViewAdapter();

    @BindView(R.id.recycler_view_languages)
    RecyclerView languagesRecyclerView;

    /**
     * Запускает активити для выбора языка.
     *
     * @param fragment
     * @param requestCode
     */
    public static void launchForResult(Fragment fragment, int requestCode) {
        Intent intent = new Intent(fragment.getContext(), SelectLanguageActivity.class);

        fragment.startActivityForResult(intent, requestCode);
        fragment.getActivity().overridePendingTransition(R.anim.show_activity, R.anim.hide_activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_select_language);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back));
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        ButterKnife.bind(this);

        selectLanguageRecyclerViewAdapter.setActionListener(new SelectLanguageRecyclerViewAdapter.ActionListener() {
            @Override
            public void onLanguageSelected(LanguageInfo languageInfo) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra(EXTRA_RESULT_LANGUAGE_INFO, languageInfo);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        languagesRecyclerView.setAdapter(selectLanguageRecyclerViewAdapter);
        languagesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        languagesRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                recyclerViewScrolledY += dy;

                //Изменение тени в зависимости от скролла экрана
                changeToolbarElevation((recyclerViewScrolledY <= TOOLBAR_MAX_ELEVATION) ? recyclerViewScrolledY : TOOLBAR_MAX_ELEVATION);
            }
        });

        startRequestLanguagesList();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                return true;
        }

        return false;
    }

    //===== <PRIVATE_METHODS> =====

    /**
     * Запускает процесс получения списка доступных языков
     */
    private void startRequestLanguagesList() {
        APIsHandler.getInstance().getYandexTranslateAPI().getLangs(DEFAULT_UI).enqueue(new Callback<GetLangsResponse>() {

            @Override
            public void onResponse(Call<GetLangsResponse> call, Response<GetLangsResponse> response) {
                if (response.isSuccessful()) {

                    selectLanguageRecyclerViewAdapter.getLanguages().clear();

                    Set<Map.Entry<String, String>> languages = response.body().getLangs().entrySet();
                    for (Map.Entry<String, String> languageInfo : languages) {
                        selectLanguageRecyclerViewAdapter.getLanguages().add(new LanguageInfo(languageInfo.getKey(), languageInfo.getValue()));
                    }

                    Collections.sort(selectLanguageRecyclerViewAdapter.getLanguages(), new LanguagesComparator());

                    selectLanguageRecyclerViewAdapter.notifyDataSetChanged();

                } else {
                    Log.d(LOG_TAG, "Failed");
                }
            }

            @Override
            public void onFailure(Call<GetLangsResponse> call, Throwable t) {
                Log.d(LOG_TAG, "Failed: " + t.toString());
            }
        });
    }

    /**
     * Изменение подъема (тени) toolbar
     *
     * @param elevationInDp elevation в dp
     */
    private void changeToolbarElevation(float elevationInDp) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            findViewById(R.id.appbar).setElevation(DimensionsConverter.convertDpToPixel(elevationInDp, this));
        }
    }
    //===== </PRIVATE_METHODS> =====
}
