package com.example.devil.mvplayer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.devil.mvplayer.Model.SubCategoryGetter;

import java.util.ArrayList;
import java.util.List;




/**
 * Created by whizkraft on 9/11/17.
 */

public class MusicDataBase extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "download";
    public static final String KEY_ID = "id";
    public static final String ITEMID = "itemid";
    //public static final String TABLE_WISHLIST="wishlist_table";
    public static final String ITEMNAME = "itemname";
    public static final String ARTISTNAME = "artistname";
    public static final String FILEPATH = "filepath";
    public static final String IMAGE = "image";
    public static final String CONTENTTYPE = "contetnt_type";
    private static final String DATABASENAME = "Music";
    private static final int DATABASE_VERSION = 1;
    public String Create_Table = "CREATE TABLE " + TABLE_NAME + "( " + KEY_ID + " INTEGER  PRIMARY KEY AUTOINCREMENT , "
            + ITEMID + " TEXT ," + ITEMNAME + " TEXT ," + ARTISTNAME + " TEXT ,"
            + FILEPATH + " TEXT ," + CONTENTTYPE + " TEXT ," + IMAGE + " TEXT);";

/*
    public String Wislist_table = "CREATE TABLE "+TABLE_WISHLIST + "( "+KEY_ID + " INTEGER  PRIMARY KEY AUTOINCREMENT , "
            +BOOKID +" TEXT ," +BOOKTITILE +" TEXT ,"+AUTHORNAME +" TEXT ,"
            +BOOKPRICE +" TEXT ,"+BOOKLANGAUGE +" TEXT ,"+BOOKIMAGE +" TEXT);";
*/

    public MusicDataBase(Context context) {
        super(context, DATABASENAME, null, DATABASE_VERSION);


    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Create_Table);
        // sqLiteDatabase.execSQL(Wislist_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addtodownload(String itemid, String itemname, String artistname, String filepath, String image) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEMID, itemid);
        contentValues.put(ITEMNAME, itemname);
        contentValues.put(ARTISTNAME, artistname);
        contentValues.put(FILEPATH, filepath);

        contentValues.put(IMAGE, image);

        database.insert(MusicDataBase.TABLE_NAME, null, contentValues);
        database.close();
        return true;
    }


    public List<SubCategoryGetter> getDownloads() {
        //   List<SubCategoryGetter> cartlist = new ArrayList<>();
        List<SubCategoryGetter> list = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                list = new ArrayList<>();


                String itemid = cursor.getString(1);
                String name = (cursor.getString(2));
                String artistname = (cursor.getString(3));
                String filepath = (cursor.getString(4));
                String image = (cursor.getString(6));
                SubCategoryGetter subCategoryGetter = new SubCategoryGetter("",name,filepath,"",artistname,"",image,itemid);
                list.add(subCategoryGetter);



            } while (cursor.moveToNext());
        }


        // return contact list
        return list;
    }


    public void delete(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + "=?",
                new String[]{String.valueOf(id)}); // KEY_ID= id of row and third parameter is argument.
        db.close();
    }

}
