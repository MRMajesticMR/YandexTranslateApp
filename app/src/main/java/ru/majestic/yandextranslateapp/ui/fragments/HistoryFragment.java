package ru.majestic.yandextranslateapp.ui.fragments;

import android.os.AsyncTask;
import android.os.Build;
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
import ru.majestic.yandextranslateapp.ui.utils.DimensionsConverter;
import ru.majestic.yandextranslateapp.ui.utils.Updatable;

public class HistoryFragment extends Fragment implements Updatable {

    private TranslateHistoryRecyclerViewAdapter translateHistoryRecyclerViewAdapter = new TranslateHistoryRecyclerViewAdapter();

    private static final int TOOLBAR_MAX_ELEVATION = 4;

    private int recyclerViewScrolledY = 0;

    @BindView(R.id.history_recycler_view)
    RecyclerView historyRecyclerView;

    @BindView(R.id.search_shadow)
    View searchShadow;

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

        historyRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                recyclerViewScrolledY += dy;

                //Изменение тени под поиском
                int searchElevation = (recyclerViewScrolledY <= TOOLBAR_MAX_ELEVATION) ? recyclerViewScrolledY : TOOLBAR_MAX_ELEVATION;
                if (searchElevation < 1) searchElevation = 1;

                ViewGroup.LayoutParams layoutParams = searchShadow.getLayoutParams();
                layoutParams.height = DimensionsConverter.convertDpToPixel(searchElevation, getContext());
                searchShadow.setLayoutParams(layoutParams);
            }
        });
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

    //===== <PRIVATE_METHODS> =====
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
    //===== </PRIVATE_METHODS> =====
}
