package com.url.msi.uixy;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SQLiteDatabase db;
    AssetsDatabaseManager mg;
    private RecyclerView recyclerView;
    List<String> list = new ArrayList<String>();
    private ListAdapter listAdapters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // 获取管理对象，因为数据库需要通过管理对象才能够获取
        mg = AssetsDatabaseManager.getManager();
        // 通过管理对象获取数据库
        db = mg.getDatabase("identifier.db");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start(bmqmActivity.class, getName());
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        readDb(getName());
        recyc();
        initTH();
    }



    public void onResume() {
        list.clear();
        super.onResume();
        readDb(getName());
        listAdapters.notifyDataSetChanged();
    }


    private void initTH()
    {

        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(listAdapters);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
    }



    private void recyc()
    {
        recyclerView = findViewById(R.id.recycleview);
        listAdapters = new ListAdapter(list);
        recyclerView.setAdapter(listAdapters);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private String getName()
    {
        String user = null;
        Intent intent = getIntent();
        if (intent != null)
        {
            user = intent.getStringExtra("user");
            Log.d("user", user);
        }
        return user;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void start(Class cls, String user) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, cls);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    private void readDb(String user)
    {
        Cursor cursor = db.rawQuery("select * from file where user = '" +  user + "'", null);
        int sum = cursor.getCount();
        while (cursor.moveToNext())
        {
            String name = cursor.getString(1);
            long filename = cursor.getLong(2);
            String context = cursor.getString(3);
            Log.d("file", String.valueOf(filename));
            list.add(context);
        }
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
