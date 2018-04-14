/*Don't give a fuck to the world*/

package com.apkglobal.alice;

import android.Manifest;
import android.animation.Animator;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.gesture.Gesture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.AlarmClock;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.apkglobal.alice.about.About;
import com.chibde.visualizer.CircleBarVisualizer;
import com.cleveroad.audiovisualization.AudioVisualization;
import com.cleveroad.audiovisualization.DbmHandler;
import com.cleveroad.audiovisualization.SpeechRecognizerDbmHandler;
import com.cleveroad.audiovisualization.VisualizerDbmHandler;
import com.willowtreeapps.spruce.Spruce;
import com.willowtreeapps.spruce.animation.DefaultAnimations;
import com.willowtreeapps.spruce.sort.DefaultSort;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static android.view.View.VISIBLE;
import static java.security.AccessController.getContext;

/*Do not get Gentle into sleep
        Endure the pain Inflected to you
        For when the world sleeps, i take my revenge
        Do not get gentle into the sleep */


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TextToSpeech.OnInitListener, RecognitionListener {

    /*------------MISCELLANEOUS----------------*/
    static final int REQUEST_TAKE_PHOTO = 123;
    private final int MY_PERMISSIONS_RECORD_AUDIO = 1;
    private final int MY_PERMISSIONS_CALL = 1;
    private final int MY_PERMISSIONS_READ_CONTACTS = 1;
    /*---------------------------------------------EVERY SINGLE VARIABLE HERE ----------------------------*/
    SpeechRecognizer speech;
    Intent recognizerIntent;
    TextView fling;
    String TAG = "-------------->";
    String phoneNumber = "null";
    String day;
    View myView;
    Animation animation;
    GestureDetectorCompat gestureDetectorcompat;
    TextToSpeech tts;
    int store_access = 0;
    int counter = 0;  //to direct to the storing arrat
    int app_switch = 0;
    ImageView imageView;
    int pid = 1;

    /*-------------Variables for Render--------*/
    SimpleAdapter sa;

    /*-----------------------------------------*/
    /*-------Varibales for database------------*/
    int flipper = 0;
    /*-----------------------------------------*/
    String interact, interacttwo;
    TextView tv_speak;
    int index = 0;
    Button btn_developer_tool;
    //sqlite database
    SQLiteDatabase sd;
    ListView listview;

    /*Declares arraylist for the notes*/
    ArrayList<String> note = new ArrayList<String>();
    ArrayList<String> memo = new ArrayList<String>();
    String que;
    private Button btn_speak;
    /*-----------------------------------------*/
/*--------------------------------------------------------------------------------------------------*/

    /*-----------------------------------------------------ONCREATE----------------------------------*/

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

    /*-------------------End of ON Create------------------------*/

    /*used for Extracting numbers from the string*/
    public static int extractAlphaNumeric(String alphaNumeric) {
        alphaNumeric = alphaNumeric.length() > 0 ? alphaNumeric.replaceAll("\\D+", "") : "";
        int extract_no = alphaNumeric.length() > 0 ? Integer.parseInt(alphaNumeric) : 0;
        return extract_no;
    }


    @Override
    public void onInit(int i) {
        String response;
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(System.currentTimeMillis());
        int time = cal.get(Calendar.HOUR_OF_DAY);
        if (time < 12) {
            response = "Good Morning Sunshine ! ";
        } else if (time < 17) {
            response = "Good Afternoon Lets Do it !";
        } else {
            response = "Good Evening Let's Rock !";
        }

       /* Toast.makeText(this, response, Toast.LENGTH_SHORT).show();*/
        Log.e(TAG, response);

        tts.setLanguage(Locale.ENGLISH);
        tts.speak(response, tts.QUEUE_FLUSH, null);
        tts.setSpeechRate(1.2f);

        //Automatically generated by tts module
    }


/*
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gd.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
*/

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetectorcompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        gestureDetectorcompat = new GestureDetectorCompat(this, new MyGesture());


        CircleBarVisualizer circleBarVisualizer = (CircleBarVisualizer) findViewById(R.id.visualizer);
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.you_music);

// set custom color to the line.
        circleBarVisualizer.setColor(ContextCompat.getColor(this, R.color.colorPrimary));

// Set you media player to the visualizer.
        circleBarVisualizer.setPlayer(mediaPlayer);
       /* CustomGesturesDetector cd=new CustomGesturesDetector();
        gd=new GestureDetector(this,cd);
        gd.setOnDoubleTapListener(cd);
*/

        //firstresponse();

        //startService(new Intent(this,MyService.class));
        /*----------Adding the arrow on the action toolbar-------------*/


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*----------For recording audio during runtime----------------------------*/
        while (!requestAudioPermissions()) {
        }
        ;



        /*-----------------------MEAT TEXT TO SPEECH---------------------------------------------*/
        setSupportActionBar(toolbar);


        /*--------------Typecasting here---------------------*/
        imageView = (ImageView) findViewById(R.id.image);
        tv_speak = (TextView) findViewById(R.id.tv_speak);
        btn_speak = (Button) findViewById(R.id.btn_speak);
        listview = (ListView) findViewById(R.id.listview);
        //fling=(TextView)findViewById(R.id.tv_fling);

        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /*btn_developer_tool=(Button)findViewById(R.id.developer_tool);*/
