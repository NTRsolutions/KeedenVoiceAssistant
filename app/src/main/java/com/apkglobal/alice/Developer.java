package com.apkglobal.alice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Developer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
