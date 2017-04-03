package ru.majestic.yandextranslateapp.dao;

import java.util.List;

import ru.majestic.yandextranslateapp.data.TranslateItem;

/**
 * Created by arkadiy.zakharov on 30.03.2017.
 */

public interface ITranslateItemDAO {

    /**
     * Создает или обновляет существующую запись о переводе
     *
     * @param translateItem
     * @return
     */
    TranslateItem save(TranslateItem translateItem);

    /**
     * Возвращает список всех переводов.
     *
     * @return
     */
    List<TranslateItem> list();

    /**
     * Список избранных переводов.
     *
     * @return
     */
    List<TranslateItem> favorites();


}
