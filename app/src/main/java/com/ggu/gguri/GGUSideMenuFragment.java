package com.ggu.gguri;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ggu.gguri.databinding.FragmentGgusideMenuBinding;

import static com.ggu.gguri.R.id.frame;


/**
 * A simple {@link Fragment} subclass.
 */
public class GGUSideMenuFragment extends Fragment implements View.OnClickListener {

    FragmentGgusideMenuBinding binding;
    GGUNoticeFragment gguNoticeFragment = new GGUNoticeFragment();
    MainActivity mainActivity = new MainActivity();

    Fragment fragment = null;
    String URL;

    public GGUSideMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gguside_menu, container, false);
        View v = binding.getRoot();

        mainActivity.setActionBarTitle(getResources().getString(R.string.more), getResources().getColor(R.color.colorBlack), 24);

        binding.sideSchool.setOnClickListener(this);
        binding.sideDepartment.setOnClickListener(this);
        binding.sideNotice.setOnClickListener(this);
        binding.sideFree.setOnClickListener(this);
        binding.sideAnonymous.setOnClickListener(this);
        binding.sideCast.setOnClickListener(this);
        binding.sideDelivery.setOnClickListener(this);
        binding.sideMenu.setOnClickListener(this);
        binding.sideBus.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.side_school) {
            gguNoticeFragment.URL = "https://www.ggu.ac.kr/sub01080101";
            fragment = new GGUNoticeFragment();
        } else if(v.getId() == R.id.side_department) {
            gguNoticeFragment.URL = "https://www.ggu.ac.kr/sub050501";
            fragment = new GGUNoticeFragment();
        } else if(v.getId() == R.id.side_notice) {
            fragment = new GGUNoticeFragment();
        } else if(v.getId() == R.id.side_free) {
            fragment = new GGUNoticeFragment();
        } else if(v.getId() == R.id.side_anonymous) {
            fragment = new GGUNoticeFragment();
        } else if(v.getId() == R.id.side_cast) {
            fragment = new GGUNoticeFragment();
        } else if(v.getId() == R.id.side_delivery) {
            fragment = new GGUDeliveryFragment();
        } else if(v.getId() == R.id.side_menu) {
            fragment = new GGUMenuFragment();
        } else if(v.getId() == R.id.side_bus) {
            fragment = new GGUBusFragment();
        }

        if (fragment != null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.replace(frame, fragment);
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            ft.commit();
        }
    }
}
