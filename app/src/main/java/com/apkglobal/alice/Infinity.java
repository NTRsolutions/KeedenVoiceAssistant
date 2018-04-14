package com.apkglobal.alice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;


public class Infinity extends AppCompatActivity {

    Button btn_start, btn_stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infinity);

        Button btn_beta=(Button)findViewById(R.id.btn_beta);
        btn_start = (Button) findViewById(R.id.btn_start);
        btn_stop = (Button) findViewById(R.id.btn_stop);

        btn_beta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Infinity.this, "Might Need to Restart Phone if app Crashes", Toast.LENGTH_LONG).show();
            }
        });

        btn_beta.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(Infinity.this, "Might need to Restart Phone if App Crashes ", Toast.LENGTH_LONG).show();
                return false;

            }
        });

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Infinity.this, "Starting Service", Toast.LENGTH_SHORT).show();
                startService(new Intent(getApplicationContext(), MyService.class));
            }
        });

        btn_stop=(Button)findViewById(R.id.btn_stop);
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Infinity.this, "Aborting Service", Toast.LENGTH_SHORT).show();
                stopService(new Intent(getApplicationContext(), MyService.class));
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    }

        /*startService(new Intent(this,Service.class));
        btn_startService=(ToggleButton)findViewById(R.id.btn_startService);
*/
        /*btn_startService.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isclicked) {
                if(isclicked)
                {
                    Toast.makeText(Infinity.this, "Starting Service", Toast.LENGTH_SHORT).show();
                    startService(new Intent(getApplicationContext(),MyService.class));
                    btn_startService.setText("STOP SERVICE");
                }
                else
                {
                    Toast.makeText(Infinity.this, "ABORTING SERVICE", Toast.LENGTH_SHORT).show();
                    stopService(new Intent(getApplicationContext(),MyService.class));
                    btn_startService.setText("START SERVICE");
                }
            }
        });

    }
}
*/
