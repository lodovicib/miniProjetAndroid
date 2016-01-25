package com.m2dl.miniprojetpointinteret.utils;

import android.util.Base64;

/**
 * Created by Romain on 22/01/2016.
 */
public class PhotoDecoder {

    public static String byteToString(byte[] img) {
        return Base64.encodeToString(img, Base64.DEFAULT);
    }

    public static byte[] stringToByte(String file) {
        return Base64.decode(file, Base64.DEFAULT);
    }
}
