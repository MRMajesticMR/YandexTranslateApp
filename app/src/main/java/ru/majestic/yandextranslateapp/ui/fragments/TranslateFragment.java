package ru.majestic.yandextranslateapp.ui.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.majestic.yandextranslateapp.R;
import ru.majestic.yandextranslateapp.api.APIsHandler;
import ru.majestic.yandextranslateapp.api.YandexTranslateAPI;
import ru.majestic.yandextranslateapp.api.responses.translate.TranslateResponse;
import ru.majestic.yandextranslateapp.ui.utils.StringUtils;

public class TranslateFragment extends Fragment {

    @BindView(R.id.edt_text_input)
    EditText inputEdt;

    @BindView(R.id.clear_input)
    View clearInput;

    @BindView(R.id.txt_translate_result)
    TextView translateResultTxt;

    private Call<TranslateResponse> callTranslate;

    /**
     * Обрабатывает ответ сервера от translate
     */
    private Callback<TranslateResponse> callbackTranslate = new Callback<TranslateResponse>() {
        @Override
        public void onResponse(Call<TranslateResponse> call, Response<TranslateResponse> response) {
            if (getActivity().isFinishing()) return;

            if (response.isSuccessful()) {
                TranslateResponse translateResponse = response.body();

                if (translateResponse.getCode() == TranslateResponse.CODE_SUCCESS) {
                    translateResultTxt.setText(StringUtils.strArrayToStr(translateResponse.getText()));

                } else {
                    Toast.makeText(getContext(), translateResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(getContext(), "Не удалось перевести текст", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<TranslateResponse> call, Throwable t) {
            if (getActivity().isFinishing()) return;

            if (!call.isCanceled()) {
                Toast.makeText(getContext(), "Не удалось перевести текст", Toast.LENGTH_SHORT).show();
            }
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

                clearTranslate();
            } else {
                //Показываем кнопку очистки поля ввода, только если там что-то есть
                clearInput.setVisibility(View.VISIBLE);

                String text = inputEdt.getText().toString();

                if (text.length() <= YandexTranslateAPI.MAX_TEXT_LENGTH) {
                    startRequestTranslate(text, "ru-en");

                } else {
                    Toast.makeText(getContext(), "Текст не должен превышать 10.000 символов", Toast.LENGTH_SHORT).show();
                }

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
    }

    /**
     * Очистка поля ввода
     */
    @OnClick(R.id.clear_input)
    public void clearInput() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getActivity().isFinishing()) return;

                inputEdt.setText("");
            }
        }, 150);

    }

    //===== <PRIVATE_METHODS> =====

    /**
     * Начинает процесс запроса перевода текста
     */
    private void startRequestTranslate(@NonNull String text, @NonNull String lang) {
        if (callTranslate != null && callTranslate.isExecuted()) {
            callTranslate.cancel();
        }

        callTranslate = APIsHandler.getInstance().getYandexTranslateAPI().translate(lang, text);
        callTranslate.enqueue(callbackTranslate);
    }

    /**
     * Очищает все результаты перевода
     */
    private void clearTranslate() {
        translateResultTxt.setText("");
    }
    //===== </PRIVATE_METHODS> =====
}
