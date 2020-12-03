package com.eflexsoft.note;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import java.util.Random;

public class Utils {

    //    ColorDrawable colorDrawable = new ColorDrawable()
    private static ColorDrawable colorDrawable[] = {

            new ColorDrawable(Color.parseColor("#2e86c1")),
            new ColorDrawable(Color.parseColor("#6c3483")),
            new ColorDrawable(Color.parseColor("#239b56")),
            new ColorDrawable(Color.parseColor("#641e16")),
            new ColorDrawable(Color.parseColor("#58d68d")),
            new ColorDrawable(Color.parseColor("#7e5109")),
            new ColorDrawable(Color.parseColor("#17202a")),
            new ColorDrawable(Color.parseColor("#7d3c98")),
            new ColorDrawable(Color.parseColor("#d2b4de")),
            new ColorDrawable(Color.parseColor("#f39c12")),
            new ColorDrawable(Color.parseColor("#154360")),
            new ColorDrawable(Color.parseColor("#641e16")),
            new ColorDrawable(Color.parseColor("#145a32")),


    };

    public static ColorDrawable getRandomColor() {
        int r = new Random().nextInt(12);

        return colorDrawable[r];
    }
}
