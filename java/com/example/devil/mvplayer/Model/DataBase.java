package com.example.devil.mvplayer.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.TransformerConfigurationException;

/**
 * Created by Devil on 3/21/2018.
 */
public class DataBase extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="Categorylist";

    private static final String TABLE_NAME="category";

    private static final String TC_NAME="name";

    private static final String TC_IMAGE ="image";


    public DataBase(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_FREIND_TABLE="CREATE TABLE" + TABLE_NAME+"(" +TC_NAME +"TEXT,"+ TC_IMAGE +"TEXT"+")";
        db.execSQL(CREATE_FREIND_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public Boolean insertvalue(String name,String image){
        boolean flag=false;
        SQLiteDatabase db= this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(TC_IMAGE, image);
            values.put(TC_NAME, name);
            long row = db.insert(DataBase.TABLE_NAME, null, values);
            if (row > 0) {
                flag = true;

            }
        }catch(Exception ex){
            Log.e("Insert error",ex.toString());
        }
        return flag;

    }
    public List<Getter> getAllFriends(){
        List<Getter> friendList=new ArrayList<>();
        String selectQuery ="SELECT * FROM "+ TABLE_NAME;
        SQLiteDatabase db =this.getWritableDatabase();

        Cursor cursor =db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()) {
            do {
                Getter friend = new Getter(cursor.getString(0), cursor.getString(1));
            friendList.add(friend);
        }
            while(cursor.moveToNext());
        }return  friendList;
    }
}
