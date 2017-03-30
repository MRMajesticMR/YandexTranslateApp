package ru.majestic.yandextranslateapp.ui.adapters;

import android.support.v7.widget.LinearLayoutManager;
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

public class DictionaryEntryRecyclerViewAdapter extends RecyclerView.Adapter<DictionaryEntryRecyclerViewAdapter.ViewHolder> {

    private final List<DictionaryEntry> dictionaryEntries = new LinkedList<>();

    @Override
    public DictionaryEntryRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_dictionary_entry, parent, false);

        return new DictionaryEntryRecyclerViewAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DictionaryEntry dictionaryEntry = dictionaryEntries.get(position);

        holder.partOfSpeechTxt.setText(dictionaryEntry.getPartOfSpeech());

        holder.dictionaryEntryTranslateRecyclerViewAdapter.getTranslates().clear();
        for (Translate translate : dictionaryEntry.getTranslates()) {
            holder.dictionaryEntryTranslateRecyclerViewAdapter.getTranslates().add(translate);
        }
        holder.dictionaryEntryTranslateRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dictionaryEntries.size();
    }

    public List<DictionaryEntry> getDictionaryEntries() {
        return dictionaryEntries;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_part_of_speech)
        TextView partOfSpeechTxt;
        @BindView(R.id.recycler_view_translates)
        RecyclerView translatesRecyclerView;
        private DictionaryEntryTranslateRecyclerViewAdapter dictionaryEntryTranslateRecyclerViewAdapter = new DictionaryEntryTranslateRecyclerViewAdapter();

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            translatesRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.VERTICAL, false));
            translatesRecyclerView.setAdapter(dictionaryEntryTranslateRecyclerViewAdapter);
        }
    }

}