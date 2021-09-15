package com.eflexsoft.note;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.ColorRes;

import java.util.Random;

public class Utils {

    //    ColorDrawable colorDrawable = new ColorDrawable()
    private static ColorDrawable colorDrawable[] = {

            //light red
            new ColorDrawable(Color.parseColor("#ee4444")),

            // light blue
            new ColorDrawable(Color.parseColor("#2e86c1")),

            //light blue
            new ColorDrawable(Color.parseColor("#9054A8")),

            // light green
            new ColorDrawable(Color.parseColor("#239b56")),

            //light pink
            new ColorDrawable(Color.parseColor("#D58B83")),

            // light lemon
            new ColorDrawable(Color.parseColor("#58d68d")),

            // light yellow
            new ColorDrawable(Color.parseColor("#E3AC55")),

            //blue
            new ColorDrawable(Color.parseColor("#0079FF")),

            //purple
            new ColorDrawable(Color.parseColor("#9D49BF")),

            // purple light
            new ColorDrawable(Color.parseColor("#d2b4de")),

            //yellow
            new ColorDrawable(Color.parseColor("#f39c12")),

            //sky blue
            new ColorDrawable(Color.parseColor("#89D1FF")),

//            new ColorDrawable(Color.parseColor("#641e16")),

            //dark green
            new ColorDrawable(Color.parseColor("#145a32")),

    };

    public static ColorDrawable getRandomColor() {
        int r = new Random().nextInt(12);

        return colorDrawable[r];
    }

    public static void setSystemBarColor(Activity activity, @ColorRes int color) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(activity.getResources().getColor(color));

        }

    }

}
