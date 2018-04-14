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

public class Email extends AppCompatActivity {
    Button btn_send, btn_sub, btn_text;
    TextView tv_sub, tv_text;
    Animation animation;
    String sub,text;
    int pid = 1;
    int tpid=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        tv_sub = (TextView) findViewById(R.id.tv_sub);
        tv_text = (TextView) findViewById(R.id.tv_text);
        btn_text = (Button) findViewById(R.id.btn_text);
        btn_sub = (Button) findViewById(R.id.btn_sub);
        btn_send = (Button) findViewById(R.id.btn_send);

        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation);


        btn_sub.startAnimation(animation);

        btn_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hey Sunshine ! Speak Your Mind");
                startActivityForResult(intent, 1);
               /* animation.cancel();
                animation.reset();*/
            }
        });

        btn_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /*   btn_text.startAnimation(animation);*/
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hey Sunshine ! Speak Your Mind");
                startActivityForResult(intent, 2);
       /* animation.cancel();
                animation.reset();*/
            }
        });
        /*To take the text */

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent email=new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_SUBJECT,sub);
                email.putExtra(Intent.EXTRA_TEXT,text);
                email.setType("email/rfc822");
                startActivity(Intent.createChooser(email,"Send Email Using"));
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> arraylist = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                tv_sub.setText(arraylist.get(0).toString());
                sub = arraylist.get(0).toString();
            }
        }


        if (requestCode == 2) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> arraylist = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                tv_text.setText(arraylist.get(0).toString());
                text= arraylist.get(0).toString();
            }
        }

    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}
