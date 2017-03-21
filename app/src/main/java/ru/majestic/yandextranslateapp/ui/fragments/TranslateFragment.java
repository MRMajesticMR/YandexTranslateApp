package ru.majestic.yandextranslateapp.ui.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.majestic.yandextranslateapp.R;

public class TranslateFragment extends Fragment {

    @BindView(R.id.edt_text_input)
    EditText inputEdt;

    @BindView(R.id.clear_input)
    View clearInput;

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
            //Показываем кнопку очистки поля ввода, только если там что-то есть
            clearInput.setVisibility(inputEdt.getText().toString().isEmpty() ? View.INVISIBLE : View.VISIBLE);
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
}
