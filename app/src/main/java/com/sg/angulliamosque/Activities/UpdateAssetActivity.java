package com.sg.angulliamosque.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.sg.angulliamosque.R;
import com.sg.angulliamosque.RetrofitClient;
import com.sg.angulliamosque.databinding.ActivityUpdateAssetBinding;
import com.sg.angulliamosque.models.Building;
import com.sg.angulliamosque.models.CreateAssetRequest;
import com.sg.angulliamosque.models.EditObjectRecieved;
import com.sg.angulliamosque.models.EquipmentSystem;
import com.sg.angulliamosque.models.EquipmnetSubSystem;
import com.sg.angulliamosque.models.Location;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateAssetActivity extends AppCompatActivity {

    ActivityUpdateAssetBinding binding;
    //      String token="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJhZG1pbiIsImlhdCI6MTYyOTgwNjQwMywic3ViIjoic3RpZW1vYmlsZWFwcHdlYnRva2VucyIsImlzcyI6ImFzdGUuY28uaW4iLCJleHAiOjE2OTI4Nzg0MDN9.T7p5kw9gdweXiaO9JWeh8ZjtqXHy4Z6dMQwKhC0CjiQ";
    String workspace = "CMMS-AngulliaMosque-072021-001";
    //    String workspace = "cmms-DEM-082009-001";
    private String token;
    List<String> buildingList = new ArrayList<>();
    List<String> locationList = new ArrayList<>();
    int selectedBuildingId, selectedLocation, selectedEquipId, selectedSubEquipId;
    List<Building> mainBuildingList;
    List<Location> mainLocationList;
    int equipIdInt;
    Long equipIdLong;
    private EditObjectRecieved objectRecieved;
    String intentComingFrom, equipcodeString;
    String inputdateOfTaq = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_asset);

        SharedPreferences sharedPref = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        token = sharedPref.getString("token", "notset");

        Intent intent = getIntent();
        equipIdInt = intent.getIntExtra("equipId", 0);
        intentComingFrom = intent.getStringExtra("from");
        equipcodeString = intent.getStringExtra("equipCodeString");

        equipIdLong = new Long(equipIdInt);


