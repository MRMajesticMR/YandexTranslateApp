package ru.majestic.yandextranslateapp.ui.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.activeandroid.query.Select;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.majestic.yandextranslateapp.R;
import ru.majestic.yandextranslateapp.dao.DAOsHandler;
import ru.majestic.yandextranslateapp.data.TranslateItem;
import ru.majestic.yandextranslateapp.ui.adapters.TranslateHistoryRecyclerViewAdapter;
import ru.majestic.yandextranslateapp.ui.utils.Updatable;

public class HistoryFragment extends Fragment implements Updatable {

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
            public void onTranslateHistoryItemSelected(TranslateItem translateItem) {

            }
        });

        ButterKnife.bind(this, view);

        historyRecyclerView.setAdapter(translateHistoryRecyclerViewAdapter);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void update() {
        new AsyncTask<Void, Void, List<TranslateItem>>() {

            @Override
            protected List<TranslateItem> doInBackground(Void... params) {
                return DAOsHandler.getInstance().getTranslateItemDAO().list();
            }

            @Override
            protected void onPostExecute(List<TranslateItem> translateItems) {
                if(getActivity().isFinishing()) return;

                translateHistoryRecyclerViewAdapter.getTranslateItems().clear();

                for (TranslateItem translateItem: translateItems) {
                    translateHistoryRecyclerViewAdapter.getTranslateItems().add(translateItem);
                }

                translateHistoryRecyclerViewAdapter.notifyDataSetChanged();
            }
        }.execute();

    }
}
