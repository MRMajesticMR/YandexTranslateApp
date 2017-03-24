package ru.majestic.yandextranslateapp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import ru.majestic.yandextranslateapp.R;

public class SelectLanguageActivity extends AppCompatActivity {

    private static final String EXTRA_LANG = "EXTRA_LANG";

    /**
     * Запускает активити для выбора языка.
     *
     * @param fragment
     * @param requestCode
     * @param lang        - код языка по Яндекс.Переводчику
     */
    public static void launchForResult(Fragment fragment, int requestCode, String lang) {
        Intent intent = new Intent(fragment.getContext(), SelectLanguageActivity.class);
        intent.putExtra(EXTRA_LANG, lang);

        fragment.startActivityForResult(intent, requestCode);
        fragment.getActivity().overridePendingTransition(R.anim.show_activity, R.anim.hide_activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_language);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

}