/*
       btn_developer_tool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent developer=new Intent(getApplicationContext(),Developer.class);
                startActivity(developer);
            }
        });

        btn_developer_tool.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(MainActivity.this, "Meet the Developer", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

  */ //TO hid the repositry/*----------------------------------------------------------------------------------*/
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation);
        btn_speak.startAnimation(animation);

        /*----------------------------Behold-----------------*/

        /*----------------------------DATABASES INVOCKMENT HERE------------------------------------*/
        createdatabase();
        createadapt();
        createchathead();
        firstresponse();

        /*-----------------------------Rendering display and USER WELCOME SURPRISE-----------------*/

        render();/*
        listview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listview.getVisibility() == listview.VISIBLE)
                {
                    listview.setVisibility(View.INVISIBLE);
                }
                else
                    {
                        listview.setVisibility(View.VISIBLE);
                    }

            }
        });*/


        /*-------------------------------MEAT SPEECH TO TEXT--------------------------------------*/
        speech = SpeechRecognizer.createSpeechRecognizer(this);
        speech.setRecognitionListener(this);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);



         /*-------------TAKING THE INPUT AND GO PHUUURRRRRRRRRRRRRR! ------------------------------*/
        btn_speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioManager audiomanager;
                audiomanager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                int max = audiomanager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                audiomanager.setStreamVolume(AudioManager.STREAM_MUSIC, max, 0);

                speech.startListening(recognizerIntent);
                Toast.makeText(MainActivity.this, "Speak Your mind ", Toast.LENGTH_SHORT).show();
                //Control Ridirect: OnResult
            }
        });

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent about = new Intent(this, About.class);
            startActivity(about);
            /*finish();*/
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_email) {
            Intent email = new Intent(this, Email.class);
            startActivity(email);
            /*finish();*/

        } else if (id == R.id.nav_message) {

            Intent message = new Intent(this, Send_Text.class);
            startActivity(message);
            /*finish();*/
        } else if (id == R.id.nav_feature) {

            Intent feature = new Intent(this, Features.class);
            startActivity(feature);
            /*finish();*/

        } else if (id == R.id.nav_note) {
            Intent note = new Intent(this, ListDesign.class);
            startActivity(note);
            /*finish();*/
        } else if (id == R.id.nav_adapt) {
            Intent adapt = new Intent(this, com.apkglobal.alice.adapt.class);
            startActivity(adapt);
            /*finish();*/
        } else if (id == R.id.nav_translate) {
            Intent translate = new Intent(this, Translate.class);
            startActivity(translate);
            /*finish();*/
        } else if (id == R.id.nav_Infinity) {
            Intent infinity = new Intent(this, Infinity.class);
            startActivity(infinity);
            /*finish();*/
        } else if (id == R.id.nav_share) {
            Intent share = new Intent(Intent.ACTION_SEND);
            share.putExtra(Intent.EXTRA_TEXT, "Hey I'm Using this new App Samsara. Check out Guys, Its so Cool !" + "https://play.google.com/store/apps/details?id=com.apkglobal.alice&hl=en");
            share.setType("text/plain");
            startActivity(Intent.createChooser(share, "Share App via"));


        } else if (id == R.id.nav_feedback) {
            Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_EMAIL, new String[]{"anshul.aggarwal.sfd@gnail.com"});
            email.setType("email/rfc822");
            startActivity(Intent.createChooser(email, "Send Mail Via"));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public void onReadyForSpeech(Bundle bundle) {
        Log.e(TAG, "READY FOR THE SPEECH");

    }

    @Override
    public void onBeginningOfSpeech() {
        Log.e(TAG, "ON SPEECH BEGIN");

    }

    @Override
    public void onRmsChanged(float v) {

    }

    @Override
    public void onBufferReceived(byte[] bytes) {
        Log.e(TAG, "Addtion buffer recieved");
    }

    @Override
    public void onEndOfSpeech() {
        Log.e("*******", "No Match Found !");
        Log.d(TAG, "Volume Disabled");
        Log.e(TAG, "Speech END");

    }

    @Override
    public void onError(int errorCode) {
        String errorMessage = getErrorText(errorCode);
        Log.d(TAG, "FAILED -------> " + errorMessage);
        if (errorMessage.equals("RecognitionService busy")) {
        }
        //Toast.makeText(this, "Error message" + errorMessage, Toast.LENGTH_SHORT).show();
        Log.e("*********", "Listening Start");
    }
