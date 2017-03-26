package ru.majestic.yandextranslateapp.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.majestic.yandextranslateapp.R;
import ru.majestic.yandextranslateapp.data.LanguageInfo;


/**
 * Created by arkadiy.zakharov on 17.10.2016.
 */

public class SelectLanguageRecyclerViewAdapter extends RecyclerView.Adapter<SelectLanguageRecyclerViewAdapter.ViewHolder> {

    private final List<LanguageInfo> languages = new LinkedList<>();

    private ActionListener actionListener;

    @Override
    public SelectLanguageRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_select_language, parent, false);

        SelectLanguageRecyclerViewAdapter.ViewHolder vh = new SelectLanguageRecyclerViewAdapter.ViewHolder(v);

        vh.setActionListener(new ViewHolder.ActionListener() {
            @Override
            public void onItemClicked(int languagePosition) {
                if (actionListener != null) {
                    actionListener.onLanguageSelected(languages.get(languagePosition));
                }
            }
        });

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LanguageInfo language = languages.get(position);

        holder.languagePosition = position;

        holder.languageTitleTxt.setText(language.getTitle());
        holder.languageCodeTxt.setText(language.getLang().toUpperCase());
        holder.delimiter.setVisibility(position == languages.size() ? View.GONE : View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return languages.size();
    }

    public List<LanguageInfo> getLanguages() {
        return languages;
    }

    public void setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }

    public interface ActionListener {

        void onLanguageSelected(LanguageInfo languageInfo);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public int languagePosition;

        @BindView(R.id.txt_lang_code)
        TextView languageCodeTxt;

        @BindView(R.id.txt_lang_title)
        TextView languageTitleTxt;

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
                actionListener.onItemClicked(languagePosition);
        }

        interface ActionListener {

            void onItemClicked(int languagePosition);

        }
    }

}