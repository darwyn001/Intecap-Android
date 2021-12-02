package com.example.intecap.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;

public class BitmapUtils {
    public static Bitmap getBitmapFromPath(String path) {
        return BitmapFactory.decodeFile(new File(path).getAbsolutePath(), new BitmapFactory.Options());
    }
}
