package com.sg.angulliamosque.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.zxing.pdf417.encoder.PDF417;
import com.sg.angulliamosque.R;
import com.sg.angulliamosque.Adapters.VPadapter;
import com.sg.angulliamosque.Fragments.CreateAssetFragment;
import com.sg.angulliamosque.Fragments.ViewAssetListFragment;
import com.sg.angulliamosque.Fragments.ScannerFragment;
import com.sg.angulliamosque.Fragments.TaqEquipmentFragment;
import com.sg.angulliamosque.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    String token,devicetoken;
    private int[] tabIcons = {
            R.drawable.createasset,
            R.drawable.viewassets,
            R.drawable.sacnasset,
            R.drawable.tagasset
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPref = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        editor = sharedPref.edit();
        token = sharedPref.getString("token", "notset");
        devicetoken=sharedPref.getString("deviceToken","empty");

        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager);

        tabLayout.setupWithViewPager(viewPager);

        VPadapter vPadapter = new VPadapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vPadapter.addFragment(new CreateAssetFragment(), "Create asset");
        vPadapter.addFragment(new ViewAssetListFragment(), "View assets");
        vPadapter.addFragment(new ScannerFragment(), "Scanner");
        vPadapter.addFragment(new TaqEquipmentFragment(), "Tag equipment");
        viewPager.setAdapter(vPadapter);
        setupTabIcons();
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logoutmenu) {
            LogoutMethod();
            return true;
        }
        return super.onOptionsItemSelected(item);


    }

    private void LogoutMethod() {

        editor.clear();
        editor.apply();
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Logging out");
        progressDialog.setMessage("Please wait ..");
        progressDialog.show();
        Call<Void> call = RetrofitClient.getInstance().getapi().getLogout(devicetoken);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    editor.clear();
                    editor.apply();
                    Toast.makeText(MainActivity.this, "Logout successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "error while logging out !! ", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Failed to Logout", Toast.LENGTH_SHORT).show();
            }
        });


    }
}