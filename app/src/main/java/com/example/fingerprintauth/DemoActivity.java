package com.example.fingerprintauth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

public class DemoActivity extends AppCompatActivity {

    Button buttonDisable;
    Switch switch1;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String SWITCH1 = "switch1";

    private boolean switchOnOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        buttonDisable = (Button)findViewById(R.id.btDisable);
        switch1 = (Switch)findViewById(R.id.sDisable);

        buttonDisable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        loadData();
        updateViews();
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(SWITCH1, switch1.isChecked());

        editor.apply();

        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        switchOnOff = sharedPreferences.getBoolean(SWITCH1, false);
    }

    public void updateViews() {
        switch1.setChecked(switchOnOff);
    }
}