//        String workspace=getIntent().getStringExtra("workspace");
//        String token=getIntent().getStringExtra("token");
        buildingList.add(0, "Select Building");
        locationList.add(0, "Select Location");
        callBuilding();

        binding.createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateMethod();
            }
        });


    }


    private void getData(int euipId, String workspace, String token) {

        Call<EditObjectRecieved> call = RetrofitClient.getInstance().getapi().getDataForUpdate(workspace, euipId, token);
        call.enqueue(new Callback<EditObjectRecieved>() {
            @Override
            public void onResponse(Call<EditObjectRecieved> call, Response<EditObjectRecieved> response) {
                if (response.code() == 200) {
                    objectRecieved = response.body();

                    setData(objectRecieved);
                }
                Log.d("TAG", "onResponse: " + response.code());
            }

            @Override
            public void onFailure(Call<EditObjectRecieved> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getCause());
            }
        });

    }

    private void setData(EditObjectRecieved objectRecieved) {

        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");

        Date acquisitiondate = null;
        Date serviceInDate = null;
        Date tagIndate = null;


        try {

            acquisitiondate = inputFormat.parse(objectRecieved.getDateOfAquistion());
            serviceInDate = inputFormat.parse(objectRecieved.getIntallationDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (objectRecieved.getDateOfTagging() != null) {
            inputdateOfTaq = objectRecieved.getDateOfTagging();
            try {
                tagIndate = inputFormat.parse(inputdateOfTaq);
                String outputTaggingDate = outputFormat.format(tagIndate);

                binding.dateofTagging.setText(outputTaggingDate);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        String outputAcquisition = outputFormat.format(acquisitiondate);
        String outputServiceIn = outputFormat.format(serviceInDate);


        binding.brand.setText(objectRecieved.getBrandd());
        binding.discription.setText(objectRecieved.getDescription());
        binding.datein.setText(outputAcquisition);
        binding.dateofacquistion.setText(outputServiceIn);

        selectedEquipId = objectRecieved.getEquipmentSystem().getId();
        selectedSubEquipId = objectRecieved.getSubtype().getId();

        binding.equipmentnames.setText(objectRecieved.getName());
        binding.user.setText(objectRecieved.getUser());
        binding.equipmentcode.setText(objectRecieved.getEquipmentCode());
        binding.cost.setText(objectRecieved.getCost());
        binding.expectedLifeInMonths.setText(String.valueOf(objectRecieved.expectedDateinMonths));
        binding.contactno.setText(objectRecieved.getContactNo());
        binding.dia.setText(objectRecieved.getDia());
        binding.fullloadcurrent.setText(objectRecieved.getFullLoadCurrent());
        binding.cpref.setText(objectRecieved.getCpRef());
        binding.powerrequirement.setText(objectRecieved.getPowerRequirement());
        binding.staticpressure.setText(objectRecieved.getStaticPressure());
        binding.serialno.setText(objectRecieved.getSerialNumber());
        binding.sublocation.setText(objectRecieved.getSubLocation());
        binding.fanref.setText(objectRecieved.getFanRef());
        binding.warrentyperiod.setText(objectRecieved.getWarrantyPeriod());
        binding.weight.setText(objectRecieved.getWeightt());
        binding.isolatorsize.setText(objectRecieved.getIsolatorSize());
        binding.electricpowersupply.setText(objectRecieved.getElectricPowerSupply());
        binding.dimension.setText(objectRecieved.getDimension());
        binding.curef.setText(objectRecieved.getCuRef());
        binding.culocation.setText(objectRecieved.getCuLocation());
        binding.fcuref.setText(objectRecieved.getFcuRef());
        binding.airflow.setText(objectRecieved.getAirFlow());
        binding.coolincapacity.setText(objectRecieved.getCoolingCapacity());
        binding.supplieraddress.setText(objectRecieved.getSupplierAddress());
        binding.suppliername.setText(objectRecieved.getSupplierName());
        binding.assetType.setText(objectRecieved.getAssettype());
        binding.assetNo.setText(objectRecieved.getAssetNo());
        binding.remarks.setText(objectRecieved.getRemarks());
        binding.sublocation.setText(objectRecieved.getSubLocation());
        binding.model.setText(objectRecieved.getMake());
        binding.type.setText(objectRecieved.getTypeOfFcu());
        binding.power.setText(objectRecieved.getPower());
        binding.serialno.setText(objectRecieved.getSerialNumber());
        binding.cpref.setText(objectRecieved.getCpRef());

        binding.quantity.setText(objectRecieved.getQty());
        binding.unit.setText(objectRecieved.getUnit());
        binding.expectedLifeInMonths.setText(String.valueOf(objectRecieved.getLife()));

        binding.equipNameTv.setText(objectRecieved.getEquipmentSystem().getSystemName());
        binding.equipSubNameTv.setText(objectRecieved.getSubtype().subSystemName);

        int bindex = 0;
        for (int i = 0; i < buildingList.size(); i++) {
            if (buildingList.get(i).equals(objectRecieved.getBuilding().getBuild_name())) {
                bindex = i;
            }
            binding.buildingSpinner.setSelection(bindex);
        }

        int lindex = 0;
        for (int i = 0; i < locationList.size(); i++) {
            if (locationList.get(i).equals(objectRecieved.getLocation().getLoc_id())) {
                lindex = i;
            }
            binding.locationSpinner.setSelection(lindex);
        }

    }

    private void callBuilding() {
        Call<List<Building>> call = RetrofitClient.getInstance().getapi().getBuilding(workspace, token);
        call.enqueue(new Callback<List<Building>>() {
            @Override
            public void onResponse(Call<List<Building>> call, Response<List<Building>> response) {
                if (response.code() == 200) {
                    mainBuildingList = new ArrayList<>();
                    String name;
                    String build_desc = null, priority_id = null, zone_id = null;
                    int id = 0;

                    for (int i = 0; i < response.body().size(); i++) {
                        name = response.body().get(i).getBuild_name();
                        id = response.body().get(i).getId();
                        buildingList.add(name);
                        mainBuildingList.add(new Building(id, name, build_desc, priority_id, zone_id));
                    }
                    setBuildingAdapter(mainBuildingList);
                }
            }

            @Override
            public void onFailure(Call<List<Building>> call, Throwable t) {
            }
        });


    }

    private void callLocation(int buildId) {
        Call<List<Location>> call = RetrofitClient.getInstance().getapi().getLocation(workspace, buildId, token);
        call.enqueue(new Callback<List<Location>>() {
            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                if (response.code() == 200) {
                    mainLocationList = new ArrayList<>();
                    locationList.clear();
                    locationList.add("Select Location");
                    String name = null;
                    int id = 0;

                    for (int i = 0; i < response.body().size(); i++) {
                        name = response.body().get(i).getLoc_id();
                        id = response.body().get(i).getId();
                        locationList.add(name);
                        mainLocationList.add(new Location(id, name));
                    }
                    setLocationAdapter(mainLocationList);

                    if (intentComingFrom.equals("list")) {
                        getData(equipIdInt, workspace, token);
                    } else if (intentComingFrom.equals("scanner")) {
                        callDataFromQr(equipcodeString);
                    }


                }
            }

            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {
                Toast.makeText(UpdateAssetActivity.this, "failed " + t.getCause(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setBuildingAdapter(List<Building> mainBuildingList) {
        ArrayAdapter<String> buildingAdapter = new ArrayAdapter<>(UpdateAssetActivity.this,
                android.R.layout.simple_spinner_dropdown_item, buildingList);

        buildingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.buildingSpinner.setAdapter(buildingAdapter);
        binding.buildingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedBuilding = binding.buildingSpinner.getSelectedItem().toString();
                int iid = 0;

                for (int i = 0; i < mainBuildingList.size(); i++) {
                    if (mainBuildingList.get(i).getBuild_name().contains(selectedBuilding)) {
                        iid = mainBuildingList.get(i).getId();
                        selectedBuildingId = iid;
                    }
                }

                callLocation(iid);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void setLocationAdapter(List<Location> mainLocationList) {

        ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(UpdateAssetActivity.this, android.R.layout.simple_spinner_dropdown_item, locationList);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.locationSpinner.setAdapter(locationAdapter);
        binding.locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = binding.locationSpinner.getSelectedItem().toString();
                for (int i = 0; i < mainLocationList.size(); i++) {
                    if (mainLocationList.get(i).getLoc_id().contains(selectedItem)) {
                        selectedLocation = mainLocationList.get(i).getId();
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    private void updateMethod() {
        ProgressDialog progressDialog = new ProgressDialog(UpdateAssetActivity.this);
        progressDialog.setTitle("Updating Asset");
        progressDialog.setMessage("Please wait..");
        progressDialog.show();

        Building building = new Building(selectedBuildingId);
        Location location = new Location(selectedLocation);
        EquipmentSystem equipmentSystem = new EquipmentSystem(selectedEquipId, objectRecieved.getEquipmentSystem().getSystemName(),
                objectRecieved.getEquipmentSystem().getCategory());
        EquipmnetSubSystem equipmnetSubSystem = new EquipmnetSubSystem(selectedSubEquipId,
                objectRecieved.getSubtype().getSubSystemName());

        double life = Double.parseDouble(binding.expectedLifeInMonths.getText().toString());


        DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date acquisitiondate = null;
        Date serviceInDate = null;
        try {
            acquisitiondate = inputFormat.parse(binding.dateofacquistion.getText().toString());
            serviceInDate = inputFormat.parse(binding.datein.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputAcquisition = outputFormat.format(acquisitiondate);
        String outputServiceIn = outputFormat.format(serviceInDate);
        int frequency = 1;
        CreateAssetRequest createAssetRequest = new CreateAssetRequest(frequency, binding.unit.getText().toString(), binding.discription.getText().toString(),
                binding.brand.getText().toString(), inputdateOfTaq, equipIdLong, life, "depreciable",
                binding.assetNo.getText().toString(), "nonmaintainable", binding.sublocation.getText().toString(),
                binding.model.getText().toString(), binding.coolincapacity.getText().toString(), binding.type.getText().toString(), binding.airflow.getText().toString(),
                binding.curef.getText().toString(), binding.culocation.getText().toString(), binding.weight.getText().toString(), binding.dimension.getText().toString(),
                binding.electricpowersupply.getText().toString(),
                binding.isolatorsize.getText().toString(), binding.remarks.getText().toString(), binding.equipmentcode.getText().toString(),
                binding.suppliername.getText().toString(), binding.supplieraddress.getText().toString(),
                binding.contactno.getText().toString(), binding.fcuref.getText().toString(), binding.quantity.getText().toString(),
                binding.fanref.getText().toString(), binding.staticpressure.getText().toString(),
                binding.dia.getText().toString(), binding.warrentyperiod.getText().toString(), binding.power.getText().toString(),
                binding.fullloadcurrent.getText().toString(), binding.cpref.getText().toString(),
                binding.powerrequirement.getText().toString(), binding.serialno.getText().toString(), binding.equipmentnames.getText().toString(),
                outputAcquisition, binding.cost.getText().toString(),
                binding.user.getText().toString(), outputServiceIn
                , building, location, equipmentSystem, equipmnetSubSystem);

        Call<Void> call = RetrofitClient.getInstance().getapi().updateAsset(createAssetRequest, token);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                progressDialog.dismiss();
                if (response.code() == 200) {
                    Toast.makeText(UpdateAssetActivity.this, "Asset Updated Successfully", Toast.LENGTH_SHORT).show();

                    gotoMainPage();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(UpdateAssetActivity.this, "Failed to update :" + t.getCause(), Toast.LENGTH_SHORT).show();

            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    private void gotoMainPage() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    private void callDataFromQr(String equipCodeString) {
        Call<EditObjectRecieved> call = RetrofitClient.getInstance().getapi().getDataFormQr(workspace, equipCodeString, token);
        call.enqueue(new Callback<EditObjectRecieved>() {
            @Override
            public void onResponse(Call<EditObjectRecieved> call, Response<EditObjectRecieved> response) {
                if (response.code() == 200) {
                    objectRecieved = response.body();
                    equipIdLong= new Long(response.body().id);
                    setData(objectRecieved);
                }
                Log.d("TAG", "onResponse: " + response.code());
            }

            @Override
            public void onFailure(Call<EditObjectRecieved> call, Throwable t) {
                Log.d("TAG", "onFailure: for qr" + t.getCause());
            }
        });
    }
}