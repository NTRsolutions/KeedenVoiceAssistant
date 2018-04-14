package com.apkglobal.alice.about;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.apkglobal.alice.R;
import com.apkglobal.alice.Send_Text;

public class lag_saver extends AppCompatActivity implements View.OnClickListener {
    Button btn_call, btn_chat, btn_apps, btn_email, btn_send, btn_note, btn_mood;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lag_saver);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_mood = (Button) findViewById(R.id.btn_mood);
        btn_call = (Button) findViewById(R.id.btn_call);
        btn_chat = (Button) findViewById(R.id.btn_chat);
        btn_apps = (Button) findViewById(R.id.btn_apps);
        btn_email = (Button) findViewById(R.id.btn_email);
        btn_send = (Button) findViewById(R.id.btn_text);
        btn_note = (Button) findViewById(R.id.btn_note);


        btn_call.setOnClickListener(this);
        btn_chat.setOnClickListener(this);
        btn_apps.setOnClickListener(this);
        btn_email.setOnClickListener(this);
        btn_send.setOnClickListener(this);
        btn_mood.setOnClickListener(this);
        btn_note.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.btn_call:
                Intent intent = new Intent(this, Call.class);
                startActivity(intent);
                break;
            case R.id.btn_chat:
                Intent chat = new Intent(this, Chat.class);
                startActivity(chat);
                break;

            case R.id.btn_apps:
                Intent open_apps = new Intent(this, Open_Apps.class);
                startActivity(open_apps);
                break;
            case R.id.btn_text:
                Intent send_text = new Intent(this, Send_Text.class);
                startActivity(send_text);
                break;
            case R.id.btn_note:
                Intent note = new Intent(this, Notes.class);
                startActivity(note);
                break;
            case R.id.btn_mood:
                Intent mood = new Intent(this, Mood.class);
                startActivity(mood);
                break;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}
