package com.gaia.app.smartwarehouse;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gaia.app.smartwarehouse.adapters.RecyclerRowAdapter;
import com.gaia.app.smartwarehouse.classes.BackgroundTask;
import com.gaia.app.smartwarehouse.classes.userdatabase;

/**
 * Created by praveen_gadi on 6/15/2016.
 */
public class SignupActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    EditText et1,et2,et3,et4;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordi);
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.content_signup, coordinatorLayout);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }



    public void signup(View view)
    {
        String ch1="",ch2="",ch3="",ch4="",ch="signup";
        et1=(EditText)findViewById(R.id.email);
        et2=(EditText)findViewById(R.id.editText_username);
        et3=(EditText)findViewById(R.id.editText_password);
        et4=(EditText)findViewById(R.id.reenter_password);
        ch1=et1.getText().toString().trim();
        ch2=et2.getText().toString().trim();
        ch3=et3.getText().toString().trim();
        ch4=et4.getText().toString().trim();
        int a=ch1.length(),b=ch2.length(),c=ch3.length(),d=ch4.length();
        if(a!=0 && b!=0 && c!=0 && d!=0) {
            if (ch3.equals(ch4)) {
                BackgroundTask httprequest = new BackgroundTask(this);
                httprequest.execute(ch, ch1, ch2, ch3);
                finish();
            } else {
                Toast.makeText(getBaseContext(), "Passwords are not matching", Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            if(a==0)
                Toast.makeText(getBaseContext(), "E-mail is Necessary", Toast.LENGTH_LONG).show();
            else if (b==0)
                Toast.makeText(getBaseContext(), "Username is Necessary", Toast.LENGTH_LONG).show();
            else if (c==0)
                Toast.makeText(getBaseContext(), "Password is Necessary", Toast.LENGTH_LONG).show();
            else if (d==0)
                Toast.makeText(getBaseContext(), "Password is Necessary", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
        if (id == R.id.notifications) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);



        int id = item.getItemId();

        if (id == R.id.detail) {
            Intent i = new Intent(getApplicationContext(), DetailActivity.class);
            startActivity(i);

        }
        else if (id == R.id.wishlist) {

        }else if (id == R.id.login) {

        } else if (id == R.id.notifications) {

        } else if (id == R.id.account_settings) {
            Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(i);

        }
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}