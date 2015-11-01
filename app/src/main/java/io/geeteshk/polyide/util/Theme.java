package io.geeteshk.polyide.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import io.geeteshk.polyide.R;

public class Theme {

    public static int getThemeId(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences.getBoolean("night_mode", false)) {
            return R.style.AppTheme_Night;
        } else {
            return R.style.AppTheme;
        }
    }
}
