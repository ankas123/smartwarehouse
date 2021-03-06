package com.gaia.app.smartwarehouse.resources;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gaia.app.smartwarehouse.LoginActivity;
import com.gaia.app.smartwarehouse.MainActivity;
import com.gaia.app.smartwarehouse.classes.Userdata;

public class Splashscreen extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Progress().execute();
    }

    class Progress extends AsyncTask<Void,Void,Void>{
        Userdata details =new Userdata(Splashscreen.this);
        Intent intent;




        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            startActivity(intent);

            finish();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Cursor cursor=details.getuserdata();
            if(cursor.moveToFirst())
            {
                intent= new Intent(Splashscreen.this, MainActivity.class);

            }
            else
            {
                 intent = new Intent(Splashscreen.this, LoginActivity.class);

            }
            return null;


        }
    }

}
