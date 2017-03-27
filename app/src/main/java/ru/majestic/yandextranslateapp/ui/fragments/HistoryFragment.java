package ru.majestic.yandextranslateapp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.majestic.yandextranslateapp.R;
import ru.majestic.yandextranslateapp.data.TranslateHistoryItem;
import ru.majestic.yandextranslateapp.ui.adapters.TranslateHistoryRecyclerViewAdapter;

public class HistoryFragment extends Fragment {

    private TranslateHistoryRecyclerViewAdapter translateHistoryRecyclerViewAdapter = new TranslateHistoryRecyclerViewAdapter();

    @BindView(R.id.history_recycler_view)
    RecyclerView historyRecyclerView;

    public HistoryFragment() {
        // Required empty public constructor
    }

    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        translateHistoryRecyclerViewAdapter.setActionListener(new TranslateHistoryRecyclerViewAdapter.ActionListener() {
            @Override
            public void onTranslateHistoryItemSelected(TranslateHistoryItem translateHistoryItem) {

            }
        });

        ButterKnife.bind(this, view);

        historyRecyclerView.setAdapter(translateHistoryRecyclerViewAdapter);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        //TODO: For debug only
        TranslateHistoryItem translateHistoryItem = new TranslateHistoryItem();
        translateHistoryItem.setSource("Привет Привет Привет Привет Привет Привет Привет Привет Привет Привет Привет Привет Привет Привет Привет Привет Привет Привет Привет Привет Привет Привет Привет Привет Привет Привет Привет Привет Привет Привет Привет Привет Привет Привет Привет Привет Привет Привет Привет Привет Привет Привет Привет Привет Привет Привет Привет Привет Привет ");
        translateHistoryItem.setTranslate("Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi Hi ");
        translateHistoryItem.setFavorite(true);
        translateHistoryItem.setLangDist("en");
        translateHistoryItem.setLangSource("ru");
        translateHistoryItem.setTranslateTime(new Date());

        translateHistoryRecyclerViewAdapter.getTranslateHistoryItems().add(translateHistoryItem);

        translateHistoryRecyclerViewAdapter.notifyDataSetChanged();
    }
}
