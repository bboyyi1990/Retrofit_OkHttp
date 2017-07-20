package com.yi.retrofit_okhttp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.gxz.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    PagerSlidingTabStrip slidingTabStrip;
    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pager = (ViewPager) findViewById(R.id.pager);
        slidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("TAB " + i);
        }
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), list));
        slidingTabStrip.setViewPager(pager);

        slidingTabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<String> titles;


        public MyPagerAdapter(FragmentManager fm, ArrayList<String> list) {
            super(fm);
            this.titles = list;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }


        @Override
        public Fragment getItem(int position) {
            Bundle b = new Bundle();
            b.putString("title", titles.get(position));
            return FragmentContent.getInstance(b);
        }

        @Override
        public int getCount() {
            return null == titles ? 0 : titles.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }


    }
}
