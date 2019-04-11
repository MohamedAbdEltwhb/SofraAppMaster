package com.example.mm.sofraappmaster.helper;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;

import java.io.File;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Url;


public class HelperMethod {


    /**
     * Helper Method
     * to Replace Fragments
     *
     * @param fragment
     * @param supportFragmentManager
     * @param id
     * @param toolBarTitle
     * @param title
     */
    public static void replaceFragments(Fragment fragment, FragmentManager supportFragmentManager
            , int id, TextView toolBarTitle, String title) {

        supportFragmentManager.beginTransaction()
                .replace(id, fragment)
                .addToBackStack(null)
                .commit();
        if (toolBarTitle != null) {
            toolBarTitle.setText(title);
        }
    }

    public static String dateFormat(String dateFormat) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateFormat);
            String newstring = new SimpleDateFormat("yyyy-MM-dd").format(date);
            return newstring;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Helper Method
     * Intent to Start a new Activity
     *
     * @param context
     * @param cls
     */
    public static void intentTo(Context context, Class<?> cls) {
        Intent in = new Intent(context, cls);
        context.startActivity(in);
    }

    /**
     * Helper Method to Show Toast Message
     *
     * @param context
     * @param message
     */
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Use library ...
     *
     * @param Counter
     * @param context
     * @param ImagesFiles
     * @param action
     * @com.yanzhenjie:album To Chose Multi Select Album Item
     */
    public static void openAlbum(int Counter, Context context, final ArrayList<AlbumFile> ImagesFiles, Action<ArrayList<AlbumFile>> action) {
        Album album = new Album();
        Album.initialize(AlbumConfig.newBuilder(context)
                .setAlbumLoader(new MediaLoader())
                .setLocale(Locale.ENGLISH).build());

        album.image(context)
                .multipleChoice()
                .columnCount(3)
                .selectCount(Counter)
                .camera(true)
                .checkedList(ImagesFiles)
                .widget(Widget.newLightBuilder(context)
                        .title("")
                        .statusBarColor(Color.WHITE)
                        .toolBarColor(Color.WHITE)
                        .navigationBarColor(Color.WHITE)
                        .mediaItemCheckSelector(Color.BLUE, Color.GREEN)
                        .bucketItemCheckSelector(Color.RED, Color.YELLOW)
                        .build())
                .onResult(action)
                .onCancel(new Action<String>() {

                    @Override
                    public void onAction(@NonNull String result) {
                        // The user canceled the operation.
                    }
                }).start();
    }

    public void selectDate(Context context)
    {
        int mYear=0;
        int mMonth=0;
        int mDay= 0;
        TextView expiry = null;

        final TextView finalExpiry = expiry;
        DatePickerDialog dpd = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    final Calendar myCalendar = Calendar.getInstance();
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String myFormat = "yyyy-MM-dd"; //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                        finalExpiry.setText(sdf.format(myCalendar.getTime()));

                    }
                }, mYear, mMonth, mDay);
        dpd.getDatePicker().setMinDate(System.currentTimeMillis());
        dpd.show();
    }

    /**
     * Convert Input To RequestBody
     *
     * @param part
     */
    public static RequestBody convertToRequestBody(String part) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), part);
    }

    /**
     * Convert File To Multipart
     *
     * @param pathImageFile
     * @param Key
     */
    public static MultipartBody.Part convertFileToMultipart(String pathImageFile, String Key) {
        File file = new File(pathImageFile);
        RequestBody reqFileSelect = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part imageBody = MultipartBody.Part.createFormData(Key, file.getName(), reqFileSelect);
        return imageBody;
    }

    /**
     * Lode image into ImageView Use Glide library
     *
     * @param context
     * @param URL
     * @param image
     */
    public static void glideLode(Context context, ImageView image, String URL) {

        Glide.with(context).load(URL).into(image);
    }

    /**
     * Helper Method Sharing App
     */
    public static void sharingApp(Context context) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Here is the share content body";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }


}
