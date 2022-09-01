package com.sg.angulliamosque;

import com.sg.angulliamosque.models.Building;
import com.sg.angulliamosque.models.CreateAssetRequest;
import com.sg.angulliamosque.models.EditObjectRecieved;
import com.sg.angulliamosque.models.EquipmentSystem;
import com.sg.angulliamosque.models.ListObject;
import com.sg.angulliamosque.models.Location;
import com.sg.angulliamosque.models.LoginRequest;
import com.sg.angulliamosque.models.LoginResponse;
import com.sg.angulliamosque.models.EquipmnetSubSystem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    //login

    @POST("loginservice/")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);
//            @Path("username") String username,
//            @Path("password") String password,
//            @Query("deviceToken") String deviceToken);

    //create assets
    @POST("ws/asset/")
    Call<Void> creaetAsset(
            @Header("WorkSpaceSite") String workspace,
            @Header("X-Auth-Token") String token,
            @Body CreateAssetRequest createAssetRequest);

    //equipment name list
    @GET("ws/getsystem")
    Call<List<EquipmentSystem>> getEquipment(
            @Header("workspace") String workspace
            , @Header("X-Auth-Token") String token);

    //sub equipment name list
    @GET("ws/getsubsystem/")
    Call<List<EquipmnetSubSystem>> getSubEquipment(
            @Header("workspace") String workspace
            , @Query("eqtype") String eqtype
            , @Header("X-Auth-Token") String token);

    //building list
    @GET("ws/getbuildings")
    Call<List<Building>> getBuilding(
            @Header("workspace") String workspace
            , @Header("X-Auth-Token") String token);

    //location list
    @GET("ws/locationbybuilding/{buildId}")
    Call<List<Location>> getLocation(
            @Header("workspace") String workspace
            , @Path("buildId") int buildId
            , @Header("X-Auth-Token") String token);


    //get list of assets
    @GET("ws/asset/search/{key}")
    Call<List<ListObject>> getListOfAssets(
            @Header("workspace") String workspace
            , @Path("key") String key
            , @Header("X-Auth-Token") String token);


    //get details of asset
    @GET("ws/asset/edit/{eqId}")
    Call<EditObjectRecieved> getDataForUpdate(
            @Header("workspace") String workspace
            , @Path("eqId") int key
            , @Header("X-Auth-Token") String token);

    //update asset
    @POST("ws/asset/update")
    Call<Void> updateAsset(@Body CreateAssetRequest createAssetRequest,
                           @Header("X-Auth-Token") String token);


    //get detals from qr
    @GET("ws/asset/{equipcode}")
    Call<EditObjectRecieved> getDataFormQr(
            @Header("workspace") String workspace
            , @Path("equipcode") String equipcode
            , @Header("X-Auth-Token") String token);

    //check tagging
    @GET("ws/asset/checktagging/{equipcode}")
    Call<Void> getTCheckagging(
            @Header("workspace") String workspace
            , @Path("equipcode") String equipcode
            , @Header("X-Auth-Token") String token);

    //tagging asset
    @GET("ws/asset/tagging/{equipcode}")
    Call<Void> getTagging(
            @Header("workspace") String workspace
            , @Path("equipcode") String equipcode
            , @Header("X-Auth-Token") String token);

    //logout method
    @POST("ws/logout")
    Call<Void> getLogout(
            @Header("deviceToken") String deviceToken);

}

