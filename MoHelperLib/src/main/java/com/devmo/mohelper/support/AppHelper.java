package com.devmo.mohelper.support;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class AppHelper {

    static public String getCurrentDate() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String dateString = dateFormat.format(currentDate);
        return dateString;
    }

    static public void setTimeZone(){
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+3"));
    }

    static public String getShift() {
        int time = Integer.parseInt(new SimpleDateFormat("HH",Locale.ENGLISH).format(new Date()));
        if (time >= 8 && time < 16)
            return "A";
        else if (time >= 16 && time < 24)
            return "B";
        else return "C";
    }

    static public long getDaysBetweenDates(String date1,String date2){
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");


        Date startDate = null;   // initialize start date
        Date endDate = null;   // initialize start date
        try {
            startDate = myFormat.parse(date1);
            endDate = myFormat.parse(date2); // initialize  end date
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long duration = endDate.getTime() - startDate.getTime();

        return Math.abs(TimeUnit.MILLISECONDS.toDays(duration));
    }

//    static public String internetConnectionFrom(Context context) {
//        ConnectivityManager cm =
//                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (cm.getActiveNetworkInfo() != null) {
//            if (cm.getActiveNetworkInfo().getExtraInfo().toLowerCase().contains("\"lss-")) {
//                Log.d("test", "*/////// wifi test :" + cm.getActiveNetworkInfo().getExtraInfo());
//
//                return StaticString.INSIDE_COMPANY.name();
//            } else {
//                Log.d("test", "*/////// wifi test :" + cm.getActiveNetworkInfo().getExtraInfo());
//
//                return StaticString.OUTSIDE_COMPANY.name();
//            }
//
//        } else {
//            return StaticString.NO_INTERNET.name();
//        }
//    }

    static public void openFile(Context context, String fileName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), fileName);
        Uri uri = FileProvider.getUriForFile(context, "com.lss.mreapp" + ".provider", file);
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(uri, "application/pdf");
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(i);


    }

    static public void hideKeyboard(Activity activity) {
        if (activity.getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    static public void showSnackbar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    static public void setAppModeNight(boolean value) {
        AppCompatDelegate.setDefaultNightMode(value ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
    }

    static public boolean getAppMode(Context context) {
        if ((context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_NO)
            return true;
        else
            return false;
    }

    static public void setAnimateVisibility(View view, int visibility, Transition transition, long duration) {
//                                       (binding.toolbarCard, View.GONE, new Slide(Gravity.TOP), 500);

//        Transition transition1 = new Fade();
        Transition transition1 = transition;
        transition.setDuration(duration);
        transition.addTarget(view);
        TransitionManager.beginDelayedTransition((ViewGroup) view.getParent(), transition);
        view.setVisibility(visibility);
    }

    static public void setMargin(View view, int left, int top, int right, int bottom) {


        ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        p.setMargins(left, top, right, bottom);
        view.requestLayout();

    }

    static public void expandedBottomSheetDialog(BottomSheetDialogFragment fragment) {
        BottomSheetDialog dialog = (BottomSheetDialog) fragment.getDialog();
        dialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
    }
//    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    static public void setAnimation(Context context, View view, int res, long duration) {
        Animation animation=AnimationUtils.loadAnimation(context, res);
        animation.setDuration(duration);
        view.startAnimation(animation);
    }
    static public void setAnimation2(View view, Float vertical, Float horizontal, long duration) {
        ViewGroup.MarginLayoutParams vlp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        view.animate().x(vertical == null ? vlp.rightMargin | vlp.leftMargin : vertical).
                y(horizontal == null ? vlp.topMargin : horizontal).setDuration(duration).start();

        // CAN USE ANIMATION IN THIS WAY TOO
//        view.animate().x(vertical==null?vlp.rightMargin|vlp.leftMargin:vertical).
//                y(horizontal==null?vlp.topMargin:horizontal).setDuration(duration).setListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        super.onAnimationEnd(animation);
//                        //type Code Here
//                    }
//                });


    }

    static public void removeFocus(Activity context) {
        View view = context.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    static public DatePickerDialog datePicker(Context context, TextView view2) {
        view2.setText("");
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
                (view, year1, month1, dayOfMonth) -> {
                    int newMonth = month1 + 1;
                    try {
                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                        String dateInString = dayOfMonth + "-" + newMonth + "-" + year1;
                        Date date1 = formatter.parse(dateInString);
                        formatter = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
                        view2.setText(formatter.format(date1));
                    } catch (Exception e) {
                    }


                }, year, month, day
        );
        datePickerDialog.show();
        return datePickerDialog;
    }

    static public TimePickerDialog timePicker(Context context, TextView view2, boolean showDate) {
        TimePickerDialog timePicker = new TimePickerDialog(context,
                (timePicker1, selectedHour, selectedMinute) -> {

                    Calendar time = Calendar.getInstance();

                    time.set(Calendar.HOUR_OF_DAY, selectedHour);

                    time.set(Calendar.MINUTE, selectedMinute);
                    SimpleDateFormat format = new SimpleDateFormat(
                            "hh:mm a");
                    view2.setText(view2.getText() + "   " + (showDate ? getCurrentDate() + "\t" : "") + format.format(time.getTime()));
                }, 1, 1, false);// Yes 24 hour time
        timePicker.setTitle("TEST");
        timePicker.show();
        return timePicker;
    }


    static public void dateTimePicker(Context context, TextView view) {
        datePicker(context, view).setOnDismissListener(dialog -> timePicker(context, view, false));
    }

    static public void setLanguage(Context context, String language) {
        Configuration configuration = context.getResources().getConfiguration();
        Locale newLocale = new Locale(language);
        configuration.setLocale(newLocale);
        context.createConfigurationContext(configuration);

        AppStorage.setAppLanguage(language);
    }

    static public void setVisible(View view) {
        view.setVisibility(View.VISIBLE);
    }

    static public void setGone(View view) {
        view.setVisibility(View.GONE);
    }

    static public void setInvisible(View view) {
        view.setVisibility(View.INVISIBLE);
    }


    static public int recycleViewGetCurrentPosition(RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        return (linearLayoutManager.findFirstCompletelyVisibleItemPosition() + 1);
    }

    static public void freezeRecyclerView(RecyclerView recyclerView) {
        recyclerView.setOnTouchListener((v, event) -> true);
    }

    static public void delay(Runnable runnable, long time) {
        new Handler().postDelayed(() -> {
            runnable.run();
        }, time);
    }

    static public String getAndroidVersion() {
        Log.d("APPPP_RELESE",Build.VERSION.RELEASE+"");
        Log.d("APPPP_SDK_INT",Build.VERSION.SDK_INT+"");
        Log.d("APPPP_BRAND",Build.BRAND.toLowerCase(Locale.ENGLISH));
        return Build.BRAND.toLowerCase(Locale.ENGLISH);
    }

    static public void onFailure(Context context, String s, boolean b) {
        if (b) {
//            Toast.makeText(context, context.getString(R.string.no_connection) + " : " + s, Toast.LENGTH_LONG).show();
            Log.d("onFailure", s);
        } else {
            Toast.makeText(context, "TEST" + " : " + s, Toast.LENGTH_LONG).show();
        }
    }


}
