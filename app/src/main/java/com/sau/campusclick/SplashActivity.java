package com.sau.campusclick;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPref = this.getSharedPreferences(
                getString(R.string.user_pref_file), Context.MODE_PRIVATE);

        Intent i;

        if(sharedPref.getBoolean("isSignedIn", false))
            i = new Intent(this, MainActivity.class);
        else
            i = new Intent(this, LoginActivity.class);

        startActivity(i);
        finish();
    }
}
