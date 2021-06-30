package com.example.sqliteexample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static int VERSION = 1;
    private SQLiteDatabase db;
    private String tableName;

    public DatabaseHelper(Context context, String databaseName) {
        super(context, databaseName, null,VERSION);
    }

    public void setTableName(String tableName){
        this.tableName = tableName;
    }
    public String getTableName(){
        return this.tableName;
    }

    // table 생성
    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
    }

    public void createTable(){
        String sql = "create table if not exists " + tableName + "("
                + "id int primary key autoincrement, "
                + "title varchar(100), "
                + "content text, "
                + "sentTime varchar(30)"
                + ")";
        db.execSQL(sql);
    }   // end of createTable

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion > 1){
            db.execSQL("DROP TABLE IF EXISTS "+tableName);
        }
    }
}
