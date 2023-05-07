package com.samsung.fastcook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private Button buttonNext, buttonFinish;
    private int currentPage;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "MyPrefs";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean isFirstTimeLaunch = sharedPreferences.getBoolean(IS_FIRST_TIME_LAUNCH, true);


        if (isFirstTimeLaunch) {
            setContentView(R.layout.activity_main);

            viewPager = findViewById(R.id.viewPager);
            buttonNext = findViewById(R.id.button_next);
            buttonFinish = findViewById(R.id.button_finish);

            MyPagerAdapter adapter = new MyPagerAdapter();
            viewPager.setAdapter(adapter);

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

                @Override
                public void onPageSelected(int position) {
                    currentPage = position;

                    // Show/hide Next and Finish buttons
                    if (position == adapter.getCount() - 1) {
                        buttonNext.setVisibility(View.GONE);
                        buttonFinish.setVisibility(View.VISIBLE);
                    } else {
                        buttonNext.setVisibility(View.VISIBLE);
                        buttonFinish.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {}
            });

            buttonNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(currentPage + 1);
                }
            });

            buttonFinish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(IS_FIRST_TIME_LAUNCH, false);
                    editor.apply();

                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

        } else {

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private class MyPagerAdapter extends PagerAdapter {

        private int[] layouts = { R.layout.onboarding_page_1, R.layout.onboarding_page_2, R.layout.onboarding_page_3 };

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
            View view = inflater.inflate(layouts[position], container, false);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}
