package com.example.cdsteer.memoapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

/**
 * Created by cdsteer on 22/03/15.
 */
public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "memo_directory";
    public static final String DATABASE_TABLE = "memo";

    public static final String KEY_ID = "_id";
    public static final String KEY_CONTENT = "content";
    public static final String KEY_URGENT = "urgentLvl";
    public static final String KEY_DATE = "date";
    public static final String KEY_ALARM = "alarm";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS memo (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "content TEXT, " +
                "urgentLvl TEXT, " +
                "date TEXT, " +
                "alarm TEXT)";
        db.execSQL(sql);
        ContentValues values = new ContentValues();
        values.put("content", "Welcome to memos");
        values.put("urgentLvl", "Normal");
        values.put("date", "12-12-1984");
        values.put("alarm", "12:00");
        db.insert("memo", "content", values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS memo");
        onCreate(db);
    }

    public static Cursor getMemos(){
        Cursor c = MemoList.db.query(DbHelper.DATABASE_TABLE,
                new String[]{DbHelper.KEY_ID, DbHelper.KEY_CONTENT, DbHelper.KEY_URGENT, DbHelper.KEY_DATE, DbHelper.KEY_ALARM},
                null, null, null, null, null);
        return c;
    }

    public static void addMemo(String memo, String priority, String date, String time){
        ContentValues values = new ContentValues();
        values.put("content", memo);
        values.put("urgentLvl", priority);
        values.put("date", date);
        values.put("alarm", time);
        MemoList.db.insert("memo", "content", values);
    }

    public static void updateMemo(int id, String content, String priority, String date, String alarm){
        Integer.toString(id);
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.KEY_CONTENT, content); //These Fields should be your String values of actual column names
        cv.put(DbHelper.KEY_URGENT,priority);
        cv.put(DbHelper.KEY_DATE,date);
        cv.put(DbHelper.KEY_ALARM, alarm);
        MemoList.db.update(DbHelper.DATABASE_TABLE, cv, (DbHelper.KEY_ID + "=" + id), null);
    }

    public static void deleteMemo(int id){
        Integer.toString(id);
        MemoList.db.delete(DbHelper.DATABASE_TABLE, DbHelper.KEY_ID + "=" + id, null);
    }


    public static String getMemoContent(int id){
        Integer.toString(id);
        String content = "";
        Cursor cursor = MemoList.db.rawQuery("SELECT * FROM "+ DbHelper.DATABASE_TABLE +" WHERE " +DbHelper.KEY_ID+ "="+id, null);
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            content = cursor.getString(cursor.getColumnIndex(DbHelper.KEY_CONTENT));
        }
        return content;
    }

    public static String getMemoPriority(int id){
        Integer.toString(id);
        String content = "";
        Cursor cursor = MemoList.db.rawQuery("SELECT * FROM "+ DbHelper.DATABASE_TABLE +" WHERE " +DbHelper.KEY_ID+ "="+id, null);
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            content = cursor.getString(cursor.getColumnIndex(DbHelper.KEY_URGENT));
        }
        return content;
    }

    public static String getMemoDate(int id){
        Integer.toString(id);
        String content = "";
        Cursor cursor = MemoList.db.rawQuery("SELECT * FROM "+ DbHelper.DATABASE_TABLE +" WHERE " +DbHelper.KEY_ID+ "="+id, null);
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            content = cursor.getString(cursor.getColumnIndex(DbHelper.KEY_DATE));
        }
        return content;
    }

    public static String getMemoTime(int id){
        Integer.toString(id);
        String content = "";
        Cursor cursor = MemoList.db.rawQuery("SELECT * FROM "+ DbHelper.DATABASE_TABLE +" WHERE " +DbHelper.KEY_ID+ "="+id, null);
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            content = cursor.getString(cursor.getColumnIndex(DbHelper.KEY_ALARM));
        }
        return content;
    }




}
