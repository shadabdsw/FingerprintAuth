package com.example.fingerprintauth;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

@TargetApi(Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    private Context context;

    public FingerprintHandler(Context context) {
        this.context = context;
    }

    public void startAuth(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject) {
        CancellationSignal cancellationSignal = new CancellationSignal();
        fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        this.update("There was an auth error. " + errString, false);
    }

    @Override
    public void onAuthenticationFailed() {
        this.update("Auth Failed. ", false);
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        this.update("Error: " + helpString, false);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        this.update("Access Granted.", true);
    }

    private void update(String s, boolean b) {

        TextView textViewFingerprint = (TextView) ((Activity) context).findViewById(R.id.tvFingerprint);
        ImageView imageViewFingerprint = (ImageView) ((Activity) context).findViewById(R.id.ivFingerprint);

        textViewFingerprint.setText(s);

        if (b == false) {
            textViewFingerprint.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        } else {
            textViewFingerprint.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            imageViewFingerprint.setImageResource(R.mipmap.ic_done);

            Intent mIntent = new Intent(context, DemoActivity.class);
            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mIntent);
            ((Activity) context).finish();

        }
    }
}
