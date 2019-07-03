package com.url.msi.uixy;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ChangeActivity extends AppCompatActivity {
    private EditText today;
    private Button commit;
    private String user = null;
    private String conts =null;
    SQLiteDatabase db;
    AssetsDatabaseManager mg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmqm);

        init();
        butt();
    }

    public void init()
    {
        mg = AssetsDatabaseManager.getManager();
        // 通过管理对象获取数据库
        db = mg.getDatabase("identifier.db");
        Intent intent = getIntent();
        today = findViewById(R.id.today);
        commit = findViewById(R.id.commit);
        if (intent != null)
        {
            user = intent.getStringExtra("user");
            conts = intent.getStringExtra("cont");
            today.setText(conts);
        }


    }

    private void butt()
    {
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = today.getText().toString();
                ChangeDb(s);
                ChangeActivity.this.finish();
            }
        });
    }

    private void ChangeDb(String cont)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put("context",cont);
        String where = "context=?";
        String[] whereAge = {conts};
        db.update("file",contentValues,where,whereAge);
    }

}
