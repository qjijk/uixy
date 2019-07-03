package com.url.msi.uixy;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class bmqmActivity extends AppCompatActivity {
    private EditText today;
    private Button commit;
    private String user = null;
    SQLiteDatabase db;
    AssetsDatabaseManager mg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmqm);
        init();
    }

    public void init()
    {
        mg = AssetsDatabaseManager.getManager();
        // 通过管理对象获取数据库
        db = mg.getDatabase("identifier.db");
        Intent intent = getIntent();
        if (intent != null)
        {
            user = intent.getStringExtra("user");
        }
        today = findViewById(R.id.today);
        commit = findViewById(R.id.commit);
        commit();
    }



    public void commit()
    {
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cont = today.getText().toString();
                if (cont.isEmpty())
                {
                    Toast.makeText(bmqmActivity.this,"内容为空",Toast.LENGTH_LONG).show();
                }
                else {
                    save(cont);
                    bmqmActivity.this.finish();
                }
            }

        });
    }

    public void start(Class cls, String user) {
        Intent intent = new Intent();
        intent.setClass(bmqmActivity.this, cls);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    private void save(String filecontent)
    {
        try
        {
            long time = System.currentTimeMillis()/1000;
            String stime = String.format("%010d",time) ;
            System.out.println(filecontent);
            FileOutputStream outputStream = openFileOutput(stime, Context.MODE_PRIVATE);
            outputStream.write(filecontent.getBytes());
            outputStream.close();
            svaeDb(time, user, filecontent);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void svaeDb(long time, String user, String cont)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("user",user);
        contentValues.put("file",time);
        contentValues.put("context",cont);
        db.replace("file",null,contentValues);
    }



}
