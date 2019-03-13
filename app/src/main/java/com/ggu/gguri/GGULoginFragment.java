package com.ggu.gguri;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ggu.gguri.databinding.FragmentGguloginBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class GGULoginFragment extends Fragment {

    FragmentGguloginBinding binding;

    MainActivity mainActivity = new MainActivity();


    public GGULoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mainActivity.setActionBarTitle(getResources().getString(R.string.empty));

        return inflater.inflate(R.layout.fragment_ggulogin, container, false);
    }

}
