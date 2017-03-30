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
import ru.majestic.yandextranslateapp.R;
import ru.majestic.yandextranslateapp.data.dictionary.DictionaryEntry;
import ru.majestic.yandextranslateapp.data.dictionary.Translate;


/**
 * Created by arkadiy.zakharov on 17.10.2016.
 */

public class DictionaryEntryTranslateRecyclerViewAdapter extends RecyclerView.Adapter<DictionaryEntryTranslateRecyclerViewAdapter.ViewHolder> {

    private final List<Translate> translates = new LinkedList<>();

    @Override
    public DictionaryEntryTranslateRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_dictionary_entry_translate, parent, false);

        return new DictionaryEntryTranslateRecyclerViewAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Translate translate = translates.get(position);

        holder.numberTxt.setText(String.valueOf(position + 1));

        String synonyms = translate.getAllSynonymsString();

        if (synonyms.isEmpty()) {
            holder.synonymsTxt.setVisibility(View.GONE);
        } else {
            holder.synonymsTxt.setVisibility(View.VISIBLE);
            holder.synonymsTxt.setText(synonyms);
        }

        String meanings = translate.getAllMeaningsString();
        if (meanings == null) {
            holder.meaningTxt.setVisibility(View.GONE);
        } else {
            holder.meaningTxt.setVisibility(View.VISIBLE);
            holder.meaningTxt.setText(String.format("(%s)", meanings));
        }
    }

    @Override
    public int getItemCount() {
        return translates.size();
    }

    public List<Translate> getTranslates() {
        return translates;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_number)
        TextView numberTxt;

        @BindView(R.id.txt_synonyms)
        TextView synonymsTxt;

        @BindView(R.id.txt_meaning)
        TextView meaningTxt;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

}