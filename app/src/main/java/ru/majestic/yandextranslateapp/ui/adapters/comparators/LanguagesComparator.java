package ru.majestic.yandextranslateapp.ui.adapters.comparators;

import java.util.Comparator;

import ru.majestic.yandextranslateapp.data.LanguageInfo;

/**
 * Created by arkadiy.zakharov on 31.10.2016.
 *
 * Сортирует список языков по алфавиту
 */

public class LanguagesComparator implements Comparator<LanguageInfo> {

    @Override
    public int compare(LanguageInfo o1, LanguageInfo o2) {
        return o1.getTitle().compareToIgnoreCase(o2.getTitle());
    }
}
