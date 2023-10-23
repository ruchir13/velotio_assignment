package com.helloworld;

import android.annotation.SuppressLint;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AppUtils {
    public static void loadThumbImage(String url, ImageView imageView) {
        Picasso.with(imageView.getContext())
                .load(url)
                .placeholder(R.drawable.ic_launcher_background)
                .into(imageView);
    }

    public static String getDate(String dateString) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date = inputFormat.parse(dateString);
            String formattedDate = outputFormat.format(date);
            return formattedDate;
        } catch (ParseException e) {
            //Ignore
        }
        return dateString;
    }
}
