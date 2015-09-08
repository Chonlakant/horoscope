package co.aquario.horoscope.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import co.aquario.horoscope.MainApplication;
import co.aquario.horoscope.PrefManager;
import co.aquario.horoscope.fragment.Tab1;
import co.aquario.horoscope.fragment.FragmentNoti;
import co.aquario.horoscope.fragment.FragmentZodiacDetail;
import co.aquario.horoscope.fragment.TabNoti;

/**
 * Created by root1 on 8/16/15.
 */
public class PagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "ราศีเธอ", "12 ราศี", "วันนี้" };
    PrefManager pref = MainApplication.getPrefManager();
    String title = pref.zodiac().getOr("");
    String data = pref.zodiac().getOr("");

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch(position) {
            case 0:

                fragment = new FragmentZodiacDetail().newInstance(position,title,data);
                break;
            case 1:
                fragment = new Tab1().newInstance(position);
                break;
            case 2:

                fragment = new TabNoti().newInstance(position);
                break;
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}