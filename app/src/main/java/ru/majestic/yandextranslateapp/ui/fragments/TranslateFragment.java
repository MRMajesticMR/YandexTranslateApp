package ru.majestic.yandextranslateapp.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.majestic.yandextranslateapp.BuildConfig;
import ru.majestic.yandextranslateapp.R;
import ru.majestic.yandextranslateapp.api.APIsHandler;
import ru.majestic.yandextranslateapp.api.YandexDictionaryAPI;
import ru.majestic.yandextranslateapp.api.YandexTranslateAPI;
import ru.majestic.yandextranslateapp.api.responses.dictionary.LookupResponse;
import ru.majestic.yandextranslateapp.dao.DAOsHandler;
import ru.majestic.yandextranslateapp.data.LanguageInfo;
import ru.majestic.yandextranslateapp.data.TranslateItem;
import ru.majestic.yandextranslateapp.data.dictionary.DictionaryEntry;
import ru.majestic.yandextranslateapp.translator.ITranslator;
import ru.majestic.yandextranslateapp.translator.impl.YandexTranslator;
import ru.majestic.yandextranslateapp.ui.activities.SelectLanguageActivity;
import ru.majestic.yandextranslateapp.ui.adapters.DictionaryEntryRecyclerViewAdapter;
import ru.majestic.yandextranslateapp.ui.utils.DimensionsConverter;

public class TranslateFragment extends Fragment {

    private static final int REQUEST_CODE_SELECT_LANGUAGE_FROM = 101;
    private static final int REQUEST_CODE_SELECT_LANGUAGE_TO = 102;

    private static final int TOOLBAR_MAX_ELEVATION = 4;
    @BindView(R.id.nested_scroll_view_result)
    NestedScrollView resultNestedScrollView;
    @BindView(R.id.txt_translate_result)
    TextView translateResultTxt;
    @BindView(R.id.dictionary_result_container)
    View dictionaryResultContainer;
    @BindView(R.id.txt_dictionary_text)
    TextView dictionaryTextTxt;
    @BindView(R.id.txt_dictionary_transcription)
    TextView dictionaryTranscriptionTxt;
    @BindView(R.id.dictionary_entry_recycler_view)
    RecyclerView dictionaryEntriesReciclerView;
    @BindView(R.id.edt_text_input)
    EditText inputEdt;
    @BindView(R.id.clear_input)
    View clearInput;
    @BindView(R.id.txt_language_from)
    TextView languageFromTxt;
    @BindView(R.id.txt_language_to)
    TextView languageToTxt;
    @BindView(R.id.swap_language)
    View swapLanguageView;
    private DictionaryEntryRecyclerViewAdapter dictionaryEntryRecyclerViewAdapter = new DictionaryEntryRecyclerViewAdapter();
    private Call<LookupResponse> lookupCall;

    private Callback<LookupResponse> lookupCallback = new Callback<LookupResponse>() {
        @Override
        public void onResponse(Call<LookupResponse> call, Response<LookupResponse> response) {
            if (response.isSuccessful()) {

                LookupResponse lookupResponse = response.body();

                if (lookupResponse.getCode() == LookupResponse.CODE_OK) {
                    handleLookupResponse(lookupResponse);

                } else {
                    //TODO: Do something
                }

            } else {
                //TODO: Do something
            }
        }

        @Override
        public void onFailure(Call<LookupResponse> call, Throwable t) {
            if (!call.isCanceled()) {
                //TODO: Do something
            }
        }
    };

    private ITranslator translator = new YandexTranslator();
    private ITranslator.TranslationListener translationListener = new ITranslator.TranslationListener() {
        @Override
        public void onTranslateSuccess(TranslateItem result) {
            DAOsHandler.getInstance().getTranslateItemDAO().save(result);

            translateResultTxt.setText(result.getTranslate());

            requestDictionary(inputEdt.getText().toString());
        }

        @Override
        public void onTranslateFailed(String reason) {
            Toast.makeText(getContext(), reason, Toast.LENGTH_SHORT).show();
        }
    };


