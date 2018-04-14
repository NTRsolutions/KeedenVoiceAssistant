package com.apkglobal.alice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Features extends AppCompatActivity {
    ImageView imageView;
    Animation animation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_features);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageView = (ImageView) findViewById(R.id.image);
        animation = AnimationUtils.loadAnimation(this, R.anim.animation);
        imageView.startAnimation(animation);

    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
