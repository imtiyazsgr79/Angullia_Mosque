package com.sg.angulliamosque.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.material.button.MaterialButton;
import com.google.zxing.Result;
import com.sg.angulliamosque.R;
import com.sg.angulliamosque.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TaqEquipmentFragment extends Fragment {

    private CodeScanner mCodeScanner;
    private MaterialButton tagEQUIP, AUDITEQUIP;
    private CodeScannerView scannerView;
    private String workspace = "CMMS-AngulliaMosque-072021-001";
    private String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tag_equip_fragment, container, false);


        SharedPreferences sharedPref = getContext().getSharedPreferences("sharedPrefs", getContext().MODE_PRIVATE);
        token = sharedPref.getString("token", "notset");

        tagEQUIP = rootView.findViewById(R.id.taqEquip);
        scannerView = rootView.findViewById(R.id.scanner_view_tag);
        mCodeScanner = new CodeScanner(getContext(), scannerView);

        tagEQUIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scannerView.setVisibility(View.VISIBLE);
                mCodeScanner.startPreview();
                mCodeScanner.setDecodeCallback(new DecodeCallback() {
                    @Override
                    public void onDecoded(@NonNull final Result result) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callChecktaqqing(result.getText());
//                                callTaqmethod(result.getText());
                            }
                        });
                    }
                });
//
            }
        });

        return rootView;
    }//

    private void showAlert(String text) {
        mCodeScanner.releaseResources();


        AlertDialog.Builder alertd=new AlertDialog.Builder(getContext());
        alertd.setTitle("Confirmation for  "+text  +" Equipment");
        alertd.setMessage("Do you want to tag equipment?");
        alertd.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callTaqmethod(text);
                dialog.dismiss();
            }
        });
        alertd.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mCodeScanner.releaseResources();
                scannerView.setVisibility(View.GONE);
           dialog.dismiss();
            }
        });
        alertd.create().show();
    }

    private void callChecktaqqing(String text) {
        Call<Void> call = RetrofitClient.getInstance().getapi().getTCheckagging(workspace, text, token);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 404) {
                    //not tagged yet
                    showAlert(text);

                } else if (response.code() == 208) {
                    mCodeScanner.releaseResources();
                    scannerView.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Already tagged", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "error while tagging:" + response.code(), Toast.LENGTH_SHORT).show();
                    mCodeScanner.releaseResources();
                    scannerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    private void callTaqmethod(String text) {
        Call<Void> call = RetrofitClient.getInstance().getapi().getTagging(workspace, text, token);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    mCodeScanner.releaseResources();
                    scannerView.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Tagged successfully", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 208) {
                    Toast.makeText(getContext(), "Already Tagged", Toast.LENGTH_SHORT).show();
                    mCodeScanner.releaseResources();
                    scannerView.setVisibility(View.GONE);
                } else {
                    Toast.makeText(getContext(), "error while tagging:" + response.code(), Toast.LENGTH_SHORT).show();
                    mCodeScanner.releaseResources();
                    scannerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }


    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
}