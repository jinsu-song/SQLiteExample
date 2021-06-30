package com.example.sqliteexample;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btn1, btn2, btn3;
    private EditText edt1, edt2;
    private TextView tv1;

    String tableName;

    DatabaseHelper dbHelper;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // view id
        findViewByIdFunc();

        btn1.setOnClickListener((v)->{
            String databaseName = edt1.getText().toString().trim();
            if(databaseName.length() != 0){

                // 데이터 베이스 만들기
                createDatabase(databaseName);
            }else{
                Toast.makeText(this, "반드시 뭐라도 써야 합니다.", Toast.LENGTH_SHORT).show();
            }
        });

        btn2.setOnClickListener((v)->{
            tableName = edt2.getText().toString().trim();
            if(tableName.length() != 0){

                // 테이블 만들기
                createTable(tableName);

                 insertRecord();
            }
        });

        btn3.setOnClickListener((v)->{
            selectQuery();
        });

    }   // end of onCreate

    private void selectQuery() {
        Cursor cursor = database.rawQuery("select * from " + tableName, null);
        int recordCount = cursor.getCount();

        for(int i = 0 ; i < recordCount ; i++){
            cursor.moveToNext();
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String content = cursor.getString(2);
            String sentTime = cursor.getString(3);
            setTextView(title, content, sentTime);
        }
        cursor.close();
    }   // end of selectQuery

    public void setTextView(String title, String content, String sentTime){
        tv1.append(title +"\n" + content + "\n" + sentTime + "\n\n");
    }   // end of setTextView

    private void insertRecord() {
        try{
            database.execSQL("insert into " + tableName
                    + "(title, content, sentTime) "
                    + " values"
                    + "('hello','my name is blahblah','10:00')"
            );
        }catch (SQLException sqle){
            Toast.makeText(this, "insert error", Toast.LENGTH_SHORT).show();
            sqle.printStackTrace();
        }
    }   // end of insertRecord

    // 테이블 만들기
    private void createTable(String tableName) {
        if(database == null){
            toastMessage("데이터베이스를 먼저 생성하시오");
            return;
        }
        if(dbHelper != null){
            dbHelper.setTableName(tableName);
            return;
        }
        if(dbHelper.getTableName().length() != 0 || dbHelper.getTableName() != null){
            dbHelper.createTable();
        }


    }   // end of createTable

    private void toastMessage(String toast) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }   // end of toastMessage

    // 데이터베이스 만들기
    private void createDatabase(String databaseName) {
        dbHelper = new DatabaseHelper(this, databaseName);
        database = dbHelper.getWritableDatabase();
    }   // end of createDatabase

    private void findViewByIdFunc() {
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);

        edt1 = findViewById(R.id.edt1);
        edt2 = findViewById(R.id.edt2);

        tv1 = findViewById(R.id.tv1);
    }   // end of findViewByIdFunc
}