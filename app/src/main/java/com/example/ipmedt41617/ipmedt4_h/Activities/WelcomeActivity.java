package com.example.ipmedt41617.ipmedt4_h.Activities;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ipmedt41617.ipmedt4_h.DatabaseHelper;
import com.example.ipmedt41617.ipmedt4_h.Models.Patient;
import com.example.ipmedt41617.ipmedt4_h.R;
import com.example.ipmedt41617.ipmedt4_h.SharedPrefs;

// Eerst activity voor de gebruiker

public class WelcomeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private WelcomePagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnSkip, btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);

        if(SharedPrefs.getInstance(this).getBooleanValue("firstRun")) {
            viewPager = (ViewPager) findViewById(R.id.view_pager);
            dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
            btnSkip = (Button) findViewById(R.id.btn_skip);
            btnNext = (Button) findViewById(R.id.btn_next);

            // Layouts van de welkom sliders

            layouts = new int[]{
                    R.layout.welcome_slide1,
                    R.layout.welcome_slide2,
                    R.layout.welcome_slide3,
                    R.layout.welcome_slide4};

            // Het toevoegen van de dots

            addBottomDots(0);

            changeStatusBarColor();


            myViewPagerAdapter = new WelcomePagerAdapter();
            viewPager.setAdapter(myViewPagerAdapter);
            viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

            btnSkip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    launchHomeScreen();
                }
            });

            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // kijken of het de laatste pagina is
                    int current = getItem(+1);
                    if (current < layouts.length) {
                        // ga naar het volgende scherm
                        viewPager.setCurrentItem(current);
                    } else {
                        launchHomeScreen();
                    }
                }
            });
        } else {
            startActivity(new Intent(WelcomeActivity.this, MenuActivity.class));
        }
    }

    // Toevoegen dots afhankelijk van het aantal pagina's

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
        //prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(this, PatientgegevensActivity.class));
        finish();
    }

    // Viewpager change listener

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            // verander de tekst van "Volgende" naar "Eind"
            if (position == layouts.length - 1) {
                btnNext.setText(getString(R.string.start));

                btnSkip.setVisibility(View.GONE);
            } else {
                btnNext.setText(getString(R.string.next));
                btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLACK);
        }
    }

    // View pager adapter

    public class WelcomePagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public WelcomePagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}