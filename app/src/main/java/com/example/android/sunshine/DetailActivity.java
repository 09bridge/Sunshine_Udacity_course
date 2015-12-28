package com.example.android.sunshine;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.ShareActionProvider;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
       /* MenuItem menuItem = menu.findItem(R.id.menu_action_shared);
        mShareActionProvider = (ShareActionProvider) menuItem.getActionProvider();*/
        return true;
    }

    /*private void updateShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);

        }
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class DetailFragment extends Fragment {
        private static final String LOG_TAG = DetailFragment.class.getSimpleName();
        private static final String FORECAST_SHARE_HASHTAG = " #SunshineApp";
        private String mForecastString;

        public DetailFragment() {
            setHasOptionsMenu(true);
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            // Inflate the menu; this adds items to the action bar if it is present.
            inflater.inflate(R.menu.detailfragment, menu);
            MenuItem menuItem = menu.findItem(R.id.menu_action_shared);
            ShareActionProvider mShareActionProvider =
                    (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
            if (mShareActionProvider != null) {
                mShareActionProvider.setShareIntent(createShareIntent());

            } else {
                Log.d(LOG_TAG, "ShareActionProvider is null((");
            }

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            Intent intent = getActivity().getIntent();
            if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
                mForecastString = intent.getStringExtra(Intent.EXTRA_TEXT);
                TextView forecastMessage = (TextView) rootView.findViewById(R.id.forecastMessage);
                forecastMessage.setText(mForecastString);
            }
            return rootView;
        }

        private Intent createShareIntent () {
            Intent sendIntent = new Intent();
            sendIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, mForecastString + FORECAST_SHARE_HASHTAG);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
            return sendIntent;
        }

    }
}
