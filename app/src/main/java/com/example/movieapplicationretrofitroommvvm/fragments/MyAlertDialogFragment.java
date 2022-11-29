package com.example.movieapplicationretrofitroommvvm.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.movieapplicationretrofitroommvvm.InterfaceEditTextDialog;
import com.example.movieapplicationretrofitroommvvm.R;
import com.example.movieapplicationretrofitroommvvm.model.Result;


public class MyAlertDialogFragment extends DialogFragment {
    public  EditText mEditText;
    private Button done;
    private Button cancel;
    private Result result;
    private InterfaceEditTextDialog interfaceEditTextDialog;

    public MyAlertDialogFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        done =view.findViewById(R.id.btnValidez);
        cancel =view.findViewById(R.id.btnAnnulez);
        mEditText = (EditText) view.findViewById(R.id.txt_your_search);
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaceEditTextDialog.transfertEditText(mEditText.getText().toString());
                dismiss();

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public static MyAlertDialogFragment newInstance(String title) {
        MyAlertDialogFragment frag = new MyAlertDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    //je cree le setter de l'interface dont j'ai besoin
    public void setInterfaceEditTextDialog(InterfaceEditTextDialog interfaceEditTextDialog) {
        this.interfaceEditTextDialog = interfaceEditTextDialog;
    }










}
