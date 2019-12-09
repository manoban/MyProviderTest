package com.example.providertest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button addData,queryData,updateData,deleteData;
    static final private String TAG="MainActivity";
    private String newId;
    private Cursor cursor;
    private TextView textView;
    private ArrayList<String> r = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://com.example.book.mycontentprovider/wordlist");
                ContentValues values = new ContentValues();
                values.put("word","pear");
                values.put("meaning","梨");
                values.put("sample","Eating pears is healthy");
                Uri newUri = getContentResolver().insert(uri,values);
                newId = newUri.getPathSegments().get(1);
                Toast.makeText(MainActivity.this,"success",Toast.LENGTH_SHORT).show();
            }
        });
        queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r.clear();
                Uri uri = Uri.parse("content://com.example.book.mycontentprovider/wordlist");
                cursor = getContentResolver().query(uri,null,null,null,null);
                if(cursor != null){
                    while (cursor.moveToNext()){
                        String word = cursor.getString(cursor.getColumnIndex("word"));
                        String meaning = cursor.getString(cursor.getColumnIndex("meaning"));
                        String sample = cursor.getString(cursor.getColumnIndex("sample"));
                        String value = "word:"+word+"\n"+"  meaning:"+meaning+"\n"+"sample:"+sample+"\n";
                        Log.d(TAG,"word is:"+word);
                        Log.d(TAG,"meaning is:"+meaning);
                        Log.d(TAG,"sample is:"+sample);
                        r.add(value);
                    }
                }
                String tv = r.toString();
                textView.setText(tv);
                cursor.close();
            }
        });
        updateData.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Uri uri = Uri.parse("content://com.example.book.mycontentprovider/wordlist/"+newId);
                ContentValues values = new ContentValues();
                values.put("word","pear");
                values.put("meaning","梨");
                values.put("sample","Eating pears is healthy");
                getContentResolver().update(uri,values,null,null);
                Toast.makeText(MainActivity.this,"success",Toast.LENGTH_SHORT).show();
            }
        });
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://com.example.book.mycontentprovider/wordlist/"+newId);
                getContentResolver().delete(uri,null,null);
                Toast.makeText(MainActivity.this,"success",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initView(){
        addData = (Button)findViewById(R.id.add_data);
        queryData = (Button)findViewById(R.id.query_data);
        updateData = (Button)findViewById(R.id.updata_data);
        deleteData = (Button)findViewById(R.id.delete_data);
        textView = (TextView)findViewById(R.id.textview);
    }
}
