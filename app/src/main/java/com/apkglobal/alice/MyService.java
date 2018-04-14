package com.apkglobal.alice;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;

/**
 * Created by Anshul Aggarwal on 13-08-2017.
 */

public class MyService extends Service implements RecognitionListener, TextToSpeech.OnInitListener {


    /*--------------------------------Original Variables of NOidea--------------------------------*/
    static final int MSG_RECOGNIZER_START_LISTENING = 1;
    static final int MSG_RECOGNIZER_CANCEL = 2;
    private final int MY_PERMISSIONS_RECORD_AUDIO = 1;
    protected AudioManager mAudioManager;
    protected SpeechRecognizer mSpeechRecognizer;
    protected Intent mSpeechRecognizerIntent;
    protected boolean mIsListening;
    protected volatile boolean mIsCountDownOn;
    /*------------------------Variable from  ALice Part2-----------------------------*/
    String contactNumber = "null";
    String phoneNumber = "null";
    Button btn_refresh;
    int flipper = 0;
    String interact, interacttwo;
    Animation animation;
    Button btn_speak;
    TextView tv_speak;
    String ask[];
    String answer[];
    String app[];
    String apppackage[];
    int counter = 0;  //to direct to the storing arrat
    int index = 0;
    int index_que = 0;
    /*--------------------------------------------------------------------------------------------*/
    int app_switch = 0;
    ImageView imageView;
    EditText et_write;
    Button btn_delete;
    int pid = 1;
    String fresponse[];
    //sqlite database
    SQLiteDatabase sd;
    ListView listview;
    String LOG_TAG = "---------->";
    SpeechRecognizer speech;
    Intent recognizerIntent;
    int max = 15;
    Context context;
    AudioManager audioManager;
    TextToSpeech tts;
    private boolean mIsStreamSolo;
    boolean flag;
    /*-------------------------------------------------------------------------------------------*/

