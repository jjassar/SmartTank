package com.netmax.ubpro.smartank;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

/**
 * Created by preeti on 3/10/16.
 */
public class Settings extends PreferenceActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        SharedPreferences sp1 = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean p = sp1.getBoolean("Choice",false);

        SharedPreferences sp2 = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed1 = sp2.edit();
        ed1.putBoolean("Choice",p);
        ed1.commit();
    }
}
