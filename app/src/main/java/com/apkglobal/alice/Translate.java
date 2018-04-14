package com.apkglobal.alice;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class Translate extends AppCompatActivity implements TextToSpeech.OnInitListener {
    TextToSpeech tts;
    EditText et;
    Button btn, btn_et;
    String interact;
String data;
    Spinner spinner;
    int get_pid = 1;


    private void setupWindowAnimations() {
        Fade fade = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            fade = new Fade();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            fade.setDuration(1000);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(fade);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupWindowAnimations();

        btn = (Button) findViewById(R.id.btn);
        et = (EditText) findViewById(R.id.et);
        btn_et = (Button) findViewById(R.id.btn_et);
        tts = new TextToSpeech(getApplicationContext(), this);
        btn_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String speak = et.getText().toString();
                calltts(speak);
            }
        });

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.translate_data, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                data = adapterView.getItemAtPosition(i).toString();
                compare(data);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeinput();
                Log.e("**********", "Vlaue of Interact-->" + interact);

            }
        });

    }

    /*Meat of the SST MOdule*/
    private void takeinput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hey Sunshine ! Speak Your Mind");
        startActivityForResult(intent, get_pid);
    }

    /*Working mechanishm of the SST Module, The result Funciton*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == get_pid) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> arraylist = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                interact = arraylist.get(0).toString();
                Log.e("******", "the value of interact inside take input is " + interact);
                Toast.makeText(this,interact, Toast.LENGTH_SHORT).show();
                calltts(interact);
            }
        }
    }


    private void compare(String data) {
        switch (data) {
            case "English":
                tts.setLanguage(Locale.ENGLISH);
                break;
            case "Canadian":
                tts.setLanguage(Locale.CANADA);
                break;
            case "German":
                tts.setLanguage(Locale.GERMAN);
                break;
            case "Chineese":
                tts.setLanguage(Locale.CHINESE);
                break;
            case "Italian":
                tts.setLanguage(Locale.ITALIAN);
                break;
            case "Japanes":
                tts.setLanguage(Locale.JAPAN);
                break;
            case "Korean":
                tts.setLanguage(Locale.KOREAN);
                break;
        }
        Log.e("*****************", "Language set by case");


    }

    private void calltts(String message) {
        tts.setSpeechRate(1.0f);
        tts.setPitch(1.2f);
        tts.speak(message, tts.QUEUE_FLUSH, null);
    }


    @Override
    public void onInit(int i) {

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
