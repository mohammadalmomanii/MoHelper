package com.devmo.mohelper.api;

import com.devmo.mohelper.api.model.Model;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {
    //1430
//    private static final String BASE_URL = "http://apps.lss.jo:2478/lssand/AppsWebServices/OperationLssApi/";//نت برا
//    private static final String BASE_URL2 = "http://apps.lss.jo:1430/lssand/AppsWebServices/OperationLssApi/";//نت الشركة
    private static final String BASE_URL = "https://apps.lss.jo:2480/MRE_Api/Api/";

    private static ApiClient client;
    private static ApiServer server;
    Retrofit retrofit;


    public ApiClient() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(ScalarsConverterFactory.create())
//                    .client(okHttpClient)
                .build();


        server = retrofit.create(ApiServer.class);
    }


    public static ApiClient getClient() {
        if (client == null)
            client = new ApiClient();
        return client;
    }


    public Call<Model> userLogin(String key, String userName, String password) {
        return server.userLogin(key, userName, password);
    }
    public Call<Model> checkIfBlocked(String key, String userId) {
        return server.checkIfBlocked(key, userId);
    }

    public Call<Model> addNewMeal(String key, String mealId, String mealName, String maleType, String createBy, String shift) {
        return server.addNewMeal(key, mealId, mealName, maleType, createBy, shift);
    }


    public Call<Model> addNewRowMaterialJson(String key, String mealId, String json, String updateBy) {
        return server.addNewRowMaterialJson(key, mealId, json, updateBy);
    }

    public Call<Model> updateRawMaterialStatus(String key, String json) {
        return server.updateRawMaterialStatus(key, json);
    }

    public Call<Model> updateRowMaterialJson(String key, String mealId, String json, String updateBy) {
        return server.updateRowMaterialJson(key, mealId, json, updateBy);
    }

    public Call<Model> insertUpdateRawMaterialJson(String key, String mealId, String json, String updateBy) {
        return server.insertUpdateRawMaterialJson(key, mealId, json, updateBy);
    }

    public Call<Model> addNewProcessJson(String key, String json) {
        return server.addNewProcessJson(key, json);
    }

    public Call<Model> getRawMaterial(String key, String mealId) {
        return server.getRawMaterial(key, mealId);
    }

    public Call<Model> addNewKitchenProcessJson(String key, String json) {
        return server.addNewKitchenProcessJson(key, json);
    }

    public Call<Model> addNewPackagingProcessJson(String key, String json) {
        return server.addNewPackagingProcessJson(key, json);
    }

    public Call<Model> addNewProductionProcessJson(String key, String json) {
        return server.addNewProductionProcessJson(key, json);
    }

    public Call<Model> addNewProduction2ProcessJson(String key, String json) {
        return server.addNewProduction2ProcessJson(key, json);
    }

    public Call<Model> updateKitchenProcess(String key, String json) {
        return server.updateKitchenProcess(key, json);
    }

    public Call<Model> updatePackagingProcess(String key, String json) {
        return server.updatePackagingProcess(key, json);
    }

    public Call<Model> updateProductionProcess(RequestBody key, RequestBody json, MultipartBody.Part image) {
        return server.updateProductionProcess(key, json, image);
    }

    public Call<Model> updateProduction2Process(RequestBody key, RequestBody json, MultipartBody.Part image) {
        return server.updateProduction2Process(key, json, image);
    }

    public Call<Model> deleteKitchenProcess(String key, String id, String userId, String userName) {
        return server.deleteKitchenProcess(key, id, userId, userName);
    }

    public Call<Model> deletePackagingProcess(String key, String id, String userId, String userName) {
        return server.deletePackagingProcess(key, id, userId, userName);
    }

    public Call<Model> deleteRowMaterial(String key, String id, String userId, String userName) {
        return server.deleteRowMaterial(key, id, userId, userName);
    }

    public Call<Model> deleteProductionProcess(String key, String id, String userId, String userName) {
        return server.deleteProductionProcess(key, id, userId, userName);
    }

    public Call<Model> deleteProduction2Process(String key, String id, String userId, String userName) {
        return server.deleteProduction2Process(key, id, userId, userName);
    }

    public Call<Model> getCurrentMeal(String key, String search) {
        return server.getCurrentMeal(key, search);
    }

    public Call<Model> getMealProcess(String key, String mealId, String mealType) {
        return server.getMealProcess(key, mealId, mealType);
    }

    public Call<Model> insertUpdateHoldReleseJson(String key, String mealId, String json) {
        return server.insertUpdateHoldReleseJson(key, mealId, json);
    }

    public Call<Model> getHoldRelese(String key, String search, String status) {
        return server.getHoldRelese(key, search,status);
    }
    public Call<Model> getHoldReleseArchive(String key,  String status,  String date) {
        return server.getHoldReleseArchive(key, status, date);
    }
    public Call<Model> getMealDetails(String key,  String mealId) {
        return server.getMealDetails(key, mealId);
    }

    public Call<Model> updateMealStatus(String key, String type, String shift, String processId,
                                        String mealId, String status, String userNote, String userInfo, String userType) {
        return server.updateMealStatus(key, type, shift, processId, mealId, status, userNote, userInfo, userType);
    }

    public Call<Model> getMeals(String key) {
        return server.getMeals(key);
    }


}