package ru.majestic.yandextranslateapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.majestic.yandextranslateapp.ui.adapters.MainNavigationViewPagerAdapter;
import ru.majestic.yandextranslateapp.ui.fragments.HistoryFragment;
import ru.majestic.yandextranslateapp.ui.fragments.TranslateFragment;
import ru.majestic.yandextranslateapp.ui.utils.Updatable;

public class MainActivity extends AppCompatActivity {

    private static final int[] NAVIGATION_ICONS = {
            R.drawable.ic_translate,
            R.drawable.ic_history,
            R.drawable.ic_bookmark,
            R.drawable.ic_settings,
    };

    private static final String[] FRAGMENT_TITLES = {
            "Я.Переводчик",
            "История"
    };

    @BindView(R.id.txt_toolbar_title)
    TextView toolbarTitleTxt;

    @BindView(R.id.view_pager)
    ViewPager navigationViewPager;

    @BindView(R.id.tabs)
    TabLayout navigationTabs;

    private MainNavigationViewPagerAdapter mainNavigationViewPagerAdapter;

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //.
        }

        @Override
        public void onPageSelected(int position) {
            toolbarTitleTxt.setText(FRAGMENT_TITLES[position]);

            Fragment fragment = mainNavigationViewPagerAdapter.getItem(position);
            if (fragment instanceof Updatable) {
                ((Updatable) fragment).update();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            //.
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mainNavigationViewPagerAdapter = new MainNavigationViewPagerAdapter(getSupportFragmentManager());
        mainNavigationViewPagerAdapter.addFragment(TranslateFragment.newInstance());
        mainNavigationViewPagerAdapter.addFragment(HistoryFragment.newInstance());

        navigationViewPager.setAdapter(mainNavigationViewPagerAdapter);
        navigationViewPager.addOnPageChangeListener(onPageChangeListener);
        navigationTabs.setupWithViewPager(navigationViewPager);

        //Настраиваем иконки на табах
        navigationTabs.getTabAt(0).setIcon(NAVIGATION_ICONS[0]);
        navigationTabs.getTabAt(1).setIcon(NAVIGATION_ICONS[1]);
//        navigationTabs.getTabAt(2).setIcon(NAVIGATION_ICONS[2]);

        toolbarTitleTxt.setText(FRAGMENT_TITLES[0]);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        navigationViewPager.removeOnPageChangeListener(onPageChangeListener);
    }
}
