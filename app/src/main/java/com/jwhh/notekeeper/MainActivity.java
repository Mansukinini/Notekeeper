package com.jwhh.notekeeper;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.jwhh.notekeeper.ui.home.HomeFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity
            implements NavigationView.OnNavigationItemSelectedListener{
    private NoteRecyclerAdaptar mNoteRecyclerAdaptar;
    private AppBarConfiguration mAppBarConfiguration;
    private RecyclerView mrecyclerNotes;
    private LinearLayoutManager mNotesLayoutManager;
    private RecyclerView mRecyclerItems;
    private CourseRecyclerAdaptar mCourseRecycleAdptar;
    private GridLayoutManager mCourseLayoutManager;
    private NoteKeeperOpenHelper mDbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        mDbOpenHelper = new NoteKeeperOpenHelper(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, NoteActivity.class));
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_notes, R.id.list_items)
////                R.id.nav_notes, R.id.nav_courses, R.id.nav_slideshow)
//                .setDrawerLayout(drawer)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
        //

//        initializeDisplayContent();
    }

    @Override
    protected void onDestroy() {
        mDbOpenHelper.close();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mNoteRecyclerAdaptar.notifyDataSetChanged();
    }

    private void initializeDisplayContent() {
        mRecyclerItems = (RecyclerView)findViewById(R.id.list_items);
        mNotesLayoutManager = new LinearLayoutManager(this);
        mCourseLayoutManager = new GridLayoutManager(this, getResources().getInteger(R.integer.course_grid_span));

        List<NoteInfo> notes = DataManager.getInstance().getNotes();
        mNoteRecyclerAdaptar = new NoteRecyclerAdaptar(this, notes);

        List<CourseInfo> courses = DataManager.getInstance().getCourses();
        mCourseRecycleAdptar = new CourseRecyclerAdaptar(this, courses);
        displayCourses();
    }

//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
//        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
//            drawerLayout.closeDrawer(GravityCompat.START);
//        }else {
//            super.onBackPressed();
//        }
//    }

    private void displayCourses() {
        mRecyclerItems.setLayoutManager(mCourseLayoutManager);
        mRecyclerItems.setAdapter(mCourseRecycleAdptar);
        selectNavigationMenuItem(R.id.nav_courses);
    }

    private void displayNotes() {
//        mrecyclerNotes.setLayoutManager(mNotesLayoutManager);
//        mrecyclerNotes.setAdapter(mNoteRecyclerAdaptar);
//        HomeFragment homeFragment = new HomeFragment();
//        homeFragment.displayNotes();
        SQLiteDatabase db = mDbOpenHelper.getReadableDatabase();
        selectNavigationMenuItem(R.id.nav_notes);
    }

    private void selectNavigationMenuItem(int id) {
        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        menu.findItem(id).setChecked(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings){
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_notes) {
            displayNotes();
        }else if (id == R.id.nav_courses) {
            displayCourses();
        }

        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void handleSelection(String message) {
        View view = findViewById(R.id.list_items);
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }
}
