package com.sg.angulliamosque.Fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.sg.angulliamosque.Activities.LoginActivity;
import com.sg.angulliamosque.Activities.MainActivity;
import com.sg.angulliamosque.R;
import com.sg.angulliamosque.RetrofitClient;

import com.sg.angulliamosque.databinding.CreateAssetBindingImpl;
import com.sg.angulliamosque.models.Building;
import com.sg.angulliamosque.models.CreateAssetRequest;
import com.sg.angulliamosque.models.EquipmentSystem;
import com.sg.angulliamosque.models.Location;
import com.sg.angulliamosque.models.EquipmnetSubSystem;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class CreateAssetFragment extends Fragment {


    private com.sg.angulliamosque.databinding.CreateAssetBinding binding;

    private Spinner equipNameSpinner, equipSubTypeSpinner, buildingSpinner, locationSpinner;
    private TextInputLayout acqDateLayout, dateInsevLayout;
    private TextInputEditText dateofacquisition, dateInService, equipmentName,
            userEt, costEt, expectedLifeEt, assetNoEt, assetTypeEt, unitEt;
    private List<String> equipmentNameList = new ArrayList<>();
    private List<String> equipmentSubTypeNameList = new ArrayList<>();
    private List<String> buildingList = new ArrayList<>();
    private List<String> locationList = new ArrayList<>();
    private int selectedEquipmentId, selectedSubEquipId, selectedLocation, selectedBuildingId;

    private List<Building> mainBuildingList = new ArrayList<>();
    private List<Location> mainLocationList = new ArrayList<>();
    private List<EquipmentSystem> mainEquipNameList = new ArrayList<>();
    private List<EquipmnetSubSystem> mainEquipSubNameList = new ArrayList<>();


    private SharedPreferences.Editor editor;
    private String workspace = "CMMS-AngulliaMosque-072021-001";
    //    String workspace = "cmms-DEM-082009-001";
    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener date;
    private String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.create_asset, container, false);

        View view = binding.getRoot();

        SharedPreferences sharedPref = getContext().getSharedPreferences("sharedPrefs", getContext().MODE_PRIVATE);
        editor = sharedPref.edit();

        token = sharedPref.getString("token", "notset");

        acqDateLayout = view.findViewById(R.id.acqdat_layout);
        dateInsevLayout = view.findViewById(R.id.datinservice_layout);

        equipNameSpinner = view.findViewById(R.id.equip_name_spinner);
        equipSubTypeSpinner = view.findViewById(R.id.equip_sub_name_spinner);
        buildingSpinner = view.findViewById(R.id.building_spinner);
        locationSpinner = view.findViewById(R.id.location_spinner);
        dateofacquisition = view.findViewById(R.id.dateofacquistion);
        dateInService = view.findViewById(R.id.datein);
        equipmentName = view.findViewById(R.id.equipmentnames);
        userEt = view.findViewById(R.id.user);
        costEt = view.findViewById(R.id.cost);
        expectedLifeEt = view.findViewById(R.id.expectedLifeInMonths);
        assetNoEt = view.findViewById(R.id.asset_no);
        assetTypeEt = view.findViewById(R.id.asset_type);
        unitEt = view.findViewById(R.id.unit);

        equipmentNameList.add("Select Equipment Type");
        equipmentSubTypeNameList.add("Select Equipment SubType");
        locationList.add("Select Location");
        buildingList.add("Select Building");

        myCalendar = Calendar.getInstance();

