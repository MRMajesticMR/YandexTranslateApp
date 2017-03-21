package ru.majestic.yandextranslateapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.majestic.yandextranslateapp.ui.adapters.MainNavigationViewPagerAdapter;
import ru.majestic.yandextranslateapp.ui.fragments.TranslateFragment;

public class MainActivity extends AppCompatActivity {

    private static final int[] NAVIGATION_ICONS = {
            R.drawable.ic_translate,
            R.drawable.ic_bookmark,
            R.drawable.ic_settings,
    };

    @BindView(R.id.view_pager)
    ViewPager navigationViewPager;

    @BindView(R.id.tabs)
    TabLayout navigationTabs;

    private MainNavigationViewPagerAdapter mainNavigationViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mainNavigationViewPagerAdapter = new MainNavigationViewPagerAdapter(getSupportFragmentManager());
        mainNavigationViewPagerAdapter.addFragment(TranslateFragment.newInstance());

        navigationViewPager.setAdapter(mainNavigationViewPagerAdapter);
        navigationTabs.setupWithViewPager(navigationViewPager);

        //Настраиваем иконки на табах
        navigationTabs.getTabAt(0).setIcon(NAVIGATION_ICONS[0]);
//        navigationTabs.getTabAt(1).setIcon(NAVIGATION_ICONS[1]);
//        navigationTabs.getTabAt(2).setIcon(NAVIGATION_ICONS[2]);
    }

}
