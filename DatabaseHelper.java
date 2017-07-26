package com.example.sudarsan.graphfinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * creating a table with following details
 */
public class DatabaseHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "Student.db";
    public static final String TABLE_NAME = "student_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "TOTAL";
    public static final String COL_4 = "SPENT";
    public static final String COL_5 = "DATE";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,TOTAL INTEGER,SPENT INTEGER,DATE INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
/*inserting data in to our columns*/
    public boolean insertData(String name,String total,String spent,String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,total);
        contentValues.put(COL_4,spent);
        contentValues.put(COL_5,date);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }
/*reviewing all data*/
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res=db.rawQuery("select NAME,TOTAL,SPENT,DATE from"+TABLE_NAME,null);
        return res;
    }
    public int count(){
        SQLiteDatabase db=this.getReadableDatabase();
        int i=0;
        Cursor count=db.rawQuery("select count (*) from"+TABLE_NAME,null);
        i=count.getCount();
        return i;
    }

    public int xaxis() {
        int i=0;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor xaxis=db.rawQuery("select DATE from"+TABLE_NAME+"where ID="+i,null);
        if(xaxis!=null&&xaxis.getCount()!=0)
            xaxis.moveToNext();

        return i;
    }

    public int yaxis() {
        int i=0;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor yaxis=db.rawQuery("select SPENT from "+TABLE_NAME+"where ID="+i,null);
        if(yaxis!=null && yaxis.getCount()!=0)
            yaxis.moveToNext();

        return i;
    }
}
