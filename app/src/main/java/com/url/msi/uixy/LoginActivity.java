package com.url.msi.uixy;

import android.app.ListActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

public class LoginActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private AssetsDatabaseManager mg;
    private EditText username, password;
    private Button login, regise;
    private String loginurl = "http://120.77.83.151:8080/springboot-login-1.0-SNAPSHOT/user/login";
    private String registUrl ="http://120.77.83.151:8080/springboot-login-1.0-SNAPSHOT/user/regist";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AssetsDatabaseManager.initManager(getApplication());

        init();
    }


    private void init()
    {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        regise = findViewById(R.id.regist);
        login();
        regise();
    }

    private void Jsons(String user, String json)
    {
        JSONObject jsonObject = JSONObject.parseObject(json);
        String success = jsonObject.getString("success");
        Log.i("success", success);
        if(success.equals("true"))
        {
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            intent.putExtra("user",user);
            startActivity(intent);
            this.finish();
        }
        else if (success.equals("false"))
        {
            Toast.makeText(LoginActivity.this,"用户名或密码错误",Toast.LENGTH_LONG).show();
            Log.i("aa","测试");
        }
    }

    public void regise()
    {
        regise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                okGo(registUrl);
            }
        });
    }

    private void login()
    {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                okGo(loginurl);
            }
        });
    }

    private void okGo(String Url)
    {
        final String user = String.valueOf(username.getText());
        String pass = String.valueOf(password.getText());
        OkGo.<String>post(Url)
                .tag(this)
                .params("username",user)
                .params("password",pass)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.d("Login",response.body());
                        String json = response.body();
                        Jsons(user, json);
                        Log.i("inf", json);
                    }

                });
    }
}
