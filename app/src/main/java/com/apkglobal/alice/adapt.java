package com.apkglobal.alice;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import static com.apkglobal.alice.R.id.listview;

public class adapt extends AppCompatActivity {


    ListView listView;
    SQLiteDatabase sd;
    Button btn_clear_adapt;
    ArrayList<String> note = new ArrayList<String>();
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adaptive__learn);

        btn_clear_adapt=(Button)findViewById(R.id.btn_clear_adapt);
        btn_clear_adapt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sd.execSQL("delete from adapt");
                Toast.makeText(adapt.this, "Cleared !", Toast.LENGTH_SHORT).show();
                insertadapt("Questions ", "Desired Responses");
            }
        });

                getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                listView = (ListView)findViewById(listview);


                createdatabase();
                fetchadapt();

                if (index == 0) {
                    Toast.makeText(this, "Woohoo , You've done it all, LIst is empty !", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
                /*To rendeas r the display to the ListView */
                ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
                int safe;
                for (int i = 0; i < index; i+=2) {
                    HashMap<String, String> h = new HashMap<>();
                    h.put("name", note.get(i));
          /*  if(i+1==index)
            {safe=i;}
            else
            {safe=i+1;}*/
                    h.put("text2", note.get(i+1) + "");
                    arrayList.add(h);
                }
                String[] from = {"name", "text2"};
                int[] to = {R.id.tv2, R.id.tv};
                {
                    if (index == 0) {
                        Toast.makeText(this, "Woohoo , You've done it all, LIst is empty !", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                    }
                    SimpleAdapter sa = new SimpleAdapter(getApplicationContext(), arrayList, R.layout.listdesign, from, to);
                    listView.setAdapter(sa);
                }
            }

            /*Notes down the note*/
            private void insertdatabase(String note) {
                sd.execSQL("insert into register(note) values('" + note + "');");
            }

            private void createdatabase() {
                sd = openOrCreateDatabase("note", Context.MODE_PRIVATE, null);
                sd.execSQL("create table if not exists adapt(que varchar(300),ans varchar(500));");
            }

            private void fetchadapt() {
                sd = openOrCreateDatabase("note", Context.MODE_PRIVATE, null);
                Cursor c = sd.rawQuery("select * from adapt", null);
                if (c != null) {
                    c.moveToFirst();
                    do {
                        note.add(c.getString(0));
                        note.add(c.getString(1));
                        index += 2;
              /*  if( interact.equalsIgnoreCase(c.getString(0) ) )
                {
                    tv_speak.setText(c.getString(1));
                    calltts(c.getString(1));
                    flipper=1;
                }*/
                    } while (c.moveToNext());

                    if (c == null) {
                        String response = "Oops ! Looks like you are empty";
                        Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
                        Intent save = new Intent(this, MainActivity.class);
                        startActivity(save);
                    }
                }
            }

            @Override
            public boolean onSupportNavigateUp() {
                finish();
                return true;
            }
    /*Stores down the user's Response */
    private void insertadapt(String que, String ans) {
        sd.execSQL("insert into adapt(que,ans) values('" + que + "','" + ans + "');");
    }

}
