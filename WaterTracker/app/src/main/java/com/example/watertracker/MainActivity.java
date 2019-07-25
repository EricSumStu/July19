package com.example.watertracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private static final String ABOUT_TITLE = "About";
    private static final String STATS_TITLE = "Stats";
    private static final String HOME_TITLE = "Water Tracker";
    private static final String SETTINGS_TITLE = "Settings";
    private Fragment aboutFragment = new AboutFragment();
    private Fragment settingsFragment = new SettingsFragment();
    private Fragment homeFragment = new HomeFragment();
    private Fragment statFragment = new StatFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this,
                drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toogle);
        toogle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

        navigationView.setCheckedItem(R.id.nav_home);
    }

    private void updateMenu(final String title, final Fragment fragment){
        Objects.requireNonNull(getSupportActionBar()).setTitle(title);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                fragment).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.nav_about:
                updateMenu(ABOUT_TITLE, aboutFragment);
                break;
            case R.id.nav_stat:
                updateMenu(STATS_TITLE, statFragment);
                break;
            case R.id.nav_home:
                updateMenu(HOME_TITLE, homeFragment);
                break;
            case R.id.nav_settings:
                updateMenu(SETTINGS_TITLE, settingsFragment);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}



