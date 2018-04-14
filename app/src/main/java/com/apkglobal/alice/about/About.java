package com.apkglobal.alice.about;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.apkglobal.alice.MainActivity;
import com.apkglobal.alice.R;

public class About extends AppCompatActivity implements View.OnClickListener {
    Button btn_example;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        /*Typecast here*/
        btn_example = (Button) findViewById(R.id.btn_examples);
        btn_example.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_examples:
                Intent example = new Intent(this, lag_saver.class);
                startActivity(example);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}