/*SO let the world get the gentle adn best of you cause there si nothing for you except the redemption so let go of me what have i DOne*/
    /*
                   *//*Finding in the stored Responses*//*
                for (int i = 0; i < ask.length; i++) {
                    if (interact.toLowerCase().equalsIgnoreCase(ask[i])) { *//*spekaing the stored response to the question*//*
                        String response = answer[i].toString();
                        tts.setLanguage(Locale.ENGLISH);
                        tts.speak(response, tts.QUEUE_FLUSH, null);
                    }

                }

                *//*Directs to store the answer in the array*//*
                else {
                    respondsorry();
                    counter = 1;
                    ask[index] = interact; //Stores the Question here
                    btn_speak.performClick();
                    Toast.makeText(getApplicationContext(), "you Got it man ", Toast.LENGTH_LONG).show();
                }

    /*-------------------------------------------------------------------------*/

    @Override
    public void onResults(final Bundle result) {

       /* animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.animation);
        btn_speak.startAnimation(animation);
*/
        ArrayList<String> matches = new ArrayList<>();
        matches = result.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
      /*  Toast.makeText(this, matches.get(0), Toast.LENGTH_SHORT).show();*/
        interact = matches.get(0);
        insertchathead(interact);


        //user message is retrieved in the interact

        /*Stores in the adaptions else go to hell*/
        if (store_access == 1) {
            store_access = 0;
            Log.e(TAG, "inserting into the table !!!");
            interacttwo = matches.get(0);
            insertchathead("Brace Yourself, Next time I'm Gonna Beat you !");
            Log.e(TAG, que);
            Log.e(TAG, interacttwo);
            insertadapt(que, interacttwo);
        }

        /*This is the fucking hell*/
        else {
            Log.e(TAG, "reaching here");
            fetchadapt(); //Runnig through the adaptations first  || Deeplink: flipper
            if (flipper != 1)  //Not honored by the adapt database
            {
                    /*-------------Mian search detection algorithm here-----------*/

                if (interact.contains("age") || interact.toLowerCase().contains("what is your age") ||
                        interact.toLowerCase().contains("what's your age") || interact.toLowerCase().contains("age")
                        || interact.toLowerCase().contains("how many years are you old ") ||
                        interact.toLowerCase().contains("how old are you ") || interact.toLowerCase().contains("when were you born")) {

                    respondage();
                }

                /*-------------------------------------------------------------------------*/
            /*Finds the application to open*/

                else if (interact.toLowerCase().contains("open")) {/*Searched for applicatcion here*/
                    /*Toast.makeText(getApplicationContext(), "got inside the loop 1", Toast.LENGTH_SHORT).show();*/
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
                            respondok();
                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage(packagename);
                            startActivity(launchIntent);
                            Toast.makeText(getApplicationContext(), "You Get it Man", Toast.LENGTH_SHORT).show();
                            /*Toast.makeText(getApplicationContext(), appName, Toast.LENGTH_LONG).show(e), packagename, Toast.LENGTH_LONG).show();
                       */
                        }
                    }
                    if (app_switch == 0) {
                        calltts("SORRY ! APPLICATION NOT FOUND !");
                    }
                }
  /*-------------------------------------------------------------------------*/
    /*opens Whatsapp*/
                else if (interact.toLowerCase().contains(" whatsapp ") || interact.toLowerCase().contains("whatsapp ") || interact.toLowerCase().contains(" whatsapp")) {
                    calltts("Nothing Just Chilling Around , You wanna come ?");
                }
 /*-------------------------------------------------------------------------*/
    /*opens Transaltion*/
                else if (interact.toLowerCase().contains(" translate ") || interact.toLowerCase().contains("translate ") || interact.toLowerCase().contains(" translate")
                        || interact.toLowerCase().contains(" translation ") || interact.toLowerCase().startsWith("translate")) {
                    respondok();
                    Intent translate = new Intent(this, Translate.class);
                    startActivity(translate);
                    Toast.makeText(getApplicationContext(), "you Got it man ", Toast.LENGTH_LONG).show();
                }

   /*-------------------------------------------------------------------------*/
            /*Humam*/
                else if (interact.toLowerCase().contains("are you human") || interact.toLowerCase().contains("what are you") || interact.toLowerCase().contains("how it feels") || interact.toLowerCase().contains("android") || interact.toLowerCase().contains("robot") || interact.toLowerCase().contains("do you feel")) {
                    String response = "You Broke my Heart !!!! I do Feel and sure as hell have feelings";
                    calltts(response);
                    Toast.makeText(getApplicationContext(), "you Got it man ", Toast.LENGTH_LONG).show();
                }

  /*-------------------------------------------------------------------------*/
            /*Creator*/
                else if (interact.toLowerCase().contains("who created you") || interact.toLowerCase().contains("who is your creator") || interact.toLowerCase().contains("creator") || interact.toLowerCase().contains("parent") || interact.toLowerCase().contains("mother") || interact.toLowerCase().contains("father") || interact.toLowerCase().contains("god")) {
                    String response = "Well we are created by the Almighty, But i surely am thankful to Pawan for their efforts";
                    calltts(response);
                    Toast.makeText(getApplicationContext(), "you Got it man ", Toast.LENGTH_LONG).show();
                }

             /*   /*HIdes the listview*//*
                else if(interact.toLowerCase().contains("hide list"))
                    {listview.setVisibility(View.INVISIBLE);   }
                else if(interact.toLowerCase().contains("show list"))
                    {listview.setVisibility(VISIBLE);}*/
            /*abuse*/
                else if (interact.toLowerCase().contains("fuck") || interact.toLowerCase().contains("you are shit") || interact.toLowerCase().contains("you suck") || interact.toLowerCase().contains("asshole")) {

                    String response = "Fuck you ";
                    calltts(response);
                    Toast.makeText(getApplicationContext(), "you Got it man ", Toast.LENGTH_LONG).show();
                }

   /*-------------------------------------------------------------------------*/
    /*Love*/
                else if (interact.toLowerCase().contains("love me") || interact.toLowerCase().contains("love") || interact.toLowerCase().contains("kick my ass") || interact.toLowerCase().contains("kick you ass")) {
                    calltts("Huh, Like you really love me , LOL ");
                }

      /*-------------------------------------------------------------------------*/
    /*Love*/
                else if (interact.toLowerCase().contains("hate me") || interact.toLowerCase().contains("hate") || interact.toLowerCase().contains("kick my ass") || interact.toLowerCase().contains("kick you ass")) {
                    calltts("Oh You Broke my Heart darling. I Adore you ! ");
                }


 /*-------------------------------------------------------------------------*/
 /*how You Doing ?*/
                else if ((interact.toLowerCase().contains("you") && interact.toLowerCase().contains("doing")) || interact.toLowerCase().contains("what's up") || interact.contains("how are you") || interact.contains("kya hal chal") || interact.contains("kya haal chaal")) {
                    respondhowyoudoing();
                }


     /*-------------------------------------------------------------------------*/
    /*Name*/
                else if (((interact.toLowerCase().contains("what") || interact.toLowerCase().contains("what's")) && (interact.toLowerCase().contains("name") || interact.toLowerCase().contains("called") || interact.toLowerCase().contains("idea")))) {
                    respondname();
                }

       /*-------------------------------------------------------------------------*/
    /*Obliged*/
                else if (interact.toLowerCase().contains("you") && (interact.toLowerCase().contains("awesome") || interact.toLowerCase().contains("great") || interact.toLowerCase().contains("you rock") || interact.toLowerCase().contains("ki") || interact.toLowerCase().contains("best"))) {
                    respondobliged();
                }
  /*-------------------------------------------------------------------------*/
  /*Writes Message*/
                else if (interact.toLowerCase().contains("write message") || interact.toLowerCase().contains("message") || interact.toLowerCase().contains("text") || interact.toLowerCase().contains("write a message")) {
                    respondok();
                    Intent intent = new Intent(this, Send_Text.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "you Got it man ", Toast.LENGTH_LONG).show();
                }


   /*-------------------------------------------------------------------------*/
  /*Sets Alarm*/
                else if (interact.toLowerCase().contains(" alarm ") || interact.toLowerCase().contains(" alarm") || interact.toLowerCase().contains("alarm ")) {
                    respondok();

                    /*To set alarm for normal am */
                    if ((interact.toLowerCase().contains(":") && (interact.toLowerCase().contains("a.m")))) {
                        String[] extract = interact.split(":");
                        int hours = extractAlphaNumeric(extract[0]);
                        int minutes = extractAlphaNumeric(extract[1]);
                        setalarm(hours, minutes);
                    }
                    /*To set alarm for normal pm*/
                    else if ((interact.toLowerCase().contains(":") && (interact.toLowerCase().contains("p.m")))) {
                        String[] extract = interact.split(":");
                        int hours = extractAlphaNumeric(extract[0]);
                        int minutes = extractAlphaNumeric(extract[1]);
                        setalarm(hours + 12, minutes);
                    }
                    /*To set alarm for normal am*/
                    else if ((!interact.toLowerCase().contains(":")) && (interact.toLowerCase().contains("a.m"))) {
                        String[] extract = interact.split(":");
                        int hours = extractAlphaNumeric(extract[0]);
                        int minutes = 0;
                        setalarm(hours, minutes);
                    }
                    /*To set alarm for even am*/
                    else if ((!interact.toLowerCase().contains(":")) && (interact.toLowerCase().contains("p.m"))) {
                        String[] extract = interact.split(":");
                        int hours = extractAlphaNumeric(extract[0]);
                        int minutes = 0;
                        setalarm(hours + 12, minutes);
                    }
                    /*To set alarm for o'clock*/
                    else if (interact.toLowerCase().contains("o'clock")) {/*Reduce the string to only numbers and set the alarm*/
                        int hour = extractAlphaNumeric(interact);
                        setalarm(hour, 00);
                    }
                    /*IF 24hours does not contains minutes*/
                    else if (!(interact.toLowerCase().contains(":"))) {
                        String[] extract = interact.split(":");
                        int hours = extractAlphaNumeric(extract[0]);
                        int minutes = 0;
                        setalarm(hours, minutes);
                    } else {
                        /*NOrmal 24 hours format*/
                        String[] extract = interact.split(":");
                        int hours = extractAlphaNumeric(extract[0]);
                        int minutes = extractAlphaNumeric(extract[1]);
                        setalarm(hours, minutes);
                    }
                }


   /*-------------------------------------------------------------------------*/
  /*Tells Date*/
                else if (interact.toLowerCase().contains(" date ") || interact.toLowerCase().contains(" date") || interact.toLowerCase().contains("date ")) {
                    GregorianCalendar cal = new GregorianCalendar();
                    cal.setTimeInMillis(System.currentTimeMillis());
                    String month = "null";
                    switch (cal.get(Calendar.MONTH)) {
                        case 1:
                            month = "January";
                            break;
                        case 2:
                            month = "February";
                            break;
                        case 3:
                            month = "March";
                            break;

                        case 4:
                            month = "April";
                            break;
                        case 5:
                            month = "May";
                            break;
                        case 6:
                            month = "June";
                            break;
                        case 7:
                            month = "July";
                            break;
                        case 8:
                            month = "August";
                            break;
                        case 9:
                            month = "September";
                            break;
                        case 10:
                            month = "October";
                            break;
                        case 11:
                            month = "November";
                            break;
                        case 12:
                            month = "December";
                            break;
                    }

                    String response = "Well it's " + cal.get(Calendar.DAY_OF_MONTH) + "of the Month of "
                            + month +
                            +cal.get(Calendar.YEAR) + " ! ";

                    Toast.makeText(this, month + "" + cal.get(Calendar.DAY_OF_MONTH) + ", " + cal.get(Calendar.YEAR), Toast.LENGTH_LONG).show();
                    calltts(response);

                }



   /*-------------------------------------------------------------------------*/
                    /*Tells Time*/
                else if ((interact.toLowerCase().contains("what") && (interact.toLowerCase().contains(" time ") || interact.toLowerCase().contains(" time") || interact.toLowerCase().contains("time ")))) {
                    GregorianCalendar cal = new GregorianCalendar();
                    cal.setTimeInMillis(System.currentTimeMillis());


                    String month = "null";
                    switch (cal.get(Calendar.MONTH)) {
                        case 1:
                            month = "January";
                            break;
                        case 2:
                            month = "February";
                            break;
                        case 3:
                            month = "March";
                            break;

                        case 4:
                            month = "April";
                            break;
                        case 5:
                            month = "May";
                            break;
                        case 6:
                            month = "June";
                            break;
                        case 7:
                            month = "July";
                            break;
                        case 8:
                            month = "August";
                            break;
                        case 9:
                            month = "September";
                            break;
                        case 10:
                            month = "October";
                            break;
                        case 11:
                            month = "November";
                            break;
                        case 12:
                            month = "December";
                            break;
                    }


                    String response = "Well it's " + cal.get(Calendar.HOUR_OF_DAY) + " hours "
                            + cal.get(Calendar.MINUTE) + "minutes on a lovely "
                            + dayreturn(Calendar.DAY_OF_WEEK) + "  Day ! ";

                    Toast.makeText(this, cal.get(Calendar.HOUR_OF_DAY) + " Hours " + cal.get(Calendar.MINUTE) + " Minutes", Toast.LENGTH_LONG).show();
                    Toast.makeText(this, month + "" + cal.get(Calendar.DAY_OF_MONTH) + ", " + cal.get(Calendar.YEAR), Toast.LENGTH_LONG).show();
                    calltts(response);

                }


  /*-------------------------------------------------------------------------*/
                    /*Sets Timer*/
                else if (interact.toLowerCase().contains(" timer ") || interact.toLowerCase().contains(" timer") || interact.toLowerCase().contains("timer ")) {
                    respondok();
                    int time = extractAlphaNumeric(interact);
                    if (interact.toLowerCase().contains("minute")) {
                        setTimer(time * 60);   //sets timer in minutes
                    } else if (interact.toLowerCase().contains("hour")) {
                        setTimer(time * 3600);  //sets timer in hours
                    } else if (interact.toLowerCase().contains("day")) {
                        setTimer(time * (3600 * 24));  //sets timer in days
                    } else {
                        setTimer(time);
                    }
                }
  /*-------------------------------------------------------------------------*/
                    /*Show Features*/
                else if (interact.toLowerCase().contains("what can you do") || interact.toLowerCase().contains("features") || interact.toLowerCase().contains("feature") || interact.toLowerCase().contains("function")) {
                    respondok();
                    Intent features = new Intent(this, Features.class);
                    startActivity(features);
                    Toast.makeText(getApplicationContext(), "you Got it man ", Toast.LENGTH_LONG).show();
                }

/*-------------------------------------------------------------------------*/
                 /*Sends Email*/
                else if (interact.toLowerCase().contains("email") || interact.toLowerCase().contains("mail") || interact.toLowerCase().contains("send an email")) {
                    respondok();
                    Intent intent = new Intent(this, Email.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "you Got it man ", Toast.LENGTH_LONG).show();
                }

/*-------------------------------------------------------------------------*/
                    /*Hungry*/
                else if (interact.toLowerCase().contains("hunger") || interact.toLowerCase().contains("hungry") || interact.toLowerCase().contains("food") || interact.toLowerCase().contains("foodie")) {
                    {

                        /*checks for swiggy installed or not */
                        Intent swiggy = getPackageManager().getLaunchIntentForPackage("in.swiggy.android");
                        if (swiggy == null) {   //Checks for foodpanda installed or not
                            Intent foodpanda = getPackageManager().getLaunchIntentForPackage("icom.india.foodpanda.android");
                            if (foodpanda == null) {
                                Intent zomato = getPackageManager().getLaunchIntentForPackage("icom.india.foodpanda.android");
                                if (zomato == null) {  /*Rediercting to Playstore to install the application*/
                                    String response = "Oops ! Looks Like you dont have any Apps installed, But dont worry here you Go";
                                    calltts(response);
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "in.swiggy.android")));
                                } else {
                                    respondok();
                                    startActivity(zomato);
                                }
                            } else {
                                respondok();
                                startActivity(foodpanda);
                            }
                        } else {
                            respondok();
                            startActivity(swiggy);
                        } //Laucnh Swiggy


                    }
                }




