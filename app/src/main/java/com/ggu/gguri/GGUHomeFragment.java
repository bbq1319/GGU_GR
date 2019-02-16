package com.ggu.gguri;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ggu.gguri.databinding.FragmentGguhomeBinding;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.ggu.gguri.R.id.frame;


/**
 * A simple {@link Fragment} subclass.
 */
public class GGUHomeFragment extends Fragment implements View.OnClickListener {

    long now;
    Date date;
    SimpleDateFormat format = new SimpleDateFormat("yyyy\nMMdd\nhh:mm");

    public GGUHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentGguhomeBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gguhome, container, false);
        View v = binding.getRoot();

        now = System.currentTimeMillis();
        date = new Date(now);
        binding.homeTime.setText(format.format(date));

        binding.gguHome.setOnClickListener(this);
        binding.gguNotice.setOnClickListener(this);
        binding.gguBus.setOnClickListener(this);
        binding.gguMenu.setOnClickListener(this);
        binding.gguDelivery.setOnClickListener(this);
        binding.gguMyInfo.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        Fragment fragment = null;

        if(view.getId() == R.id.ggu_home) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://ggu.ac.kr"));
            startActivity(intent);
        } else if(view.getId() == R.id.ggu_notice) {
            fragment = new GGUNoticeFragment();
        } else if(view.getId() == R.id.ggu_bus){
            fragment = new GGUBusFragment();
        } else if(view.getId() == R.id.ggu_menu){
            fragment = new GGUMenuFragment();
        } else if(view.getId() == R.id.ggu_delivery) {
            fragment = new GGUDeliveryFragment();
        } else if(view.getId() == R.id.ggu_my_info){
            fragment = new GGUMyInfoFragment();
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
