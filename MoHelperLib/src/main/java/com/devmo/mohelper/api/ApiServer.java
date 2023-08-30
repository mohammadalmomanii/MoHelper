package com.devmo.mohelper.api;


import androidx.annotation.Nullable;

import com.devmo.mohelper.api.model.Model;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiServer {

    @FormUrlEncoded
    @POST("checkIfBlocked.php")
    public Call<Model> checkIfBlocked(
            @Field("key") String key,
            @Field("userId") String userId
    );

    @FormUrlEncoded
    @POST("userLogin.php")
    public Call<Model> userLogin(
            @Field("key") String key,
            @Field("userName") String userName,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("addNewMeal.php")
    public Call<Model> addNewMeal(
            @Field("key") String key,
            @Field("mealId") String mealId,
            @Field("mealName") String mealName,
            @Field("mealType") String mealType,
            @Field("createBy") String createBy,
            @Field("shift") String shift
    );

    @FormUrlEncoded
    @POST("addNewRowMaterial.php")
    public Call<Model> addNewRowMaterial(
            @Field("key") String key,
            @Field("mealId") String mealId,
            @Field("itemName") String itemName,
            @Field("createBy") String createBy,
            @Field("shift") String shift,
            @Field("productionDate") String productionDate,
            @Field("expiryDate") String expiryDate,
            @Field("brandName") String brandName,
            @Field("note") String note

    );

    @FormUrlEncoded
    @POST("addNewRowMaterialJson.php")
    public Call<Model> addNewRowMaterialJson(
            @Field("key") String key,
            @Field("mealId") String mealId,
            @Field("json") String json,
            @Field("updateBy") String updateBy
    );

    @FormUrlEncoded
    @POST("updateRawMaterialStatus.php")
    public Call<Model> updateRawMaterialStatus(
            @Field("key") String key,
            @Field("json") String json);

    @FormUrlEncoded
    @POST("updateRowMaterialJson.php")
    public Call<Model> updateRowMaterialJson(
            @Field("key") String key,
            @Field("mealId") String mealId,
            @Field("json") String json,
            @Field("updateBy") String updateBy

    );

    @FormUrlEncoded
    @POST("insertUpdateRawMaterialJson.php")
    public Call<Model> insertUpdateRawMaterialJson(
            @Field("key") String key,
            @Field("mealId") String mealId,
            @Field("json") String json,
            @Field("updateBy") String updateBy

    );

    @FormUrlEncoded
    @POST("addNewProcessJson.php")
    public Call<Model> addNewProcessJson(
            @Field("key") String key,
            @Field("json") String json
    );

    @FormUrlEncoded
    @POST("getRawMaterial.php")
    public Call<Model> getRawMaterial(
            @Field("key") String key,
            @Field("mealId") String json
    );

    @FormUrlEncoded
    @POST("deleteKitchenProcess.php")
    public Call<Model> deleteKitchenProcess(
            @Field("key") String key,
            @Field("id") String id,
            @Field("userId") String userId,
            @Field("userName") String userName
    );

    @FormUrlEncoded
    @POST("deleteRowMaterial.php")
    public Call<Model> deleteRowMaterial(
            @Field("key") String key,
            @Field("id") String id,
            @Field("userId") String userId,
            @Field("userName") String userName
    );

    @FormUrlEncoded
    @POST("deletePackagingProcess.php")
    public Call<Model> deletePackagingProcess(
            @Field("key") String key,
            @Field("id") String id,
            @Field("userId") String userId,
            @Field("userName") String userName
    );

    @FormUrlEncoded
    @POST("deleteProductionProcess.php")
    public Call<Model> deleteProductionProcess(
            @Field("key") String key,
            @Field("id") String id,
            @Field("userId") String userId,
            @Field("userName") String userName
    );

    @FormUrlEncoded
    @POST("deleteProduction2Process.php")
    public Call<Model> deleteProduction2Process(
            @Field("key") String key,
            @Field("id") String id,
            @Field("userId") String userId,
            @Field("userName") String userName
    );

    @FormUrlEncoded
    @POST("addNewKitchenProcessJson.php")
    public Call<Model> addNewKitchenProcessJson(
            @Field("key") String key,
            @Field("json") String json
    );

    @FormUrlEncoded
    @POST("addNewPackagingProcessJson.php")
    public Call<Model> addNewPackagingProcessJson(
            @Field("key") String key,
            @Field("json") String json
    );

    @FormUrlEncoded
    @POST("addNewProductionProcessJson.php")
    public Call<Model> addNewProductionProcessJson(
            @Field("key") String key,
            @Field("json") String json
    );

    @FormUrlEncoded
    @POST("addNewProduction2ProcessJson.php")
    public Call<Model> addNewProduction2ProcessJson(
            @Field("key") String key,
            @Field("json") String json
    );

    @FormUrlEncoded
    @POST("updateKitchenProcess.php")
    public Call<Model> updateKitchenProcess(
            @Field("key") String key,
            @Field("json") String json
    );

    @FormUrlEncoded
    @POST("updatePackagingProcess.php")
    public Call<Model> updatePackagingProcess(
            @Field("key") String key,
            @Field("json") String json
    );

    @Multipart
    @POST("updateProductionProcess.php")
    public Call<Model> updateProductionProcess(
            @Part("key") RequestBody key,
            @Part("json") RequestBody json,
            @Nullable @Part MultipartBody.Part image
    );

    @Multipart
    @POST("updateProduction2Process.php")
    public Call<Model> updateProduction2Process(
            @Part("key") RequestBody key,
            @Part("json") RequestBody json,
            @Nullable @Part MultipartBody.Part image);


    @FormUrlEncoded
    @POST("getMeals.php")
    public Call<Model> getMeals(
            @Field("key") String key);

    @FormUrlEncoded
    @POST("getCurrentMeal.php")
    public Call<Model> getCurrentMeal(
            @Field("key") String key,
            @Field("search") String search
    );

    @FormUrlEncoded
    @POST("getMealProcess.php")
    public Call<Model> getMealProcess(
            @Field("key") String key,
            @Field("mealId") String mealId,
            @Field("mealType") String mealType
    );

    @FormUrlEncoded
    @POST("insertUpdateHoldReleseJson.php")
    public Call<Model> insertUpdateHoldReleseJson(
            @Field("key") String key,
            @Field("mealId") String mealId,
            @Field("json") String mealType
    );

    @FormUrlEncoded
    @POST("getHoldRelese.php")
    public Call<Model> getHoldRelese(
            @Field("key") String key,
            @Field("search") String search,
            @Field("status") String status
    );

    @FormUrlEncoded
    @POST("getMealDetails.php")
    public Call<Model> getMealDetails(
            @Field("key") String key,
            @Field("mealId") String mealId);

    @FormUrlEncoded
    @POST("getHoldReleseArchive.php")
    public Call<Model> getHoldReleseArchive(
            @Field("key") String key,
            @Field("status") String status,
            @Field("date") String date
    );

    @FormUrlEncoded
    @POST("updateMealStatus.php")
    public Call<Model> updateMealStatus(
            @Field("key") String key,
            @Field("type") String type,
            @Field("shift") String shift,
            @Field("processId") String processId,
            @Field("mealId") String mealId,
            @Field("status") String status,
            @Field("userNote") String userNote,
            @Field("userInfo") String userInfo,
            @Field("userType") String userType
    );


}