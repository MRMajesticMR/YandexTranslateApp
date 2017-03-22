package ru.majestic.yandextranslateapp.ui.utils;

import java.util.List;

/**
 * Created by arkadiy.zakharov on 22.03.2017.
 *
 * Вспомогательный класс для работы со строками
 */

public class StringUtils {

    /**
     * Объеденяет массив строк в одну строку, разделяя элементы строкой @param delimiter
     *
     * @param strArr - список строковых элементов
     * @param delimiter - разделитель
     * @return
     */
    public static String strArrayToStr(List<String> strArr, String delimiter) {
        if (strArr.isEmpty())
            return "";

        if (strArr.size() == 1)
            return strArr.get(0);

        StringBuilder stringBuilder = new StringBuilder(strArr.get(0));

        for (int i = 1; i < strArr.size(); i++) {
            stringBuilder.append(delimiter);
            stringBuilder.append(strArr.get(i));
        }

        return stringBuilder.toString();
    }

    /**
     * Объеденяет массив строк в одну строку, разделяя элементы пробелом
     *
     * @param strArr - список строковых элементов
     * @return
     */
    public static String strArrayToStr(List<String> strArr) {
        return strArrayToStr(strArr, " ");
    }

}
