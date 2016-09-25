package com.kkucherenkov.teploapp.homescreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.kkucherenkov.teploapp.R;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;

public class HomescreenActivity extends AppCompatActivity {

    private static final String TAG = HomescreenFragment.class.getSimpleName();
    public static final String MOCKED_SCANNER_KEY = "use_mocked_scanner";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(null);
        showHomescreenFragment();

        checkForUpdates();
    }

    private void showHomescreenFragment() {
        if (getSupportFragmentManager().findFragmentByTag(TAG) == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_root, getFragment(), TAG)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        SharedPreferences settings = getSharedPreferences("settings", 0);
        boolean isChecked = settings.getBoolean(MOCKED_SCANNER_KEY, false);
        MenuItem item = menu.findItem(R.id.action_check);
        item.setChecked(isChecked);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_check) {
            item.setChecked(!item.isChecked());
            SharedPreferences settings = getSharedPreferences("settings", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean(MOCKED_SCANNER_KEY, item.isChecked());
            editor.apply();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public Fragment getFragment() {
        return HomescreenFragment.newInstance();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();
        checkForCrashes();
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterManagers();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterManagers();
    }

    private void checkForCrashes() {
        CrashManager.register(this);
    }

    private void checkForUpdates() {
        // Remove this for store builds!
        UpdateManager.register(this);
    }

    private void unregisterManagers() {
        UpdateManager.unregister();
    }
}