/*-------------------------------------------------------------------------*/
                    /*Outings*/
                else if (interact.toLowerCase().contains("cab") || interact.toLowerCase().contains("book a cab") || interact.toLowerCase().contains("trip")
                        || interact.toLowerCase().contains("go somewhere") || interact.toLowerCase().contains("outing") || interact.toLowerCase().contains("adventure") || interact.toLowerCase().contains("taxi")
                        || interact.toLowerCase().contains("ola") || interact.toLowerCase().contains("plan  a trip") || interact.toLowerCase().contains("uber")) {
                    {

                        /*checks for swiggy installed or not */
                        Intent uber = getPackageManager().getLaunchIntentForPackage("com.ubercab");
                        if (uber == null) {   //Checks for foodpanda installed or not
                            Intent ola = getPackageManager().getLaunchIntentForPackage("com.olacabs.customer");
                            if (ola == null) {
                                  /*Rediercting to Playstore to install the application*/
                                String response = "Oops ! Looks Like you dont have any Apps installed, But dont worry here you Go";
                                calltts(response);
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.ubercab")));
                            } else {
                                respondok();
                                startActivity(ola);
                            }
                        } else {
                            respondok();
                            startActivity(uber);
                        }
                    } //Laucnh Uber


                }


/*-------------------------------------------------------------------------*/
                    /*Plays Music*/
                else if (interact.toLowerCase().contains("music") || interact.toLowerCase().contains("songs") || interact.toLowerCase().contains("rock") || interact.toLowerCase().contains("beats") || interact.toLowerCase().contains("surprise me") || interact.toLowerCase().contains("music player")) {
                    {

                        /*checks for swiggy installed or not */
                        Intent samsung_music = getPackageManager().getLaunchIntentForPackage("com.sec.android.app.music");
                        if (samsung_music == null) {   //Checks for foodpanda installed or not
                            Intent play_music = getPackageManager().getLaunchIntentForPackage("com.google.android.music");
                            if (play_music == null) {
                                Intent gaana = getPackageManager().getLaunchIntentForPackage("com.gaana");
                                if (gaana == null) {  /*Rediercting to Playstore to install the application*/
                                    String response = "Oops ! Looks Like you dont have any Apps installed, But dont worry here you Go";
                                    calltts(response);
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.gaana")));
                                } else {
                                    respondok();
                                    startActivity(gaana);
                                }
                            } else {
                                respondok();
                                startActivity(play_music);
                            }
                        } else {
                            respondok();
                            startActivity(samsung_music);
                        } //Launch Samsung music


                    }
                }