//        toekn = getActivity().getIntent().getExtras().getString("token");

        callEquipment();
        callBuilding();

        setBuildingAdapter(mainBuildingList);
        setLocationAdapter(mainLocationList);
        setAdapters(mainEquipNameList);
        setEquipSubtypeAdapter(mainEquipSubNameList);


        dateofacquisition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dateOfAcq = "dateOfAcq";
                callDateMethod(dateOfAcq);
            }
        });
        dateInService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dateInService = "dateInService";
                callDateMethod(dateInService);
            }
        });
        binding.createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String subLocation = binding.sublocation.getText().toString();
                String model = binding.model.getText().toString();
                String coolingCapacity = binding.coolincapacity.getText().toString();
                String typeOfFcuString = binding.type.getText().toString();
                String airFlow = binding.airflow.getText().toString();
                String cuRef = binding.curef.getText().toString();
                String cuLocation = binding.culocation.getText().toString();
                String weight = binding.weight.getText().toString();
                String dimension = binding.dimension.getText().toString();
                String electricPowerSupply = binding.electricpowersupply.getText().toString();
                String isolatorSize = binding.isolatorsize.getText().toString();
                String remarks = binding.remarks.getText().toString();
                String equipCode = binding.equipmentcode.getText().toString();
                String supplierName = binding.suppliername.getText().toString();
                String supplierAddress = binding.supplieraddress.getText().toString();
                String contactNo = binding.contactno.getText().toString();
                String fcuRef = binding.fcuref.getText().toString();
                String quantity = binding.quantity.getText().toString();
                String fanRef = binding.fanref.getText().toString();
                String staticPressure = binding.staticpressure.getText().toString();
                String warrantyPeriod = binding.warrentyperiod.getText().toString();
                String dia = binding.dia.getText().toString();
                String power = binding.power.getText().toString();
                String fullLoadCurrent = binding.fullloadcurrent.getText().toString();
                String cpRef = binding.cpref.getText().toString();
                String powerRequirement = binding.powerrequirement.getText().toString();
                String serialNo = binding.serialno.getText().toString();

                String equipmentname = equipmentName.getText().toString();
                String user = binding.user.getText().toString();
                String cost = binding.cost.getText().toString();
                Date dateOfAcquiValue = null, dateInServiceVal = null;
                String exlife = binding.expectedLifeInMonths.getText().toString();
                String description = binding.discription.getText().toString();
                String brandd = binding.brand.getText().toString();
                String unitString = binding.unit.getText().toString();

                String assetNoString = "1";
                String assetTypeString = "both" +
                        "";
                String assetstatus = "depreciable";

                String acquitionDateString = dateofacquisition.getText().toString();
                String dateInserviceString = dateInService.getText().toString();


                if (selectedEquipmentId == 0 || selectedSubEquipId == 0 ||
                        selectedLocation == 0 || selectedBuildingId == 0 ||
                        user.trim().isEmpty() || cost.trim().isEmpty() ||
                        acquitionDateString.trim().isEmpty() || dateInserviceString.trim().isEmpty() ||
                        equipmentname.trim().isEmpty() || exlife.trim().isEmpty() ||
                        contactNo.trim().isEmpty() || contactNo.trim().length() < 8) {

                    Toast.makeText(getActivity(), "Fill required fields ", Toast.LENGTH_SHORT).show();

                } else {

                    DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
                    DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

                    Date acquisitiondate = null;
                    Date serviceInDate = null;
                    try {
                        acquisitiondate = inputFormat.parse(acquitionDateString);
                        serviceInDate = inputFormat.parse(dateInserviceString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String outputAcquisition = outputFormat.format(acquisitiondate);
                    String outputServiceIn = outputFormat.format(serviceInDate);

                    double expetedlifeinmonths = Double.parseDouble(exlife);

                    Building building = new Building(selectedBuildingId);
                    Location location = new Location(selectedLocation);
                    EquipmentSystem equipmentSystem = new EquipmentSystem(selectedEquipmentId);
                    EquipmnetSubSystem equipmentSubSystem = new EquipmnetSubSystem(selectedSubEquipId);
                    int frequency = 1;

                    CreateAssetRequest createAssetRequest = new CreateAssetRequest(frequency, unitString, description, brandd, assetstatus, expetedlifeinmonths,
                            assetNoString, assetTypeString,
                            subLocation, model, coolingCapacity, typeOfFcuString, airFlow, cuRef,
                            cuLocation, weight, dimension, electricPowerSupply, isolatorSize, remarks, equipCode, supplierName, supplierAddress, contactNo,
                            fcuRef, quantity, fanRef, staticPressure, dia, warrantyPeriod, power, fullLoadCurrent, cpRef, powerRequirement, serialNo,
                            equipmentname, outputAcquisition, cost, user, outputServiceIn, building, location,
                            equipmentSystem, equipmentSubSystem);

                    ProgressDialog progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setTitle("Creating Asset");
                    progressDialog.setMessage("Please wait ..");
                    progressDialog.show();

                    Call<Void> call = RetrofitClient.getInstance().getapi().creaetAsset(workspace, token, createAssetRequest);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.code() == 200) {
                                Toast.makeText(getActivity(), "Asset Created", Toast.LENGTH_SHORT).show();
                                Intent intent = getActivity().getIntent();
                                getActivity().finish();
                                startActivity(intent);
                            }
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            progressDialog.dismiss();
                        }
                    });
                }
            }
        });
        return view;

    }

    private void callDateMethod(String type) {
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(type);
            }

        };

        new DatePickerDialog(getActivity(), date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateLabel(String type) {

        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        if (type.equals("dateOfAcq")) {
            dateofacquisition.setText(sdf.format(myCalendar.getTime()));
        }
        if (type.equals("dateInService")) {
            dateInService.setText(sdf.format(myCalendar.getTime()));
        }

    }

    private void callBuilding() {
        Call<List<Building>> call = RetrofitClient.getInstance().getapi().getBuilding(workspace, token);
        call.enqueue(new Callback<List<Building>>() {
            @Override
            public void onResponse(Call<List<Building>> call, Response<List<Building>> response) {
                if (response.code() == 200) {


                    String name;
                    String build_desc = null, priority_id = null, zone_id = null;
                    int id = 0;

                    buildingList.clear();
                    ;
                    buildingList.add("Select Building");
                    for (int i = 0; i < response.body().size(); i++) {
                        name = response.body().get(i).getBuild_name();
                        id = response.body().get(i).getId();
                        buildingList.add(name);
                        mainBuildingList.add(new Building(id, name, build_desc, priority_id, zone_id));
                    }
//                    setBuildingAdapter(mainBuildingList);
                }
            }

            @Override
            public void onFailure(Call<List<Building>> call, Throwable t) {
            }
        });
    }

    private void setBuildingAdapter(List<Building> mainBuildingList) {
        ArrayAdapter<String> buildingAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, buildingList);

        buildingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        buildingSpinner.setAdapter(buildingAdapter);
        buildingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedBuilding = buildingSpinner.getSelectedItem().toString();
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

    private void callLocation(int buildId) {
        Call<List<Location>> call = RetrofitClient.getInstance().getapi().getLocation(workspace, buildId, token);
        call.enqueue(new Callback<List<Location>>() {
            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                if (response.code() == 200) {
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

//                    setLocationAdapter(mainLocationList);
                }
            }

            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {
            }
        });
    }

    private void setLocationAdapter(List<Location> mainLocationList) {

        ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, locationList);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(locationAdapter);
        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = locationSpinner.getSelectedItem().toString();
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

    private void setAdapters(List<EquipmentSystem> mainEquipNameList) {


        ArrayAdapter<String> equipAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, equipmentNameList);
        equipAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        equipNameSpinner.setAdapter(equipAdapter);
        equipNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedEquipItem = equipNameSpinner.getSelectedItem().toString();

                for (int i = 0; i < mainEquipNameList.size(); i++) {
                    if (mainEquipNameList.get(i).getSystemName().contains(selectedEquipItem)) {
                        selectedEquipmentId = mainEquipNameList.get(i).getId();
                        Log.d(TAG, "onItemSelected: selected equipm name id" + selectedEquipmentId + "name" + selectedEquipItem);
                    }
                }

                callEquipSubType(selectedEquipItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void callEquipment() {
        Call<List<EquipmentSystem>> call = RetrofitClient.getInstance().getapi().getEquipment(workspace, token);
        call.enqueue(new Callback<List<EquipmentSystem>>() {
            @Override
            public void onResponse(Call<List<EquipmentSystem>> call, Response<List<EquipmentSystem>> response) {
                if (response.code() == 200) {

                    equipmentNameList.clear();
                    equipmentNameList.add("Select EquipmentType");
                    String name = null, cat = null;
                    int id = 0;
                    List<EquipmentSystem> equipList = response.body();
                    for (int i = 0; i < response.body().size(); i++) {
                        name = response.body().get(i).getSystemName().toString();
                        cat = response.body().get(i).getCategory().toString();
                        id = response.body().get(i).getId();

                        equipmentNameList.add(name);
                        mainEquipNameList.add(new EquipmentSystem(id, name));
                    }
//                    setAdapters(mainEquipNameList);

                } else if (response.code() == 401) {
                    editor.clear();
                    editor.apply();
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }

            @Override
            public void onFailure(Call<List<EquipmentSystem>> call, Throwable t) {
            }
        });

    }

    private void callEquipSubType(String selectedItem) {

        Call<List<EquipmnetSubSystem>> call = RetrofitClient.getInstance().getapi().getSubEquipment(workspace, selectedItem, token);
        call.enqueue(new Callback<List<EquipmnetSubSystem>>() {
            @Override
            public void onResponse(Call<List<EquipmnetSubSystem>> call, Response<List<EquipmnetSubSystem>> response) {
                if (response.code() == 200) {

                    equipmentSubTypeNameList.clear();
                    equipmentSubTypeNameList.add("Select Equipment SubType");
                    String name = null;
                    int id = 0;
                    for (int i = 0; i < response.body().size(); i++) {
                        name = response.body().get(i).getSubSystemName();
                        id = response.body().get(i).getId();
                        equipmentSubTypeNameList.add(name);
                        mainEquipSubNameList.add(new EquipmnetSubSystem(id, name));
                    }
//                    setEquipSubtypeAdapter(mainEquipSubNameList);

                }
            }

            @Override
            public void onFailure(Call<List<EquipmnetSubSystem>> call, Throwable t) {

            }
        });
    }

    private void setEquipSubtypeAdapter(List<EquipmnetSubSystem> mainEquipSubNameList) {
        ArrayAdapter<String> subEquipListAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, equipmentSubTypeNameList);

        subEquipListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        equipSubTypeSpinner.setAdapter(subEquipListAdapter);
        equipSubTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = equipSubTypeSpinner.getSelectedItem().toString();
                for (int i = 0; i < mainEquipSubNameList.size(); i++) {
                    if (mainEquipSubNameList.get(i).getSubSystemName().contains(selectedItem)) {
                        selectedSubEquipId = mainEquipSubNameList.get(i).getId();
                        Log.d(TAG, "onItemSelected: selected sub equip" + selectedSubEquipId + "name " + selectedSubEquipId);
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}