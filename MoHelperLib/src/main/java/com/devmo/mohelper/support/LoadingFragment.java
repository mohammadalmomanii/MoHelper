package com.devmo.mohelper.support;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;

import com.devmo.mohelper.R;
import com.devmo.mohelper.databinding.FragmentLoadingBinding;


public class LoadingFragment extends DialogFragment {

    FragmentLoadingBinding binding;
    static LoadingFragment fragment;


    public LoadingFragment() {
        // Required empty public constructor
    }


    public static LoadingFragment newInstance() {
        dismissDialog();
        fragment = new LoadingFragment();
        fragment.setCancelable(false);
        return fragment;
    }


    public static void dismissDialog() {
        if (fragment != null) {
            fragment.dismiss();
            fragment = null;
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLoadingBinding.inflate(inflater, container, false);
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.shape_rounded_10);
        return binding.getRoot();
    }


}

