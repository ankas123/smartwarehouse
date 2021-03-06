package com.gaia.app.smartwarehouse.classes;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.Log;

import com.gaia.app.smartwarehouse.DetailActivity;
import com.gaia.app.smartwarehouse.ItemActivity;

import java.util.ArrayList;

import static com.gaia.app.smartwarehouse.R.layout.item;

/**
 * Created by praveen_gadi on 6/30/2016.
 */
public class ProductsData extends SQLiteOpenHelper {
      /*
    * SQLite is an open-source relational database
    * i.e. used to perform database operations on android devices such as storing, manipulating or retrieving persistent data from the database.

     * It is embedded in android bydefault.

     * SQLiteOpenHelper class provides the functionality to use the SQLite database.
    */

    private static final String DB_name = "appdatabase";
    private static final int DB_version=1;
    private static final String Category_Table_name = "categorynames";
    private static final String categorynames_Table_query = "CREATE TABLE categorynames(cname TEXT)";
    private Context context;

    private static final String ITEM_CATEGORY = "cname";
    private static final String ITEM_NAME = "iname";
    private static final String ITEM_UNIT = "unit";
    private static final String ITEM_WEIGHT = "weight";
    private static final String ITEM_QUANTITY = "quant";

    private static SQLiteDatabase readable_db, writable_db;

    public ProductsData(Context context) {
        //Context of an Activity is necessary
        super(context,DB_name,null,DB_version);
        this.context=context;
        Log.e("Database  ", "product database created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
         /*It is called only once when the Database is created. Tables which are compulsory have to be created here
        *
        * Here we are creating only one table for storing User details
        *
        * */
        db.execSQL(categorynames_Table_query);
        Log.e("Table ", "Product Table created");

    }



    public void create_category_table(Category category) {
        /*
        * This method is called  at the starting of app  when data is loaded from online server from MainActivity
        * In this method, Actually the data is updated.Tables are previously created and the data is updates in those tables
        *
        * */
        writable_db = this.getWritableDatabase();
        writable_db.execSQL("DROP TABLE IF EXISTS " + category.getCname());

        writable_db.delete(Category_Table_name, "cname = ?", new String[]{category.getCname().trim()});

        ContentValues cnames = new ContentValues();
        cnames.put(ITEM_CATEGORY, category.getCname());
        writable_db.insert(Category_Table_name, null, cnames);

        String create_category_table = "CREATE TABLE " + category.getCname().trim() + "(iname TEXT ,unit TEXT,weight TEXT,quant TEXT)";
        writable_db.execSQL(create_category_table);

        ArrayList<Item> items = category.getItems();

        for (int i = 0; i < items.size(); i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(ITEM_NAME, items.get(i).getIname());
            contentValues.put(ITEM_UNIT, items.get(i).getUnit());
            contentValues.put(ITEM_WEIGHT, items.get(i).getWeight());
            contentValues.put(ITEM_QUANTITY, items.get(i).getQuant());

            writable_db.insert(category.getCname(), null, contentValues);
        }
        Log.e("Offline data", "Offline data updated");
    }




    public Cursor getcategorydata() {
        /*
        * This method is used for retrieving the category details
        * It returns all the item names in the form of cursor object
        * */
        readable_db = this.getReadableDatabase();

        String[] projections = {ITEM_CATEGORY};
        Cursor cursor = readable_db.query(Category_Table_name, projections, null, null, null, null, null);

        Log.e("Offline data", "product Data Reading");
        return cursor;
    }



    public Cursor getitemsdata(String cname) {
          /*
        * This method is used for retrieving the item details
        * It returns all the item names in the form of cursor object
        * */
        readable_db = this.getReadableDatabase();
        String[] category_projections = {ITEM_NAME, ITEM_UNIT, ITEM_WEIGHT, ITEM_QUANTITY};
        Cursor cursor2 = readable_db.query(cname, category_projections, null, null, null, null, null);
        return cursor2;
    }




    public boolean search_result(String name) {
        /*
        * This method will be invoked, when the user enters in SearchActivity
        * name(String) is being searched in database
        * if it is a Category name then
        * */
        readable_db = this.getReadableDatabase();
        String[] item_projections = {ITEM_NAME, ITEM_UNIT, ITEM_WEIGHT, ITEM_QUANTITY, "id"};
        Cursor cursor = readable_db.query(Category_Table_name, new String[]{ITEM_CATEGORY}, "cname = ?", new String[]{name}, null, null, null);
        if (cursor.moveToFirst()) {
            Intent intent = new Intent(context, ItemActivity.class);
            intent.putExtra("Category", name);
            context.startActivity(intent);
            return true;
        } else {
            String[] projections = {ITEM_CATEGORY};
            Cursor cursor2 = readable_db.query(Category_Table_name, projections, null, null, null, null, null);
            if (cursor2.moveToFirst()) {

                do {
                    String cname = cursor2.getString(0);
                    Cursor cursor4 = readable_db.query(cname,item_projections, "iname = ?", new String[]{name}, null, null, null);
                    if (cursor4.moveToFirst()) {
                        String id, iname, unit, weight, quant;
                        iname = cursor4.getString(0);
                        unit = cursor4.getString(1);
                        weight = cursor4.getString(2);
                        quant = cursor4.getString(3);
                        id = cursor4.getString(4);
                        Item item = new Item(id, iname, cname, unit, weight, quant);
                        GetItemDetails itemdetails=new GetItemDetails();
                        itemdetails.setItem(item);
                        ActivityOptionsCompat activityOptionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,null);
                        Intent intent = new Intent(context,DetailActivity.class);
                        context.startActivity(intent,activityOptionsCompat.toBundle());
                        return true;
                    }
                } while (cursor2.moveToNext());

            }

        }
        return false;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
