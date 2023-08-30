package com.devmo.mohelper.support;


import android.content.Context;
import android.content.SharedPreferences;

import com.devmo.mohelper.api.model.Data;
import com.devmo.mohelper.api.model.Model;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class AppStorage {
    static private SharedPreferences sharedPreferences;
    static private SharedPreferences.Editor editor;

    static private List<Model> usersList;
    static String appLanguage="AppLanguage";

    public static void setAppLanguage(String language) {
        editor.putString(appLanguage,language);
        editor.apply();
        editor.commit();
    }

    public static String getAppLanguage() {
        return sharedPreferences.getString(appLanguage,"en");
    }

    public AppStorage(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("appStorage", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            usersList = new ArrayList<>();
        }
    }

//    static public String getUserName() {
//        return getUserData().getName();
//    }
//
//    static public String getUserId() {
//        return getUserData().getUserId();
//    }
//
//    static public String getExtraPermission() {
//        return getUserData().getExtraPermission();
//    }
//
//    static public String getPassword() {
//        return getUserData().getPassWord();
//    }
//
//    static public String getName() {
//        return getUserData().getUserName();
//    }
//
//    static public String getPermission() {
//        return getUserData().getPermission();
//    }
//
//    static public boolean isLogin() {
//        return getUserData().isLogin();
//    }
//
//

    static public void userLogin(Data data){
        Gson gson=new Gson();
        String userData=gson.toJson(data);
        editor.putString(StaticString.data.name(),userData);
        editor.commit();
    }

    static public void setCurrentPage(int currentPage){
        editor.putInt(StaticString.data.name(), currentPage);
        editor.commit();
    }
    static public int getCurrentPage(){
        return sharedPreferences.getInt(StaticString.data.name(),-1);
    }
    static public Data getActiveUser(){
        String userData= sharedPreferences.getString(StaticString.data.name(), null);
        return new Gson().fromJson(userData, Data.class );
    }

    public static void logout() {
        editor.clear();
        editor.apply();
    }
//    static public void userLogin(Model model) {
//        usersList = getUserList();
//        for (Model m : usersList) {
//            m.setActive(false);
//        }
//        Gson gson = new Gson();
//        model.setActive(true);
//        model.setLogin(true);
//        usersList.add(model);
//        String userData = gson.toJson(usersList);
//        editor.putString(users_data, userData);
////        editor.putBoolean(is_login, true);
////        editor.putString(user_id, model.getUserId());
////        editor.putString(PassWord, model.getPassWord());
////        editor.putString(user_name, model.getUserName());
////        editor.putString(name, model.getName());
////        editor.putString(permission, model.getPermission());
////        editor.putString(role, model.getRole());
//        editor.commit();
//    }
//
//    static public List<Model> getUserList() {
//
//        String userList = sharedPreferences.getString(users_data, null);
//        if (userList != null) {
//            Gson gson = new Gson();
//            Type type = new TypeToken<List<Model>>() {
//            }.getType();
//            usersList = gson.fromJson(userList, type);
//        }
//        return usersList;
//    }
//
//    static public void switchAccount(int position) {
//        int counter = 0;
//        for (Model m : usersList) {
//            if (counter == position)
//                m.setActive(true);
//            else
//                m.setActive(false);
//            counter++;
//        }
//        Gson gson = new Gson();
//        String userData = gson.toJson(usersList);
//        editor.putString(users_data, userData);
//        editor.commit();
//
//    }
//
//    static public Model getUserData() {
//        Model model = new Model();
//        if (getUserList().size() > 0) {
//            for (Model m : usersList) {
//                if (m.isActive())
//                    model = m;
//            }
//        }
//        return model;
//    }
//
//
//    static public void setAppLanguage(String lang) {
//        editor.putString(app_lang, lang);
//        editor.apply();
//    }
//
//    static public String getAppLanguage() {
//        return sharedPreferences.getString(app_lang, StaticString.en.name());
//    }
//
//

//    public static void logout() {
//        String appLang = getAppLanguage();
//        if (usersList != null)
//            usersList.clear();
//        editor.clear();
//        editor.apply();
//        setAppLanguage(appLang);
//        editor.apply();
//    }
//
//    }
}
