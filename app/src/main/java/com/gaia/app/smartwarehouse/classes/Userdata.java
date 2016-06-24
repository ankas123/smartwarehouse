package com.gaia.app.smartwarehouse.classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by praveen_gadi on 6/17/2016.
 */
public class Userdata extends SQLiteOpenHelper {

    private static final String DB_name="appdatabase";
    private static final int DB_version=1;
    private static final String Table_name="userdata";
    private static final String Category_Table_name="categorynames";
    private static final String create_Table_query="CREATE TABLE userdata(email TEXT,pass TEXT,fname TEXT,lname TEXT,orgn TEXT,address TEXT,date TEXT)";
    private static final String categorynames_Table_query="CREATE TABLE categorynames(cname TEXT)";
    private static final String clear_Table_query="DELETE * FROM userdata";
    private static final String TAG_NAME = "email";
    private static final String TAG_PASS = "pass";
    private static final String TAG_FNAME = "fname";
    private static final String TAG_LNAME = "lname";
    private static final String TAG_ORGN = "orgn";
    private static final String TAG_ADDRESS = "address";
    private static final String TAG_DATE = "date";

    private  static final String ITEM_CATEGORY = "cname";
    private  static final String ITEM_NAME = "iname";
    private  static final String ITEM_UNIT = "unit";
    private  static final String ITEM_WEIGHT = "weight";
    private  static final String ITEM_QUANTITY = "quant";

    private static SQLiteDatabase readable_db,writable_db;





    public Userdata(Context context) {
        super(context,DB_name,null,DB_version);
        Log.e("Database  ","database created");
    }



    public Userdata(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
           db.execSQL(create_Table_query);
        db.execSQL(categorynames_Table_query);
        Log.e("Table ","Table created");
    }

    public void cleardata()
    {
        writable_db=this.getWritableDatabase();
        readable_db=this.getReadableDatabase();

          writable_db.delete(Table_name,null,null);

        String[] projections={ITEM_CATEGORY};
        Cursor cursor=readable_db.query(Category_Table_name,projections,null,null,null,null,null);


        if(cursor.moveToFirst())
        {
            do{
                    String cname=cursor.getString(0);
                writable_db.delete(cname,null,null);
            }while (cursor.moveToNext());

        }
        writable_db.delete(Category_Table_name,null,null);
        Log.e("Table ","Table cleared");
    }




    public void updateuserdata(String email,String password,String fname,String lname,String orgn,String address,String date)
    {
        writable_db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(TAG_NAME,email);
        contentValues.put(TAG_PASS,password);
        contentValues.put(TAG_FNAME,fname);
        contentValues.put(TAG_LNAME,lname);
        contentValues.put(TAG_ORGN,orgn);
        contentValues.put(TAG_ADDRESS,address);
        contentValues.put(TAG_DATE,date);
        writable_db.insert(Table_name,null,contentValues);

        Log.e("Update Table  ","Table updated");
    }




 public  Cursor getuserdata() {
    readable_db=this.getReadableDatabase();
    Cursor cursor;
    String[] projections = {TAG_NAME,TAG_FNAME,TAG_LNAME,TAG_ORGN,TAG_ADDRESS,TAG_DATE};
    cursor = readable_db.query(Table_name, projections, null, null, null, null, null);
    return cursor;
}



    public void create_category_table(Category category)
    {
        writable_db=this.getWritableDatabase();
        writable_db.execSQL("DROP TABLE IF EXISTS " + category.getCname());

        ContentValues cnames=new ContentValues();
        cnames.put(ITEM_CATEGORY,category.getCname());
        writable_db.insert(Category_Table_name,null,cnames);

        String create_category_table="CREATE TABLE "+ category.getCname().trim()+"(iname TEXT ,unit TEXT,weight TEXT,quant TEXT)";
        writable_db.execSQL(create_category_table);

        ArrayList<Item> items=category.getItems();

        for(int i=0;i<items.size();i++)
        {
            ContentValues contentValues=new ContentValues();
            contentValues.put(ITEM_NAME,items.get(i).getIname());
            contentValues.put(ITEM_UNIT,items.get(i).getUnit());
            contentValues.put(ITEM_WEIGHT,items.get(i).getWeight());
            contentValues.put(ITEM_QUANTITY,items.get(i).getQuant());

            writable_db.insert(category.getCname(),null,contentValues);
        }
       Log.e("Offline data","Offline data updated");
    }

    public Cursor getofflinedata()
    {
        readable_db=this.getReadableDatabase();

        String[] projections={ITEM_CATEGORY};
        Cursor cursor=readable_db.query(Category_Table_name,projections,null,null,null,null,null);

        Log.e("Offline data","Data Reading");
        return cursor;
    }

    public Cursor getitemsdata(String cname)
    {
        String[] category_projections={ITEM_NAME,ITEM_UNIT,ITEM_WEIGHT,ITEM_QUANTITY};
        Cursor cursor2=readable_db.query(cname,category_projections,null,null,null,null,null);
        return cursor2;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
