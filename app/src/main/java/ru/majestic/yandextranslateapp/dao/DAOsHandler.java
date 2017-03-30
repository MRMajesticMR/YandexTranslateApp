package ru.majestic.yandextranslateapp.dao;

import ru.majestic.yandextranslateapp.dao.impl.ActiveAndroidTranslateItemDAO;

/**
 * Created by arkadiy.zakharov on 30.03.2017.
 */

public class DAOsHandler {

    private static final DAOsHandler ourInstance = new DAOsHandler();

    private final ITranslateItemDAO translateItemDAO;

    public static DAOsHandler getInstance() {
        return ourInstance;
    }

    private DAOsHandler() {
        translateItemDAO = new ActiveAndroidTranslateItemDAO();
    }

    public ITranslateItemDAO getTranslateItemDAO() {
        return translateItemDAO;
    }
}
