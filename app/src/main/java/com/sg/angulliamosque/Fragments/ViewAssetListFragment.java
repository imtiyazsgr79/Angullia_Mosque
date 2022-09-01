package com.sg.angulliamosque.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sg.angulliamosque.Adapters.AssetViewAdapter;
import com.sg.angulliamosque.R;
import com.sg.angulliamosque.RetrofitClient;
import com.sg.angulliamosque.databinding.ViewAssetListFragmentBinding;
import com.sg.angulliamosque.models.ListObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ViewAssetListFragment extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private AssetViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ViewAssetListFragmentBinding binding;
    private List<ListObject> listObjects;

        private String workspace = "CMMS-AngulliaMosque-072021-001";
//    String workspace = "cmms-DEM-082009-001";

    private String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater, R.layout.view_asset_list_fragment, container, false);
        View view = binding.getRoot();

        SharedPreferences sharedPref = getContext().getSharedPreferences("sharedPrefs", getContext().MODE_PRIVATE);
        token = sharedPref.getString("token", "notset");

        binding.searchAsset.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                callListOfAssets(newText);

                return false;
            }
        });
        binding.searchAsset.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                if (!listObjects.isEmpty()) {
                    listObjects.clear();
                }
                return false;
            }
        });

        mLayoutManager = new LinearLayoutManager(getContext());
        binding.recyclerViewViewAsset.setLayoutManager(mLayoutManager);

        return view;
    }

    private void callListOfAssets(String newText) {

        Call<List<ListObject>> call = RetrofitClient.getInstance().getapi().getListOfAssets(workspace, newText, token);
        call.enqueue(new Callback<List<ListObject>>() {
            @Override
            public void onResponse(Call<List<ListObject>> call, Response<List<ListObject>> response) {
                if (response.code() == 200) {
                    listObjects = response.body();
                    mAdapter = new AssetViewAdapter(listObjects, getContext());
                    binding.recyclerViewViewAsset.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<List<ListObject>> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getCause());
            }
        });
    }
}