/*

    */
/* ------------------------------------------------------------------------*//*

   */
/*Camera*//*

                    else if (interact.toLowerCase().contains("camera") || interact.toLowerCase().contains("click") || interact.toLowerCase().contains("selfie") || interact.toLowerCase().contains("picture")) {
                        respondok();
                        takePicture();
                        Toast.makeText(this, "Click Man Click !", Toast.LENGTH_SHORT).show();
                    }

*/


 /*-------------------------------------------------------------------------*/
                   /*Takes notes*/
                else if (interact.toLowerCase().contains("remember") || interact.toLowerCase().contains(" remember") || interact.toLowerCase().contains(" remember ") ||
                        interact.toLowerCase().contains("remind") || interact.toLowerCase().contains(" remind") || interact.toLowerCase().contains(" remind ") ||
                        interact.toLowerCase().contains("note that")) {

                    if (interact.toLowerCase().contains("remember to")) {
                        String fresponse[] = interact.split("remember to");
                        insertdatabase(fresponse[1]);
                        Toast.makeText(this, fresponse[1], Toast.LENGTH_SHORT).show();

                    } else if (interact.toLowerCase().contains("remember")) {
                        String fresponse[] = interact.split("remember");
                        insertdatabase(fresponse[1]);
                        Toast.makeText(this, fresponse[1], Toast.LENGTH_SHORT).show();

                    } else if (interact.toLowerCase().contains("remind me")) {
                        String fresponse[] = interact.split("me");
                        insertdatabase(fresponse[1]);
                        Toast.makeText(this, fresponse[1], Toast.LENGTH_LONG).show();
                    } else if (interact.toLowerCase().contains("note")) {
                        String fresponse[] = interact.split("note");
                        insertdatabase(fresponse[1]);
                        Toast.makeText(this, fresponse[1], Toast.LENGTH_LONG).show();
                    }

                    String response = "Duly Noted";
                    calltts(response);
                }


 /*-------------------------------------------------------------------------*/
                   /*Show me my Notes*/
                else if ((interact.toLowerCase().contains("show me my notes") || interact.toLowerCase().contains("show my notes") || interact.toLowerCase().contains("my notes") || interact.toLowerCase().contains("to do list"))) {

                    Intent intent = new Intent(this, ListDesign.class);
                    startActivity(intent);

                }

 /*-------------------------------------------------------------------------*/
                   /*Delete me my Notes*/
                else if ((interact.toLowerCase().contains("delete all notes") || interact.toLowerCase().contains("empty the list") || interact.toLowerCase().contains("clear the notes"))) {
                    cleartable();
                    Toast.makeText(this, "Cleared ! ", Toast.LENGTH_SHORT).show();

                }


 /*-------------------------------------------------------------------------*/
                   /*Delete all Logs*/
                else if ((interact.toLowerCase().contains("wipe") || interact.toLowerCase().contains("clean")||interact.toLowerCase().contains("chandelier"))) {
                    wipelogs();
                    Toast.makeText(this, "Cleared ! ", Toast.LENGTH_SHORT).show();

                }




 /*-------------------------------------------------------------------------*/
                   /*Delete the Logs*/
                else if ((interact.toLowerCase().contains("whiskey") || interact.toLowerCase().contains("tango"))) {
                    clearchat();
                    Toast.makeText(this, "Cleared ! ", Toast.LENGTH_SHORT).show();

                }



                     /*-------------------------------------------------------------------------*/
                   /*Clears Adaptive Learning*/
                else if ((interact.toLowerCase().contains("delete all adapt") || interact.toLowerCase().contains("empty learn") || interact.toLowerCase().contains("delta alpha"))) {
                    clearadapt();
                    Toast.makeText(this, "Cleared ! ", Toast.LENGTH_SHORT).show();

                }
 /*-------------------------------------------------------------------------*/
                   /*Turns On or Off Bluetooth*/
                else if (interact.toLowerCase().contains(" bluetooth") || interact.toLowerCase().contains("bluetooth ") || interact.toLowerCase().contains(" bluetooth ")) {
                    if (interact.toLowerCase().contains("on") || interact.toLowerCase().contains("enable")) {
                        setBluetooth(true);
                        respondok();
                        Toast.makeText(getApplicationContext(), "you Got it man ", Toast.LENGTH_LONG).show();
                    } else {
                        setBluetooth(false);
                        respondok();
                        Toast.makeText(getApplicationContext(), "you Got it man ", Toast.LENGTH_LONG).show();
                    }
                }



  /*-------------------------------------------------------------------------*/

/*

                      */
/*Turns On or Off Airplane Mode*//*

                if (interact.toLowerCase().contains("Airplane") || interact.toLowerCase().contains("aeroplane") || interact.toLowerCase().contains("offline")) {
                    airplaneOn();
                    respondok();
                    Toast.makeText(getApplicationContext(), "you Got it man ", Toast.LENGTH_LONG).show();
                }

*/



/*
                */
