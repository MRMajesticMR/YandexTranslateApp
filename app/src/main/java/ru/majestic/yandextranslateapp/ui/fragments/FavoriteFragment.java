package ru.majestic.yandextranslateapp.ui.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.majestic.yandextranslateapp.R;
import ru.majestic.yandextranslateapp.dao.DAOsHandler;
import ru.majestic.yandextranslateapp.data.TranslateItem;
import ru.majestic.yandextranslateapp.ui.adapters.TranslatesRecyclerViewAdapter;
import ru.majestic.yandextranslateapp.ui.utils.DimensionsConverter;
import ru.majestic.yandextranslateapp.ui.utils.Updatable;

public class FavoriteFragment extends Fragment implements Updatable {

    private TranslatesRecyclerViewAdapter translateFavoriteRecyclerViewAdapter = new TranslatesRecyclerViewAdapter();

    private static final int TOOLBAR_MAX_ELEVATION = 4;

    private int recyclerViewScrolledY = 0;

    @BindView(R.id.favorite_recycler_view)
    RecyclerView favoriteRecyclerView;

    @BindView(R.id.search_shadow)
    View searchShadow;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    public static FavoriteFragment newInstance() {
        return new FavoriteFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        translateFavoriteRecyclerViewAdapter.setActionListener(new TranslatesRecyclerViewAdapter.ActionListener() {
            @Override
            public void onTranslateItemSelected(TranslateItem translateItem) {

            }
        });

        ButterKnife.bind(this, view);

        favoriteRecyclerView.setAdapter(translateFavoriteRecyclerViewAdapter);
        favoriteRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        favoriteRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                return DAOsHandler.getInstance().getTranslateItemDAO().favorites();
            }

            @Override
            protected void onPostExecute(List<TranslateItem> translateItems) {
                if(getActivity().isFinishing()) return;

                translateFavoriteRecyclerViewAdapter.getTranslateItems().clear();

                for (TranslateItem translateItem: translateItems) {
                    translateFavoriteRecyclerViewAdapter.getTranslateItems().add(translateItem);
                }

                translateFavoriteRecyclerViewAdapter.notifyDataSetChanged();
            }
        }.execute();

    }
}
