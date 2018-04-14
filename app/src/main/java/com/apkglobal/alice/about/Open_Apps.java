package com.apkglobal.alice.about;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.apkglobal.alice.R;

public class Open_Apps extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open__apps);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}