    private TextWatcher inputTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //.
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //.
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (inputEdt.getText().toString().isEmpty()) {
                clearInput.setVisibility(View.INVISIBLE);

            } else {
                //Показываем кнопку очистки поля ввода, только если там что-то есть
                clearInput.setVisibility(View.VISIBLE);
            }
        }
    };

    public TranslateFragment() {
        // Required empty public constructor
    }

    public static TranslateFragment newInstance() {
        return new TranslateFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_translate, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        ButterKnife.bind(this, view);

        //Отслеживаем изменения в поле ввода
        inputEdt.addTextChangedListener(inputTextWatcher);
        inputEdt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (inputEdt.getText().toString().isEmpty()) {
                    clearTranslate();

                } else if (inputEdt.getText().toString().length() > YandexTranslateAPI.MAX_TEXT_LENGTH) {
                    Toast.makeText(getContext(), "Текст не должен превышать 10.000 символов", Toast.LENGTH_SHORT).show();

                } else {
                    hideKeyboard();
                    clearTranslate();
                    translator.translateAsync(inputEdt.getText().toString());
                }

                return true;
            }
        });

        resultNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                //Изменение тени в зависимости от скролла результата перевода
                changeToolbarElevation((scrollY <= TOOLBAR_MAX_ELEVATION) ? scrollY : TOOLBAR_MAX_ELEVATION);
            }
        });

        dictionaryEntriesReciclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        dictionaryEntriesReciclerView.setAdapter(dictionaryEntryRecyclerViewAdapter);

        //Для быстроты отладки
        if (BuildConfig.DEBUG) {
            inputEdt.setText("Привет");
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        translator.setTranslationListener(translationListener);
    }

    @Override
    public void onStop() {
        super.onStop();

        translator.setTranslationListener(null);
        translator.cancelTranslate();
    }

    /**
     * Очистка поля ввода
     */
    @OnClick(R.id.clear_input)
    protected void clearInput() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getActivity().isFinishing()) return;

                inputEdt.setText("");
            }
        }, 150);

    }

    /**
     * Перемена местами языка с которого переводят и на который переводят
     */
    @OnClick(R.id.swap_language)
    protected void swapLanguage() {
        LanguageInfo fromLanguageInfo = translator.getLanguageFrom();
        translator.setLanguageFrom(translator.getLanguageTo());
        translator.setLanguageTo(fromLanguageInfo);

        updateLanguagesView();
    }

    /**
     * Нажата кнопка выбора, с какого языка переводить
     */
    @OnClick(R.id.txt_language_from)
    protected void selectLanguageFrom() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getActivity().isFinishing()) return;

                SelectLanguageActivity.launchForResult(TranslateFragment.this, REQUEST_CODE_SELECT_LANGUAGE_FROM);
            }
        }, 150);
    }

    /**
     * Нажата кнопка выбора, на какой язык переводить
     */
    @OnClick(R.id.txt_language_to)
    protected void selectLanguageTo() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getActivity().isFinishing()) return;

                SelectLanguageActivity.launchForResult(TranslateFragment.this, REQUEST_CODE_SELECT_LANGUAGE_TO);
            }
        }, 150);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_SELECT_LANGUAGE_FROM) { //Выбор языка, с которого производится перевод
            if (resultCode == Activity.RESULT_OK) {
                LanguageInfo languageInfo = data.getParcelableExtra(SelectLanguageActivity.EXTRA_RESULT_LANGUAGE_INFO);

                translator.setLanguageFrom(languageInfo);

                if (!inputEdt.getText().toString().isEmpty()) {
                    clearTranslateResult();
                    translator.translateAsync(inputEdt.getText().toString());
                }

                updateLanguagesView();
            }

        } else if (requestCode == REQUEST_CODE_SELECT_LANGUAGE_TO) { //Выбор языка, на который производится перевод
            if (resultCode == Activity.RESULT_OK) {
                LanguageInfo languageInfo = data.getParcelableExtra(SelectLanguageActivity.EXTRA_RESULT_LANGUAGE_INFO);

                if (!inputEdt.getText().toString().isEmpty()) {
                    clearTranslateResult();
                    translator.translateAsync(inputEdt.getText().toString());
                }

                translator.setLanguageTo(languageInfo);

                updateLanguagesView();
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    //===== <PRIVATE_METHODS> =====

    private void clearTranslateResult() {
        translateResultTxt.setText("");
    }

    /**
     * Обновляет отображение языков перевода
     */
    private void updateLanguagesView() {
        languageFromTxt.setText(translator.getLanguageFrom().getTitle());
        languageToTxt.setText(translator.getLanguageTo().getTitle());
    }

    /**
     * Очищает все результаты перевода
     */
    private void clearTranslate() {
        translateResultTxt.setText("");
        dictionaryResultContainer.setVisibility(View.GONE);
        dictionaryEntriesReciclerView.setVisibility(View.GONE);
    }

    /**
     * Изменение подъема (тени) toolbar
     *
     * @param elevationInDp elevation в dp
     */
    private void changeToolbarElevation(float elevationInDp) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().findViewById(R.id.appbar).setElevation(DimensionsConverter.convertDpToPixel(elevationInDp, getContext()));
        }
    }

    /**
     * Скрывает клавиатуру
     */
    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        View currentFocusView = getActivity().getCurrentFocus();
        if (currentFocusView != null) {
            inputManager.hideSoftInputFromWindow(currentFocusView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void requestDictionary(String text) {
        if (lookupCall != null && lookupCall.isExecuted()) {
            lookupCall.cancel();
        }

        lookupCall = APIsHandler.getInstance().getYandexDictionaryAPI().lookup(
                String.format("%s-%s", translator.getLanguageFrom().getLang(), translator.getLanguageTo().getLang()),
                text,
                Locale.getDefault().getLanguage(),
                YandexDictionaryAPI.FLAG_SHORT_POS
        );

        lookupCall.enqueue(lookupCallback);

    }

    /**
     * Обрабатывает ответ, пришедший от Яндекс.Словаря
     *
     * @param lookupResponse
     */
    private void handleLookupResponse(LookupResponse lookupResponse) {
        if (!lookupResponse.getDictionaryEntries().isEmpty()) {
            dictionaryResultContainer.setVisibility(View.VISIBLE);

            DictionaryEntry firstDictionaryEntry = lookupResponse.getDictionaryEntries().get(0);

            //Текст
            dictionaryTextTxt.setText(firstDictionaryEntry.getText());

            //Транскрипция
            if (firstDictionaryEntry.getTranscription() != null) {
                dictionaryTranscriptionTxt.setText(String.format("[%s]", firstDictionaryEntry.getTranscription()));
                dictionaryTranscriptionTxt.setVisibility(View.VISIBLE);
            } else {
                dictionaryTranscriptionTxt.setVisibility(View.GONE);
            }

            //Словарь
            dictionaryEntriesReciclerView.setVisibility(View.VISIBLE);

            dictionaryEntryRecyclerViewAdapter.getDictionaryEntries().clear();
            for (DictionaryEntry dictionaryEntry : lookupResponse.getDictionaryEntries()) {
                dictionaryEntryRecyclerViewAdapter.getDictionaryEntries().add(dictionaryEntry);
            }
            dictionaryEntryRecyclerViewAdapter.notifyDataSetChanged();

        } else {
            dictionaryEntriesReciclerView.setVisibility(View.GONE);
        }
    }
    //===== </PRIVATE_METHODS> =====
}
