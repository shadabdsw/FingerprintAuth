package com.example.fingerprintauth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textViewFingerprint;
    ImageView imageViewFingerprint;
    FingerprintManager fingerprintManager;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String SWITCH1 = "switch1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewFingerprint = (TextView) findViewById(R.id.tvFingerprint);
        imageViewFingerprint = (ImageView) findViewById(R.id.ivFingerprint);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);

            if (!fingerprintManager.isHardwareDetected()) {

                textViewFingerprint.setText("Fingerprint Scanner not detected in device.");

            } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {

                textViewFingerprint.setText("Permission not granted to use Fingerprint Scanner.");

            } else if (!fingerprintManager.hasEnrolledFingerprints()) {

                textViewFingerprint.setText("Atleast 1 fingerprint must be registered on your device.");

            } else {

                textViewFingerprint.setText("Place your finger on the scanner to authenticate.");

                FingerprintHandler fingerprintHandler = new FingerprintHandler(this);
                fingerprintHandler.startAuth(fingerprintManager, null);

                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                switchOnOff = sharedPreferences.getBoolean(SWITCH1, false);

                if(switchOnOff.isChecked()){
                    startActivity(new Intent(MainActivity.this, DemoActivity.class));
                }

            }

        }

    }
}
