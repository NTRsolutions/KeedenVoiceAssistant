package com.apkglobal.alice;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class Send_Text extends AppCompatActivity {

    Animation animation;
    Button btn_write, btn_send;
    TextView tv_write;
    int pid = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send__text);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_write = (TextView) findViewById(R.id.tv_write);
        btn_write = (Button) findViewById(R.id.btn_write);

        animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.animation);
        btn_write.startAnimation(animation);

        btn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hey Sunshine ! Speak Your Mind");
                startActivityForResult(intent, pid);
            }
        });

        btn_send=(Button)findViewById(R.id.btn_send);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == pid) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> arraylist = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                tv_write.setText(arraylist.get(0).toString());
                final String interact = arraylist.get(0).toString();


                    /*Shares the data to other Applications*/
                btn_send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(Intent.ACTION_SEND);
                        intent.putExtra(Intent.EXTRA_TEXT,interact);
                        intent.setType("text/plain");
                        startActivity(Intent.createChooser(intent,interact));
                    }
                });

            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}
