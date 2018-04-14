package com.apkglobal.alice;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import static com.apkglobal.alice.R.id.notelistview;

public class ListDesign extends AppCompatActivity {
    ListView listView;
    SQLiteDatabase sd;
    Button btn_clear_notes;
    ArrayList<String> memo = new ArrayList<String>();
    int index = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_design);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Toast.makeText(this, "Welcome to Notes !", Toast.LENGTH_SHORT).show();
        listView = (ListView) findViewById(notelistview);
        btn_clear_notes = (Button) findViewById(R.id.btn_delete_notes);

        btn_clear_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteall();
            }
        });

        createdatabase();
        fetchdatabase();

        if (index == 0) {
            Toast.makeText(this, "Woohoo , You've done it all, LIst is empty !", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
                /*To rendeas r the display to the ListView */
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        for (int i = 0; i < index; i++) {
            HashMap<String, String> h = new HashMap<>();
            h.put("name", memo.get(i));
            h.put("text2", i + 1 + "");
            arrayList.add(h);
        }
        String[] from = {"name", "text2"};
        int[] to = {R.id.tv, R.id.tv2};
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

    private void deleteall() {
        sd.execSQL("delete from register");
        insertdatabase("Your Notes Here ! ");
        Intent refresh = new Intent(this, ListDesign.class);
        startActivity(refresh);
    }


    /*Notes down the note*/
    private void insertdatabase(String note) {
        sd.execSQL("insert into register(note) values('" + note + "');");
    }

    private void createdatabase() {
        sd = openOrCreateDatabase("note", Context.MODE_PRIVATE, null);
        sd.execSQL("create table if not exists register(note varchar(300));");
        String message = "Your Notes Here ! ";

        Cursor cursor = sd.rawQuery("select * from register where note='"+message+ "';", null);
        if (cursor.getCount() <= 0) {
            sd.execSQL("insert into register values('" + message + "');");
            cursor.close();
        }

    }


    private void fetchdatabase() {

        sd = openOrCreateDatabase("note", Context.MODE_PRIVATE, null);
        Cursor c = sd.rawQuery("select * from register", null);
        Log.e("********", "Checking the cursor factory");
        if (c != null) {
            c.moveToFirst();
            do {
                Log.e("************", "inserted into the memo List form the table reister/note");
                memo.add(c.getString(0));
                index += 1;
            } while (c.moveToNext());
            /*Data fed to the arraylist note*/
        }

        if (c == null) {
            String response = "Oops ! Looks like you are empty";
            Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
            Intent save = new Intent(this, MainActivity.class);
            startActivity(save);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


}
