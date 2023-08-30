package com.devmo.mohelper.support;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.devmo.mohelper.MainInterface;
import com.devmo.mohelperlib.R;
import com.devmo.mohelperlib.databinding.FragmentCustomeDialogBinding;


public class CustomDialogFragment extends DialogFragment {

    static public FragmentCustomeDialogBinding binding;
    static public CustomDialogFragment fragment;
    MainInterface mainInterface;

    public CustomDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (mainInterface instanceof CustomDialogFragment)
            mainInterface = (MainInterface) context;
    }

    public static CustomDialogFragment newInstance() {
        dismissDialog();
        fragment = new CustomDialogFragment();
        return fragment;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mainInterface = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.shape_rounded_10);
        binding = FragmentCustomeDialogBinding.inflate(inflater, container, false);

        AppHelper.setInvisible(binding.iv);
        AppHelper.setInvisible(binding.tvTitle);
        AppHelper.setInvisible(binding.btnNegative);
        AppHelper.setInvisible(binding.btnNeutral);
        AppHelper.setInvisible(binding.btnPositive);
        AppHelper.setGone(binding.layNote);
        AppHelper.setGone(binding.tvDescription);


        return binding.getRoot();
    }

    public static void dismissDialog() {
        if (fragment != null) {
            fragment.dismiss();
            fragment = null;
        }
    }



    public CustomDialogFragment startShow(FragmentManager manager) {

        this.showNow(manager, "");
        return fragment;
    }

    public CustomDialogFragment setImage(Drawable drawable) {
        AppHelper.setVisible(binding.iv);
        binding.iv.setImageDrawable(drawable);
        return fragment;
    }

    public CustomDialogFragment setTitle(String title) {
        AppHelper.setVisible(binding.tvTitle);
        binding.tvTitle.setText(title);
        return fragment;
    }

    public CustomDialogFragment setDescription(String description) {
        AppHelper.setVisible(binding.tvDescription);
        binding.tvDescription.setText(description);
        return fragment;
    }

    public CustomDialogFragment setBtnPositive(String title, MainInterface mainInterface) {
        AppHelper.setVisible(binding.btnPositive);
        binding.btnPositive.setText(title);
        binding.btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mainInterface.onCustomDialogItemClick();
                dismissDialog();
            }
        });
        return fragment;
    }

    public CustomDialogFragment setBtnNegative(String title, MainInterface mainInterface) {
        AppHelper.setVisible(binding.btnNegative);
        binding.btnNegative.setText(title);
        binding.btnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mainInterface.onCustomDialogItemClick();
                dismissDialog();
            }
        });
        return fragment;
    }

    public CustomDialogFragment setNote() {
        AppHelper.setVisible(binding.layNote);

        return fragment;
    }

    public String getNote() {
        return binding.etNote.getText() + "";
    }

    public CustomDialogFragment setBtnNeutral(String title, MainInterface mainInterface) {
        AppHelper.setVisible(binding.btnNeutral);
        binding.btnNeutral.setText(title);
        binding.btnNeutral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mainInterface.onCustomDialogItemClick();
                dismissDialog();
            }
        });
        return fragment;
    }


}