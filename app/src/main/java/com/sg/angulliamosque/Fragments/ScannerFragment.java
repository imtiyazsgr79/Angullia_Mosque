package com.sg.angulliamosque.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import com.sg.angulliamosque.Activities.UpdateAssetActivity;
import com.sg.angulliamosque.R;
import com.sg.angulliamosque.databinding.ScannerEquipFragBinding;


public class ScannerFragment extends Fragment {

    ScannerEquipFragBinding binding;
    private CodeScanner mCodeScanner;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(
                inflater, R.layout.scanner_equip_frag, container, false);

        View view = binding.getRoot();

         CodeScannerView scannerView=binding.scannerView;
        mCodeScanner = new CodeScanner(getContext(),scannerView);
        binding.scanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCodeScanner.startPreview();
                scannerView.setVisibility(View.VISIBLE);


                mCodeScanner.setDecodeCallback(new DecodeCallback() {
                    @Override
                    public void onDecoded(@NonNull final Result result) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent=new Intent(getContext(), UpdateAssetActivity.class);
                                intent.putExtra("equipCodeString",result.getText());
                                intent.putExtra("from","scanner");
                                getContext().startActivity(intent);
                                getActivity().finish();
                                mCodeScanner.releaseResources();
                            }
                        });
                    }
                });
            }
        });


        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
}