/*Storing the answer int the array answer*//*

                else if (counter == 1) {
                    answer[index] = interact;   //Stored the response to the answer here
                    index += 1;
                    counter=0;
                }
*/
  /*-------------------------------------------------------------------------*/
            /*Opens Book My SHow*/
                else if (interact.toLowerCase().contains(" movie ") || interact.toLowerCase().contains("movie ") || interact.toLowerCase().contains(" movie") ||
                        interact.toLowerCase().contains("bored") || interact.toLowerCase().contains("bored ") || interact.toLowerCase().contains("bored ") ||
                        interact.toLowerCase().contains(" film ") || interact.toLowerCase().contains("film ") || interact.toLowerCase().contains("film ") ||
                        interact.toLowerCase().contains(" bore ") || interact.toLowerCase().contains("bore ") || interact.toLowerCase().contains("bore ") ||
                        interact.toLowerCase().contains(" movies ") || interact.toLowerCase().contains("movies ") || interact.toLowerCase().contains("movies")) {
                    respondmovie();

                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.bt.bms");
                    if (launchIntent != null) {
                        startActivity(launchIntent);
                        Toast.makeText(getApplicationContext(), "you Got it man ", Toast.LENGTH_LONG).show();
                    } else {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.bt.bms")));
                    }
                }
  /*-------------------------------------------------------------------------*/
                /*To call an contaact bt name*/
                else if (interact.toLowerCase().contains(" call ") || interact.toLowerCase().contains("call ") || interact.toLowerCase().contains(" call") ||
                        interact.toLowerCase().contains(" dial ") || interact.toLowerCase().contains(" dial") || interact.toLowerCase().contains("dial ")
                        || interact.toLowerCase().contains("check with")) {

                    calltts("Dialing ...");
                    if (checkPermission() == true) {
                        int check = 0;
                        callruntime();
                        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
                        while (phones.moveToNext()) {
                            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                            Log.e("Contact Name", name);

                            if (interact.toLowerCase().contains(name.toLowerCase())) {
                                check = 1;
                                phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                Log.e("Number", phoneNumber);
                                Intent call = new Intent(Intent.ACTION_CALL);
                                call.setData(Uri.parse("tel: " + phoneNumber));
                                startActivity(call);
                            }


                        }

                        if (check == 0) {
                            calltts("Apologies, No such Contact found !");
                        }
                        phones.close();
                    } else {
                        String response = "Permission Denied. Please Try Again Later !";
                        calltts(response);
                    }
                }

  /*-------------------------------------------------------------------------*/
            /*Responds hi*/
                else if (interact.toLowerCase().contains(" hi ") || interact.toLowerCase().contains(" hi") || interact.toLowerCase().contains("hi ") ||
                        interact.toLowerCase().startsWith("hey") ||
                        interact.toLowerCase().startsWith("hi") ||
                        interact.toLowerCase().startsWith("hello") ||
                        interact.toLowerCase().contains(" hello ") || interact.toLowerCase().contains("hello ") || interact.toLowerCase().contains(" hello")
                        || interact.toLowerCase().contains("Samsara")) {
                    respondhi();
                }
/*


  */
/*-------------------------------------------------------------------------*//*

            */
/*Responds hi*//*

                else if (interact.equalsIgnoreCase(que)) {
                    String respone="gotcha";
                    calltts(respone);
                }
*/

