package com.fancytank.gamegen;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private String[] mRandomList = {"derp", "herp"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
       // AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

        FrameLayout contentFrame = (FrameLayout) findViewById(R.id.content_frame);
        View gdxView = initializeForView(new AndroidGameGenerator());
        contentFrame.addView(gdxView);

        setUpDrawer();
    }

    private void setUpDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_activity);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerList = (ListView) findViewById(R.id.drawer_list);

        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mRandomList));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.e("LOG", "selected option " + item.getItemId());
        return true;
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        Log.e("LOG", "selected item!");
        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        //  mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        //   mDrawerToggle.onConfigurationChanged(newConfig);
    }


}
