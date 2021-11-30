package com.codekiller.bankingsystem.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDataBase extends SQLiteOpenHelper {
    public static final String TAG = "DATABASE";

    public static final String DBNAME = "BANKING";
    public static final String TABLE_NAME = "usertable";
    public static final String NAME = "name";
    public static final String AGE = "age";
    public static final String PHNO = "ph_no";
    public static final String INITAMT = "initial_amount";
    public static final String MAILID = "mail_id";
    public static final String ACC_NO = "account_number";
    public MyDataBase(Context context){
        super(context, DBNAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String table = "CREATE TABLE " + TABLE_NAME+
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NAME+" TEXT," +
                    AGE+" TEXT," +
                    PHNO+" TEXT," +
                    INITAMT+" TEXT," +
                    MAILID+" TEXT," +
                    ACC_NO+" TEXT"+
                ")";
        db.execSQL(table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    }

    public boolean add(String name, String age, String phno, String initamt, String mailid, String acc_no){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(NAME, name);
        c.put(AGE, age);
        c.put(PHNO, phno);
        c.put(INITAMT, initamt);
        c.put(MAILID, mailid);
        c.put(ACC_NO, acc_no);
        try{
            db.insert(TABLE_NAME, null, c);
        }catch (Exception e){
            Log.d(TAG, "add: "+e);
            return false;
        }
        return true;
    }

    public boolean updateAcc(String name, String age, String phno, String initamt, String mailid, String acc_no){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE "+TABLE_NAME+" SET "+NAME+"='"+name+"',"+AGE+"='"+age+"',"+PHNO+"='"+phno+"',"+INITAMT+"='"+initamt+"',"+MAILID+"='"+mailid+"' WHERE "+ACC_NO+"="+acc_no;
        try{
            db.execSQL(query);
        }catch (Exception e){
            Log.d(TAG, "updateAcc: "+e);
            return false;
        }
        return true;
    }

    public Cursor getDetails(String accno){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT *FROM "+TABLE_NAME+" WHERE "+ACC_NO+"="+accno, null);
        c.moveToFirst();
        return c;
    }

    public String getAmount(String accno){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT *FROM "+TABLE_NAME+" WHERE "+ACC_NO+"="+accno, null);
        c.moveToFirst();
        return c.getString(c.getColumnIndex(INITAMT));
    }

    public boolean putAmount(String accno, String amount){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE "+TABLE_NAME+" SET "+INITAMT+"="+amount+" WHERE "+ACC_NO+"="+accno;
        try{
            db.execSQL(query);
        }catch (Exception e){
            return false;
        }
        return true;
    }
}