    /*--------------------------------Generate Error Logs-----------------------------------------*/
    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }
    /*---------------------------------------------------------------------------------------------*/


    /*-----------------------------OnCreate Method Starts------------------------------------------*/
    @Override
    public void onCreate() {
        Toast.makeText(this, "SErivce Runnng", Toast.LENGTH_SHORT).show();
        Log.d("-------->", "SErvice is Running");


        /*----------------------------TextToSpeech Ready------------------------*/
        tts = new TextToSpeech(this, this);
        /*-----------------------------------------------------------------------*/


        /*------------------------THE MEAT--------------------------------- */
        speech = SpeechRecognizer.createSpeechRecognizer(this);
        speech.setRecognitionListener(this);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
        /*--------------------------------------------------------------------*/


        Log.e("------------>", "OnCreate");
        Log.d(TAG, " ");
        Log.d(TAG, "Listennign start");
        Log.d(TAG, " ");
        audioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,0,0);
        speech.startListening(recognizerIntent);

        /*------------------------------Volume Set Module---------------------------------------*/
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//      int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
       /* int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float percent = 0.1f;
        int seventyVolume = (int) (maxVolume*percent);
        Log.e("----------------->"," "+seventyVolume);*/
        //audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
        Log.e("------------>", "Volume Limited");
        /*------------------------------------------------------------------------*/


    }

    @Override
    public void onBeginningOfSpeech() {
        Log.e(LOG_TAG, "onBeginningOfSpeech");
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.e(LOG_TAG, "onBufferReceived: " + buffer);
    }

    @Override
    public void onEndOfSpeech() {
        Log.e(LOG_TAG, "onEndOfSpeech");
    }

    @Override
    public void onError(int errorCode) {
        String errorMessage = getErrorText(errorCode);
        Log.d(LOG_TAG, "FAILED -------> " + errorMessage);
        Toast.makeText(this, "Error message" + errorMessage, Toast.LENGTH_SHORT).show();
        Log.e("****************", "No Match Found !");
        //audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
        Log.d(TAG, "Volume Disabled");
        Log.d(TAG, "Listening Start");

        audioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,0,0);
        speech.startListening(recognizerIntent);
    }

    @Override
    public void onEvent(int arg0, Bundle arg1) {
        Log.e(LOG_TAG, "onEvent");
    }

    @Override
    public void onPartialResults(Bundle arg0) {
        Log.e(LOG_TAG, "onPartialResults");
    }

    @Override
    public void onReadyForSpeech(Bundle arg0) {
        Log.e(LOG_TAG, "onReadyForSpeech");
    }

    @Override
    public void onResults(Bundle results) {
        Toast.makeText(this, "Command Detected.. Hang on Buddy ! ", Toast.LENGTH_SHORT).show();
        Log.e(LOG_TAG, "onResults");
        ArrayList<String> matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String text = "";
        Toast.makeText(this, matches.get(0), Toast.LENGTH_SHORT).show();
        interact = matches.get(0);



       /*-------------------------------Detection ALgorithm Here----------------------------*/

                    /*-------------------------------CAMERA----------------------------*/
        if (interact.toLowerCase().contains("open")) {/*Searched for applicatcion here*/
                    /*Toast.makeText(getApplicationContext(), "got inside the loop 1", Toast.LENGTH_SHORT).show();*/
            Toast.makeText(this, "Hang there Buddy !", Toast.LENGTH_SHORT).show();
            List<PackageInfo> packList = getPackageManager().getInstalledPackages(0);
            counter = packList.size();
                    /*---------------------Application List Obtained---------------------*/
                    /*--------------------Getting inside the loop to search for application ------------*/
            for (int i = 0; i < packList.size(); i++) {
                PackageInfo packInfo = packList.get(i);
                String appName = packInfo.applicationInfo.loadLabel(getPackageManager()).toString();
                Log.d("name", appName);

                        /*------------------Comparing the User request with app name in List--------------*/
                if (interact.toLowerCase().contains(appName.toLowerCase())) {
                    app_switch = 1;
                    String packagename = packInfo.packageName;
                    Log.d("Application name", packagename);
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage(packagename);
                    startActivity(launchIntent);
                    Toast.makeText(getApplicationContext(), "You Get it Man", Toast.LENGTH_SHORT).show();
                            /*Toast.makeText(getApplicationContext(), appName, Toast.LENGTH_LONG).show(e), packagename, Toast.LENGTH_LONG).show();
                       */
                }
            }

        }

        /*-----------------------------CALL---------------------------*/

        else if (interact.toLowerCase().contains(" call ") || interact.toLowerCase().contains("call ") || interact.toLowerCase().contains(" call") ||
                interact.toLowerCase().contains(" dial ") || interact.toLowerCase().contains(" dial") || interact.toLowerCase().contains("dial ")
                || interact.toLowerCase().contains("check with")) {
            Toast.makeText(this, "Hang there Buddy !", Toast.LENGTH_SHORT).show();
            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            while (phones.moveToNext()) {
                String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                Log.e("Contact Name", name);

                if (interact.toLowerCase().contains(name.toLowerCase())) {
                    phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    Log.e("Number", phoneNumber);
                    Intent call = new Intent(Intent.ACTION_CALL);
                    call.setData(Uri.parse("tel: " + phoneNumber));
                    startActivity(call);
                }

            }
            phones.close();
        }


/*
    private void requestAudioPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            //When permission is not granted by user, show them message why this permission is needed.
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.RECORD_AUDIO)) {
                Toast.makeText(this, "Please grant permissions to record audio", Toast.LENGTH_LONG).show();

                //Give user option to still opt-in the permissions
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        MY_PERMISSIONS_RECORD_AUDIO);
a
            } else {
                // Show user dialog to grant permission to record audio
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        MY_PERMISSIONS_RECORD_AUDIO);
            }
        }
        //If permission is granted, then go ahead recording audio
        else if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED) {

            //Go ahead with recording audio now

        }
    }

*/


        /*---------------------------------------------------------------------------------------*/


        /*-------------------------Make the End of the Detection ALgorithm-----------------------*/
        Log.d(TAG, "Ran from the OnResult");
    //audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);


        audioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,0,0);
        speech.startListening(recognizerIntent);

}

    @Override
    public void onDestroy()
    {   super.onDestroy();
        Log.e("------------>","Called on Destroy");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onRmsChanged(float rmsdB) {

    }


    @Override
    public void onInit(int i) {

    }



}
