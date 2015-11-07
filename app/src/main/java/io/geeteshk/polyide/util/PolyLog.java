package io.geeteshk.polyide.util;

import android.util.Log;

public class PolyLog {

    private static final String TAG = "PolyLog";

    public static void log(String message, char type) {
        switch (type) {
            case 'i':
                Log.i(TAG, message);
                break;
            case 'd':
                Log.d(TAG, message);
                break;
            case 'v':
                Log.v(TAG, message);
                break;
            case 'w':
                Log.w(TAG, message);
                break;
            case 'e':
                Log.e(TAG, message);
                break;
            case 'x':
                Log.wtf(TAG, message);
                break;
        }
    }
}
