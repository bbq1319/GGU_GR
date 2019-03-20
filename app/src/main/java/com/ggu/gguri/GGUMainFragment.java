package com.ggu.gguri;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class GGUMainFragment extends Fragment
        implements NavigationView.OnNavigationItemSelectedListener {

    ViewPager viewPager;

    public GGUMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_ggumain, container, false);

        // ViewPager Test
        viewPager = v.findViewById(R.id.viewpager);
        viewPager.setAdapter(new GGUMainFragment.pagerAdapter(getActivity().getSupportFragmentManager()));
        viewPager.setCurrentItem(1);

        BottomNavigationView bottomNavigationView = v.findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.action_more:
                    viewPager.setCurrentItem(0);
                    // fragment = new GGUSideMenuFragment();
                    break;
                case R.id.action_home:
                    viewPager.setCurrentItem(1);
                    //fragment = new GGUHomeFragment();
                    break;
                case R.id.action_login:
                    viewPager.setCurrentItem(2);
                    // fragment = new GGULoginFragment();
                    break;
            }
//            String fragmentTag = fragment.getClass().getSimpleName();
//            getSupportFragmentManager().popBackStack(fragmentTag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(frame, fragment);
//            ft.addToBackStack(fragmentTag);
//            ft.commit();
            return true;
        });
        bottomNavigationView.setSelectedItemId(R.id.action_home);


        return v;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
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
                    return new GGUHomeFragment();
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

}
