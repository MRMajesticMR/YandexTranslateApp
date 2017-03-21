package ru.majestic.yandextranslateapp.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by arkadiy.zakharov on 21.03.2017.
 */

public class MainNavigationViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments = new LinkedList<>();

    public MainNavigationViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void addFragment(Fragment fragment) {
        fragments.add(fragment);
    }
}
