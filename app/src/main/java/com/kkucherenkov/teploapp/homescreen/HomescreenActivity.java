package com.kkucherenkov.teploapp.homescreen;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kkucherenkov.teploapp.R;

public class HomescreenActivity extends AppCompatActivity {

    private static final String TAG = HomescreenFragment.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(null);
        showHomescreenFragment();
    }

    private void showHomescreenFragment() {
        if (getSupportFragmentManager().findFragmentByTag(TAG) == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_root, getFragment(), TAG)
                    .commit();
        }
    }

    public Fragment getFragment() {
        return HomescreenFragment.newInstance();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
