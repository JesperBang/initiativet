package com.example.jespe.initiativiet;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

public class TabActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, TabHost.OnTabChangeListener {


    private SectionPageAdapter adapter;

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;


    private ViewPager mViewPager;
    private ActionBar tools;
    private FragmentTabHost tabHost;
    private FrameLayout frameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        frameLayout = (FrameLayout) findViewById(android.R.id.tabcontent);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



       // mViewPager = (ViewPager) findViewById(R.id.container);

       // iniViewPager(mViewPager);
        //TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);
        //tabLayout.setupWithViewPager(mViewPager);
      tabHost = (FragmentTabHost) findViewById(R.id.tab_host);
        tabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        /*
            TabHost.TabSpec mSpec = mTabHost.newTabSpec("First Tab");
        mSpec.setContent(R.id.first_Tab);
        mSpec.setIndicator("First Tab");
        mTabHost.addTab(mSpec);
         */

        //tabHost.newTabSpec("valg").setContent(R.id.tab_valg).setIndicator("Valg");
        /*
        TabHost.TabSpec valgSpec = tabHost.newTabSpec("valg");
        valgSpec.setContent(R.id.tab_valg);
        valgSpec.setIndicator("Valg");
        tabHost.addTab(valgSpec);
        TabHost.TabSpec statSpec = tabHost.newTabSpec("stat");
        statSpec.setContent(R.id.tab_stat);
        statSpec.setIndicator("Statistik");
        tabHost.addTab(statSpec);

        TabHost.TabSpec forumSpec = tabHost.newTabSpec("forum");
        forumSpec.setContent(R.id.tab_forum);
        forumSpec.setIndicator("Forum");
        tabHost.addTab(forumSpec);
        */
        tabHost.setOnTabChangedListener(this);


        tabHost.addTab(tabHost.newTabSpec("valg").setIndicator("Valg").setContent(android.R.id.tabcontent));
        tabHost.addTab(tabHost.newTabSpec("stat").setIndicator("Statistik").setContent(android.R.id.tabcontent));
        tabHost.addTab(tabHost.newTabSpec("forum").setIndicator("Forum").setContent(android.R.id.tabcontent));
        System.out.print("FØR");
        tabHost.onTabChanged("valg");
        System.out.print("Efter");
        //onTabChanged("valg");
    }
    private void iniViewPager(ViewPager viewPager){
        adapter = new SectionPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new ValgFragment(), "Valg");
        adapter.addFragment(new StatistikFragment(), "Statistik");
        adapter.addFragment(new ForumFragment(), "Forum");


        mViewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.burger_settings, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            // Handle the camera action
        } else if (id == R.id.nav_posts) {

        } else if (id == R.id.nav_debates) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    

    @Override
    public void onTabChanged(String tabId) {
        if (tabId.equals("valg")) {
            getSupportFragmentManager().beginTransaction()
                    .replace(android.R.id.tabcontent, new ValgFragment())
                    .commit();
        }
        else if (tabId.equals("stat")) {
            getSupportFragmentManager().beginTransaction()
                    .replace(android.R.id.tabcontent, new StatistikFragment())
                    .commit();
        }
        else if (tabId.equals("forum")) {
            getSupportFragmentManager().beginTransaction()
                    .replace(android.R.id.tabcontent, new ForumFragment())
                    .commit();
        }
    }


}
