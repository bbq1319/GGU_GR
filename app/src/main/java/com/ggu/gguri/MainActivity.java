package com.ggu.gguri;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;

import static com.ggu.gguri.R.id.frame;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private MenuItem prevBottomNavigation;
    BottomNavigationView bottomNavigationView;
    Fragment fragment = null;
    static TextView titleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // title bar
        titleBar = findViewById(R.id.title_bar);
        setActionBarTitle(getResources().getString(R.string.app_name));

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.action_more:
                    fragment = new GGUSideMenuFragment();
                    break;
                case R.id.action_home:
                    fragment = new GGUHomeFragment();
                    break;
                case R.id.action_login:
                    fragment = new GGULoginFragment();
                    break;
            }
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(frame, fragment);
            ft.addToBackStack(null);
            ft.commit();
            return true;
        });

        bottomNavigationView.setSelectedItemId(R.id.action_home);

        // push Test
        FirebaseInstanceId.getInstance().getToken();

        if (FirebaseInstanceId.getInstance().getToken() != null) {
            Log.d(TAG, "token = " + FirebaseInstanceId.getInstance().getToken());
        }

        // 첫 화면 setting
        GGUHomeFragment fragment = new GGUHomeFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.setCustomAnimations(R.anim.delta_in, R.anim.delta_out);
        ft.replace(frame, fragment);
        ft.commit();
    }

    // 뒤로가기 이벤트
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // 오른쪽 상단 설정
    // 현재 미사용
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    // action bar 바꾸기
    public void setActionBarTitle(String title){
        titleBar.setText(title);
    }

//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_home) {
//            // Handle the camera action
//        } else if (id == R.id.nav_ggu) {
//            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://ggu.ac.kr"));
//            startActivity(intent);
//        } else if (id == R.id.nav_notice) {
//
//        } else if (id == R.id.nav_menu) {
//
//        } else if (id == R.id.nav_delivery) {
//
//        } else if (id == R.id.nav_bus) {
//
//        } else if (id == R.id.nav_my_info) {
//
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }


    @Override
    public void onPageSelected(int position) {
        if(prevBottomNavigation != null) {
            prevBottomNavigation.setChecked(false);
        }
        prevBottomNavigation = bottomNavigationView.getMenu().getItem(position);
        prevBottomNavigation.setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }
}
