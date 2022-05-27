package com.example.shinobiwobble;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(new GameView(this));

        Log.d(TAG, "view added");
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "Destroying...");
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "Stopping...");
        super.onStop();
    }
}