package com.example.taufic.bikeapps;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.taufic.bikeapps.Adapter.TabPagerAdapter;
import com.example.taufic.bikeapps.Fragment.Bike;
import com.example.taufic.bikeapps.Fragment.Chat;
import com.example.taufic.bikeapps.Fragment.CompassFragment;
import com.example.taufic.bikeapps.Fragment.Home;
import com.example.taufic.bikeapps.Fragment.News;

/**
 * Created by taufic on 2/20/2017.
 */
public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabPagerAdapter TabAdapter;
    private BottomNavigationView bottomNavigationView;
    private MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Setting ActionBar */
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setLogo(R.drawable.);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /* Finalization Object Fragment */
        final Home home = new Home();
        final Chat chat = new Chat();
        final Bike bike = new Bike();
        final News news = new News();
        final CompassFragment compassFragment = new CompassFragment();

        /* ViewPager Navigation Initiation*/
        bottomNavigationView = (BottomNavigationView) findViewById(
                R.id.bottom_navigation_view);
        viewPager = (ViewPager) findViewById(R.id.pager);
        TabAdapter = new TabPagerAdapter(getSupportFragmentManager());
        TabAdapter.addFragments(home);
        TabAdapter.addFragments(chat);
        TabAdapter.addFragments(bike);
        TabAdapter.addFragments(news);
        TabAdapter.addFragments(compassFragment);
        viewPager.setAdapter(TabAdapter);

        /* Setting bottomNavigationView with ViewPager */
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.action_chat:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.action_bike:
                                viewPager.setCurrentItem(2);
                                break;
                            case R.id.action_news:
                                viewPager.setCurrentItem(3);
                                break;
                            case R.id.action_compass:
                                viewPager.setCurrentItem(4);
                                break;
                        }
                        return false;
                    }
                }

        );

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
//                Log.d("size" , "dari" + bottomNavigationView.getMaxItemCount());
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

}


