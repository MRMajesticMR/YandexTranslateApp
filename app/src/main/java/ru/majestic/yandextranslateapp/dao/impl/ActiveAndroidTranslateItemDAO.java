package ru.majestic.yandextranslateapp.dao.impl;

import com.activeandroid.query.Select;

import java.util.List;

import ru.majestic.yandextranslateapp.dao.ITranslateItemDAO;
import ru.majestic.yandextranslateapp.data.TranslateItem;

/**
 * Created by arkadiy.zakharov on 30.03.2017.
 */

public class ActiveAndroidTranslateItemDAO implements ITranslateItemDAO {
    @Override
    public TranslateItem save(TranslateItem translateItem) {
        translateItem.save();
        return translateItem;
    }

    @Override
    public List<TranslateItem> list() {
        return new Select()
                .from(TranslateItem.class)
                .orderBy("translateTime DESC")
                .execute();
    }
}
