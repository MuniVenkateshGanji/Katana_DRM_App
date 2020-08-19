package com.jntucep.c19_delhi;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class topbar extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ExploreFragment exploreFragment;
    private FlightsFragment flightsFragment;
    private TravelFragment travelFragment;
    private DonationFragment donationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tobpar);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        exploreFragment = new ExploreFragment();
        flightsFragment = new FlightsFragment();
        travelFragment = new TravelFragment();
        donationFragment = new DonationFragment();
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.openNavDrawer,
                R.string.closeNavDrawer
        );

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        viewPagerAdapter.addFragment(exploreFragment, "Covid");
        viewPagerAdapter.addFragment(flightsFragment, "News");
        viewPagerAdapter.addFragment(travelFragment, "medical");
        viewPagerAdapter.addFragment(donationFragment, "Donate");
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_baseline_access_time_24);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_baseline_backup_24);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_baseline_healing_24);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_baseline_people_24);
        BadgeDrawable badgeDrawable = tabLayout.getTabAt(0).getOrCreateBadge();
        badgeDrawable.setVisible(false);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentTitle = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            fragmentTitle.add(title);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitle.get(position);
        }


    }
    public void maps(MenuItem item) {
        Intent intent = new Intent(topbar.this,PermissionsActivity.class);
        startActivity(intent);
    }
    public void helpline(MenuItem item) {
        Intent intent = new Intent(topbar.this,tollFree.class);
        startActivity(intent);
    }
    public void notifier(MenuItem item) {
        Intent intent = new Intent(topbar.this,MainPage.class);
        startActivity(intent);
    }
    public void Vocational(MenuItem item) {
        Intent intent = new Intent(topbar.this,Youtube.class);
        startActivity(intent);
    }
    public void pass(MenuItem item) {
        Intent intent = new Intent(topbar.this,E_pass.class);
        startActivity(intent);
    }
    public void talk(MenuItem item) {
        Intent intent = new Intent(topbar.this,watsonbot.class);
        startActivity(intent);
    }
    public void Msme(MenuItem item) {
        Intent intent = new Intent(topbar.this,Msme.class);
        startActivity(intent);
    }
    public void Help(MenuItem item) {
        Intent intent = new Intent(topbar.this,Login.class);
        startActivity(intent);
    }

}
