package ru.majestic.yandextranslateapp.ui.adapters;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.majestic.yandextranslateapp.R;
import ru.majestic.yandextranslateapp.data.TranslateHistoryItem;


/**
 * Created by arkadiy.zakharov on 17.10.2016.
 */

public class TranslateHistoryRecyclerViewAdapter extends RecyclerView.Adapter<TranslateHistoryRecyclerViewAdapter.ViewHolder> {

    private final List<TranslateHistoryItem> translateHistoryItems = new LinkedList<>();

    private ActionListener actionListener;

    @Override
    public TranslateHistoryRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_translate_history, parent, false);

        final TranslateHistoryRecyclerViewAdapter.ViewHolder vh = new TranslateHistoryRecyclerViewAdapter.ViewHolder(v);

        vh.setActionListener(new ViewHolder.ActionListener() {
            @Override
            public void onFavoriteClicked(int translateHistoryPosition) {
                TranslateHistoryItem translateHistoryItem = getTranslateHistoryItems().get(translateHistoryPosition);

                translateHistoryItem.setFavorite(!translateHistoryItem.isFavorite());

                vh.favoriteImg.setColorFilter(translateHistoryItem.isFavorite() ? ContextCompat.getColor(vh.itemView.getContext(), R.color.colorPrimary) : ContextCompat.getColor(vh.itemView.getContext(), R.color.gray));
            }

            @Override
            public void onItemClicked(int translateHistoryPosition) {
                if (actionListener != null) {
                    actionListener.onTranslateHistoryItemSelected(translateHistoryItems.get(translateHistoryPosition));
                }
            }
        });

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TranslateHistoryItem translateHistoryItem = translateHistoryItems.get(position);

        holder.translateHistoryPosition = position;

        holder.favoriteImg.setColorFilter(translateHistoryItem.isFavorite() ? ContextCompat.getColor(holder.itemView.getContext(), R.color.colorPrimary) : ContextCompat.getColor(holder.itemView.getContext(), R.color.gray));
        holder.sourceTxt.setText(translateHistoryItem.getSource());
        holder.translateTxt.setText(translateHistoryItem.getTranslate());
        holder.langCodeTxt.setText(String.format("%s-%s", translateHistoryItem.getLangSource(), translateHistoryItem.getLangSource()).toUpperCase());

        holder.delimiter.setVisibility(position == translateHistoryItems.size() ? View.GONE : View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return translateHistoryItems.size();
    }

    public List<TranslateHistoryItem> getTranslateHistoryItems() {
        return translateHistoryItems;
    }

    public void setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }

    public interface ActionListener {

        void onTranslateHistoryItemSelected(TranslateHistoryItem translateHistoryItem);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public int translateHistoryPosition;

        @BindView(R.id.img_favorite)
        ImageView favoriteImg;

        @BindView(R.id.txt_source)
        TextView sourceTxt;

        @BindView(R.id.txt_translate)
        TextView translateTxt;

        @BindView(R.id.txt_lang_code)
        TextView langCodeTxt;

        @BindView(R.id.delimiter)
        View delimiter;

        private ActionListener actionListener;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        public void setActionListener(ActionListener actionListener) {
            this.actionListener = actionListener;
        }

        @OnClick(R.id.root_container)
        protected void onRootContainerClicked() {
            if (actionListener != null)
                actionListener.onItemClicked(translateHistoryPosition);
        }

        @OnClick(R.id.img_favorite)
        protected void onFavoriteClicked() {
            if (actionListener != null)
                actionListener.onFavoriteClicked(translateHistoryPosition);
        }

        interface ActionListener {

            void onFavoriteClicked(int translateHistoryPosition);

            void onItemClicked(int translateHistoryPosition);

        }
    }

}