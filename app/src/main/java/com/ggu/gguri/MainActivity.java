package com.ggu.gguri;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    static TextView title_bar;
    static ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // title bar
        title_bar = findViewById(R.id.title);
//        setActionBarTitle(getResources().getString(R.string.app_name), getResources().getColor(R.color.colorMain), 28);
        setTitle(getResources().getString(R.string.app_name));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_bar);

        // ViewPager Test
        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new pagerAdapter(getSupportFragmentManager()));
        viewPager.setCurrentItem(1);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.action_more:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.action_home:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.action_login:
                    viewPager.setCurrentItem(2);
                    break;
            }
            return true;
        });
        bottomNavigationView.setSelectedItemId(R.id.action_home);

        // push Test
        FirebaseInstanceId.getInstance().getToken();

        if (FirebaseInstanceId.getInstance().getToken() != null) {
            Log.d(TAG, "token = " + FirebaseInstanceId.getInstance().getToken());
        }

    }

    private class pagerAdapter extends FragmentStatePagerAdapter {

        public pagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    return new GGUSideMenuFragment();
                case 1:
                    return new GGUMainFragment();
                case 2:
                    return new GGULoginFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    // 뒤로가기 이벤트
    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
    public void setActionBarTitle(String title, int color, int size){
        title_bar.setText(title);
        title_bar.setTextColor(color);
        title_bar.setTextSize(size);
//        setTitle(title);
//        setTitleColor(color);
    }

}