/*------------------------------------------------------------------------------*/

  /*-------------------------------------------------------------------------*/
                /*Apologises*/
                else {
                    /*que[index_que] = interact;
                    Toast.makeText(this, "Question Stored", Toast.LENGTH_SHORT).show();
                    index_que += 1;*/

                    /*String response = "My Apologies. Apparently you are Smarter than me !";*/
                    String apology = "Apparently you are smarter than me. Can you please tell me what it means ??";
                    calltts(apology);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            que = interact;
                              /*Logic for adaptations*/
                            store_access = 1;
                            speech.startListening(recognizerIntent);
                        }
                    }, 3200);
                    /* Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
                    calltts(response);*/


                }

            }
        }


        /*---------------------------------MARKS THE END OF THE ONRESULT--------------------------*/
       /* animation.cancel();
        animation.reset();
    */
    }

    private void callruntime() {

        while ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) &&
                checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, pid);

        }
    }

    @Override
    public void onPartialResults(Bundle bundle) {
        Log.e(TAG, "On Partial Results");
    }

    @Override
    public void onEvent(int i, Bundle bundle) {
        Log.e(TAG, "ON EVENT");
    }

    /*-----------------------------------DATABASE fINCTIONALITY HERE------------------------------*/


    /*MEMO DATABASE ------------------------------ */
    private void createdatabase() {
        sd = openOrCreateDatabase("note", Context.MODE_PRIVATE, null);
        sd.execSQL("create table if not exists register(note varchar(300));");
    }

    /*Notes down the note*/
    private void insertdatabase(String notes) {
        sd = openOrCreateDatabase("note", Context.MODE_PRIVATE, null);
        sd.execSQL("insert into register(note) values('" + notes + "');");
        Log.e("************", "inserted into the notes");
    }

    private void fetchdatabase() {
        Cursor c = sd.rawQuery("select * from register", null);
        if (c != null) {
            c.moveToFirst();
            do {
                memo.add(c.getString(0));
                index += 1;
                Toast.makeText(this, c.getString(0), Toast.LENGTH_SHORT).show();
            } while (c.moveToNext());

            if (c == null) {
                String response = "Oops ! Looks like you are empty";
                calltts(response);
                Intent save = new Intent(this, MainActivity.class);

            }
        }
    }

                            /*-------------------------------------------*/

    private void cleartable() {
        sd.execSQL("delete from register");
    }

    /*ADAPTIVE LEARNING DATABASE----------------------------*/
    private void createadapt() {
        sd = openOrCreateDatabase("note", Context.MODE_PRIVATE, null);
        sd.execSQL("create table if not exists adapt(que varchar(200),ans varchar(250));");

        Cursor cursor = sd.rawQuery("select * from adapt", null);
        if (cursor.getCount() <= 0) {

            sd.execSQL("insert into adapt(que,ans) values('This stores the user questions','This represents the learned responses');");
            Log.e("---------------->", "inserted into adapt via check test");
            cursor.close();
        }  /*}*/

    }

    /*Stores down the user's Response */
    private void insertadapt(String que, String ans) {
        sd = openOrCreateDatabase("note", Context.MODE_PRIVATE, null);
        sd.execSQL("insert into adapt(que,ans) values('" + que.replace("'", "''") + "','" + ans.replace("'", "''") + "');");
        Log.e(TAG, "inserted sucesfully");
        scrollMyListViewToBottom();
        Toast.makeText(this, "Archived For Analysis", Toast.LENGTH_SHORT).show();
    }

    /*Delete adapt------------*/
    private void clearadapt() {
        sd.execSQL("delete from adapt");
        calltts("I don't recommend this By the Way");
        createadapt();
    }

    /*Fetches from the stored Responses*/
    private void fetchadapt() {
        flipper = 0;
        sd = openOrCreateDatabase("note", Context.MODE_PRIVATE, null);
        Cursor sc = sd.rawQuery("select * from adapt", null);
        if (sc != null) {
            sc.moveToFirst();
            do {
                memo.add(sc.getString(0));
                index += 1;
                if (interact.equalsIgnoreCase(sc.getString(0))) {
                    tv_speak.setText(sc.getString(1));
                    calltts(sc.getString(1));
                    flipper = 1;
                }
            } while (sc.moveToNext());

            if (sc == null) {
                String response = "Oops ! Looks like you are empty";
                calltts(response);
                Intent save = new Intent(this, MainActivity.class);

            }
        }
    }

    /*------------------Database 3 CHATHEADS------------------------------------------------*/
    private void createchathead() {
        sd = openOrCreateDatabase("note", Context.MODE_PRIVATE, null);
        sd.execSQL("create table if not exists chat(message varchar(300));");

        String message = "That's me :) ";
        Cursor cursor = sd.rawQuery("select * from chat", null);
        if (cursor.getCount() <= 0) {
            sd.execSQL("insert into chat(message) values('This is you !!!');");
            sd.execSQL("insert into chat(message) values('That''s Me :) ');");
            Log.e("---------------->", "inserted from here");
            cursor.close();
        }  /*}*/
        /*Already fulfilled*/

        /*
        sd.execSQL("insert into chat(message) values('User is the Best');");
        sd.execSQL("insert into chat(message) values('Samsara is the Best');");*/ //To prevent null pointer exception
    }

    private void insertchathead(String message) {
        /*Log.e("******","the Message before swapping --> "+message);
       */

        String prevent_stall = message.replace("'", "''");   //This prevents the stalling near the database insert query
        /*Log.e("******","the Message before swapping --> "+prevent_stall);
       */
        sd.execSQL("insert into chat(message) values('" + prevent_stall + "');");
        note.clear();
        fetchchathead();
        render();
        scrollMyListViewToBottom();
       /* Log.e("******","Callign notifyDatasetChanged"); */


        /*Experimnetal rendeer*/
        Log.e("*************", "Experimental Render of teh display START");
        Log.e("*************", "Experimnetal Render of the display ENDS ");
        //sd.close();
    }

    private void fetchchathead() {

        index = 0;
        Cursor ch = sd.rawQuery("select * from chat", null);
        if (ch != null) {
            Log.e("**********", "Fucking enterd here");
            ch.moveToFirst();
            do {
                note.add(ch.getString(0));
                index += 1;
            } while (ch.moveToNext());
        } else {
            Log.e("**************", "Yeah  IT ENTERED AT LAST ");
            String response = "Oops is Looks like you are empty";
            calltts(response);
            insertchathead("---------*_____________");

               /* Intent save = new Intent(this, MainActivity.class);
                  */
        }
        //sd.close();
    }

    /*--------------END OF DATABASED ---------------------------------------------------------------*/

    private void clearchat() {
        sd.execSQL("delete from chat");
        insertchathead("this is the savior ! ");

        Log.e("*******", "Trying the experimental scrolling");
        scrollMyListViewToBottom();
        Intent save = new Intent(this, MainActivity.class);
        //sd.close();


    }

    /*Experminetal BETA FUnction Functioality: To scroll autmoatically to downside*/
     /*Testing for autoscroll to the end of the list*/
    private void scrollMyListViewToBottom() {
        listview.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                sa.notifyDataSetChanged();
                listview.setSelection(sa.getCount() - 1);
            }
        });
    }

    /*-----------------------------Render & Welocme Module HERE-----------------------------------*/
    void render() {
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        Log.e("8*********", "Enter to render");
        for (int i = 0; i < index; i += 2) {
            HashMap<String, String> h = new HashMap<>();


            h.put("name", note.get(i));
            if (i + 1 == index) {
                h.put("text2", "..." +
                        "");
            } else {
                h.put("text2", note.get(i + 1));
            }
            arrayList.add(h);
        }

        String[] from = {"name", "text2"};
        int[] to = {R.id.tv2, R.id.tv3};
        {

            sa = new SimpleAdapter(getApplicationContext(), arrayList, R.layout.chathead_design, from, to);
            listview.setAdapter(sa);
            Log.e("_____________>", "calling from within the hashmap");
            sa.notifyDataSetChanged();
            scrollMyListViewToBottom();
            //listview.setVisibility(View.INVISIBLE);

        }
    }

    private void firstresponse() {

        //Already Did in Oninit Method
        /*
        String response;
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(System.currentTimeMillis());
        int time = cal.get(Calendar.HOUR_OF_DAY);
        if (time < 12) {
            response = "Good Morning !";
        } else if (time < 17) {
            response = "Good Afternoon !";
        } else {
            response = "Good Evening";
        }

        Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
        Log.e(TAG, response);
*/
        tts = new TextToSpeech(this, this);
        tts.setLanguage(Locale.getDefault());
        tts.setSpeechRate(1.2f);
        tts.setPitch(1.1f);

        fetchchathead();
    }

    /*---------------------------------------------------------------------------------------------*/
    private void respondage() {
        String response = "You know they say never ask age from a Girl ! Didn't you know that";
        calltts(response);
    }

    private void respondhowyoudoing() {
        String response = "Life is Great";
        calltts(response);
        Toast.makeText(this, "It Feels Great to be Alive", Toast.LENGTH_SHORT).show();
    }

    private void respondniceidea() {

        String response = "Well ! It's a nice Idea ";
        calltts(response);
    }

    private void respondobliged() {

        String response = "I'm Obliged. Thank you For feeling That way.";
        calltts(response);
    }

    private void respondok() {
        String response = "Sure, Here You Go!";
        calltts(response);
    }

    private void respondmovie() {
        String response = "ummmm ......., We Can go for Movies , It will be so fun!";
        calltts(response);
    }

    private void respondhate() {
        String response = "ohhh , You Broke my Heart !";
        calltts(response);
    }

    private void respondhi() {
        String response = "Hey There ! , How are you doing  !";
        calltts(response);
    }


  /*--------------------------------OBSOLETE-------------------------------------------------------
    public String getPhoneNumber(Context context) {
        String ret = null;
        String selection = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " like'%" + "Dad" + "%'";
        String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER};
        Cursor c = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection, selection, null, null);
        if (c.moveToFirst()) {
            ret = c.getString(0);
            Toast.makeText(getApplicationContext(), ret, Toast.LENGTH_SHORT).show();
        }
        c.close();
        if (ret == null) {
            ret = "Unsaved";
        }
        return ret;
    }
    /**/

    /**/
    /*public void lookup() {
        Cursor contactLookupCursor =
                getContentResolver().query(
                        Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                                Uri.encode("Rohan")),
                        new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.PhoneLookup.NUMBER},
                        null,
                        null,
                        null);

        try {
            while (contactLookupCursor.moveToNext()) {
                contactName = contactLookupCursor.getString(contactLookupCursor.getColumnIndexOrThrow(ContactsContract.PhoneLookup.DISPLAY_NAME));
                contactNumber = contactLookupCursor.getString(contactLookupCursor.getColumnIndexOrThrow(ContactsContract.PhoneLookup.NUMBER));
                Toast.makeText(this, contactNumber, Toast.LENGTH_SHORT).show();
            }
        } finally {
            contactLookupCursor.close();
        }
    }
*/
  /*  private void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        firstresponse();
    }
*/

    private void respondname() {
        String response = "My name is Samsara . Is there anything you want to ask more ?";
        calltts(response);
    }

    /*---------------------------------------CONTACTS READ RUNTIME PERMISSION------------------------*/
    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
               /* Toast.makeText(this, "Contact read permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();*/
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", this.getPackageName(), null));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, 789);
                /*Toast.makeText(this, "returned false 1", Toast.LENGTH_SHORT).show();*/
                return false;
            } else {
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 123);
                /*Toast.makeText(this, "One More Step", Toast.LENGTH_SHORT).show();*/
                return false;
            }
        } else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
                /*Toast.makeText(this, "Contact read permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();*/
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", this.getPackageName(), null));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, 789);
                /*Toast.makeText(this, "returned false 1", Toast.LENGTH_SHORT).show();*/
                return false;
            } else {
/*
                Toast.makeText(this, "All set to rock", Toast.LENGTH_SHORT).show();
*/
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 123);
                return false;
            }
        }
        return true;
    }

    private void setalarm(int hour, int minute) {
        Intent i = new Intent(AlarmClock.ACTION_SET_ALARM);
        i.putExtra(AlarmClock.EXTRA_HOUR, hour);
        i.putExtra(AlarmClock.EXTRA_MINUTES, minute);
        i.putExtra(AlarmClock.EXTRA_MESSAGE, "Wake up Boss. Lets Rock !!! ");
        i.putExtra(AlarmClock.EXTRA_VIBRATE, false);
        i.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
        startActivity(i);
        Toast.makeText(this, "Alarm Set Boss  ....", Toast.LENGTH_SHORT).show();
        if (hour == 24 || hour > 24) {
            Toast.makeText(this, "It seems the Hours are Incorrect", Toast.LENGTH_SHORT).show();
        }
        if (minute > 60) {
            Toast.makeText(this, "Apparently Earth only considers 60 minutes !! ", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean setBluetooth(boolean enable) {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        boolean isEnabled = bluetoothAdapter.isEnabled();
        if (enable && !isEnabled) {
            return bluetoothAdapter.enable();
        } else if (!enable && isEnabled) {
            return bluetoothAdapter.disable();
        }
        // No need to change bluetooth state
        return true;
    }

    private void setTimer(int time) {
        Intent timer = new Intent(AlarmClock.ACTION_SET_TIMER);
        timer.putExtra(AlarmClock.EXTRA_LENGTH, time);
        timer.putExtra(AlarmClock.EXTRA_MESSAGE, "Times'up Boss");
        timer.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
        startActivity(timer);
        Toast.makeText(this, "Timer for " + time + " set Boss !! Brace yourself", Toast.LENGTH_SHORT).show();
    }
    /*----------------------------INTERMEDIATE FUNCTIONALITY FUNCTIONS HERE -----------------------*/

    /*-------------------------------------Runtime Permission For NoIDEA-----------------------------*/
    private boolean requestAudioPermissions() {
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
        return true;
    }


    private boolean requestCallPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            //When permission is not granted by user, show them message why this permission is needed.
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CALL_PHONE)) {
                Toast.makeText(this, "Please grant permissions to record audio", Toast.LENGTH_LONG).show();

                //Give user option to still opt-in the permissions
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        MY_PERMISSIONS_CALL);

            } else {
                // Show user dialog to grant permission to record audio
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        MY_PERMISSIONS_CALL);
            }
        }
        //If permission is granted, then go ahead recording audio
        else if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED) {

            //Go ahead with recording audio now

        }
        return true;
    }


    private boolean requestContactsRead() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            //When permission is not granted by user, show them message why this permission is needed.
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
                Toast.makeText(this, "Please grant permissions to record audio", Toast.LENGTH_LONG).show();

                //Give user option to still opt-in the permissions
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_READ_CONTACTS);

            } else {
                // Show user dialog to grant permission to record audio
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_READ_CONTACTS);
            }
        }
        //If permission is granted, then go ahead recording audio
        else if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED) {

            //Go ahead with recording audio now

        }
        return true;
    }


    /*-----------------------------------------Declaring Functions here Manual mode 2---------------------*/
    private void calltts(String response) {
        tts.setLanguage(Locale.ENGLISH);
        tts.speak(response, tts.QUEUE_FLUSH, null);
        insertchathead(response);
    }

    private void wipelogs() {

        //context.deleteDatabase("note");
        sd.execSQL("delete from chat");
        Toast.makeText(this, "Wiped !!!", Toast.LENGTH_SHORT).show();
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
        finish();

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    String dayreturn(int day_no) {
        switch (day_no) {
            case 2:
                day = "MONDAY";
                break;
            case 3:
                day = "TUE";
                break;
            case 4:
                day = "WEDNESDAY";
                break;
            case 5:
                day = "THURSDAY";
                break;
            case 6:
                day = "FRIDAY";
                break;
            case 7:
                day = "SATURDAY";
                break;
            case 1:
                day = "SUNDAY";
                break;
        }
        return day;
    }

    @Override
    protected void onDestroy() {
        if (tts != null)  //searches the tts libraray
        {
            tts.stop();
            tts.shutdown();
            Log.e(TAG, "TTS CLosed . Prevented LEaked COnnecitoin");
        }
        super.onDestroy();

    }


    public class MyGesture extends GestureDetector.SimpleOnGestureListener
        {
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if(e2.getY()>e1.getY())
                    {
                        fling.setText("Backwards");
                        Toast.makeText(MainActivity.this, "Hiding the chatlog", Toast.LENGTH_SHORT).show();
                        listview.setVisibility(View.INVISIBLE);
                    }
                else if(e2.getY()<e1.getY())
                    {//backward motion//
                        fling.setText("Forwards");
                        listview.setVisibility(VISIBLE);
                    }
                return true;
            }
        }


}




